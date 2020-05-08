package it.fabiofenoglio.lelohub.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import it.fabiofenoglio.lelohub.web.rest.TestUtil;

public class SequenceStepConditionDefinitionParameterDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SequenceStepConditionDefinitionParameterDTO.class);
        SequenceStepConditionDefinitionParameterDTO sequenceStepConditionDefinitionParameterDTO1 = new SequenceStepConditionDefinitionParameterDTO();
        sequenceStepConditionDefinitionParameterDTO1.setId(1L);
        SequenceStepConditionDefinitionParameterDTO sequenceStepConditionDefinitionParameterDTO2 = new SequenceStepConditionDefinitionParameterDTO();
        assertThat(sequenceStepConditionDefinitionParameterDTO1).isNotEqualTo(sequenceStepConditionDefinitionParameterDTO2);
        sequenceStepConditionDefinitionParameterDTO2.setId(sequenceStepConditionDefinitionParameterDTO1.getId());
        assertThat(sequenceStepConditionDefinitionParameterDTO1).isEqualTo(sequenceStepConditionDefinitionParameterDTO2);
        sequenceStepConditionDefinitionParameterDTO2.setId(2L);
        assertThat(sequenceStepConditionDefinitionParameterDTO1).isNotEqualTo(sequenceStepConditionDefinitionParameterDTO2);
        sequenceStepConditionDefinitionParameterDTO1.setId(null);
        assertThat(sequenceStepConditionDefinitionParameterDTO1).isNotEqualTo(sequenceStepConditionDefinitionParameterDTO2);
    }
}
