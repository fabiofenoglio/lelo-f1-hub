package it.fabiofenoglio.lelohub.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import it.fabiofenoglio.lelohub.domain.enumeration.SequenceStepActionGeneration;
import it.fabiofenoglio.lelohub.domain.enumeration.SequenceStepActionDefinitionParameterType;
import it.fabiofenoglio.lelohub.domain.enumeration.SequenceStepActionEvaluationEngine;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link it.fabiofenoglio.lelohub.domain.SequenceStepActionDefinitionParameter} entity. This class is used
 * in {@link it.fabiofenoglio.lelohub.web.rest.user.SequenceStepActionDefinitionParameterResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /sequence-step-action-definition-parameters?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SequenceStepActionDefinitionParameterCriteria implements Serializable, Criteria {
    /**
     * Class for filtering SequenceStepActionGeneration
     */
    public static class SequenceStepActionGenerationFilter extends Filter<SequenceStepActionGeneration> {

        public SequenceStepActionGenerationFilter() {
        }

        public SequenceStepActionGenerationFilter(SequenceStepActionGenerationFilter filter) {
            super(filter);
        }

        @Override
        public SequenceStepActionGenerationFilter copy() {
            return new SequenceStepActionGenerationFilter(this);
        }

    }
    /**
     * Class for filtering SequenceStepActionDefinitionParameterType
     */
    public static class SequenceStepActionDefinitionParameterTypeFilter extends Filter<SequenceStepActionDefinitionParameterType> {

        public SequenceStepActionDefinitionParameterTypeFilter() {
        }

        public SequenceStepActionDefinitionParameterTypeFilter(SequenceStepActionDefinitionParameterTypeFilter filter) {
            super(filter);
        }

        @Override
        public SequenceStepActionDefinitionParameterTypeFilter copy() {
            return new SequenceStepActionDefinitionParameterTypeFilter(this);
        }

    }
    /**
     * Class for filtering SequenceStepActionEvaluationEngine
     */
    public static class SequenceStepActionEvaluationEngineFilter extends Filter<SequenceStepActionEvaluationEngine> {

        public SequenceStepActionEvaluationEngineFilter() {
        }

        public SequenceStepActionEvaluationEngineFilter(SequenceStepActionEvaluationEngineFilter filter) {
            super(filter);
        }

        @Override
        public SequenceStepActionEvaluationEngineFilter copy() {
            return new SequenceStepActionEvaluationEngineFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private SequenceStepActionGenerationFilter generation;

    private StringFilter code;

    private StringFilter name;

    private StringFilter description;

    private SequenceStepActionDefinitionParameterTypeFilter type;

    private SequenceStepActionEvaluationEngineFilter evaluationEngine;

    private DoubleFilter minValue;

    private DoubleFilter maxValue;

    private DoubleFilter stepValue;

    private IntegerFilter minLength;

    private IntegerFilter maxLength;

    private LongFilter definitionId;

    public SequenceStepActionDefinitionParameterCriteria() {
    }

    public SequenceStepActionDefinitionParameterCriteria(SequenceStepActionDefinitionParameterCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.generation = other.generation == null ? null : other.generation.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.evaluationEngine = other.evaluationEngine == null ? null : other.evaluationEngine.copy();
        this.minValue = other.minValue == null ? null : other.minValue.copy();
        this.maxValue = other.maxValue == null ? null : other.maxValue.copy();
        this.stepValue = other.stepValue == null ? null : other.stepValue.copy();
        this.minLength = other.minLength == null ? null : other.minLength.copy();
        this.maxLength = other.maxLength == null ? null : other.maxLength.copy();
        this.definitionId = other.definitionId == null ? null : other.definitionId.copy();
    }

    @Override
    public SequenceStepActionDefinitionParameterCriteria copy() {
        return new SequenceStepActionDefinitionParameterCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public SequenceStepActionGenerationFilter getGeneration() {
        return generation;
    }

    public void setGeneration(SequenceStepActionGenerationFilter generation) {
        this.generation = generation;
    }

    public StringFilter getCode() {
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public SequenceStepActionDefinitionParameterTypeFilter getType() {
        return type;
    }

    public void setType(SequenceStepActionDefinitionParameterTypeFilter type) {
        this.type = type;
    }

    public SequenceStepActionEvaluationEngineFilter getEvaluationEngine() {
        return evaluationEngine;
    }

    public void setEvaluationEngine(SequenceStepActionEvaluationEngineFilter evaluationEngine) {
        this.evaluationEngine = evaluationEngine;
    }

    public DoubleFilter getMinValue() {
        return minValue;
    }

    public void setMinValue(DoubleFilter minValue) {
        this.minValue = minValue;
    }

    public DoubleFilter getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(DoubleFilter maxValue) {
        this.maxValue = maxValue;
    }

    public DoubleFilter getStepValue() {
        return stepValue;
    }

    public void setStepValue(DoubleFilter stepValue) {
        this.stepValue = stepValue;
    }

    public IntegerFilter getMinLength() {
        return minLength;
    }

    public void setMinLength(IntegerFilter minLength) {
        this.minLength = minLength;
    }

    public IntegerFilter getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(IntegerFilter maxLength) {
        this.maxLength = maxLength;
    }

    public LongFilter getDefinitionId() {
        return definitionId;
    }

    public void setDefinitionId(LongFilter definitionId) {
        this.definitionId = definitionId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SequenceStepActionDefinitionParameterCriteria that = (SequenceStepActionDefinitionParameterCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(generation, that.generation) &&
            Objects.equals(code, that.code) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(type, that.type) &&
            Objects.equals(evaluationEngine, that.evaluationEngine) &&
            Objects.equals(minValue, that.minValue) &&
            Objects.equals(maxValue, that.maxValue) &&
            Objects.equals(stepValue, that.stepValue) &&
            Objects.equals(minLength, that.minLength) &&
            Objects.equals(maxLength, that.maxLength) &&
            Objects.equals(definitionId, that.definitionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        generation,
        code,
        name,
        description,
        type,
        evaluationEngine,
        minValue,
        maxValue,
        stepValue,
        minLength,
        maxLength,
        definitionId
        );
    }

    @Override
    public String toString() {
        return "SequenceStepActionDefinitionParameterCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (generation != null ? "generation=" + generation + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (evaluationEngine != null ? "evaluationEngine=" + evaluationEngine + ", " : "") +
                (minValue != null ? "minValue=" + minValue + ", " : "") +
                (maxValue != null ? "maxValue=" + maxValue + ", " : "") +
                (stepValue != null ? "stepValue=" + stepValue + ", " : "") +
                (minLength != null ? "minLength=" + minLength + ", " : "") +
                (maxLength != null ? "maxLength=" + maxLength + ", " : "") +
                (definitionId != null ? "definitionId=" + definitionId + ", " : "") +
            "}";
    }

}
