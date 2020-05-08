import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import {
  ISequenceStepConditionDefinition,
  SequenceStepConditionDefinition
} from 'app/shared/model/sequence-step-condition-definition.model';
import { SequenceStepConditionDefinitionService } from './sequence-step-condition-definition.service';

@Component({
  selector: 'jhi-sequence-step-condition-definition-update',
  templateUrl: './sequence-step-condition-definition-update.component.html'
})
export class SequenceStepConditionDefinitionUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    generation: [null, [Validators.required]],
    code: [null, [Validators.required, Validators.maxLength(100)]],
    description: [null, [Validators.maxLength(250)]]
  });

  constructor(
    protected sequenceStepConditionDefinitionService: SequenceStepConditionDefinitionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sequenceStepConditionDefinition }) => {
      this.updateForm(sequenceStepConditionDefinition);
    });
  }

  updateForm(sequenceStepConditionDefinition: ISequenceStepConditionDefinition): void {
    this.editForm.patchValue({
      id: sequenceStepConditionDefinition.id,
      generation: sequenceStepConditionDefinition.generation,
      code: sequenceStepConditionDefinition.code,
      description: sequenceStepConditionDefinition.description
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sequenceStepConditionDefinition = this.createFromForm();
    if (sequenceStepConditionDefinition.id !== undefined) {
      this.subscribeToSaveResponse(this.sequenceStepConditionDefinitionService.update(sequenceStepConditionDefinition));
    } else {
      this.subscribeToSaveResponse(this.sequenceStepConditionDefinitionService.create(sequenceStepConditionDefinition));
    }
  }

  private createFromForm(): ISequenceStepConditionDefinition {
    return {
      ...new SequenceStepConditionDefinition(),
      id: this.editForm.get(['id'])!.value,
      generation: this.editForm.get(['generation'])!.value,
      code: this.editForm.get(['code'])!.value,
      description: this.editForm.get(['description'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISequenceStepConditionDefinition>>): void {
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
}
