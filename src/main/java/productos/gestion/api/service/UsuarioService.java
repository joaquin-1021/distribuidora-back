package productos.gestion.api.service;

import productos.gestion.api.entity.Usuario;

public interface UsuarioService {
    Usuario buscarPorId(int id)throws Exception;
}
