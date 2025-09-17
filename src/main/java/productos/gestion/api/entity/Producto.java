package productos.gestion.api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data //gett y sett
@AllArgsConstructor
@NoArgsConstructor
public class Producto {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id_producto" , nullable = false )
    private int id;

    @ManyToOne
    @JoinColumn(name="id_usuario" , referencedColumnName="id_usuario")
    @JsonBackReference
    private Usuario usuario;
    
    @Column(name="nombre" ,  length = 100)
    private String nombre;

    @Column(name="precio" ,  length = 100)
    private int precio;

    @Column(name="precio_mayorista" ,  length = 100)
    private int precioMayorista;

    @Column(name="descripcion" ,  length = 100)
    private String descripcion;

     @Column(name="path_imagen" , length = 100)
    private String pathImagen;

    @Column(name="codBarra" , length = 100 , unique=true)
    private Long codBarra;

    @Column(name="cantidad"  )
    private int cantidad;

    @Enumerated(EnumType.STRING)
    @Column(name="estado")
    private EstadoProducto estado; // con o sin stock 

}


