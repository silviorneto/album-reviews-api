package srn.albumreviewapi.controller.dto;

public record UpdateAlbumDto (
        Long id,
        String title,
        Integer year,
        Integer rating,
        Long artist_id
) {
}
