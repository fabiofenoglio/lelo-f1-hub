import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LeloHubTestModule } from '../../../test.module';
import { SequenceStepConditionDefinitionParameterDetailComponent } from 'app/entities/sequence-step-condition-definition-parameter/sequence-step-condition-definition-parameter-detail.component';
import { SequenceStepConditionDefinitionParameter } from 'app/shared/model/sequence-step-condition-definition-parameter.model';

describe('Component Tests', () => {
  describe('SequenceStepConditionDefinitionParameter Management Detail Component', () => {
    let comp: SequenceStepConditionDefinitionParameterDetailComponent;
    let fixture: ComponentFixture<SequenceStepConditionDefinitionParameterDetailComponent>;
    const route = ({
      data: of({ sequenceStepConditionDefinitionParameter: new SequenceStepConditionDefinitionParameter(123) })
    } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LeloHubTestModule],
        declarations: [SequenceStepConditionDefinitionParameterDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SequenceStepConditionDefinitionParameterDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SequenceStepConditionDefinitionParameterDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load sequenceStepConditionDefinitionParameter on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sequenceStepConditionDefinitionParameter).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
