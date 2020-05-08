package it.fabiofenoglio.lelohub.service.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.fabiofenoglio.lelohub.domain.SequenceStep;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepDTO;

/**
 * Mapper for the entity {@link SequenceStep} and its DTO {@link SequenceStepDTO}.
 */
@Mapper(componentModel = "spring", uses = {SequenceMapper.class, SequenceStepConditionMapper.class, SequenceStepActionMapper.class})
public interface SequenceStepMapper extends EntityMapper<SequenceStepDTO, SequenceStep> {

    @Mapping(source = "sequence.id", target = "sequenceId")
    @Mapping(source = "sequence.name", target = "sequenceName")
    SequenceStepDTO toDto(SequenceStep sequenceStep);

    @Mapping(target = "conditions", ignore = true)
    @Mapping(target = "removeConditions", ignore = true)
    @Mapping(target = "actions", ignore = true)
    @Mapping(target = "removeActions", ignore = true)
    @Mapping(source = "sequenceId", target = "sequence")
    SequenceStep toEntity(SequenceStepDTO sequenceStepDTO);

    default SequenceStep fromId(Long id) {
        if (id == null) {
            return null;
        }
        SequenceStep sequenceStep = new SequenceStep();
        sequenceStep.setId(id);
        return sequenceStep;
    }
}
