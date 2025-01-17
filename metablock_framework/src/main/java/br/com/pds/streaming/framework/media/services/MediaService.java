package br.com.pds.streaming.framework.media.services;

import br.com.pds.streaming.framework.exceptions.EntityNotFoundException;
import br.com.pds.streaming.framework.mapper.modelMapper.MetablockMapper;
import br.com.pds.streaming.framework.media.model.entities.Media;
import br.com.pds.streaming.framework.media.model.dto.MediaDTO;
import br.com.pds.streaming.framework.media.repositories.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MediaService {

    private MediaRepository mediaRepository;
    private MetablockMapper mapper;

    @Autowired
    public MediaService(MediaRepository mediaRepository, @Qualifier("metablockMapper") MetablockMapper mapper) {
        this.mediaRepository = mediaRepository;
        this.mapper = mapper;
    }

    public List<? extends MediaDTO> findAll() {
        return mapper.convertList(mediaRepository.findAll(), MediaDTO.class);
    }

    public <M extends Media> List<M> findAll(Class<M> mediaChildClass) {
        return (List<M>) mediaRepository.findAll().stream().filter(m -> m.getClass().equals(mediaChildClass)).toList();
    }

    public <M extends MediaDTO> List<M> findAll(Class<? extends Media> mediaChildClass, Class<M> mediaResponseChildClass) {
        return mapper.convertList(mediaRepository.findAll().stream().filter(m -> m.getClass().equals(mediaChildClass)).toList(), mediaResponseChildClass);
    }

    public List<? extends MediaDTO> findByTitle(String title) {
        return mapper.convertList(mediaRepository.findByTitle(title), MediaDTO.class);
    }

    public <M extends MediaDTO> M findById(String id) {
        return (M) mapper.convertValue(mediaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Media.class)), MediaDTO.class);
    }

    public <M extends Media> M findById(String id, Class<M> mediaChildClass) {

        var media = mediaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(mediaChildClass));

        if (!media.getClass().equals(mediaChildClass)) {
            throw new RuntimeException("Media found is not a " + mediaChildClass); // Exceção temporária
        }

        return (M) media;
    }

    public <M extends MediaDTO> M findById(String id, Class<? extends Media> mediaChildClass, Class<M> mediaResponseChildClass) {

        var media = mediaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(mediaChildClass));

        if (!media.getClass().equals(mediaChildClass)) {
            throw new RuntimeException("Media found is not a " + mediaChildClass); // Exceção temporária
        }

        return mapper.convertValue(media, mediaResponseChildClass);
    }

    public <M extends Media> M persist(M media) {
        return mediaRepository.save(media);
    }

    public <M extends MediaDTO> M persist(MediaDTO mediaRequest, Class<? extends Media> mediaChildClass, Class<M> mediaResponseChildClass) {

        var media = mapper.convertValue(mediaRequest, mediaChildClass);

        var createdMedia = mediaRepository.save(media);

        return mapper.convertValue(createdMedia, mediaResponseChildClass);
    }

    public void delete(String id) {
        mediaRepository.deleteById(id);
    }

    public void delete(Media media) {
        mediaRepository.delete(media);
    }

    public void delete(List<? extends Media> mediaList) {
        mediaRepository.deleteAll(mediaList);
    }
}
