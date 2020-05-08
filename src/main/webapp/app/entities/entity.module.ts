import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'sequence',
        loadChildren: () => import('./sequence/sequence.module').then(m => m.LeloHubSequenceModule)
      },
      {
        path: 'sequence-step',
        loadChildren: () => import('./sequence-step/sequence-step.module').then(m => m.LeloHubSequenceStepModule)
      },
      {
        path: 'sequence-step-condition-definition',
        loadChildren: () =>
          import('./sequence-step-condition-definition/sequence-step-condition-definition.module').then(
            m => m.LeloHubSequenceStepConditionDefinitionModule
          )
      },
      {
        path: 'sequence-step-condition-definition-parameter',
        loadChildren: () =>
          import('./sequence-step-condition-definition-parameter/sequence-step-condition-definition-parameter.module').then(
            m => m.LeloHubSequenceStepConditionDefinitionParameterModule
          )
      },
      {
        path: 'sequence-step-condition',
        loadChildren: () =>
          import('./sequence-step-condition/sequence-step-condition.module').then(m => m.LeloHubSequenceStepConditionModule)
      },
      {
        path: 'sequence-step-condition-parameter',
        loadChildren: () =>
          import('./sequence-step-condition-parameter/sequence-step-condition-parameter.module').then(
            m => m.LeloHubSequenceStepConditionParameterModule
          )
      },
      {
        path: 'sequence-step-action-definition',
        loadChildren: () =>
          import('./sequence-step-action-definition/sequence-step-action-definition.module').then(
            m => m.LeloHubSequenceStepActionDefinitionModule
          )
      },
      {
        path: 'sequence-step-action-definition-parameter',
        loadChildren: () =>
          import('./sequence-step-action-definition-parameter/sequence-step-action-definition-parameter.module').then(
            m => m.LeloHubSequenceStepActionDefinitionParameterModule
          )
      },
      {
        path: 'sequence-step-action',
        loadChildren: () => import('./sequence-step-action/sequence-step-action.module').then(m => m.LeloHubSequenceStepActionModule)
      },
      {
        path: 'sequence-step-action-parameter',
        loadChildren: () =>
          import('./sequence-step-action-parameter/sequence-step-action-parameter.module').then(
            m => m.LeloHubSequenceStepActionParameterModule
          )
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class LeloHubEntityModule {}
