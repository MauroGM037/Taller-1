package cl.ucn.disc.as.conserjeria.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.Instant;

@Entity
@Builder
@AllArgsConstructor
public class Pago extends BaseModel {

    @Getter
    private Instant fechaPago;

    @Getter
    private Integer monto;

    @ManyToOne
    private Contrato contrato;

}
