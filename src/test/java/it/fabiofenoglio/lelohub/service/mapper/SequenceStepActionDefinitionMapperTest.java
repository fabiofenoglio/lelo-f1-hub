package it.fabiofenoglio.lelohub.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SequenceStepActionDefinitionMapperTest {

    private SequenceStepActionDefinitionMapper sequenceStepActionDefinitionMapper;

    @BeforeEach
    public void setUp() {
        sequenceStepActionDefinitionMapper = new SequenceStepActionDefinitionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(sequenceStepActionDefinitionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(sequenceStepActionDefinitionMapper.fromId(null)).isNull();
    }
}
