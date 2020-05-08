import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LeloHubTestModule } from '../../../test.module';
import { SequenceStepActionParameterDetailComponent } from 'app/entities/sequence-step-action-parameter/sequence-step-action-parameter-detail.component';
import { SequenceStepActionParameter } from 'app/shared/model/sequence-step-action-parameter.model';

describe('Component Tests', () => {
  describe('SequenceStepActionParameter Management Detail Component', () => {
    let comp: SequenceStepActionParameterDetailComponent;
    let fixture: ComponentFixture<SequenceStepActionParameterDetailComponent>;
    const route = ({ data: of({ sequenceStepActionParameter: new SequenceStepActionParameter(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LeloHubTestModule],
        declarations: [SequenceStepActionParameterDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SequenceStepActionParameterDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SequenceStepActionParameterDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load sequenceStepActionParameter on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sequenceStepActionParameter).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
