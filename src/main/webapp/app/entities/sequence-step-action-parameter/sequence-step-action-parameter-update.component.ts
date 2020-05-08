import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ISequenceStepActionParameter, SequenceStepActionParameter } from 'app/shared/model/sequence-step-action-parameter.model';
import { SequenceStepActionParameterService } from './sequence-step-action-parameter.service';
import { ISequenceStepActionDefinitionParameter } from 'app/shared/model/sequence-step-action-definition-parameter.model';
import { SequenceStepActionDefinitionParameterService } from 'app/entities/sequence-step-action-definition-parameter/sequence-step-action-definition-parameter.service';
import { ISequenceStepAction } from 'app/shared/model/sequence-step-action.model';
import { SequenceStepActionService } from 'app/entities/sequence-step-action/sequence-step-action.service';

type SelectableEntity = ISequenceStepActionDefinitionParameter | ISequenceStepAction;

@Component({
  selector: 'jhi-sequence-step-action-parameter-update',
  templateUrl: './sequence-step-action-parameter-update.component.html'
})
export class SequenceStepActionParameterUpdateComponent implements OnInit {
  isSaving = false;
  sequencestepactiondefinitionparameters: ISequenceStepActionDefinitionParameter[] = [];
  sequencestepactions: ISequenceStepAction[] = [];

  editForm = this.fb.group({
    id: [],
    generation: [null, [Validators.required]],
    valueString: [],
    valueNumber: [],
    valueBoolean: [],
    valueVariable: [],
    createdDate: [null, [Validators.required]],
    createdBy: [null, [Validators.required]],
    lastModifiedDate: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
    definitionId: [null, Validators.required],
    actionId: [null, Validators.required]
  });

  constructor(
    protected sequenceStepActionParameterService: SequenceStepActionParameterService,
    protected sequenceStepActionDefinitionParameterService: SequenceStepActionDefinitionParameterService,
    protected sequenceStepActionService: SequenceStepActionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sequenceStepActionParameter }) => {
      if (!sequenceStepActionParameter.id) {
        const today = moment().startOf('day');
        sequenceStepActionParameter.createdDate = today;
        sequenceStepActionParameter.lastModifiedDate = today;
      }

      this.updateForm(sequenceStepActionParameter);

      this.sequenceStepActionDefinitionParameterService
        .query()
        .subscribe(
          (res: HttpResponse<ISequenceStepActionDefinitionParameter[]>) => (this.sequencestepactiondefinitionparameters = res.body || [])
        );

      this.sequenceStepActionService
        .query()
        .subscribe((res: HttpResponse<ISequenceStepAction[]>) => (this.sequencestepactions = res.body || []));
    });
  }

  updateForm(sequenceStepActionParameter: ISequenceStepActionParameter): void {
    this.editForm.patchValue({
      id: sequenceStepActionParameter.id,
      generation: sequenceStepActionParameter.generation,
      valueString: sequenceStepActionParameter.valueString,
      valueNumber: sequenceStepActionParameter.valueNumber,
      valueBoolean: sequenceStepActionParameter.valueBoolean,
      valueVariable: sequenceStepActionParameter.valueVariable,
      createdDate: sequenceStepActionParameter.createdDate ? sequenceStepActionParameter.createdDate.format(DATE_TIME_FORMAT) : null,
      createdBy: sequenceStepActionParameter.createdBy,
      lastModifiedDate: sequenceStepActionParameter.lastModifiedDate ? sequenceStepActionParameter.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: sequenceStepActionParameter.lastModifiedBy,
      definitionId: sequenceStepActionParameter.definitionId,
      actionId: sequenceStepActionParameter.actionId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sequenceStepActionParameter = this.createFromForm();
    if (sequenceStepActionParameter.id !== undefined) {
      this.subscribeToSaveResponse(this.sequenceStepActionParameterService.update(sequenceStepActionParameter));
    } else {
      this.subscribeToSaveResponse(this.sequenceStepActionParameterService.create(sequenceStepActionParameter));
    }
  }

  private createFromForm(): ISequenceStepActionParameter {
    return {
      ...new SequenceStepActionParameter(),
      id: this.editForm.get(['id'])!.value,
      generation: this.editForm.get(['generation'])!.value,
      valueString: this.editForm.get(['valueString'])!.value,
      valueNumber: this.editForm.get(['valueNumber'])!.value,
      valueBoolean: this.editForm.get(['valueBoolean'])!.value,
      valueVariable: this.editForm.get(['valueVariable'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value ? moment(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT) : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      lastModifiedDate: this.editForm.get(['lastModifiedDate'])!.value ? moment(this.editForm.get(['lastModifiedDate'])!.value, DATE_TIME_FORMAT) : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      definitionId: this.editForm.get(['definitionId'])!.value,
      actionId: this.editForm.get(['actionId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISequenceStepActionParameter>>): void {
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
