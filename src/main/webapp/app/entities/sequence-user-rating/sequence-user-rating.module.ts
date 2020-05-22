import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LeloHubSharedModule } from 'app/shared/shared.module';
import { SequenceUserRatingComponent } from './sequence-user-rating.component';
import { SequenceUserRatingDetailComponent } from './sequence-user-rating-detail.component';
import { SequenceUserRatingUpdateComponent } from './sequence-user-rating-update.component';
import { SequenceUserRatingDeleteDialogComponent } from './sequence-user-rating-delete-dialog.component';
import { sequenceUserRatingRoute } from './sequence-user-rating.route';

@NgModule({
  imports: [LeloHubSharedModule, RouterModule.forChild(sequenceUserRatingRoute)],
  declarations: [
    SequenceUserRatingComponent,
    SequenceUserRatingDetailComponent,
    SequenceUserRatingUpdateComponent,
    SequenceUserRatingDeleteDialogComponent
  ],
  entryComponents: [SequenceUserRatingDeleteDialogComponent]
})
export class LeloHubSequenceUserRatingModule {}
