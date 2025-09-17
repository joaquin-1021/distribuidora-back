package productos.gestion.api.service;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    String store(MultipartFile fil , int productoId);
    Resource loadAsResource(String filename);
    Stream<Path> loadAll();
    void delete(String filename);
}
