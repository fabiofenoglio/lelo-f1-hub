import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISequenceStepAction } from 'app/shared/model/sequence-step-action.model';
import { SequenceStepActionService } from './sequence-step-action.service';

@Component({
  templateUrl: './sequence-step-action-delete-dialog.component.html'
})
export class SequenceStepActionDeleteDialogComponent {
  sequenceStepAction?: ISequenceStepAction;

  constructor(
    protected sequenceStepActionService: SequenceStepActionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sequenceStepActionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('sequenceStepActionListModification');
      this.activeModal.close();
    });
  }
}
