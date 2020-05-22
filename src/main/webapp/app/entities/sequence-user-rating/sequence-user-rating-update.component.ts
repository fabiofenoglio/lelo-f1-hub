import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ISequenceUserRating, SequenceUserRating } from 'app/shared/model/sequence-user-rating.model';
import { SequenceUserRatingService } from './sequence-user-rating.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { ISequence } from 'app/shared/model/sequence.model';
import { SequenceService } from 'app/entities/sequence/sequence.service';

type SelectableEntity = IUser | ISequence;

@Component({
  selector: 'jhi-sequence-user-rating-update',
  templateUrl: './sequence-user-rating-update.component.html'
})
export class SequenceUserRatingUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  sequences: ISequence[] = [];

  editForm = this.fb.group({
    id: [],
    createdDate: [null, [Validators.required]],
    createdBy: [null, [Validators.required]],
    lastModifiedDate: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
    score: [],
    userId: [null, Validators.required],
    sequenceId: [null, Validators.required]
  });

  constructor(
    protected sequenceUserRatingService: SequenceUserRatingService,
    protected userService: UserService,
    protected sequenceService: SequenceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sequenceUserRating }) => {
      if (!sequenceUserRating.id) {
        const today = moment().startOf('day');
        sequenceUserRating.createdDate = today;
        sequenceUserRating.lastModifiedDate = today;
      }

      this.updateForm(sequenceUserRating);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.sequenceService.query().subscribe((res: HttpResponse<ISequence[]>) => (this.sequences = res.body || []));
    });
  }

  updateForm(sequenceUserRating: ISequenceUserRating): void {
    this.editForm.patchValue({
      id: sequenceUserRating.id,
      createdDate: sequenceUserRating.createdDate ? sequenceUserRating.createdDate.format(DATE_TIME_FORMAT) : null,
      createdBy: sequenceUserRating.createdBy,
      lastModifiedDate: sequenceUserRating.lastModifiedDate ? sequenceUserRating.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: sequenceUserRating.lastModifiedBy,
      score: sequenceUserRating.score,
      userId: sequenceUserRating.userId,
      sequenceId: sequenceUserRating.sequenceId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sequenceUserRating = this.createFromForm();
    if (sequenceUserRating.id !== undefined) {
      this.subscribeToSaveResponse(this.sequenceUserRatingService.update(sequenceUserRating));
    } else {
      this.subscribeToSaveResponse(this.sequenceUserRatingService.create(sequenceUserRating));
    }
  }

  private createFromForm(): ISequenceUserRating {
    return {
      ...new SequenceUserRating(),
      id: this.editForm.get(['id'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? moment(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      lastModifiedDate: this.editForm.get(['lastModifiedDate'])!.value
        ? moment(this.editForm.get(['lastModifiedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      score: this.editForm.get(['score'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      sequenceId: this.editForm.get(['sequenceId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISequenceUserRating>>): void {
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
