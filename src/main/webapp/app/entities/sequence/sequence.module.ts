import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LeloHubSharedModule } from 'app/shared/shared.module';
import { SequenceComponent } from './sequence.component';
import { SequenceDetailComponent } from './sequence-detail.component';
import { SequenceUpdateComponent } from './sequence-update.component';
import { SequenceDeleteDialogComponent } from './sequence-delete-dialog.component';
import { sequenceRoute } from './sequence.route';

@NgModule({
  imports: [LeloHubSharedModule, RouterModule.forChild(sequenceRoute)],
  declarations: [SequenceComponent, SequenceDetailComponent, SequenceUpdateComponent, SequenceDeleteDialogComponent],
  entryComponents: [SequenceDeleteDialogComponent]
})
export class LeloHubSequenceModule {}
