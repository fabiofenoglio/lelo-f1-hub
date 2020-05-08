package it.fabiofenoglio.lelohub.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import it.fabiofenoglio.lelohub.web.rest.TestUtil;

public class SequenceStepConditionParameterDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SequenceStepConditionParameterDTO.class);
        SequenceStepConditionParameterDTO sequenceStepConditionParameterDTO1 = new SequenceStepConditionParameterDTO();
        sequenceStepConditionParameterDTO1.setId(1L);
        SequenceStepConditionParameterDTO sequenceStepConditionParameterDTO2 = new SequenceStepConditionParameterDTO();
        assertThat(sequenceStepConditionParameterDTO1).isNotEqualTo(sequenceStepConditionParameterDTO2);
        sequenceStepConditionParameterDTO2.setId(sequenceStepConditionParameterDTO1.getId());
        assertThat(sequenceStepConditionParameterDTO1).isEqualTo(sequenceStepConditionParameterDTO2);
        sequenceStepConditionParameterDTO2.setId(2L);
        assertThat(sequenceStepConditionParameterDTO1).isNotEqualTo(sequenceStepConditionParameterDTO2);
        sequenceStepConditionParameterDTO1.setId(null);
        assertThat(sequenceStepConditionParameterDTO1).isNotEqualTo(sequenceStepConditionParameterDTO2);
    }
}
