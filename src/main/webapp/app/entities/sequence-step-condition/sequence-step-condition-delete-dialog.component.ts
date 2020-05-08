import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISequenceStepCondition } from 'app/shared/model/sequence-step-condition.model';
import { SequenceStepConditionService } from './sequence-step-condition.service';

@Component({
  templateUrl: './sequence-step-condition-delete-dialog.component.html'
})
export class SequenceStepConditionDeleteDialogComponent {
  sequenceStepCondition?: ISequenceStepCondition;

  constructor(
    protected sequenceStepConditionService: SequenceStepConditionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sequenceStepConditionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('sequenceStepConditionListModification');
      this.activeModal.close();
    });
  }
}
