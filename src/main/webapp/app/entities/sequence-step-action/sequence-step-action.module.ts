import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LeloHubSharedModule } from 'app/shared/shared.module';
import { SequenceStepActionComponent } from './sequence-step-action.component';
import { SequenceStepActionDetailComponent } from './sequence-step-action-detail.component';
import { SequenceStepActionUpdateComponent } from './sequence-step-action-update.component';
import { SequenceStepActionDeleteDialogComponent } from './sequence-step-action-delete-dialog.component';
import { sequenceStepActionRoute } from './sequence-step-action.route';

@NgModule({
  imports: [LeloHubSharedModule, RouterModule.forChild(sequenceStepActionRoute)],
  declarations: [
    SequenceStepActionComponent,
    SequenceStepActionDetailComponent,
    SequenceStepActionUpdateComponent,
    SequenceStepActionDeleteDialogComponent
  ],
  entryComponents: [SequenceStepActionDeleteDialogComponent]
})
export class LeloHubSequenceStepActionModule {}
