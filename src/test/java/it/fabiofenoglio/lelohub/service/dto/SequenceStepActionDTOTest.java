package it.fabiofenoglio.lelohub.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import it.fabiofenoglio.lelohub.web.rest.TestUtil;

public class SequenceStepActionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SequenceStepActionDTO.class);
        SequenceStepActionDTO sequenceStepActionDTO1 = new SequenceStepActionDTO();
        sequenceStepActionDTO1.setId(1L);
        SequenceStepActionDTO sequenceStepActionDTO2 = new SequenceStepActionDTO();
        assertThat(sequenceStepActionDTO1).isNotEqualTo(sequenceStepActionDTO2);
        sequenceStepActionDTO2.setId(sequenceStepActionDTO1.getId());
        assertThat(sequenceStepActionDTO1).isEqualTo(sequenceStepActionDTO2);
        sequenceStepActionDTO2.setId(2L);
        assertThat(sequenceStepActionDTO1).isNotEqualTo(sequenceStepActionDTO2);
        sequenceStepActionDTO1.setId(null);
        assertThat(sequenceStepActionDTO1).isNotEqualTo(sequenceStepActionDTO2);
    }
}
