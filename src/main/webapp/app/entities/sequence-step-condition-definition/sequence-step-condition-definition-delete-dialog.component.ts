import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISequenceStepConditionDefinition } from 'app/shared/model/sequence-step-condition-definition.model';
import { SequenceStepConditionDefinitionService } from './sequence-step-condition-definition.service';

@Component({
  templateUrl: './sequence-step-condition-definition-delete-dialog.component.html'
})
export class SequenceStepConditionDefinitionDeleteDialogComponent {
  sequenceStepConditionDefinition?: ISequenceStepConditionDefinition;

  constructor(
    protected sequenceStepConditionDefinitionService: SequenceStepConditionDefinitionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sequenceStepConditionDefinitionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('sequenceStepConditionDefinitionListModification');
      this.activeModal.close();
    });
  }
}
