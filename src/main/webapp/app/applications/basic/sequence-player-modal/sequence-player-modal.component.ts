import { Component, OnInit, OnDestroy } from '@angular/core';
import { NGXLogger } from 'ngx-logger';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { LeloF1SDKService } from 'app/core/lelo-f1-sdk/lelo-f1-sdk.service';
import { SequenceService } from 'app/entities/sequence/sequence.service';
import { ISequence } from 'app/shared/model/sequence.model';
import { ISequenceStep } from 'app/shared/model/sequence-step.model';
import { ISequencePlayContext, SequencePlayStatus, ISequenceStepRunContext, SequenceStepMessageLevel } from './sequence-player.model';
import * as moment from 'moment';
import { SequenceConditionEvaluator } from './evaluators/condition.evaluator';
import { SequenceActionEvaluator } from './evaluators/action.evaluator';
import { LeloF1ButtonsStatus } from 'app/core/lelo-f1-sdk/lelo-f1-sdk.constants';


@Component({
  selector: 'jhi-sequence-player-modal',
  templateUrl: './sequence-player-modal.component.html',
  styleUrls: ['./sequence-player-modal.component.scss']
})
export class SequencePlayerModalComponent implements OnInit, OnDestroy {
  
  client: LeloF1SDKService | null = null;
  sequence: ISequence = {};
  executionSteps: ISequenceStepRunContext[] = [];

  context: ISequencePlayContext;

  ready = false;
  
  constructor(
    private logger: NGXLogger, 
    private sequenceService: SequenceService,
    private activeModal: NgbActiveModal
  ) {
    this.context = this.prepareContext();
  }

  public activate(client: LeloF1SDKService, sequence: ISequence): void {
    this.client = client;
    this.sequenceService.find(sequence.id!).subscribe(response => {
      const seq = response.body;
      if (!seq) {
        throw Error('NOT FOUND');
      }
      this.sequence = seq;
      this.importSteps(seq.steps!);
      this.context.sequence = seq;
      this.context.deviceInterface = client;
      this.ready = true;

      setTimeout(() => this.start(), 100);
    });
  }

  prepareContext(): ISequencePlayContext {
    return {
      logger: this.logger,
      status: SequencePlayStatus.IDLE,
      sequence: {},
      stepIndex: 0,
      numberExecutedSteps: 0,
      userVariables: {},
      pendingHooks: {
        gotoStep: null
      }
    };
  }

  start(): void {
    this.context.startTime = moment();
    this.context.numberExecutedSteps = 0;
    this.context.status = SequencePlayStatus.RUNNING;

    this.logger.info('starting');
    this.run();
    this.logger.info('started');
  }

  pause(): void {
    if (this.context.status === SequencePlayStatus.RUNNING) {
      this.context.status = SequencePlayStatus.PAUSED;
    }
  }

  resume(): void {
    if (this.context.status === SequencePlayStatus.PAUSED) {
      this.context.status = SequencePlayStatus.RUNNING;
    }
  }

  async run(): Promise<void> {
    let designatedStep: ISequenceStepRunContext | null = this.executionSteps[0];
    while (designatedStep) {
      await this.waitUntilPaused();
      if (this.aborting) {
        this.logger.debug('aborting sequence mid-steps');
        break;
      }

      await this.processStep(designatedStep);
      
      if (this.executionSteps.length > this.context.stepIndex) {
        designatedStep = this.executionSteps[this.context.stepIndex];
      } else {
        designatedStep = null;
      }
    }

    this.context.status = SequencePlayStatus.FINISHED;
    await this.sleep(1);
  }

  async waitUntilPaused(): Promise<void> {
    if (this.paused) {
      this.logger.debug('paused in root controller');
      while (this.paused) {
        await this.sleep(100);
      }
    }
  }

  async processStep(stepContext: ISequenceStepRunContext): Promise<void> {
    this.logger.trace('processing step - entering');
    await this.sleep(1);

    this.enterStep(stepContext);

    await this.sleep(1);
    await this.waitUntilPaused();

    // check conditions
    this.logger.trace('processing step - checking conditions');
    const doRun = await this.evaluateStepConditions(stepContext);
    if (doRun) {
      this.logger.trace('processing step - executing');
      await this.executeStep(stepContext);
    } else {
      this.logger.trace('processing step - skipping');
      this.skipStep(stepContext);
    }

    await this.sleep(1);
    await this.waitUntilPaused();

    this.logger.trace('processing step - leaving');
    this.leaveStep(stepContext);
    stepContext.sequenceContext.numberExecutedSteps ++;

    await this.sleep(1);
    await this.waitUntilPaused();

    this.logger.trace('processing step - checking hooks');
    if (this.context.pendingHooks.gotoStep !== null) {
      this.logger.debug('procesing pending hook pendingHooks.gotoStep', this.context.pendingHooks.gotoStep);
      this.context.stepIndex = this.context.pendingHooks.gotoStep;
      this.context.pendingHooks.gotoStep = null;
    } else {
      this.context.stepIndex ++;
    }

    this.logger.trace('processing step - done');
  }

  enterStep(stepContext: ISequenceStepRunContext): void {
    stepContext.current = true;
    stepContext.messages.length = 0;
  }

  leaveStep(stepContext: ISequenceStepRunContext): void {
    stepContext.numberPassed ++;
    stepContext.current = false;
  }

  async evaluateStepConditions(stepContext: ISequenceStepRunContext): Promise<boolean> {
    this.logger.debug('evaluating conditions for step ' + stepContext.step.ordinal);

    const numConditions = stepContext.step.conditions?.length || 0;
    this.logger.trace('step ' + stepContext.step.ordinal + ' has ' + numConditions + ' conditions');

    if (numConditions > 0) {
      if (numConditions > 1) {
        // TODO emit warning
        this.logger.warn('many conditions on root step object are not supported');
      }

      try {
        const conditionContext = SequenceConditionEvaluator.buildContext(stepContext.step.conditions![0], stepContext);
        return await SequenceConditionEvaluator.evaluateCondition(conditionContext);
      } catch(err) {
        this.logger.error('error evaluating condition:', err);
        stepContext.messages.push({ text: 'error evaluating condition: ' + err, level: SequenceStepMessageLevel.ERROR });
        return false;
      }
    } else {
      return true;
    }
  }

  skipStep(stepContext: ISequenceStepRunContext): void {
    stepContext.numberSkipped ++;
    stepContext.lastSkipped = true;
    stepContext.messages.push({ text: 'skipped (did not met conditions)', level: SequenceStepMessageLevel.INFO });
  }

  async executeStep(stepContext: ISequenceStepRunContext): Promise<void> {
    stepContext.lastSkipped = false;
    
    const numActions = stepContext.step.actions?.length || 0;
    this.logger.trace('step ' + stepContext.step.ordinal + ' has ' + numActions + ' actions');

    if (numActions > 0) {
      for (const action of stepContext.step.actions!) {
        try {
          const actionContext = SequenceActionEvaluator.buildContext(action, stepContext);

          // eslint-disable-next-line require-atomic-updates
          stepContext.currentActionContext = actionContext;
          await this.sleep(1);

          await SequenceActionEvaluator.evaluateAction(actionContext);

        } catch(err) {
          this.logger.error('error executing action:', err);
          stepContext.messages.push({ text: 'error executing action: ' + err, level: SequenceStepMessageLevel.ERROR });
        } finally {
          // eslint-disable-next-line require-atomic-updates
          stepContext.currentActionContext = null;
          await this.sleep(1);
        }
      }
    }

    stepContext.numberExecuted ++;
    return;
  }

  importSteps(steps: ISequenceStep[]): void {
    this.executionSteps.length = 0;
    steps.forEach(step => {
      this.executionSteps.push({
        sequenceContext: this.context,
        step,
        numberExecuted: 0,
        numberSkipped: 0,
        numberPassed: 0,
        lastSkipped: false,
        current: false,
        messages: [],
        currentActionContext: null
      });
    });
  }

  sleep(ms: number): Promise<void> {
    return new Promise((resolve) => {
      setTimeout(() => {
        resolve();
      }, ms > 0 ? ms : 0);
    })
  }

  ngOnInit(): void {
  }

  ngOnDestroy(): void {
    if (this.running || this.paused) {
      this.abort();
    }
  }

  abort(): void {
    this.logger.info('aborting sequence execution');
    this.context.status = SequencePlayStatus.ABORTING;
    setTimeout(() => {
      this.activeModal.close({
        status: SequencePlayStatus.ABORTING
      });
    }, 200);
  }

  cancel(): void {
    if (this.finished) {
      this.activeModal.close({
        status: SequencePlayStatus.FINISHED
      });
    } else if (this.idle) {
      this.activeModal.dismiss();
    }
  }

  dispatchButtonPress(status: LeloF1ButtonsStatus): void {
    if (status.any) {
      if (this.paused) {
        this.resume();
      } else if (this.running) {
        this.pause();
      }
    }
  }

  getStyle(entry: ISequenceStepRunContext): string {
    if (entry.current) {
      if (this.paused) {
        return 'list-group-item-warning current';
      } else {
        return 'list-group-item-info current';
      }
    } else if (entry.numberPassed > 0) {
      if (entry.lastSkipped) {
        return 'list-group-item-light skipped';
      } else {
        return 'list-group-item-light passed';
      }
    } else {
      return 'list-group-item-light future';
    }
  }
  
  getIcon(entry: ISequenceStepRunContext): string | null {
    if (entry.current) {
      if (this.running) {
        return 'circle-notch';
      } else if (this.paused) {
        return 'pause';
      } else {
        return 'ellipsis-h';
      }
    } else if (entry.numberPassed > 0) {
      return null;
    } else {
      return null;
    }
  }

  get idle(): boolean {
    return this.context.status === SequencePlayStatus.IDLE;
  }

  get running(): boolean {
    return this.context.status === SequencePlayStatus.RUNNING;
  }

  get paused(): boolean {
    return this.context.status === SequencePlayStatus.PAUSED;
  }

  get aborting(): boolean {
    return this.context.status === SequencePlayStatus.ABORTING;
  }

  get finished(): boolean {
    return this.context.status === SequencePlayStatus.FINISHED;
  }
}
