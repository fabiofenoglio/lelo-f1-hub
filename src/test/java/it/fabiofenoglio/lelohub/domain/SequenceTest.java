package it.fabiofenoglio.lelohub.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import it.fabiofenoglio.lelohub.web.rest.TestUtil;

public class SequenceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sequence.class);
        Sequence sequence1 = new Sequence();
        sequence1.setId(1L);
        Sequence sequence2 = new Sequence();
        sequence2.setId(sequence1.getId());
        assertThat(sequence1).isEqualTo(sequence2);
        sequence2.setId(2L);
        assertThat(sequence1).isNotEqualTo(sequence2);
        sequence1.setId(null);
        assertThat(sequence1).isNotEqualTo(sequence2);
    }
}
