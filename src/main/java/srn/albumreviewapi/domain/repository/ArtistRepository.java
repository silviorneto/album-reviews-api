package srn.albumreviewapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import srn.albumreviewapi.domain.model.Artist;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
    boolean existsByName(String name);
}
