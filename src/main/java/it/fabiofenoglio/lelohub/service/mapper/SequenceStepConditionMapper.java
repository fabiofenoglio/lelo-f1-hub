package it.fabiofenoglio.lelohub.service.mapper;


import it.fabiofenoglio.lelohub.domain.*;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepConditionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SequenceStepCondition} and its DTO {@link SequenceStepConditionDTO}.
 */
@Mapper(componentModel = "spring", uses = {SequenceStepConditionDefinitionMapper.class, SequenceStepMapper.class, SequenceStepConditionParameterMapper.class})
public interface SequenceStepConditionMapper extends EntityMapper<SequenceStepConditionDTO, SequenceStepCondition> {

    @Mapping(source = "definition.id", target = "definitionId")
    @Mapping(source = "definition.code", target = "definitionCode")
    @Mapping(source = "step.id", target = "stepId")
    @Mapping(source = "otherAndCondition.id", target = "otherAndConditionId")
    @Mapping(source = "otherOrCondition.id", target = "otherOrConditionId")
    SequenceStepConditionDTO toDto(SequenceStepCondition sequenceStepCondition);

    @Mapping(target = "parameters", ignore = true)
    @Mapping(target = "removeParameters", ignore = true)
    @Mapping(target = "andConditions", ignore = true)
    @Mapping(target = "removeAndConditions", ignore = true)
    @Mapping(target = "orConditions", ignore = true)
    @Mapping(target = "removeOrConditions", ignore = true)
    @Mapping(source = "definitionId", target = "definition")
    @Mapping(source = "stepId", target = "step")
    @Mapping(source = "otherAndConditionId", target = "otherAndCondition")
    @Mapping(source = "otherOrConditionId", target = "otherOrCondition")
    SequenceStepCondition toEntity(SequenceStepConditionDTO sequenceStepConditionDTO);

    default SequenceStepCondition fromId(Long id) {
        if (id == null) {
            return null;
        }
        SequenceStepCondition sequenceStepCondition = new SequenceStepCondition();
        sequenceStepCondition.setId(id);
        return sequenceStepCondition;
    }
}
