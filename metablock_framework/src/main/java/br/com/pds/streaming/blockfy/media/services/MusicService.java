package br.com.pds.streaming.blockfy.media.services;

import br.com.pds.streaming.blockfy.mapper.modelMapper.BlockfyMapper;
import br.com.pds.streaming.blockfy.media.model.dto.MusicDTO;
import br.com.pds.streaming.blockfy.media.model.entities.Music;
import br.com.pds.streaming.framework.cloud.services.CloudStorageService;
import br.com.pds.streaming.framework.media.repositories.LikeRatingRepository;
import br.com.pds.streaming.framework.media.services.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.pds.streaming.blockfy.media.services.AudioService.*;

@Service
public class MusicService {

    private final MediaService mediaService;
    private final LikeRatingRepository ratingRepository;
    private final BlockfyMapper mapper;
    private final CloudStorageService cloudStorageService;

    @Autowired
    public MusicService(MediaService mediaService, LikeRatingRepository ratingRepository, BlockfyMapper mapper, CloudStorageService cloudStorageService) {
        this.mediaService = mediaService;
        this.ratingRepository = ratingRepository;
        this.mapper = mapper;
        this.cloudStorageService = cloudStorageService;
    }

    public List<MusicDTO> findAll() {
        return mediaService.findAll(Music.class, MusicDTO.class);
    }

    public MusicDTO findById(String id) {
        return mediaService.findById(id, Music.class, MusicDTO.class);
    }

    public MusicDTO insert(MusicDTO musicDTO) {

        AudioService.verifyFileUrl(musicDTO);

        return mediaService.persist(musicDTO, Music.class, MusicDTO.class);
    }

    public MusicDTO update(MusicDTO musicDTO, String id) {

        AudioService.verifyFileUrl(musicDTO);

        var music = mediaService.findById(id, Music.class);

        music.setTitle(musicDTO.getTitle());
        music.setDescription(musicDTO.getDescription());
        music.setThumbnailUrl(musicDTO.getThumbnailUrl());
        music.setAnimationUrl(musicDTO.getAnimationUrl());
        music.setAudioUrl(musicDTO.getAudioUrl());
        music.setMusicGenre(musicDTO.getMusicGenre());
        music.setArtists(musicDTO.getArtists());

        var updatedMusic = mediaService.persist(music);

        return mapper.convertValue(updatedMusic, MusicDTO.class);
    }

    public MusicDTO patch(MusicDTO musicDTO, String id) {

        var music = mediaService.findById(id, Music.class);

        if (musicDTO.getTitle() != null) music.setTitle(musicDTO.getTitle());

        if (musicDTO.getDescription() != null) music.setDescription(musicDTO.getDescription());

        if (musicDTO.getThumbnailUrl() != null) {
            verifyFileThumbnailUrl(musicDTO);
            music.setThumbnailUrl(musicDTO.getThumbnailUrl());
        }

        if (musicDTO.getAnimationUrl() != null) {
            verifyFileAnimationUrl(musicDTO);
            music.setAnimationUrl(musicDTO.getAnimationUrl());
        }

        if (musicDTO.getAudioUrl() != null) {
            verifyFileAudioUrl(musicDTO);
            music.setAudioUrl(musicDTO.getAudioUrl());
        }

        if (musicDTO.getMusicGenre() != null) music.setMusicGenre(musicDTO.getMusicGenre());

        if (musicDTO.getArtists() != null) music.setArtists(musicDTO.getArtists());

        var patchedMusic = mediaService.persist(music);

        return mapper.convertValue(patchedMusic, MusicDTO.class);
    }

    public void delete(String id) {

        deleteOrphanRatings(id);

        var music = findById(id);
        var musicThumbnailUrl  = music.getThumbnailUrl();
        var musicAnimationUrl  = music.getAnimationUrl();
        var musicAudioUrl  = music.getAudioUrl();

        cloudStorageService.deleteFile(musicThumbnailUrl);
        cloudStorageService.deleteFile(musicAnimationUrl);
        cloudStorageService.deleteFile(musicAudioUrl);

        mediaService.delete(id);
    }

    private void deleteOrphanRatings(String musicId) {

        var music = mediaService.findById(musicId, Music.class);

        ratingRepository.deleteAll(ratingRepository.findAll().stream().filter(r -> music.getRatings().contains(r)).toList());
    }
}
