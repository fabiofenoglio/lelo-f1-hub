import { Component, OnInit, OnDestroy } from '@angular/core';
import { NGXLogger } from 'ngx-logger';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { SequenceService } from 'app/entities/sequence/sequence.service';
import { JhiParseLinks } from 'ng-jhipster';
import { Subject } from 'rxjs';
import { ISequence } from 'app/shared/model/sequence.model';
import { HttpResponse, HttpHeaders } from '@angular/common/http';
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';


@Component({
  selector: 'jhi-sequence-picker-modal',
  templateUrl: './sequence-picker-modal.component.html',
  styleUrls: ['./sequence-picker-modal.component.scss']
})
export class SequencePickerModalComponent implements OnInit, OnDestroy {
  
  sequences: ISequence[];
  sharedSequences: ISequence[];
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  fullTextSearchShared = '';
  fullTextSearchSharedChanged: Subject<string> = new Subject<string>();

  constructor(private logger: NGXLogger, 
    private sequenceService: SequenceService,
    protected parseLinks: JhiParseLinks,
    private activeModal: NgbActiveModal) {
    this.sequences = [];
    this.sharedSequences = [];
    this.itemsPerPage = 5;
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
      own: true,
      fullTextSearch: this.fullTextSearchShared || ''
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

  reset(): void {
    this.resetOwn();
    this.resetShared();
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
    
    this.fullTextSearchSharedChanged.pipe(
      debounceTime(300),
      distinctUntilChanged())
          .subscribe(() => {
            this.searchTermSharedChangedDebounced();
          });
  }

  ngOnDestroy(): void {
    // NOP
  }

  searchTermSharedChanged(event: KeyboardEvent): void {
    if (event) {
      this.fullTextSearchSharedChanged.next(this.fullTextSearchShared);
    }
  }

  searchTermSharedChangedDebounced(): void {
    this.reset();
  }

  trackId(index: number, item: ISequence): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
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

  pick(sequence: ISequence): void {
    this.activeModal.close(sequence);
  }

  cancel(): void {
    this.activeModal.dismiss();
  }
}
