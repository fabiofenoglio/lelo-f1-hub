package it.fabiofenoglio.lelohub.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import it.fabiofenoglio.lelohub.web.rest.TestUtil;

public class SequenceStepTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SequenceStep.class);
        SequenceStep sequenceStep1 = new SequenceStep();
        sequenceStep1.setId(1L);
        SequenceStep sequenceStep2 = new SequenceStep();
        sequenceStep2.setId(sequenceStep1.getId());
        assertThat(sequenceStep1).isEqualTo(sequenceStep2);
        sequenceStep2.setId(2L);
        assertThat(sequenceStep1).isNotEqualTo(sequenceStep2);
        sequenceStep1.setId(null);
        assertThat(sequenceStep1).isNotEqualTo(sequenceStep2);
    }
}
