package fi.jpalomaki.claims.persistence;

import java.util.Date;

/**
 * Setters and getters for {@link DomainObject}.
 *
 * @author jpalomaki
 */
privileged aspect DomainObjectBean {
    
    /**
     * Get the id.
     */
    public final Long DomainObject.getId() {
        return id;
    }

    /**
     * Set the id.
     */
    public final void DomainObject.setId(Long id) {
        this.id = id;
    }

    /**
     * Get the version.
     */
    public final Long DomainObject.getVersion() {
        return version;
    }

    /**
     * Set the version.
     */
    public final void DomainObject.setVersion(Long version) {
        this.version = version;
    }

    /**
     * Get the user that created this object.
     */
    public final String DomainObject.getCreatedBy() {
        return createdBy;
    }

    /**
     * Set the user that created this object.
     */
    public final void DomainObject.setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Get the user that last modified this object.
     */
    public final String DomainObject.getModifiedBy() {
        return modifiedBy;
    }

    /**
     * Set the user that last modified this object.
     */
    public final void DomainObject.setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    /**
     * Get the creation date.
     */
    public final Date DomainObject.getCreationDate() {
        return creationDate;
    }

    /**
     * Set the creation date.
     */
    public final void DomainObject.setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Get the modification date.
     */
    public final Date DomainObject.getModificationDate() {
        return modificationDate;
    }

    /**
     * Set the modification date.
     */
    public final void DomainObject.setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    } 
}
