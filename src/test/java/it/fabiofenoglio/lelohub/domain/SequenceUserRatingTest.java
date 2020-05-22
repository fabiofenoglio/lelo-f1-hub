package it.fabiofenoglio.lelohub.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import it.fabiofenoglio.lelohub.web.rest.TestUtil;

public class SequenceUserRatingTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SequenceUserRating.class);
        SequenceUserRating sequenceUserRating1 = new SequenceUserRating();
        sequenceUserRating1.setId(1L);
        SequenceUserRating sequenceUserRating2 = new SequenceUserRating();
        sequenceUserRating2.setId(sequenceUserRating1.getId());
        assertThat(sequenceUserRating1).isEqualTo(sequenceUserRating2);
        sequenceUserRating2.setId(2L);
        assertThat(sequenceUserRating1).isNotEqualTo(sequenceUserRating2);
        sequenceUserRating1.setId(null);
        assertThat(sequenceUserRating1).isNotEqualTo(sequenceUserRating2);
    }
}
