package it.fabiofenoglio.lelohub.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SequenceStepMapperTest {

    private SequenceStepMapper sequenceStepMapper;

    @BeforeEach
    public void setUp() {
        sequenceStepMapper = new SequenceStepMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(sequenceStepMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(sequenceStepMapper.fromId(null)).isNull();
    }
}
