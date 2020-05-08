import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LeloHubSharedModule } from 'app/shared/shared.module';
import { SequenceStepActionDefinitionComponent } from './sequence-step-action-definition.component';
import { SequenceStepActionDefinitionDetailComponent } from './sequence-step-action-definition-detail.component';
import { SequenceStepActionDefinitionUpdateComponent } from './sequence-step-action-definition-update.component';
import { SequenceStepActionDefinitionDeleteDialogComponent } from './sequence-step-action-definition-delete-dialog.component';
import { sequenceStepActionDefinitionRoute } from './sequence-step-action-definition.route';

@NgModule({
  imports: [LeloHubSharedModule, RouterModule.forChild(sequenceStepActionDefinitionRoute)],
  declarations: [
    SequenceStepActionDefinitionComponent,
    SequenceStepActionDefinitionDetailComponent,
    SequenceStepActionDefinitionUpdateComponent,
    SequenceStepActionDefinitionDeleteDialogComponent
  ],
  entryComponents: [SequenceStepActionDefinitionDeleteDialogComponent]
})
export class LeloHubSequenceStepActionDefinitionModule {}
