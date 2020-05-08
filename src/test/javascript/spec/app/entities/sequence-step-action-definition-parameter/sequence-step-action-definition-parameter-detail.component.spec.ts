import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LeloHubTestModule } from '../../../test.module';
import { SequenceStepActionDefinitionParameterDetailComponent } from 'app/entities/sequence-step-action-definition-parameter/sequence-step-action-definition-parameter-detail.component';
import { SequenceStepActionDefinitionParameter } from 'app/shared/model/sequence-step-action-definition-parameter.model';

describe('Component Tests', () => {
  describe('SequenceStepActionDefinitionParameter Management Detail Component', () => {
    let comp: SequenceStepActionDefinitionParameterDetailComponent;
    let fixture: ComponentFixture<SequenceStepActionDefinitionParameterDetailComponent>;
    const route = ({
      data: of({ sequenceStepActionDefinitionParameter: new SequenceStepActionDefinitionParameter(123) })
    } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LeloHubTestModule],
        declarations: [SequenceStepActionDefinitionParameterDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SequenceStepActionDefinitionParameterDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SequenceStepActionDefinitionParameterDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load sequenceStepActionDefinitionParameter on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sequenceStepActionDefinitionParameter).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
