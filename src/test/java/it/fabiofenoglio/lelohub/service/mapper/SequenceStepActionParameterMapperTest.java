package it.fabiofenoglio.lelohub.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SequenceStepActionParameterMapperTest {

    private SequenceStepActionParameterMapper sequenceStepActionParameterMapper;

    @BeforeEach
    public void setUp() {
        sequenceStepActionParameterMapper = new SequenceStepActionParameterMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(sequenceStepActionParameterMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(sequenceStepActionParameterMapper.fromId(null)).isNull();
    }
}
