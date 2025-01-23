package br.com.pds.streaming.blockburst.media.services;

import br.com.pds.streaming.blockburst.mapper.modelMapper.BlockburstMapper;
import br.com.pds.streaming.blockburst.media.model.dto.MovieResponse;
import br.com.pds.streaming.blockburst.media.model.dto.TvShowResponse;
import br.com.pds.streaming.blockburst.media.model.entities.Movie;
import br.com.pds.streaming.blockburst.media.model.entities.TvShow;
import br.com.pds.streaming.blockburst.media.repositories.MovieRepository;
import br.com.pds.streaming.blockburst.media.repositories.TvShowRepository;
import br.com.pds.streaming.framework.media.model.dto.MediaDTO;
import br.com.pds.streaming.framework.media.model.entities.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class SearchService extends br.com.pds.streaming.framework.media.services.SearchService {

    private final BlockburstMapper mapper;

    @Autowired
    public SearchService(MovieRepository movieRepository, TvShowRepository tvShowRepository, BlockburstMapper mapper) {
        super(Set.of(movieRepository, tvShowRepository));
        this.mapper = mapper;
    }

    @Override
    public List<? extends MediaDTO> search(String keyWord) {

        List<Media> result = new ArrayList<>();

        result.addAll(searchByTitle(keyWord));
        result.addAll(searchByDescription(keyWord));
        result.addAll(searchByCategory(keyWord));

        Collections.shuffle(result);

        return result.stream().map(m -> {
            if (m instanceof Movie) {
                return mapper.convertValue(m, MovieResponse.class);
            } else if (m instanceof TvShow) {
                return mapper.convertValue(m, TvShowResponse.class);
            } else {
                return mapper.convertValue(m, MovieResponse.class);
            }
        }).toList();
    }


}
