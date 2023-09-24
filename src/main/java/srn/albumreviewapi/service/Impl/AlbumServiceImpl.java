package srn.albumreviewapi.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import srn.albumreviewapi.domain.model.Album;
import srn.albumreviewapi.domain.repository.AlbumRepository;
import srn.albumreviewapi.service.AlbumService;
import srn.albumreviewapi.service.exceptions.BusinessException;
import srn.albumreviewapi.service.exceptions.NotFoundException;
import srn.albumreviewapi.service.exceptions.NullFieldException;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepo;

    @Autowired
    public AlbumServiceImpl(AlbumRepository albumRepo) {
        this.albumRepo = albumRepo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Album> findAll() {
        return this.albumRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Album findById(Long id) {
        return this.albumRepo.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    @Transactional
    public Album create(Album albumToCreate) {
        ofNullable(albumToCreate).orElseThrow(() -> new NullFieldException("Album"));
        ofNullable(albumToCreate.getArtist()).orElseThrow(() -> new NullFieldException("Artist"));
        ofNullable(albumToCreate.getTitle()).orElseThrow(() ->  new NullFieldException("Title"));
        ofNullable(albumToCreate.getYear()).orElseThrow(() ->  new NullFieldException("Year"));
        ofNullable(albumToCreate.getRating()).orElseThrow(() ->  new NullFieldException("Rating"));

        if (albumRepo.existsByArtistAndTitle(albumToCreate.getArtist(), albumToCreate.getTitle())) {
            throw new IllegalArgumentException("This album already exists");
        }

        return albumRepo.save(albumToCreate);
    }

    @Override
    @Transactional
    public Album update(Long id, Album album) {
        Album albumFounded = this.albumRepo.findById(id).orElseThrow(NotFoundException::new);

        albumFounded.setArtist(album.getArtist());
        albumFounded.setTitle(album.getTitle());
        albumFounded.setYear(album.getYear());
        albumFounded.setRating(album.getRating());

        return this.albumRepo.save(albumFounded);
    }

    @Override
    public void delete(Long id) {
        Album albumFounded = this.albumRepo.findById(id).orElseThrow(NotFoundException::new);
        this.albumRepo.delete(albumFounded);
    }
}
