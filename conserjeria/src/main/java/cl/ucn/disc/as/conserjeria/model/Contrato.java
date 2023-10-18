package cl.ucn.disc.as.conserjeria.model;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.Instant;
import java.util.List;

@Entity
@Getter
public class Contrato extends BaseModel {

    private Instant fechaPago;

    @ManyToOne
    private Persona persona;

    @ManyToOne
    private Departamento departamento;

    @OneToMany(mappedBy = "contrato")
    private List<Pago> pagos;

    @Builder
    public Contrato(Persona persona, Departamento departamento, Instant fechaPago) {
        this.persona = persona;
        this.departamento = departamento;
        this.fechaPago = fechaPago;
    }

}
