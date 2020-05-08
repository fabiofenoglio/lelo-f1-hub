package it.fabiofenoglio.lelohub.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SequenceMapperTest {

    private SequenceMapper sequenceMapper;

    @BeforeEach
    public void setUp() {
        sequenceMapper = new SequenceMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(sequenceMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(sequenceMapper.fromId(null)).isNull();
    }
}
