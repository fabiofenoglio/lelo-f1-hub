package it.fabiofenoglio.lelohub.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import it.fabiofenoglio.lelohub.web.rest.TestUtil;

public class SequenceStepActionDefinitionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SequenceStepActionDefinition.class);
        SequenceStepActionDefinition sequenceStepActionDefinition1 = new SequenceStepActionDefinition();
        sequenceStepActionDefinition1.setId(1L);
        SequenceStepActionDefinition sequenceStepActionDefinition2 = new SequenceStepActionDefinition();
        sequenceStepActionDefinition2.setId(sequenceStepActionDefinition1.getId());
        assertThat(sequenceStepActionDefinition1).isEqualTo(sequenceStepActionDefinition2);
        sequenceStepActionDefinition2.setId(2L);
        assertThat(sequenceStepActionDefinition1).isNotEqualTo(sequenceStepActionDefinition2);
        sequenceStepActionDefinition1.setId(null);
        assertThat(sequenceStepActionDefinition1).isNotEqualTo(sequenceStepActionDefinition2);
    }
}
