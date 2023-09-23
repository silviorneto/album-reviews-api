package srn.albumreviewapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import srn.albumreviewapi.controller.dto.ArtistDto;
import srn.albumreviewapi.service.ArtistService;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/artists")
@Tag(name = "Artist Controller", description = "RESTful API for managing artists.")
public class ArtistController {

    private final ArtistService artistService;

    @Autowired
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping
    @Operation(summary = "Get all artists", description = "Retrieve a list of all registered users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful")
    })
    public ResponseEntity<List<ArtistDto>> findAll() {
        var artists = this.artistService.findAll();
        var artistsDto = artists.stream().map(ArtistDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(artistsDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get artist by id", description = "Retrieve an artist based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Not found")

    })
    public ResponseEntity<ArtistDto> findById(@PathVariable Long id) {
        var artist = this.artistService.findById(id);
        return ResponseEntity.ok(new ArtistDto(artist));
    }

    @PostMapping
    @Operation(summary = "Create a new artist", description = "Create a new artist and return the created artist's data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "422", description = "Invalid data provided")
    })
    public ResponseEntity<ArtistDto> create(@RequestBody ArtistDto artistDto) {
        var artist = this.artistService.create(artistDto.toModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(artist.getId())
                .toUri();
        return ResponseEntity.created(location).body(new ArtistDto(artist));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an artist", description = "Update the data of an existing artist based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Artist updated successfully"),
            @ApiResponse(responseCode = "404", description = "Artist not found"),
            @ApiResponse(responseCode = "422", description = "Invalid artist data provided")
    })
    public ResponseEntity<ArtistDto> update(@PathVariable Long id, @RequestBody ArtistDto artistDto) {
        var artist = this.artistService.update(id, artistDto.toModel());
        return ResponseEntity.ok(new ArtistDto(artist));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an artist", description = "Delete an existing artist based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Artist deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Artist not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.artistService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
