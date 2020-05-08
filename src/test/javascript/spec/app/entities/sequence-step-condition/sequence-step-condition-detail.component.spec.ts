import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LeloHubTestModule } from '../../../test.module';
import { SequenceStepConditionDetailComponent } from 'app/entities/sequence-step-condition/sequence-step-condition-detail.component';
import { SequenceStepCondition } from 'app/shared/model/sequence-step-condition.model';

describe('Component Tests', () => {
  describe('SequenceStepCondition Management Detail Component', () => {
    let comp: SequenceStepConditionDetailComponent;
    let fixture: ComponentFixture<SequenceStepConditionDetailComponent>;
    const route = ({ data: of({ sequenceStepCondition: new SequenceStepCondition(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LeloHubTestModule],
        declarations: [SequenceStepConditionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SequenceStepConditionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SequenceStepConditionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load sequenceStepCondition on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sequenceStepCondition).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
