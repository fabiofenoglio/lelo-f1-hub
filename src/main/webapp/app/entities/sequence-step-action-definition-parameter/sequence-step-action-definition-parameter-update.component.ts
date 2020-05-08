import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import {
  ISequenceStepActionDefinitionParameter,
  SequenceStepActionDefinitionParameter
} from 'app/shared/model/sequence-step-action-definition-parameter.model';
import { SequenceStepActionDefinitionParameterService } from './sequence-step-action-definition-parameter.service';
import { ISequenceStepActionDefinition } from 'app/shared/model/sequence-step-action-definition.model';
import { SequenceStepActionDefinitionService } from 'app/entities/sequence-step-action-definition/sequence-step-action-definition.service';

@Component({
  selector: 'jhi-sequence-step-action-definition-parameter-update',
  templateUrl: './sequence-step-action-definition-parameter-update.component.html'
})
export class SequenceStepActionDefinitionParameterUpdateComponent implements OnInit {
  isSaving = false;
  sequencestepactiondefinitions: ISequenceStepActionDefinition[] = [];

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
    protected sequenceStepActionDefinitionParameterService: SequenceStepActionDefinitionParameterService,
    protected sequenceStepActionDefinitionService: SequenceStepActionDefinitionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sequenceStepActionDefinitionParameter }) => {
      this.updateForm(sequenceStepActionDefinitionParameter);

      this.sequenceStepActionDefinitionService
        .query()
        .subscribe((res: HttpResponse<ISequenceStepActionDefinition[]>) => (this.sequencestepactiondefinitions = res.body || []));
    });
  }

  updateForm(sequenceStepActionDefinitionParameter: ISequenceStepActionDefinitionParameter): void {
    this.editForm.patchValue({
      id: sequenceStepActionDefinitionParameter.id,
      generation: sequenceStepActionDefinitionParameter.generation,
      code: sequenceStepActionDefinitionParameter.code,
      name: sequenceStepActionDefinitionParameter.name,
      description: sequenceStepActionDefinitionParameter.description,
      type: sequenceStepActionDefinitionParameter.type,
      evaluationEngine: sequenceStepActionDefinitionParameter.evaluationEngine,
      minValue: sequenceStepActionDefinitionParameter.minValue,
      maxValue: sequenceStepActionDefinitionParameter.maxValue,
      stepValue: sequenceStepActionDefinitionParameter.stepValue,
      minLength: sequenceStepActionDefinitionParameter.minLength,
      maxLength: sequenceStepActionDefinitionParameter.maxLength,
      definitionId: sequenceStepActionDefinitionParameter.definitionId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sequenceStepActionDefinitionParameter = this.createFromForm();
    if (sequenceStepActionDefinitionParameter.id !== undefined) {
      this.subscribeToSaveResponse(this.sequenceStepActionDefinitionParameterService.update(sequenceStepActionDefinitionParameter));
    } else {
      this.subscribeToSaveResponse(this.sequenceStepActionDefinitionParameterService.create(sequenceStepActionDefinitionParameter));
    }
  }

  private createFromForm(): ISequenceStepActionDefinitionParameter {
    return {
      ...new SequenceStepActionDefinitionParameter(),
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISequenceStepActionDefinitionParameter>>): void {
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

  trackById(index: number, item: ISequenceStepActionDefinition): any {
    return item.id;
  }
}
