import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LeloHubSharedModule } from 'app/shared/shared.module';
import { SequenceStepActionParameterComponent } from './sequence-step-action-parameter.component';
import { SequenceStepActionParameterDetailComponent } from './sequence-step-action-parameter-detail.component';
import { SequenceStepActionParameterUpdateComponent } from './sequence-step-action-parameter-update.component';
import { SequenceStepActionParameterDeleteDialogComponent } from './sequence-step-action-parameter-delete-dialog.component';
import { sequenceStepActionParameterRoute } from './sequence-step-action-parameter.route';

@NgModule({
  imports: [LeloHubSharedModule, RouterModule.forChild(sequenceStepActionParameterRoute)],
  declarations: [
    SequenceStepActionParameterComponent,
    SequenceStepActionParameterDetailComponent,
    SequenceStepActionParameterUpdateComponent,
    SequenceStepActionParameterDeleteDialogComponent
  ],
  entryComponents: [SequenceStepActionParameterDeleteDialogComponent]
})
export class LeloHubSequenceStepActionParameterModule {}
