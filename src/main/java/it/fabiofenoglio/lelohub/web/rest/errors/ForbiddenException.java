package it.fabiofenoglio.lelohub.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class ForbiddenException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    public ForbiddenException() {
        super(ErrorConstants.FORBIDDEN_TYPE, "Not allowed", Status.FORBIDDEN);
    }
}
