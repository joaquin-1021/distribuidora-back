package productos.gestion.api.exceptions;

public class UsuarioNoExiste extends RuntimeException{
    public UsuarioNoExiste(String mensaje){
        super(mensaje);
    }
    
}
