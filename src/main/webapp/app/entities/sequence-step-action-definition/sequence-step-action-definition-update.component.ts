import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISequenceStepActionDefinition, SequenceStepActionDefinition } from 'app/shared/model/sequence-step-action-definition.model';
import { SequenceStepActionDefinitionService } from './sequence-step-action-definition.service';

@Component({
  selector: 'jhi-sequence-step-action-definition-update',
  templateUrl: './sequence-step-action-definition-update.component.html'
})
export class SequenceStepActionDefinitionUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    generation: [null, [Validators.required]],
    code: [null, [Validators.required, Validators.maxLength(100)]],
    description: [null, [Validators.maxLength(250)]]
  });

  constructor(
    protected sequenceStepActionDefinitionService: SequenceStepActionDefinitionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sequenceStepActionDefinition }) => {
      this.updateForm(sequenceStepActionDefinition);
    });
  }

  updateForm(sequenceStepActionDefinition: ISequenceStepActionDefinition): void {
    this.editForm.patchValue({
      id: sequenceStepActionDefinition.id,
      generation: sequenceStepActionDefinition.generation,
      code: sequenceStepActionDefinition.code,
      description: sequenceStepActionDefinition.description
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sequenceStepActionDefinition = this.createFromForm();
    if (sequenceStepActionDefinition.id !== undefined) {
      this.subscribeToSaveResponse(this.sequenceStepActionDefinitionService.update(sequenceStepActionDefinition));
    } else {
      this.subscribeToSaveResponse(this.sequenceStepActionDefinitionService.create(sequenceStepActionDefinition));
    }
  }

  private createFromForm(): ISequenceStepActionDefinition {
    return {
      ...new SequenceStepActionDefinition(),
      id: this.editForm.get(['id'])!.value,
      generation: this.editForm.get(['generation'])!.value,
      code: this.editForm.get(['code'])!.value,
      description: this.editForm.get(['description'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISequenceStepActionDefinition>>): void {
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
