import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISequenceStepActionDefinition } from 'app/shared/model/sequence-step-action-definition.model';

@Component({
  selector: 'jhi-sequence-step-action-definition-detail',
  templateUrl: './sequence-step-action-definition-detail.component.html'
})
export class SequenceStepActionDefinitionDetailComponent implements OnInit {
  sequenceStepActionDefinition: ISequenceStepActionDefinition | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(
      ({ sequenceStepActionDefinition }) => (this.sequenceStepActionDefinition = sequenceStepActionDefinition)
    );
  }

  previousState(): void {
    window.history.back();
  }
}
