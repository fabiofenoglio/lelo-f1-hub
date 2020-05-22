import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISequenceUserRating } from 'app/shared/model/sequence-user-rating.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { SequenceUserRatingService } from './sequence-user-rating.service';
import { SequenceUserRatingDeleteDialogComponent } from './sequence-user-rating-delete-dialog.component';

@Component({
  selector: 'jhi-sequence-user-rating',
  templateUrl: './sequence-user-rating.component.html'
})
export class SequenceUserRatingComponent implements OnInit, OnDestroy {
  sequenceUserRatings: ISequenceUserRating[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected sequenceUserRatingService: SequenceUserRatingService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.sequenceUserRatings = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.sequenceUserRatingService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ISequenceUserRating[]>) => this.paginateSequenceUserRatings(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.sequenceUserRatings = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSequenceUserRatings();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISequenceUserRating): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSequenceUserRatings(): void {
    this.eventSubscriber = this.eventManager.subscribe('sequenceUserRatingListModification', () => this.reset());
  }

  delete(sequenceUserRating: ISequenceUserRating): void {
    const modalRef = this.modalService.open(SequenceUserRatingDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.sequenceUserRating = sequenceUserRating;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateSequenceUserRatings(data: ISequenceUserRating[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.sequenceUserRatings.push(data[i]);
      }
    }
  }
}
