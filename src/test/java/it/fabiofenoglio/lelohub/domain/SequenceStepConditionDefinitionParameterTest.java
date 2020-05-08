package it.fabiofenoglio.lelohub.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import it.fabiofenoglio.lelohub.web.rest.TestUtil;

public class SequenceStepConditionDefinitionParameterTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SequenceStepConditionDefinitionParameter.class);
        SequenceStepConditionDefinitionParameter sequenceStepConditionDefinitionParameter1 = new SequenceStepConditionDefinitionParameter();
        sequenceStepConditionDefinitionParameter1.setId(1L);
        SequenceStepConditionDefinitionParameter sequenceStepConditionDefinitionParameter2 = new SequenceStepConditionDefinitionParameter();
        sequenceStepConditionDefinitionParameter2.setId(sequenceStepConditionDefinitionParameter1.getId());
        assertThat(sequenceStepConditionDefinitionParameter1).isEqualTo(sequenceStepConditionDefinitionParameter2);
        sequenceStepConditionDefinitionParameter2.setId(2L);
        assertThat(sequenceStepConditionDefinitionParameter1).isNotEqualTo(sequenceStepConditionDefinitionParameter2);
        sequenceStepConditionDefinitionParameter1.setId(null);
        assertThat(sequenceStepConditionDefinitionParameter1).isNotEqualTo(sequenceStepConditionDefinitionParameter2);
    }
}
