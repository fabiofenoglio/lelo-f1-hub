import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LeloHubTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { SequenceStepConditionDefinitionParameterDeleteDialogComponent } from 'app/entities/sequence-step-condition-definition-parameter/sequence-step-condition-definition-parameter-delete-dialog.component';
import { SequenceStepConditionDefinitionParameterService } from 'app/entities/sequence-step-condition-definition-parameter/sequence-step-condition-definition-parameter.service';

describe('Component Tests', () => {
  describe('SequenceStepConditionDefinitionParameter Management Delete Component', () => {
    let comp: SequenceStepConditionDefinitionParameterDeleteDialogComponent;
    let fixture: ComponentFixture<SequenceStepConditionDefinitionParameterDeleteDialogComponent>;
    let service: SequenceStepConditionDefinitionParameterService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LeloHubTestModule],
        declarations: [SequenceStepConditionDefinitionParameterDeleteDialogComponent]
      })
        .overrideTemplate(SequenceStepConditionDefinitionParameterDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SequenceStepConditionDefinitionParameterDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SequenceStepConditionDefinitionParameterService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
