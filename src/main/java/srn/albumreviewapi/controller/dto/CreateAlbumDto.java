package srn.albumreviewapi.controller.dto;

public record CreateAlbumDto (
        Long id,
        String title,
        Integer year,
        Long artist_id
) {

}
