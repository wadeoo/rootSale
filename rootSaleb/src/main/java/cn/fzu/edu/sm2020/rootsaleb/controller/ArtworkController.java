package cn.fzu.edu.sm2020.rootsaleb.controller;

import cn.fzu.edu.sm2020.rootsaleb.entity.Artwork;
import cn.fzu.edu.sm2020.rootsaleb.repository.ArtworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/artwork")
public class ArtworkController {
    private final ArtworkRepository artworkRepository;

    @Autowired
    public ArtworkController(ArtworkRepository artworkRepository) {
        this.artworkRepository = artworkRepository;
    }

    @Value("${artwork.images.directory}")
    private String artworkImagesDirectory;

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getDishImage(@PathVariable("id") int id) throws IOException {
        Optional<Artwork> optionalDish = artworkRepository.findById(id);
        if (optionalDish.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Artwork artwork = optionalDish.get();
        String imagePath = artworkImagesDirectory + "/" + id+".jpg";
        byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));

        return ResponseEntity.ok()
                .contentLength(imageBytes.length)
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageBytes);
    }

    @GetMapping
    public ResponseEntity<List<Artwork>> getDishList() {
        List<Artwork> dishList = artworkRepository.findAll();
        if (dishList.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(dishList);
        }
    }
}
