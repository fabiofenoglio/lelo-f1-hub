import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { LeloHubTestModule } from '../../../test.module';
import { SequenceStepActionDefinitionParameterComponent } from 'app/entities/sequence-step-action-definition-parameter/sequence-step-action-definition-parameter.component';
import { SequenceStepActionDefinitionParameterService } from 'app/entities/sequence-step-action-definition-parameter/sequence-step-action-definition-parameter.service';
import { SequenceStepActionDefinitionParameter } from 'app/shared/model/sequence-step-action-definition-parameter.model';

describe('Component Tests', () => {
  describe('SequenceStepActionDefinitionParameter Management Component', () => {
    let comp: SequenceStepActionDefinitionParameterComponent;
    let fixture: ComponentFixture<SequenceStepActionDefinitionParameterComponent>;
    let service: SequenceStepActionDefinitionParameterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LeloHubTestModule],
        declarations: [SequenceStepActionDefinitionParameterComponent],
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
        .overrideTemplate(SequenceStepActionDefinitionParameterComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SequenceStepActionDefinitionParameterComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SequenceStepActionDefinitionParameterService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SequenceStepActionDefinitionParameter(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.sequenceStepActionDefinitionParameters && comp.sequenceStepActionDefinitionParameters[0]).toEqual(
        jasmine.objectContaining({ id: 123 })
      );
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SequenceStepActionDefinitionParameter(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.sequenceStepActionDefinitionParameters && comp.sequenceStepActionDefinitionParameters[0]).toEqual(
        jasmine.objectContaining({ id: 123 })
      );
    });

    it('should re-initialize the page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SequenceStepActionDefinitionParameter(123)],
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
      expect(comp.sequenceStepActionDefinitionParameters && comp.sequenceStepActionDefinitionParameters[0]).toEqual(
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
