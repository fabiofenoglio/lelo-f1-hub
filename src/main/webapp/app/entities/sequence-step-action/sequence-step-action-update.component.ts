import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ISequenceStepAction, SequenceStepAction } from 'app/shared/model/sequence-step-action.model';
import { SequenceStepActionService } from './sequence-step-action.service';
import { ISequenceStepActionDefinition } from 'app/shared/model/sequence-step-action-definition.model';
import { SequenceStepActionDefinitionService } from 'app/entities/sequence-step-action-definition/sequence-step-action-definition.service';
import { ISequenceStep } from 'app/shared/model/sequence-step.model';
import { SequenceStepService } from 'app/entities/sequence-step/sequence-step.service';

type SelectableEntity = ISequenceStepActionDefinition | ISequenceStep;

@Component({
  selector: 'jhi-sequence-step-action-update',
  templateUrl: './sequence-step-action-update.component.html'
})
export class SequenceStepActionUpdateComponent implements OnInit {
  isSaving = false;
  sequencestepactiondefinitions: ISequenceStepActionDefinition[] = [];
  sequencesteps: ISequenceStep[] = [];

  editForm = this.fb.group({
    id: [],
    generation: [null, [Validators.required]],
    description: [null, [Validators.maxLength(250)]],
    createdDate: [null, [Validators.required]],
    createdBy: [null, [Validators.required]],
    lastModifiedDate: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
    definitionId: [null, Validators.required],
    stepId: [null, Validators.required]
  });

  constructor(
    protected sequenceStepActionService: SequenceStepActionService,
    protected sequenceStepActionDefinitionService: SequenceStepActionDefinitionService,
    protected sequenceStepService: SequenceStepService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sequenceStepAction }) => {
      if (!sequenceStepAction.id) {
        const today = moment().startOf('day');
        sequenceStepAction.createdDate = today;
        sequenceStepAction.lastModifiedDate = today;
      }

      this.updateForm(sequenceStepAction);

      this.sequenceStepActionDefinitionService
        .query()
        .subscribe((res: HttpResponse<ISequenceStepActionDefinition[]>) => (this.sequencestepactiondefinitions = res.body || []));

      this.sequenceStepService.query().subscribe((res: HttpResponse<ISequenceStep[]>) => (this.sequencesteps = res.body || []));
    });
  }

  updateForm(sequenceStepAction: ISequenceStepAction): void {
    this.editForm.patchValue({
      id: sequenceStepAction.id,
      generation: sequenceStepAction.generation,
      description: sequenceStepAction.description,
      createdDate: sequenceStepAction.createdDate ? sequenceStepAction.createdDate.format(DATE_TIME_FORMAT) : null,
      createdBy: sequenceStepAction.createdBy,
      lastModifiedDate: sequenceStepAction.lastModifiedDate ? sequenceStepAction.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: sequenceStepAction.lastModifiedBy,
      definitionId: sequenceStepAction.definitionId,
      stepId: sequenceStepAction.stepId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sequenceStepAction = this.createFromForm();
    if (sequenceStepAction.id !== undefined) {
      this.subscribeToSaveResponse(this.sequenceStepActionService.update(sequenceStepAction));
    } else {
      this.subscribeToSaveResponse(this.sequenceStepActionService.create(sequenceStepAction));
    }
  }

  private createFromForm(): ISequenceStepAction {
    return {
      ...new SequenceStepAction(),
      id: this.editForm.get(['id'])!.value,
      generation: this.editForm.get(['generation'])!.value,
      description: this.editForm.get(['description'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value ? moment(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT) : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      lastModifiedDate: this.editForm.get(['lastModifiedDate'])!.value ? moment(this.editForm.get(['lastModifiedDate'])!.value, DATE_TIME_FORMAT) : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      definitionId: this.editForm.get(['definitionId'])!.value,
      stepId: this.editForm.get(['stepId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISequenceStepAction>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
