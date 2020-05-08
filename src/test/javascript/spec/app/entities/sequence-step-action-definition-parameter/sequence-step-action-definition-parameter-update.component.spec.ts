import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LeloHubTestModule } from '../../../test.module';
import { SequenceStepActionDefinitionParameterUpdateComponent } from 'app/entities/sequence-step-action-definition-parameter/sequence-step-action-definition-parameter-update.component';
import { SequenceStepActionDefinitionParameterService } from 'app/entities/sequence-step-action-definition-parameter/sequence-step-action-definition-parameter.service';
import { SequenceStepActionDefinitionParameter } from 'app/shared/model/sequence-step-action-definition-parameter.model';

describe('Component Tests', () => {
  describe('SequenceStepActionDefinitionParameter Management Update Component', () => {
    let comp: SequenceStepActionDefinitionParameterUpdateComponent;
    let fixture: ComponentFixture<SequenceStepActionDefinitionParameterUpdateComponent>;
    let service: SequenceStepActionDefinitionParameterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LeloHubTestModule],
        declarations: [SequenceStepActionDefinitionParameterUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SequenceStepActionDefinitionParameterUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SequenceStepActionDefinitionParameterUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SequenceStepActionDefinitionParameterService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SequenceStepActionDefinitionParameter(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new SequenceStepActionDefinitionParameter();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
