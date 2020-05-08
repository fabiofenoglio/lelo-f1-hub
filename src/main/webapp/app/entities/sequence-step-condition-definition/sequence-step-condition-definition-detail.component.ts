import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISequenceStepConditionDefinition } from 'app/shared/model/sequence-step-condition-definition.model';

@Component({
  selector: 'jhi-sequence-step-condition-definition-detail',
  templateUrl: './sequence-step-condition-definition-detail.component.html'
})
export class SequenceStepConditionDefinitionDetailComponent implements OnInit {
  sequenceStepConditionDefinition: ISequenceStepConditionDefinition | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(
      ({ sequenceStepConditionDefinition }) => (this.sequenceStepConditionDefinition = sequenceStepConditionDefinition)
    );
  }

  previousState(): void {
    window.history.back();
  }
}
