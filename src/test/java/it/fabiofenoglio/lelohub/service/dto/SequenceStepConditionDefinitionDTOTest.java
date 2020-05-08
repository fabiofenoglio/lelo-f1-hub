package it.fabiofenoglio.lelohub.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import it.fabiofenoglio.lelohub.web.rest.TestUtil;

public class SequenceStepConditionDefinitionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SequenceStepConditionDefinitionDTO.class);
        SequenceStepConditionDefinitionDTO sequenceStepConditionDefinitionDTO1 = new SequenceStepConditionDefinitionDTO();
        sequenceStepConditionDefinitionDTO1.setId(1L);
        SequenceStepConditionDefinitionDTO sequenceStepConditionDefinitionDTO2 = new SequenceStepConditionDefinitionDTO();
        assertThat(sequenceStepConditionDefinitionDTO1).isNotEqualTo(sequenceStepConditionDefinitionDTO2);
        sequenceStepConditionDefinitionDTO2.setId(sequenceStepConditionDefinitionDTO1.getId());
        assertThat(sequenceStepConditionDefinitionDTO1).isEqualTo(sequenceStepConditionDefinitionDTO2);
        sequenceStepConditionDefinitionDTO2.setId(2L);
        assertThat(sequenceStepConditionDefinitionDTO1).isNotEqualTo(sequenceStepConditionDefinitionDTO2);
        sequenceStepConditionDefinitionDTO1.setId(null);
        assertThat(sequenceStepConditionDefinitionDTO1).isNotEqualTo(sequenceStepConditionDefinitionDTO2);
    }
}
