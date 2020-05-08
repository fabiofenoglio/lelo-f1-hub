import { Moment } from 'moment';
import { ISequence } from 'app/shared/model/sequence.model';
import { ISequenceStep } from 'app/shared/model/sequence-step.model';
import { NGXLogger } from 'ngx-logger';
import { ISequenceStepCondition } from 'app/shared/model/sequence-step-condition.model';
import { LeloF1SDKService } from 'app/core/lelo-f1-sdk/lelo-f1-sdk.service';
import { ISequenceStepAction } from 'app/shared/model/sequence-step-action.model';

export enum SequencePlayStatus {
    IDLE = 'IDLE',
    RUNNING = 'RUNNING',
    PAUSED = 'PAUSED',
    ABORTING = 'ABORTING',
    FINISHED = 'FINISHED'
}

export interface ISequencePlayContext {
    logger: NGXLogger,
    stepIndex: number;
    status: SequencePlayStatus;
    numberExecutedSteps: number;
    sequence: ISequence;
    deviceInterface?: LeloF1SDKService;
    startTime?: Moment;
    userVariables: {
        [key: string]: any
    },
    pendingHooks: {
        gotoStep: number | null
    }
}

export interface ISequenceStepRunContext {
    sequenceContext: ISequencePlayContext;
    numberExecuted: number;
    numberSkipped: number;
    numberPassed: number;
    lastSkipped: boolean;
    current: boolean;
    step: ISequenceStep;
    messages: ISequenceStepMessage[];
    currentActionContext: ISequenceStepActionRunContext | null;
}

export interface ISequenceStepConditionRunContext {
    stepContext: ISequenceStepRunContext;
    condition: ISequenceStepCondition;
    parameterMap: ISequenceParameterMap;
}

export interface ISequenceStepActionRunContext {
    stepContext: ISequenceStepRunContext;
    action: ISequenceStepAction;
    parameterMap: ISequenceParameterMap;
    progress?: number | null;
}

export enum SequenceStepMessageLevel {
    DEBUG = 'DEBUG',
    INFO = 'INFO',
    WARNING = 'WARNING',
    ERROR = 'ERROR'
}

export interface ISequenceStepMessage {
    text: string;
    level: SequenceStepMessageLevel;
}

export type ISequenceParameterMap = {[code: string]: any};