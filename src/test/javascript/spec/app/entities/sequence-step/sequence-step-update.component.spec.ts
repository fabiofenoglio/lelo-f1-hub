import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LeloHubTestModule } from '../../../test.module';
import { SequenceStepUpdateComponent } from 'app/entities/sequence-step/sequence-step-update.component';
import { SequenceStepService } from 'app/entities/sequence-step/sequence-step.service';
import { SequenceStep } from 'app/shared/model/sequence-step.model';

describe('Component Tests', () => {
  describe('SequenceStep Management Update Component', () => {
    let comp: SequenceStepUpdateComponent;
    let fixture: ComponentFixture<SequenceStepUpdateComponent>;
    let service: SequenceStepService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LeloHubTestModule],
        declarations: [SequenceStepUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SequenceStepUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SequenceStepUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SequenceStepService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SequenceStep(123);
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
        const entity = new SequenceStep();
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
