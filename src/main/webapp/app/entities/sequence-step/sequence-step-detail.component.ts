import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISequenceStep } from 'app/shared/model/sequence-step.model';

@Component({
  selector: 'jhi-sequence-step-detail',
  templateUrl: './sequence-step-detail.component.html'
})
export class SequenceStepDetailComponent implements OnInit {
  sequenceStep: ISequenceStep | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sequenceStep }) => (this.sequenceStep = sequenceStep));
  }

  previousState(): void {
    window.history.back();
  }
}
