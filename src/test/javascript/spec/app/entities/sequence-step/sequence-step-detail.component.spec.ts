import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LeloHubTestModule } from '../../../test.module';
import { SequenceStepDetailComponent } from 'app/entities/sequence-step/sequence-step-detail.component';
import { SequenceStep } from 'app/shared/model/sequence-step.model';

describe('Component Tests', () => {
  describe('SequenceStep Management Detail Component', () => {
    let comp: SequenceStepDetailComponent;
    let fixture: ComponentFixture<SequenceStepDetailComponent>;
    const route = ({ data: of({ sequenceStep: new SequenceStep(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LeloHubTestModule],
        declarations: [SequenceStepDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SequenceStepDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SequenceStepDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load sequenceStep on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sequenceStep).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
