package br.com.pds.streaming.blockfy.media.services;

import br.com.pds.streaming.blockfy.mapper.modelMapper.BlockfyMapper;
import br.com.pds.streaming.blockfy.media.model.dto.MusicDTO;
import br.com.pds.streaming.blockfy.media.model.dto.PodcastDTO;
import br.com.pds.streaming.blockfy.media.model.entities.Music;
import br.com.pds.streaming.blockfy.media.model.entities.Podcast;
import br.com.pds.streaming.blockfy.media.repositories.MusicRepository;
import br.com.pds.streaming.blockfy.media.repositories.PodcastRepository;
import br.com.pds.streaming.framework.media.model.dto.MediaDTO;
import br.com.pds.streaming.framework.media.model.entities.Media;
import br.com.pds.streaming.framework.media.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class BlockfySearchService extends SearchService {

    private final MusicRepository musicRepository;
    private final PodcastRepository podcastRepository;
    private final BlockfyMapper mapper;

    @Autowired
    public BlockfySearchService(MusicRepository musicRepository, PodcastRepository podcastRepository, BlockfyMapper mapper) {
        super(Set.of(musicRepository, podcastRepository));
        this.musicRepository = musicRepository;
        this.podcastRepository = podcastRepository;
        this.mapper = mapper;
    }

    @Override
    public List<? extends MediaDTO> search(String keyWord) {

        List<Media> result = new ArrayList<>();

        result.addAll(searchByTitle(keyWord));
        result.addAll(searchByDescription(keyWord));
        result.addAll(searchByCategory(keyWord));
        result.addAll(searchByArtist(keyWord));
        result.addAll(searchByMusicGenre(keyWord));
        result.addAll(searchByHost(keyWord));
        result.addAll(searchByGuest(keyWord));

        Collections.shuffle(result);

        return result.stream().map(m -> {
            if (m instanceof Music) {
                return mapper.convertValue(m, MusicDTO.class);
            } else if (m instanceof Podcast) {
                return mapper.convertValue(m, PodcastDTO.class);
            } else {
                throw new RuntimeException("This media type shouldn't be here.");
            }
        }).toList();
    }

    private List<Music> searchByArtist(String keyWord) {

        List<Music> result = new ArrayList<>();

        musicRepository.findAll().forEach(music -> music.getArtists().stream().filter(a -> a.toLowerCase().contains(keyWord.toLowerCase())).forEach(a -> result.add(music)));

        return result;
    }

    private List<Music> searchByMusicGenre(String keyWord) {
        return musicRepository.findAll().stream().filter(music -> music.getMusicGenre().toLowerCase().contains(keyWord.toLowerCase())).toList();
    }

    private List<Podcast> searchByHost(String keyWord) {

        List<Podcast> result = new ArrayList<>();

        podcastRepository.findAll().forEach(podcast -> podcast.getHosts().stream().filter(h -> h.toLowerCase().contains(keyWord.toLowerCase())).forEach(h -> result.add(podcast)));

        return result;
    }

    private List<Podcast> searchByGuest(String keyWord) {

        List<Podcast> result = new ArrayList<>();

        podcastRepository.findAll().forEach(podcast -> podcast.getGuests().stream().filter(g -> g.toLowerCase().contains(keyWord.toLowerCase())).forEach(g -> result.add(podcast)));

        return result;
    }
}
