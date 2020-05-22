import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISequenceUserRating } from 'app/shared/model/sequence-user-rating.model';

@Component({
  selector: 'jhi-sequence-user-rating-detail',
  templateUrl: './sequence-user-rating-detail.component.html'
})
export class SequenceUserRatingDetailComponent implements OnInit {
  sequenceUserRating: ISequenceUserRating | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sequenceUserRating }) => (this.sequenceUserRating = sequenceUserRating));
  }

  previousState(): void {
    window.history.back();
  }
}
