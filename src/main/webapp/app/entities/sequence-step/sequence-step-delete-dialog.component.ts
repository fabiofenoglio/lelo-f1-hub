import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISequenceStep } from 'app/shared/model/sequence-step.model';
import { SequenceStepService } from './sequence-step.service';

@Component({
  templateUrl: './sequence-step-delete-dialog.component.html'
})
export class SequenceStepDeleteDialogComponent {
  sequenceStep?: ISequenceStep;

  constructor(
    protected sequenceStepService: SequenceStepService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sequenceStepService.delete(id).subscribe(() => {
      this.eventManager.broadcast('sequenceStepListModification');
      this.activeModal.close();
    });
  }
}
