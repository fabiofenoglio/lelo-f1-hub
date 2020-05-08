import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ISequenceStep, SequenceStep } from 'app/shared/model/sequence-step.model';
import { SequenceStepService } from './sequence-step.service';
import { ISequence } from 'app/shared/model/sequence.model';
import { SequenceService } from 'app/entities/sequence/sequence.service';

@Component({
  selector: 'jhi-sequence-step-update',
  templateUrl: './sequence-step-update.component.html'
})
export class SequenceStepUpdateComponent implements OnInit {
  isSaving = false;
  sequences: ISequence[] = [];

  editForm = this.fb.group({
    id: [],
    generation: [null, [Validators.required]],
    ordinal: [null, [Validators.required]],
    description: [null, [Validators.maxLength(250)]],
    createdDate: [null, [Validators.required]],
    createdBy: [null, [Validators.required]],
    lastModifiedDate: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
    sequenceId: [null, Validators.required]
  });

  constructor(
    protected sequenceStepService: SequenceStepService,
    protected sequenceService: SequenceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sequenceStep }) => {
      if (!sequenceStep.id) {
        const today = moment().startOf('day');
        sequenceStep.createdDate = today;
        sequenceStep.lastModifiedDate = today;
      }

      this.updateForm(sequenceStep);

      this.sequenceService.query().subscribe((res: HttpResponse<ISequence[]>) => (this.sequences = res.body || []));
    });
  }

  updateForm(sequenceStep: ISequenceStep): void {
    this.editForm.patchValue({
      id: sequenceStep.id,
      generation: sequenceStep.generation,
      ordinal: sequenceStep.ordinal,
      description: sequenceStep.description,
      createdDate: sequenceStep.createdDate ? sequenceStep.createdDate.format(DATE_TIME_FORMAT) : null,
      createdBy: sequenceStep.createdBy,
      lastModifiedDate: sequenceStep.lastModifiedDate ? sequenceStep.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: sequenceStep.lastModifiedBy,
      sequenceId: sequenceStep.sequenceId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sequenceStep = this.createFromForm();
    if (sequenceStep.id !== undefined) {
      this.subscribeToSaveResponse(this.sequenceStepService.update(sequenceStep));
    } else {
      this.subscribeToSaveResponse(this.sequenceStepService.create(sequenceStep));
    }
  }

  private createFromForm(): ISequenceStep {
    return {
      ...new SequenceStep(),
      id: this.editForm.get(['id'])!.value,
      generation: this.editForm.get(['generation'])!.value,
      ordinal: this.editForm.get(['ordinal'])!.value,
      description: this.editForm.get(['description'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value ? moment(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT) : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      lastModifiedDate: this.editForm.get(['lastModifiedDate'])!.value ? moment(this.editForm.get(['lastModifiedDate'])!.value, DATE_TIME_FORMAT) : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      sequenceId: this.editForm.get(['sequenceId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISequenceStep>>): void {
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

  trackById(index: number, item: ISequence): any {
    return item.id;
  }
}
