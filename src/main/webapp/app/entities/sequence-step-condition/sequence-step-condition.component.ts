import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISequenceStepCondition } from 'app/shared/model/sequence-step-condition.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { SequenceStepConditionService } from './sequence-step-condition.service';
import { SequenceStepConditionDeleteDialogComponent } from './sequence-step-condition-delete-dialog.component';

@Component({
  selector: 'jhi-sequence-step-condition',
  templateUrl: './sequence-step-condition.component.html'
})
export class SequenceStepConditionComponent implements OnInit, OnDestroy {
  sequenceStepConditions: ISequenceStepCondition[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected sequenceStepConditionService: SequenceStepConditionService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.sequenceStepConditions = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.sequenceStepConditionService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ISequenceStepCondition[]>) => this.paginateSequenceStepConditions(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.sequenceStepConditions = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSequenceStepConditions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISequenceStepCondition): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSequenceStepConditions(): void {
    this.eventSubscriber = this.eventManager.subscribe('sequenceStepConditionListModification', () => this.reset());
  }

  delete(sequenceStepCondition: ISequenceStepCondition): void {
    const modalRef = this.modalService.open(SequenceStepConditionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.sequenceStepCondition = sequenceStepCondition;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateSequenceStepConditions(data: ISequenceStepCondition[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.sequenceStepConditions.push(data[i]);
      }
    }
  }
}
