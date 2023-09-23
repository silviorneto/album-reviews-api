package srn.albumreviewapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import srn.albumreviewapi.domain.model.Album;
import srn.albumreviewapi.domain.model.Artist;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    boolean existsByArtistAndTitle(Artist artist, String title);
}
