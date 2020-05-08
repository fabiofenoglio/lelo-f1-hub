import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LeloHubTestModule } from '../../../test.module';
import { SequenceStepConditionDefinitionUpdateComponent } from 'app/entities/sequence-step-condition-definition/sequence-step-condition-definition-update.component';
import { SequenceStepConditionDefinitionService } from 'app/entities/sequence-step-condition-definition/sequence-step-condition-definition.service';
import { SequenceStepConditionDefinition } from 'app/shared/model/sequence-step-condition-definition.model';

describe('Component Tests', () => {
  describe('SequenceStepConditionDefinition Management Update Component', () => {
    let comp: SequenceStepConditionDefinitionUpdateComponent;
    let fixture: ComponentFixture<SequenceStepConditionDefinitionUpdateComponent>;
    let service: SequenceStepConditionDefinitionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LeloHubTestModule],
        declarations: [SequenceStepConditionDefinitionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SequenceStepConditionDefinitionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SequenceStepConditionDefinitionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SequenceStepConditionDefinitionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SequenceStepConditionDefinition(123);
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
        const entity = new SequenceStepConditionDefinition();
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
