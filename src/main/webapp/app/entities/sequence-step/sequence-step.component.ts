import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISequenceStep } from 'app/shared/model/sequence-step.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { SequenceStepService } from './sequence-step.service';
import { SequenceStepDeleteDialogComponent } from './sequence-step-delete-dialog.component';

@Component({
  selector: 'jhi-sequence-step',
  templateUrl: './sequence-step.component.html'
})
export class SequenceStepComponent implements OnInit, OnDestroy {
  sequenceSteps: ISequenceStep[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected sequenceStepService: SequenceStepService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.sequenceSteps = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.sequenceStepService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ISequenceStep[]>) => this.paginateSequenceSteps(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.sequenceSteps = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSequenceSteps();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISequenceStep): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSequenceSteps(): void {
    this.eventSubscriber = this.eventManager.subscribe('sequenceStepListModification', () => this.reset());
  }

  delete(sequenceStep: ISequenceStep): void {
    const modalRef = this.modalService.open(SequenceStepDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.sequenceStep = sequenceStep;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateSequenceSteps(data: ISequenceStep[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.sequenceSteps.push(data[i]);
      }
    }
  }
}
