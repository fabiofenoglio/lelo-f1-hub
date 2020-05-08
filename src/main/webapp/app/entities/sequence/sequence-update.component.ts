import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators, FormArray, FormGroup, FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, forkJoin } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { DeviceDetectorService } from 'ngx-device-detector';
import { ISequence, Sequence } from 'app/shared/model/sequence.model';
import { SequenceService } from './sequence.service';
import { SequenceStep, ISequenceStep } from 'app/shared/model/sequence-step.model';
import { SequenceStepConditionDefinitionService } from '../sequence-step-condition-definition/sequence-step-condition-definition.service';
import { ISequenceStepConditionDefinition } from 'app/shared/model/sequence-step-condition-definition.model';
import { SequenceVisibility } from 'app/shared/model/enumerations/sequence-visibility.model';
import { ISequenceStepActionDefinition } from 'app/shared/model/sequence-step-action-definition.model';
import { SequenceStepActionDefinitionService } from '../sequence-step-action-definition/sequence-step-action-definition.service';
import { ISequenceStepActionDefinitionParameter } from 'app/shared/model/sequence-step-action-definition-parameter.model';
import { SequenceStepConditionDefinitionParameterType } from 'app/shared/model/enumerations/sequence-step-condition-definition-parameter-type.model';
import { SequenceStepActionDefinitionParameterType } from 'app/shared/model/enumerations/sequence-step-action-definition-parameter-type.model';
import { ISequenceStepCondition } from 'app/shared/model/sequence-step-condition.model';
import { ISequenceStepConditionParameter } from 'app/shared/model/sequence-step-condition-parameter.model';
import { ISequenceStepAction, SequenceStepAction } from 'app/shared/model/sequence-step-action.model';
import { ISequenceStepActionParameter } from 'app/shared/model/sequence-step-action-parameter.model';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { SequenceDeleteDialogComponent } from './sequence-delete-dialog.component';

@Component({
  selector: 'jhi-sequence-update',
  templateUrl: './sequence-update.component.html'
})
export class SequenceUpdateComponent implements OnInit {
  isSaving = false;
  isMobile = false;
  readOnly = false;

  sequencestepconditiondefinitions: ISequenceStepConditionDefinition[] = [];
  sequencestepactiondefinitions: ISequenceStepActionDefinition[] = [];
  
  editForm = this.fb.group({
    id: [],
    generation: [null],
    name: [null, [Validators.required, Validators.maxLength(250)]],
    description: [null, [Validators.maxLength(2000)]],
    visibility: [null, [Validators.required]],
    createdDate: [null],
    createdBy: [null],
    lastModifiedDate: [null],
    lastModifiedBy: [null],
    userId: [null],
    steps: this.fb.array([])
  });

  constructor(
    protected sequenceService: SequenceService,
    protected sequenceStepConditionDefinitionService: SequenceStepConditionDefinitionService,
    protected sequenceStepActionDefinitionService: SequenceStepActionDefinitionService,
    protected activatedRoute: ActivatedRoute,
    protected deviceService: DeviceDetectorService,
    protected modalService: NgbModal,
    private fb: FormBuilder,
    private cdr: ChangeDetectorRef,
    private router: Router
  ) {
    this.isMobile = this.deviceService.isMobile();
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sequence, readOnly }) => {
      this.readOnly = readOnly;

      if (!sequence.id) {
        const today = moment().startOf('day');
        sequence.createdDate = today;
        sequence.lastModifiedDate = today;
      }

      forkJoin(
        this.sequenceStepConditionDefinitionService.queryOptions(),
        this.sequenceStepActionDefinitionService.queryOptions(),
      ).subscribe(results => {
        this.sequencestepconditiondefinitions = results[0].body || []
        this.sequencestepactiondefinitions = results[1].body || []

        this.sequencestepconditiondefinitions.sort((a, b) => {
          return (a.description || '').localeCompare(b.description || '');
        });

        if (!sequence.id && !this.readOnly) {
          this.addStep();
        }
        
        this.updateForm(sequence);

        if (this.readOnly) {
          this.editForm.disable();
        }
      });
    });
  }

  updateForm(sequence: ISequence): void {
    const patchValue = {
      id: sequence.id,
      generation: sequence.generation,
      name: sequence.name || 'New sequence',
      description: sequence.description,
      visibility: sequence.visibility || SequenceVisibility.PUBLIC,
      createdDate: sequence.createdDate ? sequence.createdDate.format(DATE_TIME_FORMAT) : null,
      createdBy: sequence.createdBy,
      lastModifiedDate: sequence.lastModifiedDate ? sequence.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: sequence.lastModifiedBy,
      userId: sequence.userId
    };

    this.editForm.patchValue(patchValue);

    // import steps
    (sequence.steps || []).forEach(stepDTO => {
      this.addStep(stepDTO);
    });

    this.cdr.detectChanges();
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    if (this.readOnly) {
      return;
    }
    this.isSaving = true;
    const sequence = this.createFromForm();
    // eslint-disable-next-line no-console
    console.log(sequence);

    if (sequence.id !== undefined) {
      this.subscribeToSaveResponse(this.sequenceService.update(sequence));
    } else {
      this.subscribeToSaveResponse(this.sequenceService.create(sequence));
    }
  }

  private createFromForm(): ISequence {
    return {
      ...new Sequence(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      visibility: this.editForm.get(['visibility'])!.value,
      steps: this.editForm.get('steps')!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISequence>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: any): any {
    return item.id;
  }
  
  get steps(): FormArray {
    return this.editForm?.get('steps') as FormArray;
  }

  addStep(dto: ISequenceStep | null = null): void {
    // get number of steps
    const ordinal = dto ? dto.ordinal : this.editForm && this.steps ? 
      (Math.max(0, ...this.editForm.getRawValue().steps.map((o: SequenceStep) => o.ordinal || 0)) || 0) + 1 : 1;

    const step = this.fb.group({
      id: [dto?.id],
      ordinal: [ordinal, Validators.required],
      description: [dto ? dto.description : 'step #' + ordinal, [Validators.maxLength(250)]],
      conditions: this.fb.array([]),
      actions: this.fb.array([])
    });

    this.steps.push(step);

    if (!dto) {
      this.addAction(step);
    } else {
      (dto.conditions || []).forEach(conditionDTO => {
        this.addCondition(step, 'conditions', '', '', conditionDTO);
      });
      (dto.actions || []).forEach(actionDTO => {
        this.addAction(step, '', '', actionDTO);
      });
    }
    
    this.cdr.detectChanges();
  }

  deleteStep(stepIndex: number): void {
    (this.editForm.get('steps') as FormArray).removeAt(stepIndex);
    this.adjustStepOrdinals(stepIndex);
    this.cdr.detectChanges();
  }

  adjustStepOrdinals(removedIndex: number): void {
    const stepsArray = (this.editForm.get('steps') as FormArray);
    let i = 1;
    for (const control of stepsArray.controls) {
      control.get('ordinal')?.setValue(i);
      if (control.value.description?.startsWith('step #')) {
        control.get('description')?.setValue('step #' + i);
      }
      i ++;

      (control.get('actions') as FormArray).controls.forEach(actionControl => {
        const definition = (actionControl.get('definition') as FormGroup).value as ISequenceStepActionDefinition;
        if (definition?.code === 'GOTO') {
          (actionControl.get('parameters') as FormArray).controls.forEach(parameterControl => {
            const paramDefinition = (parameterControl.get('definition') as FormGroup).value as ISequenceStepActionDefinitionParameter;
            if (paramDefinition?.code === 'STEP_ORDINAL') {
              const targetOrdinalControl = (parameterControl.get('valueNumber') as FormControl);
              const targetOrdinal = targetOrdinalControl.value;
              if (targetOrdinal === removedIndex + 1) {
                targetOrdinalControl.setValue(null);
              } else if (targetOrdinal > removedIndex + 1) {
                targetOrdinalControl.setValue(targetOrdinal - 1);
              }
            }
          });
        }
      });
    }
  }

  hasConditionalExecution(step: FormGroup): boolean {
    return (step.get('conditions') as FormArray).controls.length > 0;
  }

  addConditionalExecution(step: FormGroup): void {
    return this.addCondition(step, 'conditions', 'ALWAYS', 'step condition');
  }

  addCondition(
    toGroup: FormGroup, 
    field = 'conditions', code = 'ALWAYS', description = 'step condition', 
    conditionDTO: ISequenceStepCondition | undefined = undefined): void {

    const startingDefinition: ISequenceStepConditionDefinition | undefined = 
      conditionDTO ? conditionDTO.definition :
      this.sequencestepconditiondefinitions.find(d => d.code === code);

    const newControl = this.fb.group({
      id: [conditionDTO?.id],
      description: [conditionDTO ? conditionDTO.description : description, [Validators.maxLength(250)]],
      negate: [conditionDTO ? conditionDTO.negate : false],
      definitionId: [startingDefinition?.id || null, Validators.required],
      definition: this.fb.control(conditionDTO ? conditionDTO.definition : {}),
      andConditions: this.fb.array([]),
      orConditions: this.fb.array([]),
      parameters: this.fb.array([]),
    });

    newControl.setParent(toGroup.get(field) as FormArray);

    (toGroup.get(field) as FormArray).controls.push(newControl);

    if (!this.readOnly) {
      (newControl.get('definitionId') as FormControl).valueChanges.subscribe(
        v => this.onConditionDefinitionChanged(newControl, v));
    }

    if (conditionDTO) {
      (conditionDTO.andConditions || []).forEach(conjConditionDTO => {
        this.addCondition(newControl, 'andConditions', '', '', conjConditionDTO);
      });
      (conditionDTO.orConditions || []).forEach(conjConditionDTO => {
        this.addCondition(newControl, 'orConditions', '', '', conjConditionDTO);
      });

      this.onConditionDefinitionChanged(newControl, conditionDTO.definition?.id, conditionDTO.parameters);
    } else {
      this.onConditionDefinitionChanged(newControl, startingDefinition?.id);
    }

    this.cdr.detectChanges();
  }

  addConditionAND(toGroup: FormGroup): void {
    return this.addCondition(toGroup, 'andConditions', 'ALWAYS', 'AND ...');
  }

  addConditionOR(toGroup: FormGroup): void {
    return this.addCondition(toGroup, 'orConditions', 'NEVER', 'OR ...');
  }

  deleteCondition(conditionGroup: FormGroup, parent: FormArray, index: number): void {
    parent.removeAt(index);
    this.cdr.detectChanges();
  }

  onConditionDefinitionChanged(sequenceGroup: FormGroup, newValue: number | undefined, paramsDTO: ISequenceStepConditionParameter[] | null = null): void {
    const definition: ISequenceStepConditionDefinition | undefined = 
      this.sequencestepconditiondefinitions.find(d => d.id === newValue);
    
    sequenceGroup.get('definition')?.setValue(definition);

    const parametersArray = sequenceGroup.get('parameters') as FormArray;
    parametersArray.clear();

    if (paramsDTO) {
      paramsDTO.forEach(paramDTO => {
        const parameterFormGroup = this.fb.group({
          id: [paramDTO?.id],
          valueString: [paramDTO.valueString, (paramDTO.definition?.type === SequenceStepConditionDefinitionParameterType.STRING ? Validators.required : undefined)],
          valueNumber: [paramDTO.valueNumber, (paramDTO.definition?.type === SequenceStepConditionDefinitionParameterType.NUMBER ? Validators.required : undefined)],
          valueBoolean: [paramDTO.valueBoolean, (paramDTO.definition?.type === SequenceStepConditionDefinitionParameterType.BOOLEAN ? Validators.required : undefined)],
          valueVariable: [paramDTO.valueVariable, (paramDTO.definition?.type === SequenceStepConditionDefinitionParameterType.VARIABLE ? Validators.required : undefined)],
          valueOperator: [paramDTO.valueOperator, (paramDTO.definition?.type === SequenceStepConditionDefinitionParameterType.OPERATOR ? Validators.required : undefined)],
          definitionId: [paramDTO.definition?.id, Validators.required],
          definition: [paramDTO.definition]
        });
  
        parameterFormGroup.setParent(parametersArray);
        parametersArray.controls.push(parameterFormGroup);
      });
    } else {
      definition?.parameters?.forEach(parameterDefinition => {
        const parameterFormGroup = this.fb.group({
          valueString: [null, (parameterDefinition.type === SequenceStepConditionDefinitionParameterType.STRING ? Validators.required : undefined)],
          valueNumber: [null, (parameterDefinition.type === SequenceStepConditionDefinitionParameterType.NUMBER ? Validators.required : undefined)],
          valueBoolean: [null, (parameterDefinition.type === SequenceStepConditionDefinitionParameterType.BOOLEAN ? Validators.required : undefined)],
          valueVariable: [null, (parameterDefinition.type === SequenceStepConditionDefinitionParameterType.VARIABLE ? Validators.required : undefined)],
          valueOperator: [null, (parameterDefinition.type === SequenceStepConditionDefinitionParameterType.OPERATOR ? Validators.required : undefined)],
          definitionId: [parameterDefinition.id, Validators.required],
          definition: [parameterDefinition]
        });
  
        parameterFormGroup.setParent(parametersArray);
        parametersArray.controls.push(parameterFormGroup);
      });
    }
    
    // eslint-disable-next-line no-debugger
    // debugger;

    this.cdr.detectChanges();
  }

  hasActions(step: FormGroup): boolean {
    return (step.get('actions') as FormArray).controls.length > 0;
  }

  addAction(toGroup: FormGroup, code = 'WAIT', description = 'step action', actionDTO: ISequenceStepAction | undefined = undefined): void {
    const startingDefinition: ISequenceStepActionDefinition | undefined = 
      actionDTO ? actionDTO.definition : 
      this.sequencestepactiondefinitions.find(d => d.code === code);
    
    const targetActions = toGroup.get('actions') as FormArray;

    const ordinal = actionDTO ? actionDTO.ordinal : this.editForm && this.steps ? 
      (Math.max(0, ...targetActions.getRawValue().map((o: SequenceStepAction) => o.ordinal || 0)) || 0) + 1 : 1;

    const newControl = this.fb.group({
      id: [actionDTO?.id],
      ordinal: [ordinal, Validators.required],
      description: [actionDTO ? actionDTO.description : description, [Validators.maxLength(250)]],
      definitionId: [startingDefinition?.id || null, Validators.required],
      definition: this.fb.control(actionDTO ? actionDTO.definition : {}),
      parameters: this.fb.array([]),
    });

    newControl.setParent(toGroup.get('actions') as FormArray);

    targetActions.controls.push(newControl);

    if (!this.readOnly) {
      (newControl.get('definitionId') as FormControl).valueChanges.subscribe(
        v => this.onActionDefinitionChanged(newControl, v));
    }

    if (actionDTO) {
      this.onActionDefinitionChanged(newControl, actionDTO.definition?.id, actionDTO.parameters);
    } else {
      this.onActionDefinitionChanged(newControl, startingDefinition?.id);
    }
    
    this.cdr.detectChanges();
  }

  deleteAction(parent: FormArray, index: number): void {
    parent.removeAt(index);
    this.adjustActionOrdinals(parent);
    this.cdr.detectChanges();
  }

  adjustActionOrdinals(parent: FormArray): void {
    const actionsArray = (parent.get('actions') as FormArray);
    let i = 1;
    for (const control of actionsArray.controls) {
      control.get('ordinal')?.setValue(i);
      i ++;
    }
  }

  onActionDefinitionChanged(sequenceGroup: FormGroup, newValue: number | undefined, paramsDTO: ISequenceStepActionParameter[] | undefined = undefined): void {
    const definition: ISequenceStepActionDefinition | undefined = 
      this.sequencestepactiondefinitions.find(d => d.id === newValue);
    
    sequenceGroup.get('definition')?.setValue(definition);

    const parametersArray = sequenceGroup.get('parameters') as FormArray;
    parametersArray.clear();

    if (paramsDTO) {
      paramsDTO.forEach(paramDTO => {
        const parameterFormGroup = this.fb.group({
          id: [paramDTO?.id],
          valueString: [paramDTO.valueString, (paramDTO.definition?.type === SequenceStepActionDefinitionParameterType.STRING ? Validators.required : undefined)],
          valueNumber: [paramDTO.valueNumber, (paramDTO.definition?.type === SequenceStepActionDefinitionParameterType.NUMBER ? Validators.required : undefined)],
          valueBoolean: [paramDTO.valueBoolean, (paramDTO.definition?.type === SequenceStepActionDefinitionParameterType.BOOLEAN ? Validators.required : undefined)],
          valueVariable: [paramDTO.valueVariable, (paramDTO.definition?.type === SequenceStepActionDefinitionParameterType.VARIABLE ? Validators.required : undefined)],
          definitionId: [paramDTO.definition?.id, Validators.required],
          definition: [paramDTO.definition]
        });
  
        parameterFormGroup.setParent(parametersArray);
        parametersArray.controls.push(parameterFormGroup);
      });
    } else {
      definition?.parameters?.forEach(parameterDefinition => {
        const parameterFormGroup = this.fb.group({
          valueString: [null, (parameterDefinition.type === SequenceStepActionDefinitionParameterType.STRING ? Validators.required : undefined)],
          valueNumber: [null, (parameterDefinition.type === SequenceStepActionDefinitionParameterType.NUMBER ? Validators.required : undefined)],
          valueBoolean: [null, (parameterDefinition.type === SequenceStepActionDefinitionParameterType.BOOLEAN ? Validators.required : undefined)],
          valueVariable: [null, (parameterDefinition.type === SequenceStepActionDefinitionParameterType.VARIABLE ? Validators.required : undefined)],
          valueOperator: [null, (parameterDefinition.type === SequenceStepActionDefinitionParameterType.OPERATOR ? Validators.required : undefined)],
          definitionId: [parameterDefinition.id, Validators.required],
          definition: [parameterDefinition]
        });
  
        parameterFormGroup.setParent(parametersArray);
        parametersArray.controls.push(parameterFormGroup);
      });
    }

    // eslint-disable-next-line no-debugger
    // debugger;

    this.cdr.detectChanges();
  }

  get id(): number {
    return this.editForm && this.editForm.get('id')?.value;
  }

  clone(): void {
    if (this.readOnly) {
      this.sequenceService.clone(this.id).subscribe(created => {
        this.router.navigate(['/sequence', created.body?.id, 'edit']);
      });
    } else {
      if (this.id) {
        this.sequenceService.clone(this.id, this.createFromForm()).subscribe(() => {
          this.router.navigate(['/sequence']);
        });
      }
    }
  }
  
  delete(): void {
    const modalRef = this.modalService.open(SequenceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.sequence = this.createFromForm();
    modalRef.result.then(() => {
      this.router.navigate(['/sequence']);
    }, () => {});
  }
}
