package br.com.pds.streaming.framework.media.services;

import br.com.pds.streaming.framework.mapper.modelMapper.MetablockMapper;
import br.com.pds.streaming.framework.media.model.dto.MediaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SearchService {

    private MediaService mediaService;
    private MetablockMapper mapper;

    @Autowired
    public SearchService(MediaService mediaService, MetablockMapper mapper) {
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
        return mapper.convertList(mediaService.findAll().stream().filter(m -> m.getCategories().contains(category)).toList(), MediaDTO.class);
    }

    public List<? extends MediaDTO> getRandomMedia(int quantity) {

        var media = mediaService.findAll();

        Collections.shuffle(media);

        return media.subList(0, Math.min(quantity, media.size()));
    }
}
