import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISequenceStepActionDefinitionParameter } from 'app/shared/model/sequence-step-action-definition-parameter.model';
import { SequenceStepActionDefinitionParameterService } from './sequence-step-action-definition-parameter.service';

@Component({
  templateUrl: './sequence-step-action-definition-parameter-delete-dialog.component.html'
})
export class SequenceStepActionDefinitionParameterDeleteDialogComponent {
  sequenceStepActionDefinitionParameter?: ISequenceStepActionDefinitionParameter;

  constructor(
    protected sequenceStepActionDefinitionParameterService: SequenceStepActionDefinitionParameterService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sequenceStepActionDefinitionParameterService.delete(id).subscribe(() => {
      this.eventManager.broadcast('sequenceStepActionDefinitionParameterListModification');
      this.activeModal.close();
    });
  }
}
