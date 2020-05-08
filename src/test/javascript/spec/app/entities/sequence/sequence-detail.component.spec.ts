import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LeloHubTestModule } from '../../../test.module';
import { SequenceDetailComponent } from 'app/entities/sequence/sequence-detail.component';
import { Sequence } from 'app/shared/model/sequence.model';

describe('Component Tests', () => {
  describe('Sequence Management Detail Component', () => {
    let comp: SequenceDetailComponent;
    let fixture: ComponentFixture<SequenceDetailComponent>;
    const route = ({ data: of({ sequence: new Sequence(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LeloHubTestModule],
        declarations: [SequenceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SequenceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SequenceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load sequence on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sequence).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
