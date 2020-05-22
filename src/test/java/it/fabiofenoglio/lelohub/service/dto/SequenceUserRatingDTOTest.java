package it.fabiofenoglio.lelohub.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import it.fabiofenoglio.lelohub.web.rest.TestUtil;

public class SequenceUserRatingDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SequenceUserRatingDTO.class);
        SequenceUserRatingDTO sequenceUserRatingDTO1 = new SequenceUserRatingDTO();
        sequenceUserRatingDTO1.setId(1L);
        SequenceUserRatingDTO sequenceUserRatingDTO2 = new SequenceUserRatingDTO();
        assertThat(sequenceUserRatingDTO1).isNotEqualTo(sequenceUserRatingDTO2);
        sequenceUserRatingDTO2.setId(sequenceUserRatingDTO1.getId());
        assertThat(sequenceUserRatingDTO1).isEqualTo(sequenceUserRatingDTO2);
        sequenceUserRatingDTO2.setId(2L);
        assertThat(sequenceUserRatingDTO1).isNotEqualTo(sequenceUserRatingDTO2);
        sequenceUserRatingDTO1.setId(null);
        assertThat(sequenceUserRatingDTO1).isNotEqualTo(sequenceUserRatingDTO2);
    }
}
