import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISequenceStepActionDefinition } from 'app/shared/model/sequence-step-action-definition.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { SequenceStepActionDefinitionService } from './sequence-step-action-definition.service';
import { SequenceStepActionDefinitionDeleteDialogComponent } from './sequence-step-action-definition-delete-dialog.component';

@Component({
  selector: 'jhi-sequence-step-action-definition',
  templateUrl: './sequence-step-action-definition.component.html'
})
export class SequenceStepActionDefinitionComponent implements OnInit, OnDestroy {
  sequenceStepActionDefinitions: ISequenceStepActionDefinition[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected sequenceStepActionDefinitionService: SequenceStepActionDefinitionService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.sequenceStepActionDefinitions = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.sequenceStepActionDefinitionService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ISequenceStepActionDefinition[]>) => this.paginateSequenceStepActionDefinitions(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.sequenceStepActionDefinitions = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSequenceStepActionDefinitions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISequenceStepActionDefinition): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSequenceStepActionDefinitions(): void {
    this.eventSubscriber = this.eventManager.subscribe('sequenceStepActionDefinitionListModification', () => this.reset());
  }

  delete(sequenceStepActionDefinition: ISequenceStepActionDefinition): void {
    const modalRef = this.modalService.open(SequenceStepActionDefinitionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.sequenceStepActionDefinition = sequenceStepActionDefinition;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateSequenceStepActionDefinitions(data: ISequenceStepActionDefinition[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.sequenceStepActionDefinitions.push(data[i]);
      }
    }
  }
}
