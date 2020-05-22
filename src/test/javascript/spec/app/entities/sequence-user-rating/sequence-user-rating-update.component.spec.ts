import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LeloHubTestModule } from '../../../test.module';
import { SequenceUserRatingUpdateComponent } from 'app/entities/sequence-user-rating/sequence-user-rating-update.component';
import { SequenceUserRatingService } from 'app/entities/sequence-user-rating/sequence-user-rating.service';
import { SequenceUserRating } from 'app/shared/model/sequence-user-rating.model';

describe('Component Tests', () => {
  describe('SequenceUserRating Management Update Component', () => {
    let comp: SequenceUserRatingUpdateComponent;
    let fixture: ComponentFixture<SequenceUserRatingUpdateComponent>;
    let service: SequenceUserRatingService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LeloHubTestModule],
        declarations: [SequenceUserRatingUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SequenceUserRatingUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SequenceUserRatingUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SequenceUserRatingService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SequenceUserRating(123);
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
        const entity = new SequenceUserRating();
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
