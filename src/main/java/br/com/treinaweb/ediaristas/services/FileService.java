package br.com.treinaweb.ediaristas.services;

import java.nio.file.Paths;
import java.util.UUID;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService 
{
    private final Path pastaUpload = Paths.get("uploads");

    public String salvar(MultipartFile file) throws IOException {
        if(!Files.exists(pastaUpload)) {
            Files.createDirectory(pastaUpload);
        }
        
        var originalFilename = file.getOriginalFilename();
        var ext = originalFilename.split("\\.")[1];
        var filename = UUID.randomUUID().toString() + "." + ext;

        Files.copy(file.getInputStream(), pastaUpload.resolve(filename));

        return filename;

    }

    public Resource carregar(String filename) throws MalformedURLException {
        var filePath = pastaUpload.resolve(filename);

        return new UrlResource(filePath.toUri());
    }
}
