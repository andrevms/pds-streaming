package br.com.pds.streaming.blockburst.repositories;

import br.com.pds.streaming.blockburst.media.model.entities.Episode;
import br.com.pds.streaming.blockburst.media.model.entities.Season;
import br.com.pds.streaming.framework.media.repositories.MediaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EpisodeRepository extends MongoRepository<Episode, String> {
    List<Episode> findBySeasonId(String seasonId);
}