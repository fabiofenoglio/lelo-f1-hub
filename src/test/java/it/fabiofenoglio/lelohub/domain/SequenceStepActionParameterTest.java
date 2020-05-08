package it.fabiofenoglio.lelohub.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import it.fabiofenoglio.lelohub.web.rest.TestUtil;

public class SequenceStepActionParameterTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SequenceStepActionParameter.class);
        SequenceStepActionParameter sequenceStepActionParameter1 = new SequenceStepActionParameter();
        sequenceStepActionParameter1.setId(1L);
        SequenceStepActionParameter sequenceStepActionParameter2 = new SequenceStepActionParameter();
        sequenceStepActionParameter2.setId(sequenceStepActionParameter1.getId());
        assertThat(sequenceStepActionParameter1).isEqualTo(sequenceStepActionParameter2);
        sequenceStepActionParameter2.setId(2L);
        assertThat(sequenceStepActionParameter1).isNotEqualTo(sequenceStepActionParameter2);
        sequenceStepActionParameter1.setId(null);
        assertThat(sequenceStepActionParameter1).isNotEqualTo(sequenceStepActionParameter2);
    }
}
