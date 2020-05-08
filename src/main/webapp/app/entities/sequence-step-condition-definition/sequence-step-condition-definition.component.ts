import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISequenceStepConditionDefinition } from 'app/shared/model/sequence-step-condition-definition.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { SequenceStepConditionDefinitionService } from './sequence-step-condition-definition.service';
import { SequenceStepConditionDefinitionDeleteDialogComponent } from './sequence-step-condition-definition-delete-dialog.component';

@Component({
  selector: 'jhi-sequence-step-condition-definition',
  templateUrl: './sequence-step-condition-definition.component.html'
})
export class SequenceStepConditionDefinitionComponent implements OnInit, OnDestroy {
  sequenceStepConditionDefinitions: ISequenceStepConditionDefinition[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected sequenceStepConditionDefinitionService: SequenceStepConditionDefinitionService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.sequenceStepConditionDefinitions = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.sequenceStepConditionDefinitionService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ISequenceStepConditionDefinition[]>) =>
        this.paginateSequenceStepConditionDefinitions(res.body, res.headers)
      );
  }

  reset(): void {
    this.page = 0;
    this.sequenceStepConditionDefinitions = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSequenceStepConditionDefinitions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISequenceStepConditionDefinition): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSequenceStepConditionDefinitions(): void {
    this.eventSubscriber = this.eventManager.subscribe('sequenceStepConditionDefinitionListModification', () => this.reset());
  }

  delete(sequenceStepConditionDefinition: ISequenceStepConditionDefinition): void {
    const modalRef = this.modalService.open(SequenceStepConditionDefinitionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.sequenceStepConditionDefinition = sequenceStepConditionDefinition;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateSequenceStepConditionDefinitions(data: ISequenceStepConditionDefinition[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.sequenceStepConditionDefinitions.push(data[i]);
      }
    }
  }
}
