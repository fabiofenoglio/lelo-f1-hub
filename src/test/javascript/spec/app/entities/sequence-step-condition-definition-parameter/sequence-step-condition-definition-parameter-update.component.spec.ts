import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LeloHubTestModule } from '../../../test.module';
import { SequenceStepConditionDefinitionParameterUpdateComponent } from 'app/entities/sequence-step-condition-definition-parameter/sequence-step-condition-definition-parameter-update.component';
import { SequenceStepConditionDefinitionParameterService } from 'app/entities/sequence-step-condition-definition-parameter/sequence-step-condition-definition-parameter.service';
import { SequenceStepConditionDefinitionParameter } from 'app/shared/model/sequence-step-condition-definition-parameter.model';

describe('Component Tests', () => {
  describe('SequenceStepConditionDefinitionParameter Management Update Component', () => {
    let comp: SequenceStepConditionDefinitionParameterUpdateComponent;
    let fixture: ComponentFixture<SequenceStepConditionDefinitionParameterUpdateComponent>;
    let service: SequenceStepConditionDefinitionParameterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LeloHubTestModule],
        declarations: [SequenceStepConditionDefinitionParameterUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SequenceStepConditionDefinitionParameterUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SequenceStepConditionDefinitionParameterUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SequenceStepConditionDefinitionParameterService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SequenceStepConditionDefinitionParameter(123);
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
        const entity = new SequenceStepConditionDefinitionParameter();
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
