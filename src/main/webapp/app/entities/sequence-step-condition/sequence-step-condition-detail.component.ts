import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISequenceStepCondition } from 'app/shared/model/sequence-step-condition.model';

@Component({
  selector: 'jhi-sequence-step-condition-detail',
  templateUrl: './sequence-step-condition-detail.component.html'
})
export class SequenceStepConditionDetailComponent implements OnInit {
  sequenceStepCondition: ISequenceStepCondition | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sequenceStepCondition }) => (this.sequenceStepCondition = sequenceStepCondition));
  }

  previousState(): void {
    window.history.back();
  }
}
