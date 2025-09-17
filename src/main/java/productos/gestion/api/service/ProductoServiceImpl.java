package productos.gestion.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import productos.gestion.api.Dto.ProductoDto;
import productos.gestion.api.entity.EstadoProducto;
import productos.gestion.api.entity.Producto;
import productos.gestion.api.entity.Usuario;
import productos.gestion.api.exceptions.ProductoExistenteException;
import productos.gestion.api.exceptions.UsuarioNoExiste;
import productos.gestion.api.repository.ProductoRepository;
import productos.gestion.api.repository.UsuarioRepository;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    
    @Override
    public List<Producto> listarProducto() throws Exception {
        List<Producto> listaProductos = productoRepository.findAll();
        return listaProductos;
    }

    @Override
    public Producto buscarPorId(int id) throws Exception {
        return productoRepository.findById(id)
                .orElseThrow(() -> new Exception("Producto no encontrado"));
    }


    @Override
    public List<Producto> buscarPorNombre(String nombre) throws Exception {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }
    


    @Override
    public Producto actualizarProducto(int id, Producto producto) throws Exception {
        Producto productoExistente = productoRepository.findById(id)
                .orElseThrow(() -> new Exception("Producto no encontrado"));

        productoExistente.setNombre(producto.getNombre());
        productoExistente.setDescripcion(producto.getDescripcion());
        productoExistente.setCantidad(producto.getCantidad());
        productoExistente.setEstado(producto.getEstado());
        productoExistente.setPrecio(producto.getPrecio());

        return productoRepository.save(productoExistente);
    }


    @Override
    public Producto cambiarEstadoProducto(int id, EstadoProducto nuevoEstadoProducto) throws Exception  {
        Producto productoExistente = productoRepository.findById(id)
                .orElseThrow(() -> new Exception("Producto no encontrado"));
        productoExistente.setEstado(nuevoEstadoProducto);
        return productoRepository.save(productoExistente);
    }

    @Override
    public void eliminarProducto(int id) throws Exception {
        Producto productoEliminar = productoRepository.findById(id)
                .orElseThrow(() -> new Exception("Producto no encontrado"));
        productoEliminar.setEstado(EstadoProducto.NO_DISPONIBLE);
        productoRepository.save(productoEliminar);
    }


    @Override
    public Producto registrarProducto(ProductoDto productoDto) throws Exception {

        this.existeProducto(productoDto);

        Usuario usuario = usuarioRepository.findById(productoDto.getUserId())
            .orElseThrow(() -> new UsuarioNoExiste("Usuario no existe"));
        
        Producto producto = new Producto();
        producto.setNombre(productoDto.getNombre());
        producto.setPrecio(productoDto.getPrecio());
        producto.setDescripcion(productoDto.getDescripcion());
        producto.setCodBarra(productoDto.getCodBarra());
        producto.setCantidad(productoDto.getCantidad());
        producto.setEstado(productoDto.getEstado());
        producto.setPrecioMayorista(productoDto.getPrecioMayorista());
        producto.setPathImagen(productoDto.getPathImagen());
        producto.setUsuario(usuario);
       
        
        return productoRepository.save(producto);
    }

    @Override
    public void existeProducto(ProductoDto productoDto){
        Producto productoEncontrado = productoRepository.findByNombreOrCodBarra(productoDto.getNombre(),productoDto.getCodBarra())
                .orElse(null);

        if (productoEncontrado != null &&
        ((productoDto.getNombre().equals(productoEncontrado.getNombre()) || productoDto.getCodBarra().equals(productoEncontrado.getCodBarra()))
        || (productoDto.getNombre().equals(productoEncontrado.getNombre()) && productoDto.getCodBarra().equals(productoEncontrado.getCodBarra()) ))) {
            throw new ProductoExistenteException("El producto ya existe");
        }
    }

    
    



}
