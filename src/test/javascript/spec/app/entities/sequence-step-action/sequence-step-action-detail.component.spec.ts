import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LeloHubTestModule } from '../../../test.module';
import { SequenceStepActionDetailComponent } from 'app/entities/sequence-step-action/sequence-step-action-detail.component';
import { SequenceStepAction } from 'app/shared/model/sequence-step-action.model';

describe('Component Tests', () => {
  describe('SequenceStepAction Management Detail Component', () => {
    let comp: SequenceStepActionDetailComponent;
    let fixture: ComponentFixture<SequenceStepActionDetailComponent>;
    const route = ({ data: of({ sequenceStepAction: new SequenceStepAction(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LeloHubTestModule],
        declarations: [SequenceStepActionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SequenceStepActionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SequenceStepActionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load sequenceStepAction on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sequenceStepAction).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
