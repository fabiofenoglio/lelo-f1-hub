import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LeloHubSharedModule } from 'app/shared/shared.module';
import { SequenceStepConditionDefinitionComponent } from './sequence-step-condition-definition.component';
import { SequenceStepConditionDefinitionDetailComponent } from './sequence-step-condition-definition-detail.component';
import { SequenceStepConditionDefinitionUpdateComponent } from './sequence-step-condition-definition-update.component';
import { SequenceStepConditionDefinitionDeleteDialogComponent } from './sequence-step-condition-definition-delete-dialog.component';
import { sequenceStepConditionDefinitionRoute } from './sequence-step-condition-definition.route';

@NgModule({
  imports: [LeloHubSharedModule, RouterModule.forChild(sequenceStepConditionDefinitionRoute)],
  declarations: [
    SequenceStepConditionDefinitionComponent,
    SequenceStepConditionDefinitionDetailComponent,
    SequenceStepConditionDefinitionUpdateComponent,
    SequenceStepConditionDefinitionDeleteDialogComponent
  ],
  entryComponents: [SequenceStepConditionDefinitionDeleteDialogComponent]
})
export class LeloHubSequenceStepConditionDefinitionModule {}
