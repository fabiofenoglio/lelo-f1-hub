package it.fabiofenoglio.lelohub.service.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.fabiofenoglio.lelohub.domain.Sequence;
import it.fabiofenoglio.lelohub.service.dto.SequenceDTO;
import it.fabiofenoglio.lelohub.service.dto.SequenceListDTO;

/**
 * Mapper for the entity {@link Sequence} and its DTO {@link SequenceDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, SequenceStepMapper.class})
public interface SequenceMapper extends EntityMapper<SequenceDTO, Sequence> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(target = "authorizations", ignore = true)
    SequenceDTO toDto(Sequence sequence);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(target = "authorizations", ignore = true)
    SequenceListDTO toListDto(Sequence sequence);

    @Mapping(target = "steps", ignore = true)
    @Mapping(target = "removeSteps", ignore = true)
    @Mapping(source = "userId", target = "user")
    Sequence toEntity(SequenceDTO sequenceDTO);

    default Sequence fromId(Long id) {
        if (id == null) {
            return null;
        }
        Sequence sequence = new Sequence();
        sequence.setId(id);
        return sequence;
    }
}
