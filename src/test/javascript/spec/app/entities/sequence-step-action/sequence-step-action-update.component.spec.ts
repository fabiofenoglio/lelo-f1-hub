import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LeloHubTestModule } from '../../../test.module';
import { SequenceStepActionUpdateComponent } from 'app/entities/sequence-step-action/sequence-step-action-update.component';
import { SequenceStepActionService } from 'app/entities/sequence-step-action/sequence-step-action.service';
import { SequenceStepAction } from 'app/shared/model/sequence-step-action.model';

describe('Component Tests', () => {
  describe('SequenceStepAction Management Update Component', () => {
    let comp: SequenceStepActionUpdateComponent;
    let fixture: ComponentFixture<SequenceStepActionUpdateComponent>;
    let service: SequenceStepActionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LeloHubTestModule],
        declarations: [SequenceStepActionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SequenceStepActionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SequenceStepActionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SequenceStepActionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SequenceStepAction(123);
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
        const entity = new SequenceStepAction();
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
