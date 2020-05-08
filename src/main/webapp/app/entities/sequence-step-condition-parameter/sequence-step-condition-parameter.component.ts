import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISequenceStepConditionParameter } from 'app/shared/model/sequence-step-condition-parameter.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { SequenceStepConditionParameterService } from './sequence-step-condition-parameter.service';
import { SequenceStepConditionParameterDeleteDialogComponent } from './sequence-step-condition-parameter-delete-dialog.component';

@Component({
  selector: 'jhi-sequence-step-condition-parameter',
  templateUrl: './sequence-step-condition-parameter.component.html'
})
export class SequenceStepConditionParameterComponent implements OnInit, OnDestroy {
  sequenceStepConditionParameters: ISequenceStepConditionParameter[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected sequenceStepConditionParameterService: SequenceStepConditionParameterService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.sequenceStepConditionParameters = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.sequenceStepConditionParameterService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ISequenceStepConditionParameter[]>) =>
        this.paginateSequenceStepConditionParameters(res.body, res.headers)
      );
  }

  reset(): void {
    this.page = 0;
    this.sequenceStepConditionParameters = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSequenceStepConditionParameters();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISequenceStepConditionParameter): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSequenceStepConditionParameters(): void {
    this.eventSubscriber = this.eventManager.subscribe('sequenceStepConditionParameterListModification', () => this.reset());
  }

  delete(sequenceStepConditionParameter: ISequenceStepConditionParameter): void {
    const modalRef = this.modalService.open(SequenceStepConditionParameterDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.sequenceStepConditionParameter = sequenceStepConditionParameter;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateSequenceStepConditionParameters(data: ISequenceStepConditionParameter[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.sequenceStepConditionParameters.push(data[i]);
      }
    }
  }
}
