import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISequenceStepConditionDefinitionParameter } from 'app/shared/model/sequence-step-condition-definition-parameter.model';

@Component({
  selector: 'jhi-sequence-step-condition-definition-parameter-detail',
  templateUrl: './sequence-step-condition-definition-parameter-detail.component.html'
})
export class SequenceStepConditionDefinitionParameterDetailComponent implements OnInit {
  sequenceStepConditionDefinitionParameter: ISequenceStepConditionDefinitionParameter | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(
      ({ sequenceStepConditionDefinitionParameter }) =>
        (this.sequenceStepConditionDefinitionParameter = sequenceStepConditionDefinitionParameter)
    );
  }

  previousState(): void {
    window.history.back();
  }
}
