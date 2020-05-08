import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LeloHubTestModule } from '../../../test.module';
import { SequenceStepActionParameterUpdateComponent } from 'app/entities/sequence-step-action-parameter/sequence-step-action-parameter-update.component';
import { SequenceStepActionParameterService } from 'app/entities/sequence-step-action-parameter/sequence-step-action-parameter.service';
import { SequenceStepActionParameter } from 'app/shared/model/sequence-step-action-parameter.model';

describe('Component Tests', () => {
  describe('SequenceStepActionParameter Management Update Component', () => {
    let comp: SequenceStepActionParameterUpdateComponent;
    let fixture: ComponentFixture<SequenceStepActionParameterUpdateComponent>;
    let service: SequenceStepActionParameterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LeloHubTestModule],
        declarations: [SequenceStepActionParameterUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SequenceStepActionParameterUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SequenceStepActionParameterUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SequenceStepActionParameterService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SequenceStepActionParameter(123);
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
        const entity = new SequenceStepActionParameter();
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
