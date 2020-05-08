package it.fabiofenoglio.lelohub.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import it.fabiofenoglio.lelohub.web.rest.TestUtil;

public class SequenceStepActionParameterDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SequenceStepActionParameterDTO.class);
        SequenceStepActionParameterDTO sequenceStepActionParameterDTO1 = new SequenceStepActionParameterDTO();
        sequenceStepActionParameterDTO1.setId(1L);
        SequenceStepActionParameterDTO sequenceStepActionParameterDTO2 = new SequenceStepActionParameterDTO();
        assertThat(sequenceStepActionParameterDTO1).isNotEqualTo(sequenceStepActionParameterDTO2);
        sequenceStepActionParameterDTO2.setId(sequenceStepActionParameterDTO1.getId());
        assertThat(sequenceStepActionParameterDTO1).isEqualTo(sequenceStepActionParameterDTO2);
        sequenceStepActionParameterDTO2.setId(2L);
        assertThat(sequenceStepActionParameterDTO1).isNotEqualTo(sequenceStepActionParameterDTO2);
        sequenceStepActionParameterDTO1.setId(null);
        assertThat(sequenceStepActionParameterDTO1).isNotEqualTo(sequenceStepActionParameterDTO2);
    }
}
