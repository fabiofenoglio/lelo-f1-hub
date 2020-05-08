import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISequenceStepActionParameter } from 'app/shared/model/sequence-step-action-parameter.model';

@Component({
  selector: 'jhi-sequence-step-action-parameter-detail',
  templateUrl: './sequence-step-action-parameter-detail.component.html'
})
export class SequenceStepActionParameterDetailComponent implements OnInit {
  sequenceStepActionParameter: ISequenceStepActionParameter | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(
      ({ sequenceStepActionParameter }) => (this.sequenceStepActionParameter = sequenceStepActionParameter)
    );
  }

  previousState(): void {
    window.history.back();
  }
}
