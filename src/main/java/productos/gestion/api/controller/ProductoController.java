package productos.gestion.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import productos.gestion.api.Dto.ProductoDto;
import productos.gestion.api.entity.Producto;
import productos.gestion.api.service.ProductoService;


@CrossOrigin(origins = "http://localhost:4200")
@RestController //indica q es api
@RequestMapping("api/productos")

public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarProducto(@RequestBody ProductoDto productoDTO) throws Exception{
        Producto nuevoProducto = productoService.registrarProducto(productoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto); 
    }
    
    @GetMapping("/")
    public ResponseEntity<?> listarProducto() throws Exception{
        List<Producto> productos = productoService.listarProducto();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) throws Exception{
        Producto producto = productoService.buscarPorId(id);
        return ResponseEntity.ok(producto);
        
    }
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<?> buscarPorNombre(@PathVariable String nombre) throws Exception{
        List<Producto> productos = productoService.buscarPorNombre(nombre);
        return productos.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron productos")
                : ResponseEntity.ok(productos);
    }




}
