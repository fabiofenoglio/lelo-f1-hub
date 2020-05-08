import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LeloHubTestModule } from '../../../test.module';
import { SequenceStepConditionParameterUpdateComponent } from 'app/entities/sequence-step-condition-parameter/sequence-step-condition-parameter-update.component';
import { SequenceStepConditionParameterService } from 'app/entities/sequence-step-condition-parameter/sequence-step-condition-parameter.service';
import { SequenceStepConditionParameter } from 'app/shared/model/sequence-step-condition-parameter.model';

describe('Component Tests', () => {
  describe('SequenceStepConditionParameter Management Update Component', () => {
    let comp: SequenceStepConditionParameterUpdateComponent;
    let fixture: ComponentFixture<SequenceStepConditionParameterUpdateComponent>;
    let service: SequenceStepConditionParameterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LeloHubTestModule],
        declarations: [SequenceStepConditionParameterUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SequenceStepConditionParameterUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SequenceStepConditionParameterUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SequenceStepConditionParameterService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SequenceStepConditionParameter(123);
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
        const entity = new SequenceStepConditionParameter();
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
