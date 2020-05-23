import { Component, OnInit, Input, Output, EventEmitter, AfterViewInit } from '@angular/core';
import { RatingModalComponent } from './rating-modal/rating-modal.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';


@Component({
  selector: 'jhi-rating',
  templateUrl: './rating.component.html',
  styleUrls: ['./rating.component.scss']
})
export class RatingComponent implements OnInit, AfterViewInit {

  @Input() mode: string | null = null;
  @Input() userRating: number | null = null;
  @Input() rating: number | null = null;
  @Input() ratingNumber: number | null = null;
  @Input() containerClasses: string[] | null = null;

  @Input() readonly = false;
  @Output() rated = new EventEmitter<number>();

  ready = false;

  constructor(
    private modalService: NgbModal
  ) { }

  ngOnInit(): void {
  }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.ready = true;
    }, 10);
  }

  get standardMode(): boolean {
    return !this.mode || this.mode === 'STANDARD';
  }

  get inPlaceMode(): boolean {
    return this.mode === 'IN_PLACE';
  }
  
  get isReadonly(): boolean {
    return this.readonly;
  }

  get hasUserRating(): boolean {
    return this.userRating !== null && typeof this.userRating !== 'undefined';
  }

  get hasRating(): boolean {
    return this.rating !== null && typeof this.rating !== 'undefined';
  }

  get ratingOrZero(): number {
    return this.rating || 0;
  }

  get userRatingOrZero(): number {
    return this.userRating || 0;
  }

  changed(event: any, firstVote: boolean): void {
    // eslint-disable-next-line no-console
    console.log('firstVote', firstVote);

    if (firstVote && event === 0) {
      this.rated.emit(this.ratingOrZero);
    } else {
      this.rated.emit(event);
    }
  }

  clickOnInPlaceRating(): void {
    if (this.isReadonly) {
      return;
    }

    const modal = this.modalService.open(RatingModalComponent, {
      backdrop: true,
      keyboard: true,
      size: 'md'
    });

    modal.componentInstance.parentComponent = this;

    modal.result.then(result => {
      this.changed(result, !this.hasUserRating);
    }, () => {});
  }
}
