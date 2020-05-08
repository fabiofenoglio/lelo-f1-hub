import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISequenceStepActionDefinitionParameter } from 'app/shared/model/sequence-step-action-definition-parameter.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { SequenceStepActionDefinitionParameterService } from './sequence-step-action-definition-parameter.service';
import { SequenceStepActionDefinitionParameterDeleteDialogComponent } from './sequence-step-action-definition-parameter-delete-dialog.component';

@Component({
  selector: 'jhi-sequence-step-action-definition-parameter',
  templateUrl: './sequence-step-action-definition-parameter.component.html'
})
export class SequenceStepActionDefinitionParameterComponent implements OnInit, OnDestroy {
  sequenceStepActionDefinitionParameters: ISequenceStepActionDefinitionParameter[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected sequenceStepActionDefinitionParameterService: SequenceStepActionDefinitionParameterService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.sequenceStepActionDefinitionParameters = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.sequenceStepActionDefinitionParameterService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ISequenceStepActionDefinitionParameter[]>) =>
        this.paginateSequenceStepActionDefinitionParameters(res.body, res.headers)
      );
  }

  reset(): void {
    this.page = 0;
    this.sequenceStepActionDefinitionParameters = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSequenceStepActionDefinitionParameters();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISequenceStepActionDefinitionParameter): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSequenceStepActionDefinitionParameters(): void {
    this.eventSubscriber = this.eventManager.subscribe('sequenceStepActionDefinitionParameterListModification', () => this.reset());
  }

  delete(sequenceStepActionDefinitionParameter: ISequenceStepActionDefinitionParameter): void {
    const modalRef = this.modalService.open(SequenceStepActionDefinitionParameterDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.sequenceStepActionDefinitionParameter = sequenceStepActionDefinitionParameter;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateSequenceStepActionDefinitionParameters(
    data: ISequenceStepActionDefinitionParameter[] | null,
    headers: HttpHeaders
  ): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.sequenceStepActionDefinitionParameters.push(data[i]);
      }
    }
  }
}
