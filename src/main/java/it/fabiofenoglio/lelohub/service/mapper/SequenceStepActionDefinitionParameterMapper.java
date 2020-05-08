package it.fabiofenoglio.lelohub.service.mapper;


import it.fabiofenoglio.lelohub.domain.*;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepActionDefinitionParameterDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SequenceStepActionDefinitionParameter} and its DTO {@link SequenceStepActionDefinitionParameterDTO}.
 */
@Mapper(componentModel = "spring", uses = {SequenceStepActionDefinitionMapper.class})
public interface SequenceStepActionDefinitionParameterMapper extends EntityMapper<SequenceStepActionDefinitionParameterDTO, SequenceStepActionDefinitionParameter> {

    @Mapping(source = "definition.id", target = "definitionId")
    @Mapping(source = "definition.code", target = "definitionCode")
    SequenceStepActionDefinitionParameterDTO toDto(SequenceStepActionDefinitionParameter sequenceStepActionDefinitionParameter);

    @Mapping(source = "definitionId", target = "definition")
    SequenceStepActionDefinitionParameter toEntity(SequenceStepActionDefinitionParameterDTO sequenceStepActionDefinitionParameterDTO);

    default SequenceStepActionDefinitionParameter fromId(Long id) {
        if (id == null) {
            return null;
        }
        SequenceStepActionDefinitionParameter sequenceStepActionDefinitionParameter = new SequenceStepActionDefinitionParameter();
        sequenceStepActionDefinitionParameter.setId(id);
        return sequenceStepActionDefinitionParameter;
    }
}
