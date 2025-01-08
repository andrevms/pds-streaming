package br.com.pds.streaming.media.services;

import br.com.pds.streaming.mapper.modelMapper.MyModelMapper;
import br.com.pds.streaming.media.model.dto.MediaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class SearchService {

    private MediaService mediaService;
    private MyModelMapper mapper;

    @Autowired
    public SearchService(MediaService mediaService, MyModelMapper mapper) {
        this.mediaService = mediaService;
        this.mapper = mapper;
    }

    public List<? extends MediaDTO> searchByTitle(String title) {
        return mediaService.findByTitle(title);
    }

    public List<? extends MediaDTO> findByDescription(String keyWord) {
        return mapper.convertList(mediaService.findAll().stream().filter(m -> m.getDescription().contains(keyWord)).toList(), MediaDTO.class);
    }

    public List<? extends MediaDTO> searchByCategory(String category) {
        return mediaService.findByCategory(category);
    }

    public List<? extends MediaDTO> getRandomMedia(int quantity) {

        var media = mediaService.findAll();

        Collections.shuffle(media);

        return media.subList(0, Math.min(quantity, media.size()));
    }
}
