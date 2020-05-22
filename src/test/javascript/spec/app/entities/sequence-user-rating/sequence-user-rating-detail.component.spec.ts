import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LeloHubTestModule } from '../../../test.module';
import { SequenceUserRatingDetailComponent } from 'app/entities/sequence-user-rating/sequence-user-rating-detail.component';
import { SequenceUserRating } from 'app/shared/model/sequence-user-rating.model';

describe('Component Tests', () => {
  describe('SequenceUserRating Management Detail Component', () => {
    let comp: SequenceUserRatingDetailComponent;
    let fixture: ComponentFixture<SequenceUserRatingDetailComponent>;
    const route = ({ data: of({ sequenceUserRating: new SequenceUserRating(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LeloHubTestModule],
        declarations: [SequenceUserRatingDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SequenceUserRatingDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SequenceUserRatingDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load sequenceUserRating on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sequenceUserRating).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
