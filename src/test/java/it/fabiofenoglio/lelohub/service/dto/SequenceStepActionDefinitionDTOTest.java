package it.fabiofenoglio.lelohub.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import it.fabiofenoglio.lelohub.web.rest.TestUtil;

public class SequenceStepActionDefinitionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SequenceStepActionDefinitionDTO.class);
        SequenceStepActionDefinitionDTO sequenceStepActionDefinitionDTO1 = new SequenceStepActionDefinitionDTO();
        sequenceStepActionDefinitionDTO1.setId(1L);
        SequenceStepActionDefinitionDTO sequenceStepActionDefinitionDTO2 = new SequenceStepActionDefinitionDTO();
        assertThat(sequenceStepActionDefinitionDTO1).isNotEqualTo(sequenceStepActionDefinitionDTO2);
        sequenceStepActionDefinitionDTO2.setId(sequenceStepActionDefinitionDTO1.getId());
        assertThat(sequenceStepActionDefinitionDTO1).isEqualTo(sequenceStepActionDefinitionDTO2);
        sequenceStepActionDefinitionDTO2.setId(2L);
        assertThat(sequenceStepActionDefinitionDTO1).isNotEqualTo(sequenceStepActionDefinitionDTO2);
        sequenceStepActionDefinitionDTO1.setId(null);
        assertThat(sequenceStepActionDefinitionDTO1).isNotEqualTo(sequenceStepActionDefinitionDTO2);
    }
}
