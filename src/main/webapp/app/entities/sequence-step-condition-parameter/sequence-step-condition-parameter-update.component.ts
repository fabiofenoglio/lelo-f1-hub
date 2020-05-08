import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ISequenceStepConditionParameter, SequenceStepConditionParameter } from 'app/shared/model/sequence-step-condition-parameter.model';
import { SequenceStepConditionParameterService } from './sequence-step-condition-parameter.service';
import { ISequenceStepConditionDefinitionParameter } from 'app/shared/model/sequence-step-condition-definition-parameter.model';
import { SequenceStepConditionDefinitionParameterService } from 'app/entities/sequence-step-condition-definition-parameter/sequence-step-condition-definition-parameter.service';
import { ISequenceStepCondition } from 'app/shared/model/sequence-step-condition.model';
import { SequenceStepConditionService } from 'app/entities/sequence-step-condition/sequence-step-condition.service';

type SelectableEntity = ISequenceStepConditionDefinitionParameter | ISequenceStepCondition;

@Component({
  selector: 'jhi-sequence-step-condition-parameter-update',
  templateUrl: './sequence-step-condition-parameter-update.component.html'
})
export class SequenceStepConditionParameterUpdateComponent implements OnInit {
  isSaving = false;
  sequencestepconditiondefinitionparameters: ISequenceStepConditionDefinitionParameter[] = [];
  sequencestepconditions: ISequenceStepCondition[] = [];

  editForm = this.fb.group({
    id: [],
    generation: [null, [Validators.required]],
    valueString: [],
    valueNumber: [],
    valueBoolean: [],
    valueVariable: [],
    valueOperator: [],
    createdDate: [null, [Validators.required]],
    createdBy: [null, [Validators.required]],
    lastModifiedDate: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
    definitionId: [null, Validators.required],
    conditionId: [null, Validators.required]
  });

  constructor(
    protected sequenceStepConditionParameterService: SequenceStepConditionParameterService,
    protected sequenceStepConditionDefinitionParameterService: SequenceStepConditionDefinitionParameterService,
    protected sequenceStepConditionService: SequenceStepConditionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sequenceStepConditionParameter }) => {
      if (!sequenceStepConditionParameter.id) {
        const today = moment().startOf('day');
        sequenceStepConditionParameter.createdDate = today;
        sequenceStepConditionParameter.lastModifiedDate = today;
      }

      this.updateForm(sequenceStepConditionParameter);

      this.sequenceStepConditionDefinitionParameterService
        .query()
        .subscribe(
          (res: HttpResponse<ISequenceStepConditionDefinitionParameter[]>) =>
            (this.sequencestepconditiondefinitionparameters = res.body || [])
        );

      this.sequenceStepConditionService
        .query()
        .subscribe((res: HttpResponse<ISequenceStepCondition[]>) => (this.sequencestepconditions = res.body || []));
    });
  }

  updateForm(sequenceStepConditionParameter: ISequenceStepConditionParameter): void {
    this.editForm.patchValue({
      id: sequenceStepConditionParameter.id,
      generation: sequenceStepConditionParameter.generation,
      valueString: sequenceStepConditionParameter.valueString,
      valueNumber: sequenceStepConditionParameter.valueNumber,
      valueBoolean: sequenceStepConditionParameter.valueBoolean,
      valueVariable: sequenceStepConditionParameter.valueVariable,
      valueOperator: sequenceStepConditionParameter.valueOperator,
      createdDate: sequenceStepConditionParameter.createdDate ? sequenceStepConditionParameter.createdDate.format(DATE_TIME_FORMAT) : null,
      createdBy: sequenceStepConditionParameter.createdBy,
      lastModifiedDate: sequenceStepConditionParameter.lastModifiedDate ? sequenceStepConditionParameter.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: sequenceStepConditionParameter.lastModifiedBy,
      definitionId: sequenceStepConditionParameter.definitionId,
      conditionId: sequenceStepConditionParameter.conditionId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sequenceStepConditionParameter = this.createFromForm();
    if (sequenceStepConditionParameter.id !== undefined) {
      this.subscribeToSaveResponse(this.sequenceStepConditionParameterService.update(sequenceStepConditionParameter));
    } else {
      this.subscribeToSaveResponse(this.sequenceStepConditionParameterService.create(sequenceStepConditionParameter));
    }
  }

  private createFromForm(): ISequenceStepConditionParameter {
    return {
      ...new SequenceStepConditionParameter(),
      id: this.editForm.get(['id'])!.value,
      generation: this.editForm.get(['generation'])!.value,
      valueString: this.editForm.get(['valueString'])!.value,
      valueNumber: this.editForm.get(['valueNumber'])!.value,
      valueBoolean: this.editForm.get(['valueBoolean'])!.value,
      valueVariable: this.editForm.get(['valueVariable'])!.value,
      valueOperator: this.editForm.get(['valueOperator'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value ? moment(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT) : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      lastModifiedDate: this.editForm.get(['lastModifiedDate'])!.value ? moment(this.editForm.get(['lastModifiedDate'])!.value, DATE_TIME_FORMAT) : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      definitionId: this.editForm.get(['definitionId'])!.value,
      conditionId: this.editForm.get(['conditionId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISequenceStepConditionParameter>>): void {
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
