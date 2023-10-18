package cl.ucn.disc.as.conserjeria.model;

import io.ebean.annotation.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
@Builder
@AllArgsConstructor
public class Departamento extends BaseModel {

    @NotNull
    @Getter
    private Integer numero;

    @NotNull
    @Getter
    private Integer piso;

    @ManyToOne
    private Edificio edificio;

    @OneToOne(cascade = CascadeType.ALL)
    private Contrato contrato;

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }
}
