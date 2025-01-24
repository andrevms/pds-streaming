package br.com.pds.streaming.blockburst.media.repositories;

import br.com.pds.streaming.blockburst.media.model.entities.Season;
import br.com.pds.streaming.framework.media.repositories.MediaRepository;

import java.util.List;

public interface SeasonRepository extends MediaRepository<Season, String> {
    List<Season> findByTvShowId(String tvShowId);
}