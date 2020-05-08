package it.fabiofenoglio.lelohub.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import it.fabiofenoglio.lelohub.domain.enumeration.SequenceGeneration;
import it.fabiofenoglio.lelohub.domain.enumeration.SequenceVisibility;
import it.fabiofenoglio.lelohub.domain.proto.HasID;

/**
 * A Sequence.
 */
@Entity
@Table(name = "sequence")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Sequence extends AbstractAuditingEntity<Sequence> implements Serializable, HasID {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "generation", nullable = false)
    private SequenceGeneration generation = SequenceGeneration.GEN1;

    @NotNull
    @Size(max = 250)
    @Column(name = "name", length = 250, nullable = false)
    private String name;

    @Size(max = 2000)
    @Column(name = "description", length = 2000)
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "visibility", nullable = false)
    private SequenceVisibility visibility;

    @OneToMany(mappedBy = "sequence", cascade = CascadeType.ALL, orphanRemoval=true)
//    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SequenceStep> steps = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("sequences")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SequenceGeneration getGeneration() {
        return generation;
    }

    public Sequence generation(SequenceGeneration generation) {
        this.generation = generation;
        return this;
    }

    public void setGeneration(SequenceGeneration generation) {
        this.generation = generation;
    }

    public String getName() {
        return name;
    }

    public Sequence name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Sequence description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SequenceVisibility getVisibility() {
        return visibility;
    }

    public Sequence visibility(SequenceVisibility visibility) {
        this.visibility = visibility;
        return this;
    }

    public void setVisibility(SequenceVisibility visibility) {
        this.visibility = visibility;
    }

    public Set<SequenceStep> getSteps() {
        return steps;
    }

    public Sequence steps(Set<SequenceStep> sequenceSteps) {
        this.steps = sequenceSteps;
        return this;
    }

    public Sequence addSteps(SequenceStep sequenceStep) {
        this.steps.add(sequenceStep);
        sequenceStep.setSequence(this);
        return this;
    }

    public Sequence removeSteps(SequenceStep sequenceStep) {
        this.steps.remove(sequenceStep);
        sequenceStep.setSequence(null);
        return this;
    }

    public void setSteps(Set<SequenceStep> sequenceSteps) {
        this.steps = sequenceSteps;
    }

    public User getUser() {
        return user;
    }

    public Sequence user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sequence)) {
            return false;
        }
        return id != null && id.equals(((Sequence) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Sequence{" +
            "id=" + getId() +
            ", generation='" + getGeneration() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", visibility='" + getVisibility() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
