package br.com.pds.streaming.blockfy.media.services;

import br.com.pds.streaming.blockfy.mapper.modelMapper.BlockfyMapper;
import br.com.pds.streaming.blockfy.media.model.dto.MusicDTO;
import br.com.pds.streaming.blockfy.media.model.entities.Music;
import br.com.pds.streaming.blockfy.media.repositories.MusicRepository;
import br.com.pds.streaming.framework.cloud.services.CloudStorageService;
import br.com.pds.streaming.framework.exceptions.EntityNotFoundException;
import br.com.pds.streaming.framework.media.repositories.LikeRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.pds.streaming.blockfy.media.util.FileExtensionVerifier.*;
import static br.com.pds.streaming.framework.media.util.FileExtensionVerifier.*;

@Service
public class MusicService {

    private final MusicRepository musicRepository;
    private final LikeRatingRepository ratingRepository;
    private final BlockfyMapper mapper;
    private final CloudStorageService cloudStorageService;

    @Autowired
    public MusicService(MusicRepository musicRepository, LikeRatingRepository ratingRepository, BlockfyMapper mapper, CloudStorageService cloudStorageService) {
        this.musicRepository = musicRepository;
        this.ratingRepository = ratingRepository;
        this.mapper = mapper;
        this.cloudStorageService = cloudStorageService;
    }

    public List<MusicDTO> findAll() {
        return mapper.convertList(musicRepository.findAll(), MusicDTO.class);
    }

    public MusicDTO findById(String id) {
        return mapper.convertValue(musicRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Music.class)), MusicDTO.class);
    }

    public MusicDTO insert(MusicDTO musicDTO) {

        verifyFileUrl(musicDTO);

        var music = mapper.convertValue(musicDTO, Music.class);

        return mapper.convertValue(musicRepository.save(music), MusicDTO.class);
    }

    public MusicDTO update(MusicDTO musicDTO, String id) {

        verifyFileUrl(musicDTO);

        var music = musicRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Music.class));

        music.setTitle(musicDTO.getTitle());
        music.setDescription(musicDTO.getDescription());
        music.setThumbnailUrl(musicDTO.getThumbnailUrl());
        music.setAnimationUrl(musicDTO.getAnimationUrl());
        music.setAudioUrl(musicDTO.getAudioUrl());
        music.setMusicGenre(musicDTO.getMusicGenre());
        music.setArtists(musicDTO.getArtists());

        var updatedMusic = musicRepository.save(music);

        return mapper.convertValue(updatedMusic, MusicDTO.class);
    }

    public MusicDTO patch(MusicDTO musicDTO, String id) {

        var music = musicRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Music.class));

        if (musicDTO.getTitle() != null) music.setTitle(musicDTO.getTitle());

        if (musicDTO.getDescription() != null) music.setDescription(musicDTO.getDescription());

        if (musicDTO.getThumbnailUrl() != null) {
            verifyThumbnailUrl(musicDTO);
            music.setThumbnailUrl(musicDTO.getThumbnailUrl());
        }

        if (musicDTO.getAnimationUrl() != null) {
            verifyAnimationUrl(musicDTO);
            music.setAnimationUrl(musicDTO.getAnimationUrl());
        }

        if (musicDTO.getAudioUrl() != null) {
            verifyAudioUrl(musicDTO);
            music.setAudioUrl(musicDTO.getAudioUrl());
        }

        if (musicDTO.getMusicGenre() != null) music.setMusicGenre(musicDTO.getMusicGenre());

        if (musicDTO.getArtists() != null) music.setArtists(musicDTO.getArtists());

        var patchedMusic = musicRepository.save(music);

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

        musicRepository.deleteById(id);
    }

    private void deleteOrphanRatings(String musicId) {

        var music = musicRepository.findById(musicId).orElseThrow(() -> new EntityNotFoundException(Music.class));

        ratingRepository.deleteAll(ratingRepository.findAll().stream().filter(r -> music.getRatings().contains(r)).toList());
    }
}
