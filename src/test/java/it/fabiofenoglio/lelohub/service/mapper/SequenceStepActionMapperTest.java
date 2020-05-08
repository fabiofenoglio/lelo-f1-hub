package it.fabiofenoglio.lelohub.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SequenceStepActionMapperTest {

    private SequenceStepActionMapper sequenceStepActionMapper;

    @BeforeEach
    public void setUp() {
        sequenceStepActionMapper = new SequenceStepActionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(sequenceStepActionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(sequenceStepActionMapper.fromId(null)).isNull();
    }
}
