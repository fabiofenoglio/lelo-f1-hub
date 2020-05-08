package it.fabiofenoglio.lelohub.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import it.fabiofenoglio.lelohub.domain.enumeration.SequenceStepConditionGeneration;
import it.fabiofenoglio.lelohub.domain.enumeration.SequenceVariable;
import it.fabiofenoglio.lelohub.domain.enumeration.SequenceOperator;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link it.fabiofenoglio.lelohub.domain.SequenceStepConditionParameter} entity. This class is used
 * in {@link it.fabiofenoglio.lelohub.web.rest.user.SequenceStepConditionParameterResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /sequence-step-condition-parameters?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SequenceStepConditionParameterCriteria implements Serializable, Criteria {
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
     * Class for filtering SequenceVariable
     */
    public static class SequenceVariableFilter extends Filter<SequenceVariable> {

        public SequenceVariableFilter() {
        }

        public SequenceVariableFilter(SequenceVariableFilter filter) {
            super(filter);
        }

        @Override
        public SequenceVariableFilter copy() {
            return new SequenceVariableFilter(this);
        }

    }
    /**
     * Class for filtering SequenceOperator
     */
    public static class SequenceOperatorFilter extends Filter<SequenceOperator> {

        public SequenceOperatorFilter() {
        }

        public SequenceOperatorFilter(SequenceOperatorFilter filter) {
            super(filter);
        }

        @Override
        public SequenceOperatorFilter copy() {
            return new SequenceOperatorFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private SequenceStepConditionGenerationFilter generation;

    private StringFilter valueString;

    private DoubleFilter valueNumber;

    private BooleanFilter valueBoolean;

    private SequenceVariableFilter valueVariable;

    private SequenceOperatorFilter valueOperator;

    private LongFilter definitionId;

    private LongFilter conditionId;

    public SequenceStepConditionParameterCriteria() {
    }

    public SequenceStepConditionParameterCriteria(SequenceStepConditionParameterCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.generation = other.generation == null ? null : other.generation.copy();
        this.valueString = other.valueString == null ? null : other.valueString.copy();
        this.valueNumber = other.valueNumber == null ? null : other.valueNumber.copy();
        this.valueBoolean = other.valueBoolean == null ? null : other.valueBoolean.copy();
        this.valueVariable = other.valueVariable == null ? null : other.valueVariable.copy();
        this.valueOperator = other.valueOperator == null ? null : other.valueOperator.copy();
        this.definitionId = other.definitionId == null ? null : other.definitionId.copy();
        this.conditionId = other.conditionId == null ? null : other.conditionId.copy();
    }

    @Override
    public SequenceStepConditionParameterCriteria copy() {
        return new SequenceStepConditionParameterCriteria(this);
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

    public StringFilter getValueString() {
        return valueString;
    }

    public void setValueString(StringFilter valueString) {
        this.valueString = valueString;
    }

    public DoubleFilter getValueNumber() {
        return valueNumber;
    }

    public void setValueNumber(DoubleFilter valueNumber) {
        this.valueNumber = valueNumber;
    }

    public BooleanFilter getValueBoolean() {
        return valueBoolean;
    }

    public void setValueBoolean(BooleanFilter valueBoolean) {
        this.valueBoolean = valueBoolean;
    }

    public SequenceVariableFilter getValueVariable() {
        return valueVariable;
    }

    public void setValueVariable(SequenceVariableFilter valueVariable) {
        this.valueVariable = valueVariable;
    }

    public SequenceOperatorFilter getValueOperator() {
        return valueOperator;
    }

    public void setValueOperator(SequenceOperatorFilter valueOperator) {
        this.valueOperator = valueOperator;
    }

    public LongFilter getDefinitionId() {
        return definitionId;
    }

    public void setDefinitionId(LongFilter definitionId) {
        this.definitionId = definitionId;
    }

    public LongFilter getConditionId() {
        return conditionId;
    }

    public void setConditionId(LongFilter conditionId) {
        this.conditionId = conditionId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SequenceStepConditionParameterCriteria that = (SequenceStepConditionParameterCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(generation, that.generation) &&
            Objects.equals(valueString, that.valueString) &&
            Objects.equals(valueNumber, that.valueNumber) &&
            Objects.equals(valueBoolean, that.valueBoolean) &&
            Objects.equals(valueVariable, that.valueVariable) &&
            Objects.equals(valueOperator, that.valueOperator) &&
            Objects.equals(definitionId, that.definitionId) &&
            Objects.equals(conditionId, that.conditionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        generation,
        valueString,
        valueNumber,
        valueBoolean,
        valueVariable,
        valueOperator,
        definitionId,
        conditionId
        );
    }

    @Override
    public String toString() {
        return "SequenceStepConditionParameterCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (generation != null ? "generation=" + generation + ", " : "") +
                (valueString != null ? "valueString=" + valueString + ", " : "") +
                (valueNumber != null ? "valueNumber=" + valueNumber + ", " : "") +
                (valueBoolean != null ? "valueBoolean=" + valueBoolean + ", " : "") +
                (valueVariable != null ? "valueVariable=" + valueVariable + ", " : "") +
                (valueOperator != null ? "valueOperator=" + valueOperator + ", " : "") +
                (definitionId != null ? "definitionId=" + definitionId + ", " : "") +
                (conditionId != null ? "conditionId=" + conditionId + ", " : "") +
            "}";
    }

}
