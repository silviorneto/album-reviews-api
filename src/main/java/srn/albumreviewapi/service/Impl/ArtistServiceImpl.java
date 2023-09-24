package srn.albumreviewapi.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import srn.albumreviewapi.domain.model.Artist;
import srn.albumreviewapi.domain.repository.ArtistRepository;
import srn.albumreviewapi.service.ArtistService;
import srn.albumreviewapi.service.exceptions.BusinessException;
import srn.albumreviewapi.service.exceptions.NotFoundException;
import srn.albumreviewapi.service.exceptions.NullFieldException;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
public class ArtistServiceImpl implements ArtistService {
    private final ArtistRepository artistRepository;

    @Autowired
    public ArtistServiceImpl(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Artist> findAll() {
        return this.artistRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Artist findById(Long id) {
        return this.artistRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    @Transactional
    public Artist create(Artist artist) {
        ofNullable(artist.getName()).orElseThrow(() -> new NullFieldException("Name"));

        if (this.artistRepository.existsByName(artist.getName())) {
            throw new IllegalArgumentException("This artist already exists");
        }

        return this.artistRepository.save(artist);
    }

    @Override
    @Transactional
    public Artist update(Long id, Artist artist) {
        Artist artistFounded = this.artistRepository.findById(id).orElseThrow(NotFoundException::new);

        artistFounded.setName(artist.getName());
        return this.artistRepository.save(artist);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Artist artistFounded = this.artistRepository.findById(id).orElseThrow(NotFoundException::new);
        this.artistRepository.delete(artistFounded);
    }
}
