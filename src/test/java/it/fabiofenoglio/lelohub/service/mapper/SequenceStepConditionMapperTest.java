package it.fabiofenoglio.lelohub.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SequenceStepConditionMapperTest {

    private SequenceStepConditionMapper sequenceStepConditionMapper;

    @BeforeEach
    public void setUp() {
        sequenceStepConditionMapper = new SequenceStepConditionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(sequenceStepConditionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(sequenceStepConditionMapper.fromId(null)).isNull();
    }
}
