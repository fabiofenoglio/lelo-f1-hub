package it.fabiofenoglio.lelohub.service.mapper;


import it.fabiofenoglio.lelohub.domain.*;
import it.fabiofenoglio.lelohub.service.dto.SequenceUserRatingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SequenceUserRating} and its DTO {@link SequenceUserRatingDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, SequenceMapper.class})
public interface SequenceUserRatingMapper extends EntityMapper<SequenceUserRatingDTO, SequenceUserRating> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "sequence.id", target = "sequenceId")
    @Mapping(source = "sequence.name", target = "sequenceName")
    SequenceUserRatingDTO toDto(SequenceUserRating sequenceUserRating);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "sequenceId", target = "sequence")
    SequenceUserRating toEntity(SequenceUserRatingDTO sequenceUserRatingDTO);

    default SequenceUserRating fromId(Long id) {
        if (id == null) {
            return null;
        }
        SequenceUserRating sequenceUserRating = new SequenceUserRating();
        sequenceUserRating.setId(id);
        return sequenceUserRating;
    }
}
