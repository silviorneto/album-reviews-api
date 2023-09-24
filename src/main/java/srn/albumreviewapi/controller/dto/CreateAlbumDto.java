package srn.albumreviewapi.controller.dto;

public record CreateAlbumDto (
        Long id,
        String title,
        Integer year,
        Integer rating,
        Long artist_id
) {

}
