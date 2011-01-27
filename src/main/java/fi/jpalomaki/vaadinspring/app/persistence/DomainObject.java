package fi.jpalomaki.vaadinspring.app.persistence;

import java.util.*;
import javax.persistence.*;
import org.apache.commons.lang.builder.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Configurable;

/**
 * Abstract persistent domain object (<a href="http://en.wikipedia.org/wiki/Active_record_pattern">Active Record</a>).
 * Data access operations are delegated to a shared {@link EntityManager} that is injected by the Spring container.
 * <br /><br />
 * This is not an {@link Entity} in itself, hence instances of this class cannot be
 * persisted (or instantiated for that matter).
 * <br /><br />
 * {@link Configurable} to allow for the entityManager reference to be injected when an instance of this class is created.
 * <br /><br />
 * {@link MappedSuperclass} makes subclasses (entities) inherit the fields in this class.
 * 
 * @author jpalomaki
 */
@Configurable
@MappedSuperclass
@EntityListeners(DomainObjectListener.class)
public abstract class DomainObject {
    
    @PersistenceContext
    protected transient EntityManager entityManager;
    
    /**
     * Primary key. Maps to column "id". Auto-generated using identities,
     * sequences or whatever is appropriate for the underlying datastore.
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Version. Maps to column "version". Automatically updated by the JPA
     * provider. Used for concurrent update detection (optimistic locking).
     */
    @Version
    @Column(name = "version")
    private Long version;
    
    /**
     * The user that created this object.
     */
    @Basic
    private String createdBy;

    /**
     * The user that last modified this object.
     */
    @Basic
    private String modifiedBy;

    /**
     * Creation date for this object.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    /**
     * Last modification date for this object.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificationDate;
    
    /**
     * Persist this object to the underlying datastore.
     */
    @Transactional
    public void persist() {
        this.entityManager.persist(this);
    }

    /**
     * Update this object to the underlying datastore.
     */
    @Transactional
    public void update() {
        DomainObject merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.id = merged.getId();
    }
    
    /**
     * Remove this object from the underlying datastore.
     */
    @Transactional
    public void remove() {
        DomainObject object;
        if (this.entityManager.contains(this)) {
            object = this;
        } else {
            object = this.entityManager.find(getClass(), this.id);
        }
        this.entityManager.remove(object);
    }

    /**
     * Refresh the state of this object from the underlying datastore, reverting any changes.
     */
    @Transactional
    public void revert() {
        this.entityManager.refresh(this);
    }
    
    /**
     * Forcefully flush changes to this object to the underlying datastore.
     */
    @Transactional
    public void flush() {
        this.entityManager.flush();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object object) {
        return EqualsBuilder.reflectionEquals(this, object);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    /**
     * Get the shared {@link EntityManager}. Subclasses can use this method to access
     * an {@link EntityManager} in static data access code (e.g. finders) as necessary.
     */
    protected static EntityManager entityManager() {
        return new EntityManagerHolder().entityManager;
    }
    
    /**
     * EntityManagerHolder. Since the entityManager field is populated by Spring
     * when an object of type {@link DomainObject} is instantiated, we need to
     * be able to instantiate objects of that type. Unfortunately the class
     * {@link DomainObject} itself is abstract, so we secretly subclass it
     * to allow us to implement {@link #entityManager()} method as above.
     */
    private static class EntityManagerHolder extends DomainObject {     
    }
}
