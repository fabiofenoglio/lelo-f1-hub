package it.fabiofenoglio.lelohub.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SequenceUserRatingMapperTest {

    private SequenceUserRatingMapper sequenceUserRatingMapper;

    @BeforeEach
    public void setUp() {
        sequenceUserRatingMapper = new SequenceUserRatingMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(sequenceUserRatingMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(sequenceUserRatingMapper.fromId(null)).isNull();
    }
}
