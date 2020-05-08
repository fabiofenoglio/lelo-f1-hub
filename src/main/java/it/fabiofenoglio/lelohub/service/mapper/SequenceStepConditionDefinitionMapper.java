package it.fabiofenoglio.lelohub.service.mapper;


import it.fabiofenoglio.lelohub.domain.*;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepConditionDefinitionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SequenceStepConditionDefinition} and its DTO {@link SequenceStepConditionDefinitionDTO}.
 */
@Mapper(componentModel = "spring", uses = {SequenceStepConditionDefinitionParameterMapper.class})
public interface SequenceStepConditionDefinitionMapper extends EntityMapper<SequenceStepConditionDefinitionDTO, SequenceStepConditionDefinition> {


    @Mapping(target = "parameters", ignore = true)
    @Mapping(target = "removeParameters", ignore = true)
    SequenceStepConditionDefinition toEntity(SequenceStepConditionDefinitionDTO sequenceStepConditionDefinitionDTO);

    default SequenceStepConditionDefinition fromId(Long id) {
        if (id == null) {
            return null;
        }
        SequenceStepConditionDefinition sequenceStepConditionDefinition = new SequenceStepConditionDefinition();
        sequenceStepConditionDefinition.setId(id);
        return sequenceStepConditionDefinition;
    }
}
