import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISequenceStepAction } from 'app/shared/model/sequence-step-action.model';

@Component({
  selector: 'jhi-sequence-step-action-detail',
  templateUrl: './sequence-step-action-detail.component.html'
})
export class SequenceStepActionDetailComponent implements OnInit {
  sequenceStepAction: ISequenceStepAction | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sequenceStepAction }) => (this.sequenceStepAction = sequenceStepAction));
  }

  previousState(): void {
    window.history.back();
  }
}
