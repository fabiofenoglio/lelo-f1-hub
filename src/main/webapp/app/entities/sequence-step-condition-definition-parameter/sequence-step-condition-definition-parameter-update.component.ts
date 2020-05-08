import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import {
  ISequenceStepConditionDefinitionParameter,
  SequenceStepConditionDefinitionParameter
} from 'app/shared/model/sequence-step-condition-definition-parameter.model';
import { SequenceStepConditionDefinitionParameterService } from './sequence-step-condition-definition-parameter.service';
import { ISequenceStepConditionDefinition } from 'app/shared/model/sequence-step-condition-definition.model';
import { SequenceStepConditionDefinitionService } from 'app/entities/sequence-step-condition-definition/sequence-step-condition-definition.service';

@Component({
  selector: 'jhi-sequence-step-condition-definition-parameter-update',
  templateUrl: './sequence-step-condition-definition-parameter-update.component.html'
})
export class SequenceStepConditionDefinitionParameterUpdateComponent implements OnInit {
  isSaving = false;
  sequencestepconditiondefinitions: ISequenceStepConditionDefinition[] = [];

  editForm = this.fb.group({
    id: [],
    generation: [null, [Validators.required]],
    code: [null, [Validators.required, Validators.maxLength(100)]],
    name: [null, [Validators.required, Validators.maxLength(250)]],
    description: [null, [Validators.maxLength(250)]],
    type: [null, [Validators.required]],
    evaluationEngine: [null, [Validators.required]],
    minValue: [],
    maxValue: [],
    stepValue: [],
    minLength: [],
    maxLength: [],
    definitionId: [null, Validators.required]
  });

  constructor(
    protected sequenceStepConditionDefinitionParameterService: SequenceStepConditionDefinitionParameterService,
    protected sequenceStepConditionDefinitionService: SequenceStepConditionDefinitionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sequenceStepConditionDefinitionParameter }) => {
      this.updateForm(sequenceStepConditionDefinitionParameter);

      this.sequenceStepConditionDefinitionService
        .query()
        .subscribe((res: HttpResponse<ISequenceStepConditionDefinition[]>) => (this.sequencestepconditiondefinitions = res.body || []));
    });
  }

  updateForm(sequenceStepConditionDefinitionParameter: ISequenceStepConditionDefinitionParameter): void {
    this.editForm.patchValue({
      id: sequenceStepConditionDefinitionParameter.id,
      generation: sequenceStepConditionDefinitionParameter.generation,
      code: sequenceStepConditionDefinitionParameter.code,
      name: sequenceStepConditionDefinitionParameter.name,
      description: sequenceStepConditionDefinitionParameter.description,
      type: sequenceStepConditionDefinitionParameter.type,
      evaluationEngine: sequenceStepConditionDefinitionParameter.evaluationEngine,
      minValue: sequenceStepConditionDefinitionParameter.minValue,
      maxValue: sequenceStepConditionDefinitionParameter.maxValue,
      stepValue: sequenceStepConditionDefinitionParameter.stepValue,
      minLength: sequenceStepConditionDefinitionParameter.minLength,
      maxLength: sequenceStepConditionDefinitionParameter.maxLength,
      definitionId: sequenceStepConditionDefinitionParameter.definitionId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sequenceStepConditionDefinitionParameter = this.createFromForm();
    if (sequenceStepConditionDefinitionParameter.id !== undefined) {
      this.subscribeToSaveResponse(this.sequenceStepConditionDefinitionParameterService.update(sequenceStepConditionDefinitionParameter));
    } else {
      this.subscribeToSaveResponse(this.sequenceStepConditionDefinitionParameterService.create(sequenceStepConditionDefinitionParameter));
    }
  }

  private createFromForm(): ISequenceStepConditionDefinitionParameter {
    return {
      ...new SequenceStepConditionDefinitionParameter(),
      id: this.editForm.get(['id'])!.value,
      generation: this.editForm.get(['generation'])!.value,
      code: this.editForm.get(['code'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      type: this.editForm.get(['type'])!.value,
      evaluationEngine: this.editForm.get(['evaluationEngine'])!.value,
      minValue: this.editForm.get(['minValue'])!.value,
      maxValue: this.editForm.get(['maxValue'])!.value,
      stepValue: this.editForm.get(['stepValue'])!.value,
      minLength: this.editForm.get(['minLength'])!.value,
      maxLength: this.editForm.get(['maxLength'])!.value,
      definitionId: this.editForm.get(['definitionId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISequenceStepConditionDefinitionParameter>>): void {
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

  trackById(index: number, item: ISequenceStepConditionDefinition): any {
    return item.id;
  }
}
