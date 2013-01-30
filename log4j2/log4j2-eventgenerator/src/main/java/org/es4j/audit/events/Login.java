package org.es4j.audit.events;

import org.es4j.audit.dto.AuditEvent;

/**
 * Member logged in successfully.
 *
 * @author generated
 */
public interface Login extends AuditEvent {

    /**
     * Member : Member or End User number at the Host
     *
     * @param member Member or End User number at the Host
     */
    void setMember(String member);

    /**
     * Source : Source of the End User's request; or method user used to navigate (link, button)
     *
     * @param source Source of the End User's request; or method user used to navigate (link, button)
     */
    void setSource(String source);

    /**
     * Start Page Option : Chosen start page destination for IB login.
     *
     * @param startPageOption Chosen start page destination for IB login.
     */
    void setStartPageOption(String startPageOption);
}