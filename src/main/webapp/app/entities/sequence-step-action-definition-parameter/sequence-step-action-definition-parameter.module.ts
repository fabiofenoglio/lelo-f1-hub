import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LeloHubSharedModule } from 'app/shared/shared.module';
import { SequenceStepActionDefinitionParameterComponent } from './sequence-step-action-definition-parameter.component';
import { SequenceStepActionDefinitionParameterDetailComponent } from './sequence-step-action-definition-parameter-detail.component';
import { SequenceStepActionDefinitionParameterUpdateComponent } from './sequence-step-action-definition-parameter-update.component';
import { SequenceStepActionDefinitionParameterDeleteDialogComponent } from './sequence-step-action-definition-parameter-delete-dialog.component';
import { sequenceStepActionDefinitionParameterRoute } from './sequence-step-action-definition-parameter.route';

@NgModule({
  imports: [LeloHubSharedModule, RouterModule.forChild(sequenceStepActionDefinitionParameterRoute)],
  declarations: [
    SequenceStepActionDefinitionParameterComponent,
    SequenceStepActionDefinitionParameterDetailComponent,
    SequenceStepActionDefinitionParameterUpdateComponent,
    SequenceStepActionDefinitionParameterDeleteDialogComponent
  ],
  entryComponents: [SequenceStepActionDefinitionParameterDeleteDialogComponent]
})
export class LeloHubSequenceStepActionDefinitionParameterModule {}
