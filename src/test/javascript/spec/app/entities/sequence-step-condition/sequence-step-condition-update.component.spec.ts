import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LeloHubTestModule } from '../../../test.module';
import { SequenceStepConditionUpdateComponent } from 'app/entities/sequence-step-condition/sequence-step-condition-update.component';
import { SequenceStepConditionService } from 'app/entities/sequence-step-condition/sequence-step-condition.service';
import { SequenceStepCondition } from 'app/shared/model/sequence-step-condition.model';

describe('Component Tests', () => {
  describe('SequenceStepCondition Management Update Component', () => {
    let comp: SequenceStepConditionUpdateComponent;
    let fixture: ComponentFixture<SequenceStepConditionUpdateComponent>;
    let service: SequenceStepConditionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LeloHubTestModule],
        declarations: [SequenceStepConditionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SequenceStepConditionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SequenceStepConditionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SequenceStepConditionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SequenceStepCondition(123);
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
        const entity = new SequenceStepCondition();
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
