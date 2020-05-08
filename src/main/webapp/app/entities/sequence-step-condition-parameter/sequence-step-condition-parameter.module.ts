import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LeloHubSharedModule } from 'app/shared/shared.module';
import { SequenceStepConditionParameterComponent } from './sequence-step-condition-parameter.component';
import { SequenceStepConditionParameterDetailComponent } from './sequence-step-condition-parameter-detail.component';
import { SequenceStepConditionParameterUpdateComponent } from './sequence-step-condition-parameter-update.component';
import { SequenceStepConditionParameterDeleteDialogComponent } from './sequence-step-condition-parameter-delete-dialog.component';
import { sequenceStepConditionParameterRoute } from './sequence-step-condition-parameter.route';

@NgModule({
  imports: [LeloHubSharedModule, RouterModule.forChild(sequenceStepConditionParameterRoute)],
  declarations: [
    SequenceStepConditionParameterComponent,
    SequenceStepConditionParameterDetailComponent,
    SequenceStepConditionParameterUpdateComponent,
    SequenceStepConditionParameterDeleteDialogComponent
  ],
  entryComponents: [SequenceStepConditionParameterDeleteDialogComponent]
})
export class LeloHubSequenceStepConditionParameterModule {}
