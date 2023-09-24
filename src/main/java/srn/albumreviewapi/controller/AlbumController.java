package srn.albumreviewapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import srn.albumreviewapi.controller.dto.AlbumDto;
import srn.albumreviewapi.controller.dto.ArtistDto;
import srn.albumreviewapi.controller.dto.CreateAlbumDto;
import srn.albumreviewapi.controller.dto.UpdateAlbumDto;
import srn.albumreviewapi.service.AlbumService;
import srn.albumreviewapi.service.ArtistService;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/albums")
@Tag(name = "Album Controller", description = "RESTful API for managing albums.")
public class AlbumController {
    private final AlbumService albumService;

    private final ArtistService artistService;

    @Autowired
    public AlbumController(AlbumService albumService, ArtistService artistService) {
        this.albumService = albumService;
        this.artistService = artistService;
    }

    @GetMapping
    @Operation(summary = "Get all albums", description = "Retrieve a list of all registered albums")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful")
    })
    public ResponseEntity<List<AlbumDto>> findAll() {
        var albums = this.albumService.findAll();
        var albumsDto = albums.stream().map(AlbumDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(albumsDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get album by id", description = "Retrieve an album based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Not found")

    })
    public ResponseEntity<AlbumDto> findById(@PathVariable Long id) {
        var album = this.albumService.findById(id);
        return ResponseEntity.ok(new AlbumDto(album));
    }

    @PostMapping
    @Operation(summary = "Create a new album", description = "Create a new album and return the created album's data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Album created successfully"),
            @ApiResponse(responseCode = "422", description = "Invalid data provided")
    })
    public ResponseEntity<AlbumDto> create(@RequestBody CreateAlbumDto albumToCreate) {
        var artist = this.artistService.findById(albumToCreate.artist_id());
        var album = this.albumService.create(
                new AlbumDto(
                        albumToCreate.id(),
                        albumToCreate.title(),
                        albumToCreate.year(),
                        albumToCreate.rating(),
                        new ArtistDto(artist)
                ).toModel()
        );

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(album.getId())
                .toUri();
        return ResponseEntity.created(location).body(new AlbumDto(album));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an album", description = "Update the data of an existing album based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Album updated successfully"),
            @ApiResponse(responseCode = "404", description = "Album not found"),
            @ApiResponse(responseCode = "422", description = "Invalid album data provided")
    })
    public ResponseEntity<AlbumDto> update(@PathVariable Long id, @RequestBody UpdateAlbumDto albumToUpdate) {
        var artist = this.artistService.findById(albumToUpdate.artist_id());
        var album = this.albumService.update(
                albumToUpdate.id(),
                new AlbumDto(
                        albumToUpdate.id(),
                        albumToUpdate.title(),
                        albumToUpdate.year(),
                        albumToUpdate.rating(),
                        new ArtistDto(artist)
                ).toModel()
        );
        return ResponseEntity.ok(new AlbumDto(album));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an album", description = "Delete an existing album based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Album deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Album not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.albumService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
