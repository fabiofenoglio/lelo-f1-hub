package it.fabiofenoglio.lelohub.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class NotFoundException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    public NotFoundException() {
        super(ErrorConstants.NOT_FOUND_TYPE, "Not found", Status.NOT_FOUND);
    }
}
