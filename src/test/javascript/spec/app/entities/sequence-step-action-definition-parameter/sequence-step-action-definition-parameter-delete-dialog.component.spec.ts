import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LeloHubTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { SequenceStepActionDefinitionParameterDeleteDialogComponent } from 'app/entities/sequence-step-action-definition-parameter/sequence-step-action-definition-parameter-delete-dialog.component';
import { SequenceStepActionDefinitionParameterService } from 'app/entities/sequence-step-action-definition-parameter/sequence-step-action-definition-parameter.service';

describe('Component Tests', () => {
  describe('SequenceStepActionDefinitionParameter Management Delete Component', () => {
    let comp: SequenceStepActionDefinitionParameterDeleteDialogComponent;
    let fixture: ComponentFixture<SequenceStepActionDefinitionParameterDeleteDialogComponent>;
    let service: SequenceStepActionDefinitionParameterService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LeloHubTestModule],
        declarations: [SequenceStepActionDefinitionParameterDeleteDialogComponent]
      })
        .overrideTemplate(SequenceStepActionDefinitionParameterDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SequenceStepActionDefinitionParameterDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SequenceStepActionDefinitionParameterService);
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
