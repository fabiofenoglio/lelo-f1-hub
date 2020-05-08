import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LeloHubTestModule } from '../../../test.module';
import { SequenceStepActionDefinitionDetailComponent } from 'app/entities/sequence-step-action-definition/sequence-step-action-definition-detail.component';
import { SequenceStepActionDefinition } from 'app/shared/model/sequence-step-action-definition.model';

describe('Component Tests', () => {
  describe('SequenceStepActionDefinition Management Detail Component', () => {
    let comp: SequenceStepActionDefinitionDetailComponent;
    let fixture: ComponentFixture<SequenceStepActionDefinitionDetailComponent>;
    const route = ({ data: of({ sequenceStepActionDefinition: new SequenceStepActionDefinition(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LeloHubTestModule],
        declarations: [SequenceStepActionDefinitionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SequenceStepActionDefinitionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SequenceStepActionDefinitionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load sequenceStepActionDefinition on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sequenceStepActionDefinition).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
