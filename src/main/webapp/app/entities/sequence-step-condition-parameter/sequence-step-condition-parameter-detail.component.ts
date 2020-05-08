import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISequenceStepConditionParameter } from 'app/shared/model/sequence-step-condition-parameter.model';

@Component({
  selector: 'jhi-sequence-step-condition-parameter-detail',
  templateUrl: './sequence-step-condition-parameter-detail.component.html'
})
export class SequenceStepConditionParameterDetailComponent implements OnInit {
  sequenceStepConditionParameter: ISequenceStepConditionParameter | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(
      ({ sequenceStepConditionParameter }) => (this.sequenceStepConditionParameter = sequenceStepConditionParameter)
    );
  }

  previousState(): void {
    window.history.back();
  }
}
