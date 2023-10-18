/*
 * Copyright (c) 2023. Arquitectura de Sistemas, DISC, UCN.
 */

package cl.ucn.disc.as.conserjeria.model;

import io.ebean.Model;
import io.ebean.annotation.WhenCreated;
import io.ebean.annotation.WhenModified;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.time.Instant;

/**
 * Base Class.
 *
 * @author Diego Urrutia-Astorga.
 */
@ToString
@MappedSuperclass
public abstract class BaseModel extends Model {

    /**
     * The Id.
     */
    @Getter
    @Setter
    @Id
    private Long id;

    /**
     * The Version.
     */
    @Getter
    @Setter
    @Version
    private Long version;

    /**
     * Creation date.
     */
    @Getter
    @Setter
    @WhenCreated
    private Instant created;

    /**
     * Modified date.
     */
    @Getter
    @Setter
    @WhenModified
    private Instant modified;

}
