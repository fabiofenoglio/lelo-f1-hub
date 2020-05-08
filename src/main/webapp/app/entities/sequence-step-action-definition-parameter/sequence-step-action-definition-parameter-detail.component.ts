import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISequenceStepActionDefinitionParameter } from 'app/shared/model/sequence-step-action-definition-parameter.model';

@Component({
  selector: 'jhi-sequence-step-action-definition-parameter-detail',
  templateUrl: './sequence-step-action-definition-parameter-detail.component.html'
})
export class SequenceStepActionDefinitionParameterDetailComponent implements OnInit {
  sequenceStepActionDefinitionParameter: ISequenceStepActionDefinitionParameter | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(
      ({ sequenceStepActionDefinitionParameter }) => (this.sequenceStepActionDefinitionParameter = sequenceStepActionDefinitionParameter)
    );
  }

  previousState(): void {
    window.history.back();
  }
}
