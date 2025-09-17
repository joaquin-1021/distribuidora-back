package productos.gestion.api.service;

import org.springframework.stereotype.Service;

import productos.gestion.api.entity.Usuario;
import productos.gestion.api.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario buscarPorId(int id) throws Exception {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new Exception("No existe producto"));
    }
    
}
