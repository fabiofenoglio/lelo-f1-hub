package it.fabiofenoglio.lelohub.service.mapper;


import it.fabiofenoglio.lelohub.domain.*;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepActionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SequenceStepAction} and its DTO {@link SequenceStepActionDTO}.
 */
@Mapper(componentModel = "spring", uses = {SequenceStepActionDefinitionMapper.class, SequenceStepMapper.class, SequenceStepActionParameterMapper.class})
public interface SequenceStepActionMapper extends EntityMapper<SequenceStepActionDTO, SequenceStepAction> {

    @Mapping(source = "definition.id", target = "definitionId")
    @Mapping(source = "definition.code", target = "definitionCode")
    @Mapping(source = "step.id", target = "stepId")
    SequenceStepActionDTO toDto(SequenceStepAction sequenceStepAction);

    @Mapping(target = "parameters", ignore = true)
    @Mapping(target = "removeParameters", ignore = true)
    @Mapping(source = "definitionId", target = "definition")
    @Mapping(source = "stepId", target = "step")
    SequenceStepAction toEntity(SequenceStepActionDTO sequenceStepActionDTO);

    default SequenceStepAction fromId(Long id) {
        if (id == null) {
            return null;
        }
        SequenceStepAction sequenceStepAction = new SequenceStepAction();
        sequenceStepAction.setId(id);
        return sequenceStepAction;
    }
}
