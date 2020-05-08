package it.fabiofenoglio.lelohub.service.mapper;


import it.fabiofenoglio.lelohub.domain.*;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepConditionDefinitionParameterDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SequenceStepConditionDefinitionParameter} and its DTO {@link SequenceStepConditionDefinitionParameterDTO}.
 */
@Mapper(componentModel = "spring", uses = {SequenceStepConditionDefinitionMapper.class})
public interface SequenceStepConditionDefinitionParameterMapper extends EntityMapper<SequenceStepConditionDefinitionParameterDTO, SequenceStepConditionDefinitionParameter> {

    @Mapping(source = "definition.id", target = "definitionId")
    @Mapping(source = "definition.code", target = "definitionCode")
    SequenceStepConditionDefinitionParameterDTO toDto(SequenceStepConditionDefinitionParameter sequenceStepConditionDefinitionParameter);

    @Mapping(source = "definitionId", target = "definition")
    SequenceStepConditionDefinitionParameter toEntity(SequenceStepConditionDefinitionParameterDTO sequenceStepConditionDefinitionParameterDTO);

    default SequenceStepConditionDefinitionParameter fromId(Long id) {
        if (id == null) {
            return null;
        }
        SequenceStepConditionDefinitionParameter sequenceStepConditionDefinitionParameter = new SequenceStepConditionDefinitionParameter();
        sequenceStepConditionDefinitionParameter.setId(id);
        return sequenceStepConditionDefinitionParameter;
    }
}
