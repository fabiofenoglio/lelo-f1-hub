import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISequenceUserRating } from 'app/shared/model/sequence-user-rating.model';
import { SequenceUserRatingService } from './sequence-user-rating.service';

@Component({
  templateUrl: './sequence-user-rating-delete-dialog.component.html'
})
export class SequenceUserRatingDeleteDialogComponent {
  sequenceUserRating?: ISequenceUserRating;

  constructor(
    protected sequenceUserRatingService: SequenceUserRatingService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sequenceUserRatingService.delete(id).subscribe(() => {
      this.eventManager.broadcast('sequenceUserRatingListModification');
      this.activeModal.close();
    });
  }
}
