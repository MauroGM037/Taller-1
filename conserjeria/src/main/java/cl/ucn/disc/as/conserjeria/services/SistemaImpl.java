package cl.ucn.disc.as.conserjeria.services;

import cl.ucn.disc.as.conserjeria.model.*;
import io.ebean.Database;

import java.time.Instant;
import java.util.List;

public class SistemaImpl implements Sistema {
    private final Database database;

    public SistemaImpl(Database database) {
        this.database = database;
    }

    @Override
    public Edificio add(Edificio edificio) {
        edificio.save();
        return edificio;
    }

    @Override
    public Persona add(Persona persona) {
        persona.save();
        return persona;
    }

    @Override
    public Departamento addDepartamento(Departamento departamento, Edificio edificio) {
        edificio.getDepartamentos().add(departamento);
        edificio.save();
        return departamento;
    }

    @Override
    public Departamento addDepartamento(Departamento departamento, Long idEdificio) {
        Edificio edificio = database.find(Edificio.class, idEdificio);
        assert edificio != null;
        edificio.getDepartamentos().add(departamento);
        edificio.save();
        return departamento;
    }

    @Override
    public Contrato realizarContrato(Persona duenio, Departamento departamento, Instant fechaPago) {
        Contrato contrato = new Contrato(duenio, departamento, fechaPago);
        contrato.save();
        return contrato;
    }

    @Override
    public Contrato realizarContrato(Long idDuenio, Long idDepartamento, Instant fechaPago) {
        Persona duenio = database.find(Persona.class, idDuenio);
        Departamento departamento = database.find(Departamento.class, idDepartamento);
        Contrato contrato = new Contrato(duenio, departamento, fechaPago);
        contrato.save();
        return contrato;
    }

    @Override
    public List<Contrato> getContratos() {
        return database.find(Contrato.class).findList();
    }

    @Override
    public List<Persona> getPersonas() {
        return database.find(Persona.class).findList();
    }

    @Override
    public List<Pago> getPagos(String rut) {
        return database.find(Pago.class)
                .where()
                .eq("contrato.persona.rut", rut)
                .findList();
    }
}
