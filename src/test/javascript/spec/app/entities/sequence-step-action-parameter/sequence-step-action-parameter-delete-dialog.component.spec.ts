import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LeloHubTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { SequenceStepActionParameterDeleteDialogComponent } from 'app/entities/sequence-step-action-parameter/sequence-step-action-parameter-delete-dialog.component';
import { SequenceStepActionParameterService } from 'app/entities/sequence-step-action-parameter/sequence-step-action-parameter.service';

describe('Component Tests', () => {
  describe('SequenceStepActionParameter Management Delete Component', () => {
    let comp: SequenceStepActionParameterDeleteDialogComponent;
    let fixture: ComponentFixture<SequenceStepActionParameterDeleteDialogComponent>;
    let service: SequenceStepActionParameterService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LeloHubTestModule],
        declarations: [SequenceStepActionParameterDeleteDialogComponent]
      })
        .overrideTemplate(SequenceStepActionParameterDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SequenceStepActionParameterDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SequenceStepActionParameterService);
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
