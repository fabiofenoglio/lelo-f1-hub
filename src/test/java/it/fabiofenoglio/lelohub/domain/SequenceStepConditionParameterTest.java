package it.fabiofenoglio.lelohub.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import it.fabiofenoglio.lelohub.web.rest.TestUtil;

public class SequenceStepConditionParameterTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SequenceStepConditionParameter.class);
        SequenceStepConditionParameter sequenceStepConditionParameter1 = new SequenceStepConditionParameter();
        sequenceStepConditionParameter1.setId(1L);
        SequenceStepConditionParameter sequenceStepConditionParameter2 = new SequenceStepConditionParameter();
        sequenceStepConditionParameter2.setId(sequenceStepConditionParameter1.getId());
        assertThat(sequenceStepConditionParameter1).isEqualTo(sequenceStepConditionParameter2);
        sequenceStepConditionParameter2.setId(2L);
        assertThat(sequenceStepConditionParameter1).isNotEqualTo(sequenceStepConditionParameter2);
        sequenceStepConditionParameter1.setId(null);
        assertThat(sequenceStepConditionParameter1).isNotEqualTo(sequenceStepConditionParameter2);
    }
}
