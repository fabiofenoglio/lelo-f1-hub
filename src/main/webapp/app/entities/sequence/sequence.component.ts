import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription, Subject } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISequence } from 'app/shared/model/sequence.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { SequenceService } from './sequence.service';
import { SequenceDeleteDialogComponent } from './sequence-delete-dialog.component';
import { Router } from '@angular/router';
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';

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

  fullTextSearchShared = '';
  fullTextSearchSharedChanged: Subject<string> = new Subject<string>();

  constructor(
    protected sequenceService: SequenceService,
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
    this.predicate = 'name';
    this.ascending = true;
  }

  loadOwn(): void {
    this.sequenceService
    .query({
      page: 0,
      size: 1000,
      sort: this.sort(),
      own: true
    })
    .subscribe((res: HttpResponse<ISequence[]>) => this.paginateSequences(res.body, res.headers, true));
  }

  loadAll(): void {

    this.sequenceService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
        shared: true,
        fullTextSearch: this.fullTextSearchShared || ''
      })
      .subscribe((res: HttpResponse<ISequence[]>) => this.paginateSequences(res.body, res.headers, false));
  }

  resetOwn(): void {
    this.sequences = [];
    this.loadOwn();
  }

  resetShared(): void {
    this.page = 0;
    this.sharedSequences = [];
    this.loadAll();
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

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
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
}
