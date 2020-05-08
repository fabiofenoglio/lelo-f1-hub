import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LeloHubTestModule } from '../../../test.module';
import { SequenceStepActionDefinitionUpdateComponent } from 'app/entities/sequence-step-action-definition/sequence-step-action-definition-update.component';
import { SequenceStepActionDefinitionService } from 'app/entities/sequence-step-action-definition/sequence-step-action-definition.service';
import { SequenceStepActionDefinition } from 'app/shared/model/sequence-step-action-definition.model';

describe('Component Tests', () => {
  describe('SequenceStepActionDefinition Management Update Component', () => {
    let comp: SequenceStepActionDefinitionUpdateComponent;
    let fixture: ComponentFixture<SequenceStepActionDefinitionUpdateComponent>;
    let service: SequenceStepActionDefinitionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LeloHubTestModule],
        declarations: [SequenceStepActionDefinitionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SequenceStepActionDefinitionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SequenceStepActionDefinitionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SequenceStepActionDefinitionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SequenceStepActionDefinition(123);
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
        const entity = new SequenceStepActionDefinition();
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
