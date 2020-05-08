package it.fabiofenoglio.lelohub.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SequenceStepConditionDefinitionMapperTest {

    private SequenceStepConditionDefinitionMapper sequenceStepConditionDefinitionMapper;

    @BeforeEach
    public void setUp() {
        sequenceStepConditionDefinitionMapper = new SequenceStepConditionDefinitionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(sequenceStepConditionDefinitionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(sequenceStepConditionDefinitionMapper.fromId(null)).isNull();
    }
}
