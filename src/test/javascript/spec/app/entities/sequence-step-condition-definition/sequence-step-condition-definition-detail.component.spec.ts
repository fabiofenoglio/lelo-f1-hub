import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LeloHubTestModule } from '../../../test.module';
import { SequenceStepConditionDefinitionDetailComponent } from 'app/entities/sequence-step-condition-definition/sequence-step-condition-definition-detail.component';
import { SequenceStepConditionDefinition } from 'app/shared/model/sequence-step-condition-definition.model';

describe('Component Tests', () => {
  describe('SequenceStepConditionDefinition Management Detail Component', () => {
    let comp: SequenceStepConditionDefinitionDetailComponent;
    let fixture: ComponentFixture<SequenceStepConditionDefinitionDetailComponent>;
    const route = ({ data: of({ sequenceStepConditionDefinition: new SequenceStepConditionDefinition(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LeloHubTestModule],
        declarations: [SequenceStepConditionDefinitionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SequenceStepConditionDefinitionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SequenceStepConditionDefinitionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load sequenceStepConditionDefinition on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sequenceStepConditionDefinition).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
