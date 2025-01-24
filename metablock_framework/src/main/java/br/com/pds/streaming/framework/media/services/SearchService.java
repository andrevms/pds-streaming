package br.com.pds.streaming.framework.media.services;

import br.com.pds.streaming.framework.media.model.dto.MediaDTO;
import br.com.pds.streaming.framework.media.model.entities.Media;
import br.com.pds.streaming.framework.media.repositories.MediaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class SearchService {

    private final Set<MediaRepository<? extends Media, String>> repositories;

    public SearchService(Set<MediaRepository<? extends Media, String>> repositories) {
        this.repositories = repositories;
    }

    public abstract List<? extends MediaDTO> search(String keyWord);

    public List<Media> searchByTitle(String keyWord) {

        List<Media> result = new ArrayList<>();

        repositories.forEach(r -> result.addAll(r.findAll().stream().filter(m -> m.getTitle().toLowerCase().contains(keyWord.toLowerCase())).toList()));

        return result.stream().filter(media -> media.getTitle().toLowerCase().contains(keyWord.toLowerCase())).toList();
    }

    public List<Media> searchByDescription(String keyWord) {

        List<Media> result = new ArrayList<>();

        repositories.forEach(r -> result.addAll(r.findAll().stream().filter(m -> m.getDescription().toLowerCase().contains(keyWord.toLowerCase())).toList()));

        return result;
    }

    public List<Media> searchByCategory(String keyWord) {

        List<Media> result = new ArrayList<>();

        repositories.forEach(r -> r.findAll().stream().forEach(m -> m.getCategories().stream().map(String::toLowerCase).filter(c -> c.contains(keyWord)).forEach(c -> result.add(m))));

        return result;
    }
}
