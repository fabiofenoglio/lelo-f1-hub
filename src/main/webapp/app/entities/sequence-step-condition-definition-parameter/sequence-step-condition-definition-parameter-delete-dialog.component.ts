import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISequenceStepConditionDefinitionParameter } from 'app/shared/model/sequence-step-condition-definition-parameter.model';
import { SequenceStepConditionDefinitionParameterService } from './sequence-step-condition-definition-parameter.service';

@Component({
  templateUrl: './sequence-step-condition-definition-parameter-delete-dialog.component.html'
})
export class SequenceStepConditionDefinitionParameterDeleteDialogComponent {
  sequenceStepConditionDefinitionParameter?: ISequenceStepConditionDefinitionParameter;

  constructor(
    protected sequenceStepConditionDefinitionParameterService: SequenceStepConditionDefinitionParameterService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sequenceStepConditionDefinitionParameterService.delete(id).subscribe(() => {
      this.eventManager.broadcast('sequenceStepConditionDefinitionParameterListModification');
      this.activeModal.close();
    });
  }
}
