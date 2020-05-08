import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISequenceStepConditionDefinitionParameter } from 'app/shared/model/sequence-step-condition-definition-parameter.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { SequenceStepConditionDefinitionParameterService } from './sequence-step-condition-definition-parameter.service';
import { SequenceStepConditionDefinitionParameterDeleteDialogComponent } from './sequence-step-condition-definition-parameter-delete-dialog.component';

@Component({
  selector: 'jhi-sequence-step-condition-definition-parameter',
  templateUrl: './sequence-step-condition-definition-parameter.component.html'
})
export class SequenceStepConditionDefinitionParameterComponent implements OnInit, OnDestroy {
  sequenceStepConditionDefinitionParameters: ISequenceStepConditionDefinitionParameter[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected sequenceStepConditionDefinitionParameterService: SequenceStepConditionDefinitionParameterService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.sequenceStepConditionDefinitionParameters = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.sequenceStepConditionDefinitionParameterService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ISequenceStepConditionDefinitionParameter[]>) =>
        this.paginateSequenceStepConditionDefinitionParameters(res.body, res.headers)
      );
  }

  reset(): void {
    this.page = 0;
    this.sequenceStepConditionDefinitionParameters = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSequenceStepConditionDefinitionParameters();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISequenceStepConditionDefinitionParameter): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSequenceStepConditionDefinitionParameters(): void {
    this.eventSubscriber = this.eventManager.subscribe('sequenceStepConditionDefinitionParameterListModification', () => this.reset());
  }

  delete(sequenceStepConditionDefinitionParameter: ISequenceStepConditionDefinitionParameter): void {
    const modalRef = this.modalService.open(SequenceStepConditionDefinitionParameterDeleteDialogComponent, {
      size: 'lg',
      backdrop: 'static'
    });
    modalRef.componentInstance.sequenceStepConditionDefinitionParameter = sequenceStepConditionDefinitionParameter;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateSequenceStepConditionDefinitionParameters(
    data: ISequenceStepConditionDefinitionParameter[] | null,
    headers: HttpHeaders
  ): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.sequenceStepConditionDefinitionParameters.push(data[i]);
      }
    }
  }
}
