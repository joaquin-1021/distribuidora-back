package productos.gestion.api.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import productos.gestion.api.entity.Usuario;


@Repository
public interface  UsuarioRepository extends  JpaRepository<Usuario, Object>{
   Optional<Usuario> findByIdUsuario(int id_usuario);
}
