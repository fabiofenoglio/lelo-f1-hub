import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LeloHubTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { SequenceStepActionDefinitionDeleteDialogComponent } from 'app/entities/sequence-step-action-definition/sequence-step-action-definition-delete-dialog.component';
import { SequenceStepActionDefinitionService } from 'app/entities/sequence-step-action-definition/sequence-step-action-definition.service';

describe('Component Tests', () => {
  describe('SequenceStepActionDefinition Management Delete Component', () => {
    let comp: SequenceStepActionDefinitionDeleteDialogComponent;
    let fixture: ComponentFixture<SequenceStepActionDefinitionDeleteDialogComponent>;
    let service: SequenceStepActionDefinitionService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LeloHubTestModule],
        declarations: [SequenceStepActionDefinitionDeleteDialogComponent]
      })
        .overrideTemplate(SequenceStepActionDefinitionDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SequenceStepActionDefinitionDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SequenceStepActionDefinitionService);
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
