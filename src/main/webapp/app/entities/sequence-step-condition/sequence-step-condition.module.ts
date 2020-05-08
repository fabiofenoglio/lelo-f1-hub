import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LeloHubSharedModule } from 'app/shared/shared.module';
import { SequenceStepConditionComponent } from './sequence-step-condition.component';
import { SequenceStepConditionDetailComponent } from './sequence-step-condition-detail.component';
import { SequenceStepConditionUpdateComponent } from './sequence-step-condition-update.component';
import { SequenceStepConditionDeleteDialogComponent } from './sequence-step-condition-delete-dialog.component';
import { sequenceStepConditionRoute } from './sequence-step-condition.route';

@NgModule({
  imports: [LeloHubSharedModule, RouterModule.forChild(sequenceStepConditionRoute)],
  declarations: [
    SequenceStepConditionComponent,
    SequenceStepConditionDetailComponent,
    SequenceStepConditionUpdateComponent,
    SequenceStepConditionDeleteDialogComponent
  ],
  entryComponents: [SequenceStepConditionDeleteDialogComponent]
})
export class LeloHubSequenceStepConditionModule {}
