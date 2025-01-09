package br.com.pds.streaming.framework.media.services;

import br.com.pds.streaming.framework.exceptions.EntityNotFoundException;
import br.com.pds.streaming.framework.mapper.modelMapper.MetablockMapper;
import br.com.pds.streaming.framework.media.model.entities.Media;
import br.com.pds.streaming.framework.media.model.dto.MediaDTO;
import br.com.pds.streaming.framework.media.repositories.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MediaService {

    private MediaRepository mediaRepository;
    private MetablockMapper mapper;

    @Autowired
    public MediaService(MediaRepository mediaRepository, MetablockMapper mapper) {
        this.mediaRepository = mediaRepository;
        this.mapper = mapper;
    }

    public List<? extends MediaDTO> findAll() {
        return mapper.convertList(mediaRepository.findAll(), MediaDTO.class);
    }

    public List<? extends MediaDTO> findByTitle(String title) {
        return mapper.convertList(mediaRepository.findByTitle(title), MediaDTO.class);
    }

    public <M extends MediaDTO> M findById(String id) throws EntityNotFoundException {
        return (M) mapper.convertValue(mediaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Media.class)), MediaDTO.class);
    }

    public void delete(String id) {
        mediaRepository.deleteById(id);
    }
}
