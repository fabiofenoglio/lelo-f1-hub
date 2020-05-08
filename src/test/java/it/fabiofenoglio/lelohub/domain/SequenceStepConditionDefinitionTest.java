package it.fabiofenoglio.lelohub.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import it.fabiofenoglio.lelohub.web.rest.TestUtil;

public class SequenceStepConditionDefinitionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SequenceStepConditionDefinition.class);
        SequenceStepConditionDefinition sequenceStepConditionDefinition1 = new SequenceStepConditionDefinition();
        sequenceStepConditionDefinition1.setId(1L);
        SequenceStepConditionDefinition sequenceStepConditionDefinition2 = new SequenceStepConditionDefinition();
        sequenceStepConditionDefinition2.setId(sequenceStepConditionDefinition1.getId());
        assertThat(sequenceStepConditionDefinition1).isEqualTo(sequenceStepConditionDefinition2);
        sequenceStepConditionDefinition2.setId(2L);
        assertThat(sequenceStepConditionDefinition1).isNotEqualTo(sequenceStepConditionDefinition2);
        sequenceStepConditionDefinition1.setId(null);
        assertThat(sequenceStepConditionDefinition1).isNotEqualTo(sequenceStepConditionDefinition2);
    }
}
