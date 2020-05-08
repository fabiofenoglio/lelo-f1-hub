import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISequenceStepActionParameter } from 'app/shared/model/sequence-step-action-parameter.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { SequenceStepActionParameterService } from './sequence-step-action-parameter.service';
import { SequenceStepActionParameterDeleteDialogComponent } from './sequence-step-action-parameter-delete-dialog.component';

@Component({
  selector: 'jhi-sequence-step-action-parameter',
  templateUrl: './sequence-step-action-parameter.component.html'
})
export class SequenceStepActionParameterComponent implements OnInit, OnDestroy {
  sequenceStepActionParameters: ISequenceStepActionParameter[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected sequenceStepActionParameterService: SequenceStepActionParameterService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.sequenceStepActionParameters = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.sequenceStepActionParameterService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ISequenceStepActionParameter[]>) => this.paginateSequenceStepActionParameters(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.sequenceStepActionParameters = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSequenceStepActionParameters();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISequenceStepActionParameter): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSequenceStepActionParameters(): void {
    this.eventSubscriber = this.eventManager.subscribe('sequenceStepActionParameterListModification', () => this.reset());
  }

  delete(sequenceStepActionParameter: ISequenceStepActionParameter): void {
    const modalRef = this.modalService.open(SequenceStepActionParameterDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.sequenceStepActionParameter = sequenceStepActionParameter;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateSequenceStepActionParameters(data: ISequenceStepActionParameter[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.sequenceStepActionParameters.push(data[i]);
      }
    }
  }
}
