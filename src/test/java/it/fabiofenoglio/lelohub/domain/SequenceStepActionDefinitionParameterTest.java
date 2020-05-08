package it.fabiofenoglio.lelohub.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import it.fabiofenoglio.lelohub.web.rest.TestUtil;

public class SequenceStepActionDefinitionParameterTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SequenceStepActionDefinitionParameter.class);
        SequenceStepActionDefinitionParameter sequenceStepActionDefinitionParameter1 = new SequenceStepActionDefinitionParameter();
        sequenceStepActionDefinitionParameter1.setId(1L);
        SequenceStepActionDefinitionParameter sequenceStepActionDefinitionParameter2 = new SequenceStepActionDefinitionParameter();
        sequenceStepActionDefinitionParameter2.setId(sequenceStepActionDefinitionParameter1.getId());
        assertThat(sequenceStepActionDefinitionParameter1).isEqualTo(sequenceStepActionDefinitionParameter2);
        sequenceStepActionDefinitionParameter2.setId(2L);
        assertThat(sequenceStepActionDefinitionParameter1).isNotEqualTo(sequenceStepActionDefinitionParameter2);
        sequenceStepActionDefinitionParameter1.setId(null);
        assertThat(sequenceStepActionDefinitionParameter1).isNotEqualTo(sequenceStepActionDefinitionParameter2);
    }
}
