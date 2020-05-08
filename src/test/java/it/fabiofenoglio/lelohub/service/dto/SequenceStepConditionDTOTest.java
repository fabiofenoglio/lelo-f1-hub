package it.fabiofenoglio.lelohub.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import it.fabiofenoglio.lelohub.web.rest.TestUtil;

public class SequenceStepConditionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SequenceStepConditionDTO.class);
        SequenceStepConditionDTO sequenceStepConditionDTO1 = new SequenceStepConditionDTO();
        sequenceStepConditionDTO1.setId(1L);
        SequenceStepConditionDTO sequenceStepConditionDTO2 = new SequenceStepConditionDTO();
        assertThat(sequenceStepConditionDTO1).isNotEqualTo(sequenceStepConditionDTO2);
        sequenceStepConditionDTO2.setId(sequenceStepConditionDTO1.getId());
        assertThat(sequenceStepConditionDTO1).isEqualTo(sequenceStepConditionDTO2);
        sequenceStepConditionDTO2.setId(2L);
        assertThat(sequenceStepConditionDTO1).isNotEqualTo(sequenceStepConditionDTO2);
        sequenceStepConditionDTO1.setId(null);
        assertThat(sequenceStepConditionDTO1).isNotEqualTo(sequenceStepConditionDTO2);
    }
}
