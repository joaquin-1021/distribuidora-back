package productos.gestion.api.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import productos.gestion.api.entity.EstadoProducto;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductoDto {
    private String nombre;
    private int precio;
    private String descripcion;
    private Long codBarra;
    private int cantidad;
    private EstadoProducto estado;
    private int userId;
    private int precioMayorista;
    private String pathImagen;

}
