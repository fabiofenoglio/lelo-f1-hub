package it.fabiofenoglio.lelohub.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SequenceStepConditionParameterMapperTest {

    private SequenceStepConditionParameterMapper sequenceStepConditionParameterMapper;

    @BeforeEach
    public void setUp() {
        sequenceStepConditionParameterMapper = new SequenceStepConditionParameterMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(sequenceStepConditionParameterMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(sequenceStepConditionParameterMapper.fromId(null)).isNull();
    }
}
