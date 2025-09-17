package productos.gestion.api.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Oferta {
    @Id
    @Column(name="id_oferta" , nullable=false)    
    private Long id_oferta;

    @Column(name="descuento")
    private Long descuento;

    @Column(name="dia_inicio")
    private Date dia_inicial;

    @Column(name="dia_final")
    private Date dia_final;
    
}
