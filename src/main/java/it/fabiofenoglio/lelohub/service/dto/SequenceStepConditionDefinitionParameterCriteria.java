package it.fabiofenoglio.lelohub.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import it.fabiofenoglio.lelohub.domain.enumeration.SequenceStepConditionGeneration;
import it.fabiofenoglio.lelohub.domain.enumeration.SequenceStepConditionDefinitionParameterType;
import it.fabiofenoglio.lelohub.domain.enumeration.SequenceStepConditionEvaluationEngine;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link it.fabiofenoglio.lelohub.domain.SequenceStepConditionDefinitionParameter} entity. This class is used
 * in {@link it.fabiofenoglio.lelohub.web.rest.user.SequenceStepConditionDefinitionParameterResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /sequence-step-condition-definition-parameters?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SequenceStepConditionDefinitionParameterCriteria implements Serializable, Criteria {
    /**
     * Class for filtering SequenceStepConditionGeneration
     */
    public static class SequenceStepConditionGenerationFilter extends Filter<SequenceStepConditionGeneration> {

        public SequenceStepConditionGenerationFilter() {
        }

        public SequenceStepConditionGenerationFilter(SequenceStepConditionGenerationFilter filter) {
            super(filter);
        }

        @Override
        public SequenceStepConditionGenerationFilter copy() {
            return new SequenceStepConditionGenerationFilter(this);
        }

    }
    /**
     * Class for filtering SequenceStepConditionDefinitionParameterType
     */
    public static class SequenceStepConditionDefinitionParameterTypeFilter extends Filter<SequenceStepConditionDefinitionParameterType> {

        public SequenceStepConditionDefinitionParameterTypeFilter() {
        }

        public SequenceStepConditionDefinitionParameterTypeFilter(SequenceStepConditionDefinitionParameterTypeFilter filter) {
            super(filter);
        }

        @Override
        public SequenceStepConditionDefinitionParameterTypeFilter copy() {
            return new SequenceStepConditionDefinitionParameterTypeFilter(this);
        }

    }
    /**
     * Class for filtering SequenceStepConditionEvaluationEngine
     */
    public static class SequenceStepConditionEvaluationEngineFilter extends Filter<SequenceStepConditionEvaluationEngine> {

        public SequenceStepConditionEvaluationEngineFilter() {
        }

        public SequenceStepConditionEvaluationEngineFilter(SequenceStepConditionEvaluationEngineFilter filter) {
            super(filter);
        }

        @Override
        public SequenceStepConditionEvaluationEngineFilter copy() {
            return new SequenceStepConditionEvaluationEngineFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private SequenceStepConditionGenerationFilter generation;

    private StringFilter code;

    private StringFilter name;

    private StringFilter description;

    private SequenceStepConditionDefinitionParameterTypeFilter type;

    private SequenceStepConditionEvaluationEngineFilter evaluationEngine;

    private DoubleFilter minValue;

    private DoubleFilter maxValue;

    private DoubleFilter stepValue;

    private IntegerFilter minLength;

    private IntegerFilter maxLength;

    private LongFilter definitionId;

    public SequenceStepConditionDefinitionParameterCriteria() {
    }

    public SequenceStepConditionDefinitionParameterCriteria(SequenceStepConditionDefinitionParameterCriteria other) {
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
    public SequenceStepConditionDefinitionParameterCriteria copy() {
        return new SequenceStepConditionDefinitionParameterCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public SequenceStepConditionGenerationFilter getGeneration() {
        return generation;
    }

    public void setGeneration(SequenceStepConditionGenerationFilter generation) {
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

    public SequenceStepConditionDefinitionParameterTypeFilter getType() {
        return type;
    }

    public void setType(SequenceStepConditionDefinitionParameterTypeFilter type) {
        this.type = type;
    }

    public SequenceStepConditionEvaluationEngineFilter getEvaluationEngine() {
        return evaluationEngine;
    }

    public void setEvaluationEngine(SequenceStepConditionEvaluationEngineFilter evaluationEngine) {
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
        final SequenceStepConditionDefinitionParameterCriteria that = (SequenceStepConditionDefinitionParameterCriteria) o;
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
        return "SequenceStepConditionDefinitionParameterCriteria{" +
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
