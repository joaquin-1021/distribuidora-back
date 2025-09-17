package productos.gestion.api.entity;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data //gett y sett
@AllArgsConstructor
@NoArgsConstructor

public class Usuario {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_usuario" , nullable=false )
    private int idUsuario;

    @OneToMany(mappedBy = "usuario")
    @JsonBackReference
    private List<Producto> productos;
    
    @Column(name="nombre" , nullable=false , length=100)
    private String nombre;

    @Column(name="apellido" , nullable=false , length=100)
    private String apellido;

    @Column(name="correo" , nullable=false , length=100)
    private String correo;

    @Column(name="nombre_usuario" , nullable=false , length=100)
    private String nombre_usuario;

    @Column(name="clave" , nullable=false , length=100)
    private String contraseña;

    @Column(name="activo" , nullable=false )
    private Boolean activo;


}
