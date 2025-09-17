package productos.gestion.api.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;

    public FileSystemStorageService(@Value("${file.storage.location}") String location) {
        this.rootLocation = Paths.get(location).toAbsolutePath().normalize();
    }

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo inicializar el directorio de almacenamiento", e);
        }
    }

    @Override
    public String store(MultipartFile file, int productoId) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("Archivo vacío o no enviado.");
        }

        // Limpia el nombre y evita "path traversal"
        @SuppressWarnings("null")
        String original = StringUtils.cleanPath(file.getOriginalFilename() == null ? "" : file.getOriginalFilename());
        if (original.contains("..")) {
            throw new RuntimeException("Nombre de archivo inválido: " + original);
        }

        // Genera nombre único para evitar colisiones
        String newFilename = productoId+ "_.jpg" ;
        Path destinationFile = rootLocation.resolve(newFilename).normalize().toAbsolutePath();

        // Asegura que se guarde dentro del root
        if (!destinationFile.getParent().equals(rootLocation)) {
            throw new RuntimeException("Intento de salida de directorio no permitido: " + newFilename);
        }

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar el archivo: " + original, e);
        }

        return newFilename;
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = rootLocation.resolve(StringUtils.cleanPath(filename)).normalize();
            if (!file.getParent().equals(rootLocation)) {
                throw new RuntimeException("Intento de salida de directorio no permitido: " + filename);
            }
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("No se pudo leer el archivo: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Ruta inválida para: " + filename, e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.list(rootLocation).filter(Files::isRegularFile);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo listar archivos", e);
        }
    }

    @Override
    public void delete(String filename) {
        try {
            Path file = rootLocation.resolve(StringUtils.cleanPath(filename)).normalize();
            if (!file.getParent().equals(rootLocation)) {
                throw new RuntimeException("Intento de salida de directorio no permitido: " + filename);
            }
            Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo borrar el archivo: " + filename, e);
        }
    }
}
