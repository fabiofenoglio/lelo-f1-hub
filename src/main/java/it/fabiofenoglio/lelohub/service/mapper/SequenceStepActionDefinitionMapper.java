package it.fabiofenoglio.lelohub.service.mapper;


import it.fabiofenoglio.lelohub.domain.*;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepActionDefinitionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SequenceStepActionDefinition} and its DTO {@link SequenceStepActionDefinitionDTO}.
 */
@Mapper(componentModel = "spring", uses = {SequenceStepActionDefinitionParameterMapper.class})
public interface SequenceStepActionDefinitionMapper extends EntityMapper<SequenceStepActionDefinitionDTO, SequenceStepActionDefinition> {


    @Mapping(target = "parameters", ignore = true)
    @Mapping(target = "removeParameters", ignore = true)
    SequenceStepActionDefinition toEntity(SequenceStepActionDefinitionDTO sequenceStepActionDefinitionDTO);

    default SequenceStepActionDefinition fromId(Long id) {
        if (id == null) {
            return null;
        }
        SequenceStepActionDefinition sequenceStepActionDefinition = new SequenceStepActionDefinition();
        sequenceStepActionDefinition.setId(id);
        return sequenceStepActionDefinition;
    }
}
