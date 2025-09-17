package productos.gestion.api.service;
import java.util.List;

import productos.gestion.api.Dto.ProductoDto;
import productos.gestion.api.entity.EstadoProducto;
import productos.gestion.api.entity.Producto;

public interface ProductoService {
    void existeProducto(ProductoDto productoDto);
    Producto registrarProducto(ProductoDto productoDto)throws Exception;
    List<Producto> listarProducto()throws Exception;
    List<Producto> buscarPorNombre(String nombre)throws Exception;
    Producto buscarPorId(int id)throws Exception;
    Producto actualizarProducto(int id , Producto producto)throws Exception;
    void eliminarProducto(int id)throws Exception;
    Producto cambiarEstadoProducto(int id, EstadoProducto nuevoEstadoProducto)throws Exception;



}
