package srn.albumreviewapi.controller.dto;

import srn.albumreviewapi.domain.model.Album;

public record AlbumDto(
        Long id,
        String title,
        Integer year,
        Integer rating,
        ArtistDto artist
) {
    public AlbumDto(Album model) {
        this(model.getId(), model.getTitle(), model.getYear(), model.getRating(), new ArtistDto(model.getArtist()));
    }

    public Album toModel() {
        Album model = new Album();
        model.setId(this.id);
        model.setTitle(this.title);
        model.setYear(this.year);
        model.setRating(this.rating);
        model.setArtist(this.artist.toModel());
        return model;
    }
}
