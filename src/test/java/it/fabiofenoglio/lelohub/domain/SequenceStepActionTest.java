package it.fabiofenoglio.lelohub.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import it.fabiofenoglio.lelohub.web.rest.TestUtil;

public class SequenceStepActionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SequenceStepAction.class);
        SequenceStepAction sequenceStepAction1 = new SequenceStepAction();
        sequenceStepAction1.setId(1L);
        SequenceStepAction sequenceStepAction2 = new SequenceStepAction();
        sequenceStepAction2.setId(sequenceStepAction1.getId());
        assertThat(sequenceStepAction1).isEqualTo(sequenceStepAction2);
        sequenceStepAction2.setId(2L);
        assertThat(sequenceStepAction1).isNotEqualTo(sequenceStepAction2);
        sequenceStepAction1.setId(null);
        assertThat(sequenceStepAction1).isNotEqualTo(sequenceStepAction2);
    }
}
