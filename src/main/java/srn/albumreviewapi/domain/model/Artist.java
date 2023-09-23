package srn.albumreviewapi.domain.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "tb_artist")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    private String name;

    @ElementCollection
    @CollectionTable(name = "artist_albums", joinColumns = @JoinColumn(name = "artist_id"))
    @Column(name = "album_id")
    private Set<Long> albumIds = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Long> getAlbumIds() {
        return albumIds;
    }

    public void setAlbumIds(Set<Long> albumIds) {
        this.albumIds = albumIds;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
