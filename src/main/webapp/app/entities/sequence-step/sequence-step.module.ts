import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LeloHubSharedModule } from 'app/shared/shared.module';
import { SequenceStepComponent } from './sequence-step.component';
import { SequenceStepDetailComponent } from './sequence-step-detail.component';
import { SequenceStepUpdateComponent } from './sequence-step-update.component';
import { SequenceStepDeleteDialogComponent } from './sequence-step-delete-dialog.component';
import { sequenceStepRoute } from './sequence-step.route';

@NgModule({
  imports: [LeloHubSharedModule, RouterModule.forChild(sequenceStepRoute)],
  declarations: [SequenceStepComponent, SequenceStepDetailComponent, SequenceStepUpdateComponent, SequenceStepDeleteDialogComponent],
  entryComponents: [SequenceStepDeleteDialogComponent]
})
export class LeloHubSequenceStepModule {}
