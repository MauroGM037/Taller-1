/*
 * Copyright (c) 2023. Departamento de Ingenieria de Sistemas y Computacion, Universidad Catolica del Norte.
 */

package cl.ucn.disc.as.conserjeria.model.exception;

public class IllegalDomainException extends RuntimeException {
    /**
     * The constructor.
     *
     * @param message to use.
     */
    public IllegalDomainException(String message) {
        super(message);
    }
}
