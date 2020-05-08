import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISequenceStepActionDefinition } from 'app/shared/model/sequence-step-action-definition.model';
import { SequenceStepActionDefinitionService } from './sequence-step-action-definition.service';

@Component({
  templateUrl: './sequence-step-action-definition-delete-dialog.component.html'
})
export class SequenceStepActionDefinitionDeleteDialogComponent {
  sequenceStepActionDefinition?: ISequenceStepActionDefinition;

  constructor(
    protected sequenceStepActionDefinitionService: SequenceStepActionDefinitionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sequenceStepActionDefinitionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('sequenceStepActionDefinitionListModification');
      this.activeModal.close();
    });
  }
}
