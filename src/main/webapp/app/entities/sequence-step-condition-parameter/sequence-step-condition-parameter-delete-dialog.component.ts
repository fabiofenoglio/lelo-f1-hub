import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISequenceStepConditionParameter } from 'app/shared/model/sequence-step-condition-parameter.model';
import { SequenceStepConditionParameterService } from './sequence-step-condition-parameter.service';

@Component({
  templateUrl: './sequence-step-condition-parameter-delete-dialog.component.html'
})
export class SequenceStepConditionParameterDeleteDialogComponent {
  sequenceStepConditionParameter?: ISequenceStepConditionParameter;

  constructor(
    protected sequenceStepConditionParameterService: SequenceStepConditionParameterService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sequenceStepConditionParameterService.delete(id).subscribe(() => {
      this.eventManager.broadcast('sequenceStepConditionParameterListModification');
      this.activeModal.close();
    });
  }
}
