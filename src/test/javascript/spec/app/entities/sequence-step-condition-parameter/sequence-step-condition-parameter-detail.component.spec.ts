import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LeloHubTestModule } from '../../../test.module';
import { SequenceStepConditionParameterDetailComponent } from 'app/entities/sequence-step-condition-parameter/sequence-step-condition-parameter-detail.component';
import { SequenceStepConditionParameter } from 'app/shared/model/sequence-step-condition-parameter.model';

describe('Component Tests', () => {
  describe('SequenceStepConditionParameter Management Detail Component', () => {
    let comp: SequenceStepConditionParameterDetailComponent;
    let fixture: ComponentFixture<SequenceStepConditionParameterDetailComponent>;
    const route = ({ data: of({ sequenceStepConditionParameter: new SequenceStepConditionParameter(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LeloHubTestModule],
        declarations: [SequenceStepConditionParameterDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SequenceStepConditionParameterDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SequenceStepConditionParameterDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load sequenceStepConditionParameter on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sequenceStepConditionParameter).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
