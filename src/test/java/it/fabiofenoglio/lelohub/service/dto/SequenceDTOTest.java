package it.fabiofenoglio.lelohub.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import it.fabiofenoglio.lelohub.web.rest.TestUtil;

public class SequenceDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SequenceDTO.class);
        SequenceDTO sequenceDTO1 = new SequenceDTO();
        sequenceDTO1.setId(1L);
        SequenceDTO sequenceDTO2 = new SequenceDTO();
        assertThat(sequenceDTO1).isNotEqualTo(sequenceDTO2);
        sequenceDTO2.setId(sequenceDTO1.getId());
        assertThat(sequenceDTO1).isEqualTo(sequenceDTO2);
        sequenceDTO2.setId(2L);
        assertThat(sequenceDTO1).isNotEqualTo(sequenceDTO2);
        sequenceDTO1.setId(null);
        assertThat(sequenceDTO1).isNotEqualTo(sequenceDTO2);
    }
}
