package productos.gestion.api.exceptions;

public class ProductoExistenteException extends RuntimeException {
    public ProductoExistenteException(String message) {
        super(message);
    }
}