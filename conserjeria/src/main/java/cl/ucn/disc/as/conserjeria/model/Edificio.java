package cl.ucn.disc.as.conserjeria.model;

import io.ebean.annotation.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Entity
@Builder
@AllArgsConstructor
public class Edificio extends BaseModel {

    @NotNull
    private String nombre;

    @NotNull
    private String direccion;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Departamento> departamentos;

    public void add(Departamento departamento) {
        departamentos.add(departamento);
    }

}
