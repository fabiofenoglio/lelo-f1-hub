package it.fabiofenoglio.lelohub.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import it.fabiofenoglio.lelohub.domain.enumeration.SequenceStepGeneration;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link it.fabiofenoglio.lelohub.domain.SequenceStep} entity. This class is used
 * in {@link it.fabiofenoglio.lelohub.web.rest.user.SequenceStepResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /sequence-steps?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SequenceStepCriteria implements Serializable, Criteria {
    /**
     * Class for filtering SequenceStepGeneration
     */
    public static class SequenceStepGenerationFilter extends Filter<SequenceStepGeneration> {

        public SequenceStepGenerationFilter() {
        }

        public SequenceStepGenerationFilter(SequenceStepGenerationFilter filter) {
            super(filter);
        }

        @Override
        public SequenceStepGenerationFilter copy() {
            return new SequenceStepGenerationFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private SequenceStepGenerationFilter generation;

    private IntegerFilter ordinal;

    private StringFilter description;

    private LongFilter conditionsId;

    private LongFilter actionsId;

    private LongFilter sequenceId;

    public SequenceStepCriteria() {
    }

    public SequenceStepCriteria(SequenceStepCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.generation = other.generation == null ? null : other.generation.copy();
        this.ordinal = other.ordinal == null ? null : other.ordinal.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.conditionsId = other.conditionsId == null ? null : other.conditionsId.copy();
        this.actionsId = other.actionsId == null ? null : other.actionsId.copy();
        this.sequenceId = other.sequenceId == null ? null : other.sequenceId.copy();
    }

    @Override
    public SequenceStepCriteria copy() {
        return new SequenceStepCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public SequenceStepGenerationFilter getGeneration() {
        return generation;
    }

    public void setGeneration(SequenceStepGenerationFilter generation) {
        this.generation = generation;
    }

    public IntegerFilter getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(IntegerFilter ordinal) {
        this.ordinal = ordinal;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }
    
    public LongFilter getConditionsId() {
        return conditionsId;
    }

    public void setConditionsId(LongFilter conditionsId) {
        this.conditionsId = conditionsId;
    }

    public LongFilter getActionsId() {
        return actionsId;
    }

    public void setActionsId(LongFilter actionsId) {
        this.actionsId = actionsId;
    }

    public LongFilter getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(LongFilter sequenceId) {
        this.sequenceId = sequenceId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SequenceStepCriteria that = (SequenceStepCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(generation, that.generation) &&
            Objects.equals(ordinal, that.ordinal) &&
            Objects.equals(description, that.description) &&
            Objects.equals(conditionsId, that.conditionsId) &&
            Objects.equals(actionsId, that.actionsId) &&
            Objects.equals(sequenceId, that.sequenceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        generation,
        ordinal,
        description,
        conditionsId,
        actionsId,
        sequenceId
        );
    }

    @Override
    public String toString() {
        return "SequenceStepCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (generation != null ? "generation=" + generation + ", " : "") +
                (ordinal != null ? "ordinal=" + ordinal + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (conditionsId != null ? "conditionsId=" + conditionsId + ", " : "") +
                (actionsId != null ? "actionsId=" + actionsId + ", " : "") +
                (sequenceId != null ? "sequenceId=" + sequenceId + ", " : "") +
            "}";
    }

}
