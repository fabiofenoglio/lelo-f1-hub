import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LeloHubSharedModule } from 'app/shared/shared.module';
import { SequenceStepConditionDefinitionParameterComponent } from './sequence-step-condition-definition-parameter.component';
import { SequenceStepConditionDefinitionParameterDetailComponent } from './sequence-step-condition-definition-parameter-detail.component';
import { SequenceStepConditionDefinitionParameterUpdateComponent } from './sequence-step-condition-definition-parameter-update.component';
import { SequenceStepConditionDefinitionParameterDeleteDialogComponent } from './sequence-step-condition-definition-parameter-delete-dialog.component';
import { sequenceStepConditionDefinitionParameterRoute } from './sequence-step-condition-definition-parameter.route';

@NgModule({
  imports: [LeloHubSharedModule, RouterModule.forChild(sequenceStepConditionDefinitionParameterRoute)],
  declarations: [
    SequenceStepConditionDefinitionParameterComponent,
    SequenceStepConditionDefinitionParameterDetailComponent,
    SequenceStepConditionDefinitionParameterUpdateComponent,
    SequenceStepConditionDefinitionParameterDeleteDialogComponent
  ],
  entryComponents: [SequenceStepConditionDefinitionParameterDeleteDialogComponent]
})
export class LeloHubSequenceStepConditionDefinitionParameterModule {}
