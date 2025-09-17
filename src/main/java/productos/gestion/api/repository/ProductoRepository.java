package productos.gestion.api.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import productos.gestion.api.entity.EstadoProducto;
import productos.gestion.api.entity.Producto;


@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
 Optional<Producto> findByNombre(String nombre);
    Optional<Producto> findByCodBarra(Long codBarra);
    Optional<Producto> findById(Integer id); 
    Optional<Producto> findByEstado(EstadoProducto estadoProducto);
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    Optional<Producto> findByNombreOrCodBarra(String nombre, Long codBarra);
}
