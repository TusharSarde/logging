package org.es4j.audit.events;

import org.es4j.audit.dto.AuditEvent;
import org.es4j.validation.Constraint;

/**
 * Member change their password.
 *
 * @author generated
 */
public interface ChangePassword extends AuditEvent {

    /**
     * Member : Member or End User number at the Host
     *
     * @param member Member or End User number at the Host
     */
    @Constraint(required = true)
    void setMember(String member);
}