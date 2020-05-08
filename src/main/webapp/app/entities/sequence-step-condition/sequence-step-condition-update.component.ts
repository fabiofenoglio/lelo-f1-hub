import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ISequenceStepCondition, SequenceStepCondition } from 'app/shared/model/sequence-step-condition.model';
import { SequenceStepConditionService } from './sequence-step-condition.service';
import { ISequenceStepConditionDefinition } from 'app/shared/model/sequence-step-condition-definition.model';
import { SequenceStepConditionDefinitionService } from 'app/entities/sequence-step-condition-definition/sequence-step-condition-definition.service';
import { ISequenceStep } from 'app/shared/model/sequence-step.model';
import { SequenceStepService } from 'app/entities/sequence-step/sequence-step.service';

type SelectableEntity = ISequenceStepConditionDefinition | ISequenceStep | ISequenceStepCondition;

@Component({
  selector: 'jhi-sequence-step-condition-update',
  templateUrl: './sequence-step-condition-update.component.html'
})
export class SequenceStepConditionUpdateComponent implements OnInit {
  isSaving = false;
  sequencestepconditiondefinitions: ISequenceStepConditionDefinition[] = [];
  sequencesteps: ISequenceStep[] = [];
  sequencestepconditions: ISequenceStepCondition[] = [];

  editForm = this.fb.group({
    id: [],
    generation: [null, [Validators.required]],
    description: [null, [Validators.maxLength(250)]],
    negate: [],
    createdDate: [null, [Validators.required]],
    createdBy: [null, [Validators.required]],
    lastModifiedDate: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
    definitionId: [null, Validators.required],
    stepId: [],
    otherAndConditionId: [],
    otherOrConditionId: []
  });

  constructor(
    protected sequenceStepConditionService: SequenceStepConditionService,
    protected sequenceStepConditionDefinitionService: SequenceStepConditionDefinitionService,
    protected sequenceStepService: SequenceStepService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sequenceStepCondition }) => {
      if (!sequenceStepCondition.id) {
        const today = moment().startOf('day');
        sequenceStepCondition.createdDate = today;
        sequenceStepCondition.lastModifiedDate = today;
      }

      this.updateForm(sequenceStepCondition);

      this.sequenceStepConditionDefinitionService
        .query()
        .subscribe((res: HttpResponse<ISequenceStepConditionDefinition[]>) => (this.sequencestepconditiondefinitions = res.body || []));

      this.sequenceStepService.query().subscribe((res: HttpResponse<ISequenceStep[]>) => (this.sequencesteps = res.body || []));

      this.sequenceStepConditionService
        .query()
        .subscribe((res: HttpResponse<ISequenceStepCondition[]>) => (this.sequencestepconditions = res.body || []));
    });
  }

  updateForm(sequenceStepCondition: ISequenceStepCondition): void {
    this.editForm.patchValue({
      id: sequenceStepCondition.id,
      generation: sequenceStepCondition.generation,
      description: sequenceStepCondition.description,
      negate: sequenceStepCondition.negate,
      createdDate: sequenceStepCondition.createdDate ? sequenceStepCondition.createdDate.format(DATE_TIME_FORMAT) : null,
      createdBy: sequenceStepCondition.createdBy,
      lastModifiedDate: sequenceStepCondition.lastModifiedDate ? sequenceStepCondition.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: sequenceStepCondition.lastModifiedBy,
      definitionId: sequenceStepCondition.definitionId,
      stepId: sequenceStepCondition.stepId,
      otherAndConditionId: sequenceStepCondition.otherAndConditionId,
      otherOrConditionId: sequenceStepCondition.otherOrConditionId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sequenceStepCondition = this.createFromForm();
    if (sequenceStepCondition.id !== undefined) {
      this.subscribeToSaveResponse(this.sequenceStepConditionService.update(sequenceStepCondition));
    } else {
      this.subscribeToSaveResponse(this.sequenceStepConditionService.create(sequenceStepCondition));
    }
  }

  private createFromForm(): ISequenceStepCondition {
    return {
      ...new SequenceStepCondition(),
      id: this.editForm.get(['id'])!.value,
      generation: this.editForm.get(['generation'])!.value,
      description: this.editForm.get(['description'])!.value,
      negate: this.editForm.get(['negate'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value ? moment(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT) : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      lastModifiedDate: this.editForm.get(['lastModifiedDate'])!.value ? moment(this.editForm.get(['lastModifiedDate'])!.value, DATE_TIME_FORMAT) : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      definitionId: this.editForm.get(['definitionId'])!.value,
      stepId: this.editForm.get(['stepId'])!.value,
      otherAndConditionId: this.editForm.get(['otherAndConditionId'])!.value,
      otherOrConditionId: this.editForm.get(['otherOrConditionId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISequenceStepCondition>>): void {
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
