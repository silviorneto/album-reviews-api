package srn.albumreviewapi.controller.dto;

import srn.albumreviewapi.domain.model.Artist;

public record ArtistDto(
        Long id,
        String name
) {
    public ArtistDto(Artist model) {
        this(
                model.getId(),
                model.getName()
        );
    }

    public Artist toModel() {
        Artist model = new Artist();
        model.setId(this.id);
        model.setName(this.name);
        return model;
    }
}
