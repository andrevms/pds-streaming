package br.com.pds.streaming.framework.media.services;

import br.com.pds.streaming.framework.mapper.modelMapper.MetablockMapper;
import br.com.pds.streaming.framework.media.model.dto.MediaDTO;
import br.com.pds.streaming.framework.media.model.entities.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SearchService {

    private MediaService mediaService;
    private MetablockMapper mapper;

    @Autowired
    public SearchService(MediaService mediaService, @Qualifier("metablockMapper") MetablockMapper mapper) {
        this.mediaService = mediaService;
        this.mapper = mapper;
    }

    public List<? extends MediaDTO> searchByTitle(String title) {
        return mediaService.findByTitle(title);
    }

    public <M extends MediaDTO> List<M> searchByTitle(String title, Class<? extends Media> mediaChildClass, Class<M> mediaResponseChildClass) {
        return mapper.convertList(mediaService.findByTitle(title).stream().filter(m -> m.getClass().equals(mediaChildClass)).toList(), mediaResponseChildClass);
    }

    public List<? extends MediaDTO> searchByDescription(String keyWord) {
        return mapper.convertList(mediaService.findAll().stream().filter(m -> m.getDescription().contains(keyWord)).toList(), MediaDTO.class);
    }

    public <M extends MediaDTO> List<M> searchByDescription(String keyWord, Class<? extends Media> mediaChildClass, Class<M> mediaResponseChildClass) {
        return mapper.convertList(mediaService.findAll(mediaChildClass).stream().filter(m -> m.getDescription().contains(keyWord)).toList(), mediaResponseChildClass);
    }

    public List<? extends MediaDTO> searchByCategory(String category) {
        return mapper.convertList(mediaService.findAll().stream().filter(m -> m.getCategories().contains(category)).toList(), MediaDTO.class);
    }

    public <M extends MediaDTO> List<M> searchByCategory(String category, Class<? extends Media> mediaChildClass, Class<M> mediaResponseChildClass) {
        return mapper.convertList(mediaService.findAll().stream().filter(m -> m.getCategories().contains(category) && m.getClass().equals(mediaChildClass)).toList(), mediaResponseChildClass);
    }

    public List<? extends MediaDTO> getRandomMedia(int quantity) {

        var media = mediaService.findAll();

        Collections.shuffle(media);

        return media.subList(0, Math.min(quantity, media.size()));
    }

    public <M extends MediaDTO> List<M> getRandomMedia(int quantity, Class<? extends Media> mediaChildClass, Class<M> mediaResponseChildClass) {

        var media = mediaService.findAll().stream().filter(m -> m.getClass().equals(mediaChildClass)).toList();

        Collections.shuffle(media);

        return mapper.convertList(media.subList(0, Math.min(quantity, media.size())), mediaResponseChildClass);
    }
}
