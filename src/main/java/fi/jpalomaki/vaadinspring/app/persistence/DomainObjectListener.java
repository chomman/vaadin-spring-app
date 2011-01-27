package fi.jpalomaki.vaadinspring.app.persistence;

import java.util.Date;
import javax.persistence.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Reacts to persistence events for {@link DomainObject}s.
 *
 * @author jpalomaki
 */
public final class DomainObjectListener {
    
    /**
     * Set the creator and creation date for given {@link DomainObject} upon persist.
     */
    @PrePersist
    public void onCreation(DomainObject domainObject) {   
        domainObject.setCreationDate(new Date());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        domainObject.setCreatedBy(authentication != null ? authentication.getName() : null);
    }
    
    /**
     * Set the modifier and modification date for given {@link DomainObject} upon update.
     */
    @PreUpdate
    public void onModification(DomainObject domainObject) {   
        domainObject.setModificationDate(new Date());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        domainObject.setModifiedBy(authentication != null ? authentication.getName() : null);
    }
}
