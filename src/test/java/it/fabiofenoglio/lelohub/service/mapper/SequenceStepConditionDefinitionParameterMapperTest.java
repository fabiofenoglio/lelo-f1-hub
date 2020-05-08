package it.fabiofenoglio.lelohub.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SequenceStepConditionDefinitionParameterMapperTest {

    private SequenceStepConditionDefinitionParameterMapper sequenceStepConditionDefinitionParameterMapper;

    @BeforeEach
    public void setUp() {
        sequenceStepConditionDefinitionParameterMapper = new SequenceStepConditionDefinitionParameterMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(sequenceStepConditionDefinitionParameterMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(sequenceStepConditionDefinitionParameterMapper.fromId(null)).isNull();
    }
}
