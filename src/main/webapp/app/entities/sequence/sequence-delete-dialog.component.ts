import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISequence } from 'app/shared/model/sequence.model';
import { SequenceService } from './sequence.service';

@Component({
  templateUrl: './sequence-delete-dialog.component.html'
})
export class SequenceDeleteDialogComponent {
  sequence?: ISequence;

  constructor(protected sequenceService: SequenceService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sequenceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('sequenceListModification');
      this.activeModal.close();
    });
  }
}
