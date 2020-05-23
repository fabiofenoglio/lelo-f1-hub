import { Component, OnInit, OnDestroy } from '@angular/core';
import { NGXLogger } from 'ngx-logger';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { SequenceService } from 'app/entities/sequence/sequence.service';
import { JhiParseLinks } from 'ng-jhipster';
import { Subject } from 'rxjs';
import { ISequence } from 'app/shared/model/sequence.model';
import { HttpResponse, HttpHeaders } from '@angular/common/http';
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';
import { backgroundFetch } from 'app/shared/util/request-util';


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

  availableSorting = [
    { field: 'name', label: 'sort by name', directions: [true, false], defaultDirection: true, mobile: true },
    { field: 'averageRating', label: 'highest rated', directions: [false], defaultDirection: false, mobile: true },
    { field: 'lastModifiedDate', label: 'sort by last update', directions: [true, false], defaultDirection: false },
    { field: 'userLogin', label: 'sort by user', directions: [true, false], defaultDirection: true, mobile: true },
  ];

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
    this.predicate = 'averageRating';
    this.ascending = false;
  }

  toggleSort(fieldName: string): void {
    const option: any = this.availableSorting.find(o => o.field === fieldName);
    if (!option) {
      if (this.predicate === fieldName) {
        this.predicate = fieldName;
        this.ascending = true;
      } else {
        this.ascending = !this.ascending;
      }
      this.reset();
    } else if (this.predicate === option.field) {
      if (option.directions.indexOf(!this.ascending) === -1) {
        // do nothing
      } else {
        this.ascending = !this.ascending;
        this.reset();
      }
    } else {
      this.predicate = option.field;
      this.ascending = option.defaultDirection;
      this.reset();
    }
  }

  loadOwn(): void {
    this.sequenceService
    .query({
      page: 0,
      size: 1000,
      sort: this.sortOwn(),
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
        sort: this.sortShared(),
        shared: true,
        fullTextSearch: this.fullTextSearchShared || ''
      }, {
        ...backgroundFetch(this.page > 0)
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

  sortOwn(): string[] {
    if (this.predicate === 'averageRating' || this.predicate === 'userLogin') {
      return ['name,asc'];
    }

    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'name') {
      result.push('name');
    }
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  sortShared(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
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

  pick(sequence: ISequence): void {
    this.activeModal.close(sequence);
  }

  cancel(): void {
    this.activeModal.dismiss();
  }
}
