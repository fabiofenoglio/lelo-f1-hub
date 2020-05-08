import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISequence } from 'app/shared/model/sequence.model';

@Component({
  selector: 'jhi-sequence-detail',
  templateUrl: './sequence-detail.component.html'
})
export class SequenceDetailComponent implements OnInit {
  sequence: ISequence | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sequence }) => (this.sequence = sequence));
  }

  previousState(): void {
    window.history.back();
  }
}
