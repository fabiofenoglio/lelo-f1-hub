import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISequenceStepActionParameter } from 'app/shared/model/sequence-step-action-parameter.model';
import { SequenceStepActionParameterService } from './sequence-step-action-parameter.service';

@Component({
  templateUrl: './sequence-step-action-parameter-delete-dialog.component.html'
})
export class SequenceStepActionParameterDeleteDialogComponent {
  sequenceStepActionParameter?: ISequenceStepActionParameter;

  constructor(
    protected sequenceStepActionParameterService: SequenceStepActionParameterService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sequenceStepActionParameterService.delete(id).subscribe(() => {
      this.eventManager.broadcast('sequenceStepActionParameterListModification');
      this.activeModal.close();
    });
  }
}
