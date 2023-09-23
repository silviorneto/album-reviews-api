package srn.albumreviewapi.controller.dto;

public record UpdateAlbumDto (
        Long id,
        String title,
        Integer year,
        Long artist_id
) {
}
