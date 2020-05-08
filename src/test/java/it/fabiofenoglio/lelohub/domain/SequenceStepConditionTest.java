package it.fabiofenoglio.lelohub.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import it.fabiofenoglio.lelohub.web.rest.TestUtil;

public class SequenceStepConditionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SequenceStepCondition.class);
        SequenceStepCondition sequenceStepCondition1 = new SequenceStepCondition();
        sequenceStepCondition1.setId(1L);
        SequenceStepCondition sequenceStepCondition2 = new SequenceStepCondition();
        sequenceStepCondition2.setId(sequenceStepCondition1.getId());
        assertThat(sequenceStepCondition1).isEqualTo(sequenceStepCondition2);
        sequenceStepCondition2.setId(2L);
        assertThat(sequenceStepCondition1).isNotEqualTo(sequenceStepCondition2);
        sequenceStepCondition1.setId(null);
        assertThat(sequenceStepCondition1).isNotEqualTo(sequenceStepCondition2);
    }
}
