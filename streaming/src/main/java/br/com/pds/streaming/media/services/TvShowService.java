package br.com.pds.streaming.media.services;

import br.com.pds.streaming.exceptions.ObjectNotFoundException;
import br.com.pds.streaming.mapper.modelMapper.MyModelMapper;
import br.com.pds.streaming.media.model.dto.TvShowDTO;
import br.com.pds.streaming.media.model.entities.TvShow;
import br.com.pds.streaming.media.repositories.TvShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TvShowService {

    private TvShowRepository tvShowRepository;
    private MyModelMapper mapper;

    @Autowired
    public TvShowService(TvShowRepository tvShowRepository, MyModelMapper mapper) {
        this.tvShowRepository = tvShowRepository;
        this.mapper = mapper;
    }

    public List<TvShowDTO> findAll() {

        var tvShows = tvShowRepository.findAll();

        var tvShowsDTO = mapper.convertList(tvShows, TvShowDTO.class);

        tvShowsDTO.forEach(TvShowDTO::setRatingsAverage);

        return tvShowsDTO;
    }

    public TvShowDTO findById(String id) throws ObjectNotFoundException {

        var tvShow = tvShowRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("TvShow not found."));

        var tvShowDTO = mapper.convertValue(tvShow, TvShowDTO.class);

        tvShowDTO.setRatingsAverage();

        return tvShowDTO;
    }

    public TvShowDTO insert(TvShowDTO tvShowDTO) {

        var createdTvShow = tvShowRepository.save(mapper.convertValue(tvShowDTO, TvShow.class));

        return mapper.convertValue(createdTvShow, TvShowDTO.class);
    }

    public TvShowDTO update(TvShowDTO tvShowDTO, String id) throws ObjectNotFoundException {

        var tvShow = tvShowRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("TvShow not found."));

        tvShow.setTitle(tvShowDTO.getTitle());
        tvShow.setDescription(tvShowDTO.getDescription());
        tvShow.setThumbnailUrl(tvShowDTO.getThumbnailUrl());
        tvShow.setAnimationUrl(tvShowDTO.getAnimationUrl());

        var updatedTvShow = tvShowRepository.save(tvShow);

        return mapper.convertValue(updatedTvShow, TvShowDTO.class);
    }

    public void delete(String id) {
        tvShowRepository.deleteById(id);
    }
}
