import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { LeloHubTestModule } from '../../../test.module';
import { SequenceStepConditionDefinitionParameterComponent } from 'app/entities/sequence-step-condition-definition-parameter/sequence-step-condition-definition-parameter.component';
import { SequenceStepConditionDefinitionParameterService } from 'app/entities/sequence-step-condition-definition-parameter/sequence-step-condition-definition-parameter.service';
import { SequenceStepConditionDefinitionParameter } from 'app/shared/model/sequence-step-condition-definition-parameter.model';

describe('Component Tests', () => {
  describe('SequenceStepConditionDefinitionParameter Management Component', () => {
    let comp: SequenceStepConditionDefinitionParameterComponent;
    let fixture: ComponentFixture<SequenceStepConditionDefinitionParameterComponent>;
    let service: SequenceStepConditionDefinitionParameterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LeloHubTestModule],
        declarations: [SequenceStepConditionDefinitionParameterComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: {
                subscribe: (fn: (value: Data) => void) =>
                  fn({
                    pagingParams: {
                      predicate: 'id',
                      reverse: false,
                      page: 0
                    }
                  })
              }
            }
          }
        ]
      })
        .overrideTemplate(SequenceStepConditionDefinitionParameterComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SequenceStepConditionDefinitionParameterComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SequenceStepConditionDefinitionParameterService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SequenceStepConditionDefinitionParameter(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.sequenceStepConditionDefinitionParameters && comp.sequenceStepConditionDefinitionParameters[0]).toEqual(
        jasmine.objectContaining({ id: 123 })
      );
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SequenceStepConditionDefinitionParameter(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.sequenceStepConditionDefinitionParameters && comp.sequenceStepConditionDefinitionParameters[0]).toEqual(
        jasmine.objectContaining({ id: 123 })
      );
    });

    it('should re-initialize the page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SequenceStepConditionDefinitionParameter(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);
      comp.reset();

      // THEN
      expect(comp.page).toEqual(0);
      expect(service.query).toHaveBeenCalledTimes(2);
      expect(comp.sequenceStepConditionDefinitionParameters && comp.sequenceStepConditionDefinitionParameters[0]).toEqual(
        jasmine.objectContaining({ id: 123 })
      );
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,asc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,asc', 'id']);
    });
  });
});
