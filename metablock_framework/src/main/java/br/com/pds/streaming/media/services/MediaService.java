package br.com.pds.streaming.media.services;

import br.com.pds.streaming.exceptions.EntityNotFoundException;
import br.com.pds.streaming.mapper.modelMapper.MyModelMapper;
import br.com.pds.streaming.media.model.dto.MediaDTO;
import br.com.pds.streaming.media.model.entities.Media;
import br.com.pds.streaming.media.repositories.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MediaService {

    private MediaRepository mediaRepository;
    private MyModelMapper mapper;

    @Autowired
    public MediaService(MediaRepository mediaRepository, MyModelMapper mapper) {
        this.mediaRepository = mediaRepository;
        this.mapper = mapper;
    }

    public List<? extends MediaDTO> findAll() {
        return mapper.convertList(mediaRepository.findAll(), MediaDTO.class);
    }

    public List<? extends MediaDTO> findByTitle(String title) {
        return mapper.convertList(mediaRepository.findByTitle(title), MediaDTO.class);
    }

    public List<? extends MediaDTO> findByCategory(String category) {
        return mapper.convertList(mediaRepository.findByCategory(category), MediaDTO.class);
    }

    public <M extends MediaDTO> M findById(String id) throws EntityNotFoundException {
        return (M) mapper.convertValue(mediaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Media.class)), MediaDTO.class);
    }

    public void delete(String id) {
        mediaRepository.deleteById(id);
    }
}
