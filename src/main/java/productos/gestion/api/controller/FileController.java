package productos.gestion.api.controller;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import productos.gestion.api.service.StorageService;

@RestController
@RequestMapping("/files")
public class FileController {

    private final StorageService storage;

    public FileController(StorageService storage) {
        this.storage = storage;
    }

    // Subir un archivo
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileResponse> upload(@RequestPart("file") MultipartFile file , @RequestParam("productoId") int productoId) {
        String stored = storage.store(file,productoId);
        String url = "/files/" + stored; // si tenés proxy/base path, ajustá
        return ResponseEntity
                .created(URI.create(url))
                .body(new FileResponse(stored, url));
    }

    // Descargar/servir un archivo
    @GetMapping("/{filename}")
    public ResponseEntity<Resource> get(@PathVariable String filename,
                                        @RequestParam(defaultValue = "attachment") String disposition) {
        Resource resource = storage.loadAsResource(filename);

        // Trata de inferir tipo (fallback binario)
        String contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        try {
            String probe = java.nio.file.Files.probeContentType(Path.of(resource.getFile().getAbsolutePath()));
            if (probe != null && !probe.isBlank()) contentType = probe;
        } catch (IOException ignored) {}

        String cd = (disposition.equalsIgnoreCase("inline") ? "inline" : "attachment")
                + "; filename=\"" + filename.replace("\"", "") + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, cd)
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }

    // Listar archivos
    @GetMapping
    public List<String> list() {
        return storage.loadAll()
                .map(Path::getFileName)
                .map(Path::toString)
                .sorted()
                .toList();
    }

    // Borrar archivo
    @DeleteMapping("/{filename}")
    public ResponseEntity<Void> delete(@PathVariable String filename) {
        storage.delete(filename);
        return ResponseEntity.noContent().build();
    }

    public record FileResponse(String filename, String url) {}
}
