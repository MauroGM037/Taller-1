/*
 * Copyright (c) 2023. Arquitectura de Sistemas, DISC, UCN.
 */

package cl.ucn.disc.as.conserjeria.model;

import cl.ucn.disc.as.conserjeria.model.exception.IllegalDomainException;
import cl.ucn.disc.as.conserjeria.util.ValidationUtils;
import io.ebean.annotation.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Entity;

/**
 * The Persona class.
 *
 * @author Diego Urrutia-Astorga.
 */
@Getter
@ToString(callSuper = true)
@AllArgsConstructor
@Builder
@Entity
public class Persona extends BaseModel {

    /**
     * The RUT.
     */
    @NotNull
    private String rut;

    /**
     * The Nombre.
     */
    @NotNull
    private String nombre;

    /**
     * The Apellidos.
     */
    @NotNull
    private String apellidos;

    /**
     * The Email.
     */
    @NotNull
    private String email;

    /**
     * The Telefono.
     */
    @NotNull
    private String telefono;

    public static class PersonaBuilder {
        /**
         * @return the owner.
         */
        public Persona build() throws IllegalDomainException {
            // validate the rut
            if (!ValidationUtils.isRutValid(String.valueOf(this.rut))) {
                throw new IllegalDomainException("Invalid RUT: " + this.rut);
            }

            // validate the email
            if (!ValidationUtils.isEmailValid(this.email)) {
                throw new IllegalDomainException("Invalid email: " + this.email);
            }

            return new Persona(this.rut, this.nombre, this.apellidos, this.email, this.telefono);
        }
    }


}
