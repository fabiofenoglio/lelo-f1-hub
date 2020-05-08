package it.fabiofenoglio.lelohub.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import it.fabiofenoglio.lelohub.web.rest.TestUtil;

public class SequenceStepDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SequenceStepDTO.class);
        SequenceStepDTO sequenceStepDTO1 = new SequenceStepDTO();
        sequenceStepDTO1.setId(1L);
        SequenceStepDTO sequenceStepDTO2 = new SequenceStepDTO();
        assertThat(sequenceStepDTO1).isNotEqualTo(sequenceStepDTO2);
        sequenceStepDTO2.setId(sequenceStepDTO1.getId());
        assertThat(sequenceStepDTO1).isEqualTo(sequenceStepDTO2);
        sequenceStepDTO2.setId(2L);
        assertThat(sequenceStepDTO1).isNotEqualTo(sequenceStepDTO2);
        sequenceStepDTO1.setId(null);
        assertThat(sequenceStepDTO1).isNotEqualTo(sequenceStepDTO2);
    }
}
