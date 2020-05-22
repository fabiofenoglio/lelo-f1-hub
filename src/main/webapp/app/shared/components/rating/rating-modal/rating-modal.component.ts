import { Component, OnInit, OnDestroy } from '@angular/core';
import { NGXLogger } from 'ngx-logger';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiParseLinks } from 'ng-jhipster';
import { RatingComponent } from '../rating.component';


@Component({
  selector: 'jhi-rating-modal',
  templateUrl: './rating-modal.component.html',
  styleUrls: ['./rating-modal.component.scss']
})
export class RatingModalComponent implements OnInit, OnDestroy {
  
  parentComponent: RatingComponent | null = null;

  constructor(
    private logger: NGXLogger, 
    protected parseLinks: JhiParseLinks,
    private activeModal: NgbActiveModal
  ) {
    // NOP
  }

  ngOnInit(): void {
    // NOP
  }

  ngOnDestroy(): void {
    // NOP
  }

  changed(event: any): void {
    this.activeModal.close(event);
  }

  cancel(): void {
    this.activeModal.dismiss();
  }

}
