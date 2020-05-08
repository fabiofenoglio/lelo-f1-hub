package it.fabiofenoglio.lelohub.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SequenceStepActionDefinitionParameterMapperTest {

    private SequenceStepActionDefinitionParameterMapper sequenceStepActionDefinitionParameterMapper;

    @BeforeEach
    public void setUp() {
        sequenceStepActionDefinitionParameterMapper = new SequenceStepActionDefinitionParameterMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(sequenceStepActionDefinitionParameterMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(sequenceStepActionDefinitionParameterMapper.fromId(null)).isNull();
    }
}
