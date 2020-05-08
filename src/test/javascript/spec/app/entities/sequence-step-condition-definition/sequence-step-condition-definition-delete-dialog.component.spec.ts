import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LeloHubTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { SequenceStepConditionDefinitionDeleteDialogComponent } from 'app/entities/sequence-step-condition-definition/sequence-step-condition-definition-delete-dialog.component';
import { SequenceStepConditionDefinitionService } from 'app/entities/sequence-step-condition-definition/sequence-step-condition-definition.service';

describe('Component Tests', () => {
  describe('SequenceStepConditionDefinition Management Delete Component', () => {
    let comp: SequenceStepConditionDefinitionDeleteDialogComponent;
    let fixture: ComponentFixture<SequenceStepConditionDefinitionDeleteDialogComponent>;
    let service: SequenceStepConditionDefinitionService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LeloHubTestModule],
        declarations: [SequenceStepConditionDefinitionDeleteDialogComponent]
      })
        .overrideTemplate(SequenceStepConditionDefinitionDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SequenceStepConditionDefinitionDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SequenceStepConditionDefinitionService);
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
