package it.fabiofenoglio.lelohub.service.mapper;


import it.fabiofenoglio.lelohub.domain.*;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepActionParameterDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SequenceStepActionParameter} and its DTO {@link SequenceStepActionParameterDTO}.
 */
@Mapper(componentModel = "spring", uses = {SequenceStepActionDefinitionParameterMapper.class, SequenceStepActionMapper.class})
public interface SequenceStepActionParameterMapper extends EntityMapper<SequenceStepActionParameterDTO, SequenceStepActionParameter> {

    @Mapping(source = "definition.id", target = "definitionId")
    @Mapping(source = "definition.name", target = "definitionName")
    @Mapping(source = "action.id", target = "actionId")
    SequenceStepActionParameterDTO toDto(SequenceStepActionParameter sequenceStepActionParameter);

    @Mapping(source = "definitionId", target = "definition")
    @Mapping(source = "actionId", target = "action")
    SequenceStepActionParameter toEntity(SequenceStepActionParameterDTO sequenceStepActionParameterDTO);

    default SequenceStepActionParameter fromId(Long id) {
        if (id == null) {
            return null;
        }
        SequenceStepActionParameter sequenceStepActionParameter = new SequenceStepActionParameter();
        sequenceStepActionParameter.setId(id);
        return sequenceStepActionParameter;
    }
}
