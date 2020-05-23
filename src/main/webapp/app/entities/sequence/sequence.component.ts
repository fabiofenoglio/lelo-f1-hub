import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription, Subject } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ISequence, Sequence } from 'app/shared/model/sequence.model';
import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { SequenceService } from './sequence.service';
import { SequenceDeleteDialogComponent } from './sequence-delete-dialog.component';
import { Router } from '@angular/router';
import { debounceTime, distinctUntilChanged, finalize } from 'rxjs/operators';
import { SequenceUserRatingService } from '../sequence-user-rating/sequence-user-rating.service';
import { backgroundFetch } from 'app/shared/util/request-util';


@Component({
  selector: 'jhi-sequence',
  templateUrl: './sequence.component.html'
})
export class SequenceComponent implements OnInit, OnDestroy {
  sequences: ISequence[];
  sharedSequences: ISequence[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;
  ownPredicate: string;
  ownAscending: boolean;

  loading = 0;

  fullTextSearchShared = '';
  fullTextSearchSharedChanged: Subject<string> = new Subject<string>();

  availableOwnSorting = [
    { field: 'name', label: 'sort by name', directions: [true, false], defaultDirection: true, mobile: true },
    { field: 'averageRating', label: 'highest rated', directions: [false], defaultDirection: false, mobile: true },
    { field: 'lastModifiedDate', label: 'sort by last update', directions: [true, false], defaultDirection: false }
  ];

  availableCommunitySorting = [
    { field: 'name', label: 'sort by name', directions: [true, false], defaultDirection: true, mobile: true },
    { field: 'averageRating', label: 'highest rated', directions: [false], defaultDirection: false, mobile: true },
    { field: 'lastModifiedDate', label: 'sort by last update', directions: [true, false], defaultDirection: false },
    { field: 'userLogin', label: 'sort by user', directions: [true, false], defaultDirection: true, mobile: true },
  ];

  constructor(
    protected sequenceService: SequenceService,
    protected ratingService: SequenceUserRatingService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks,
    protected router: Router
  ) {
    this.sequences = [];
    this.sharedSequences = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'averageRating';
    this.ascending = false;
    this.ownPredicate = 'name';
    this.ownAscending = true;
  }

  toggleSortOwn(fieldName: string): void {
    const option: any = this.availableOwnSorting.find(o => o.field === fieldName);
    if (!option) {
      if (this.ownPredicate === fieldName) {
        this.ownPredicate = fieldName;
        this.ownAscending = true;
      } else {
        this.ownAscending = !this.ownAscending;
      }
      this.resetOwn();
    } else if (this.ownPredicate === option.field) {
      if (option.directions.indexOf(!this.ownAscending) === -1) {
        // do nothing
      } else {
        this.ownAscending = !this.ownAscending;
        this.resetOwn();
      }
    } else {
      this.ownPredicate = option.field;
      this.ownAscending = option.defaultDirection;
      this.resetOwn();
    }
  }

  toggleSort(fieldName: string): void {
    const option: any = this.availableCommunitySorting.find(o => o.field === fieldName);
    if (!option) {
      if (this.predicate === fieldName) {
        this.predicate = fieldName;
        this.ascending = true;
      } else {
        this.ascending = !this.ascending;
      }
      this.resetShared();
    } else if (this.predicate === option.field) {
      if (option.directions.indexOf(!this.ascending) === -1) {
        // do nothing
      } else {
        this.ascending = !this.ascending;
        this.resetShared();
      }
    } else {
      this.predicate = option.field;
      this.ascending = option.defaultDirection;
      this.resetShared();
    }
  }

  loadOwn(reset = false): void {
    this.loading ++;
    this.sequenceService
    .query({
      page: 0,
      size: 1000,
      sort: this.sortOwn(),
      own: true
    })
    .pipe(finalize(() => this.loading --))
    .subscribe((res: HttpResponse<ISequence[]>) => {
      if (reset) {
        this.sequences = [];
      }
      this.paginateSequences(res.body, res.headers, true);
    });
  }

  loadAll(reset = false): void {
    this.loading ++;
    this.sequenceService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
        shared: true,
        fullTextSearch: this.fullTextSearchShared || ''
      }, {
        ...backgroundFetch(this.page > 0)
      })
      .pipe(finalize(() => this.loading --))
      .subscribe((res: HttpResponse<ISequence[]>) => {
        if (reset) {
          this.sharedSequences = [];
        }
        this.paginateSequences(res.body, res.headers, false);
      });
  }

  resetOwn(): void {
    this.loadOwn(true);
  }

  resetShared(): void {
    this.page = 0;
    this.loadAll(true);
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadOwn();
    this.loadAll();
    this.registerChangeInSequences();
    
    this.fullTextSearchSharedChanged.pipe(
      debounceTime(300),
      distinctUntilChanged())
          .subscribe(() => {
            this.searchTermSharedChangedDebounced();
          });
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  searchTermSharedChanged(event: KeyboardEvent): void {
    if (event) {
      this.fullTextSearchSharedChanged.next(this.fullTextSearchShared);
    }
  }

  searchTermSharedChangedDebounced(): void {
    this.resetShared();
  }

  trackId(index: number, item: ISequence): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSequences(): void {
    this.eventSubscriber = this.eventManager.subscribe('sequenceListModification', () => this.resetOwn());
  }

  delete(sequence: ISequence): void {
    const modalRef = this.modalService.open(SequenceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.sequence = sequence;
  }

  sortOwn(): string[] {
    const result = [this.ownPredicate + ',' + (this.ownAscending ? 'asc' : 'desc')];
    if (this.ownPredicate !== 'name') {
      result.push('name');
    }
    if (this.ownPredicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (
      this.predicate === 'averageRating' ? 'desc' :
      this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'name') {
      result.push('name');
    }
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateSequences(data: ISequence[] | null, headers: HttpHeaders, own: boolean): void {
    if (own) {
      if (data) {
        for (let i = 0; i < data.length; i++) {
          this.sequences.push(data[i]);
        }
      }
    } else {
      const headersLink = headers.get('link');
      this.links = this.parseLinks.parse(headersLink ? headersLink : '');
      if (data) {
        for (let i = 0; i < data.length; i++) {
          this.sharedSequences.push(data[i]);
        }
      }
    }
  }

  clone(id: number): void {
    this.sequenceService.clone(id).subscribe(created => {
      this.router.navigate(['/sequence', created.body?.id, 'edit']);
    });
  }

  rate(sequence: Sequence, vote: number): void {
    // eslint-disable-next-line no-console
    console.log('EVENT', vote);

    if (vote) {
      this.ratingService.vote(sequence.id!, vote).subscribe(() => {
        this.singleElementChanged(sequence);
      }, () => this.singleElementChanged(sequence) );
    } else {
      this.ratingService.unvote(sequence.id!).subscribe(() => {
        this.singleElementChanged(sequence);
      }, () => this.singleElementChanged(sequence) );
    }
  }

  singleElementChanged(sequence: Sequence): void {
    this.sequenceService
    .query({
      page: 0,
      size: 1,
      'id.equals': sequence.id
    })
    .subscribe((res: HttpResponse<ISequence[]>) => {
      if (res.body?.length) {
        const updated = res.body.find(o => o.id === sequence.id);
        if (updated) {
          Object.assign(sequence, updated);
        }
      }
    });
  }
}
