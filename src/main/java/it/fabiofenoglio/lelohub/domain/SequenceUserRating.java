package it.fabiofenoglio.lelohub.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import it.fabiofenoglio.lelohub.domain.proto.HasID;

/**
 * A SequenceUserRating.
 */
@Entity
@Table(name = "sequence_user_rating")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SequenceUserRating extends AbstractAuditingEntity<SequenceStep> implements Serializable, HasID {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "score")
    @Min(0)
    @Max(5)
    private Integer score;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("sequenceUserRatings")
    private User user;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("userRatings")
    private Sequence sequence;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getScore() {
        return score;
    }

    public SequenceUserRating score(Integer score) {
        this.score = score;
        return this;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public User getUser() {
        return user;
    }

    public SequenceUserRating user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Sequence getSequence() {
        return sequence;
    }

    public SequenceUserRating sequence(Sequence sequence) {
        this.sequence = sequence;
        return this;
    }

    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SequenceUserRating)) {
            return false;
        }
        return id != null && id.equals(((SequenceUserRating) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SequenceUserRating{" +
            "id=" + getId() +
            ", score=" + getScore() +
            "}";
    }
}
