package it.fabiofenoglio.lelohub.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import it.fabiofenoglio.lelohub.web.rest.TestUtil;

public class SequenceStepActionDefinitionParameterDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SequenceStepActionDefinitionParameterDTO.class);
        SequenceStepActionDefinitionParameterDTO sequenceStepActionDefinitionParameterDTO1 = new SequenceStepActionDefinitionParameterDTO();
        sequenceStepActionDefinitionParameterDTO1.setId(1L);
        SequenceStepActionDefinitionParameterDTO sequenceStepActionDefinitionParameterDTO2 = new SequenceStepActionDefinitionParameterDTO();
        assertThat(sequenceStepActionDefinitionParameterDTO1).isNotEqualTo(sequenceStepActionDefinitionParameterDTO2);
        sequenceStepActionDefinitionParameterDTO2.setId(sequenceStepActionDefinitionParameterDTO1.getId());
        assertThat(sequenceStepActionDefinitionParameterDTO1).isEqualTo(sequenceStepActionDefinitionParameterDTO2);
        sequenceStepActionDefinitionParameterDTO2.setId(2L);
        assertThat(sequenceStepActionDefinitionParameterDTO1).isNotEqualTo(sequenceStepActionDefinitionParameterDTO2);
        sequenceStepActionDefinitionParameterDTO1.setId(null);
        assertThat(sequenceStepActionDefinitionParameterDTO1).isNotEqualTo(sequenceStepActionDefinitionParameterDTO2);
    }
}
