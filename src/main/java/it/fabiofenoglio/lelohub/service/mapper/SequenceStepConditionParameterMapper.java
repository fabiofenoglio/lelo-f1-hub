package it.fabiofenoglio.lelohub.service.mapper;


import it.fabiofenoglio.lelohub.domain.*;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepConditionParameterDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SequenceStepConditionParameter} and its DTO {@link SequenceStepConditionParameterDTO}.
 */
@Mapper(componentModel = "spring", uses = {SequenceStepConditionDefinitionParameterMapper.class, SequenceStepConditionMapper.class})
public interface SequenceStepConditionParameterMapper extends EntityMapper<SequenceStepConditionParameterDTO, SequenceStepConditionParameter> {

    @Mapping(source = "definition.id", target = "definitionId")
    @Mapping(source = "definition.name", target = "definitionName")
    @Mapping(source = "condition.id", target = "conditionId")
    SequenceStepConditionParameterDTO toDto(SequenceStepConditionParameter sequenceStepConditionParameter);

    @Mapping(source = "definitionId", target = "definition")
    @Mapping(source = "conditionId", target = "condition")
    SequenceStepConditionParameter toEntity(SequenceStepConditionParameterDTO sequenceStepConditionParameterDTO);

    default SequenceStepConditionParameter fromId(Long id) {
        if (id == null) {
            return null;
        }
        SequenceStepConditionParameter sequenceStepConditionParameter = new SequenceStepConditionParameter();
        sequenceStepConditionParameter.setId(id);
        return sequenceStepConditionParameter;
    }
}
