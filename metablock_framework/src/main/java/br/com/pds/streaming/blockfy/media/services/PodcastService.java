package br.com.pds.streaming.blockfy.media.services;

import br.com.pds.streaming.blockfy.mapper.modelMapper.BlockfyMapper;
import br.com.pds.streaming.blockfy.media.model.dto.PodcastDTO;
import br.com.pds.streaming.blockfy.media.model.entities.Podcast;
import br.com.pds.streaming.framework.cloud.services.CloudStorageService;
import br.com.pds.streaming.framework.media.repositories.LikeRatingRepository;
import br.com.pds.streaming.framework.media.services.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.pds.streaming.blockfy.media.services.AudioService.*;
import static br.com.pds.streaming.blockfy.media.services.AudioService.verifyFileAudioUrl;

@Service
public class PodcastService {

    private final MediaService mediaService;
    private final LikeRatingRepository ratingRepository;
    private final BlockfyMapper mapper;
    private final CloudStorageService cloudStorageService;

    @Autowired
    public PodcastService(MediaService mediaService, LikeRatingRepository ratingRepository, BlockfyMapper mapper, CloudStorageService cloudStorageService) {
        this.mediaService = mediaService;
        this.ratingRepository = ratingRepository;
        this.mapper = mapper;
        this.cloudStorageService = cloudStorageService;
    }

    public List<PodcastDTO> findAll() {
        return mediaService.findAll(Podcast.class, PodcastDTO.class);
    }

    public PodcastDTO findById(String id) {
        return mediaService.findById(id, Podcast.class, PodcastDTO.class);
    }

    public PodcastDTO insert(PodcastDTO podcastDTO) {

        verifyFileUrl(podcastDTO);

        return mediaService.persist(podcastDTO, Podcast.class, PodcastDTO.class);
    }

    public PodcastDTO update(PodcastDTO podcastDTO, String id) {

        verifyFileUrl(podcastDTO);

        var podcast = mediaService.findById(id, Podcast.class);

        podcast.setTitle(podcastDTO.getTitle());
        podcast.setDescription(podcastDTO.getDescription());
        podcast.setThumbnailUrl(podcastDTO.getThumbnailUrl());
        podcast.setAnimationUrl(podcastDTO.getAnimationUrl());
        podcast.setAudioUrl(podcastDTO.getAudioUrl());
        podcast.setVideoUrl(podcastDTO.getVideoUrl());
        podcast.setHosts(podcastDTO.getHosts());
        podcast.setGuests(podcastDTO.getGuests());

        var updatedPodcast = mediaService.persist(podcast);

        return mapper.convertValue(updatedPodcast, PodcastDTO.class);
    }

    public PodcastDTO patch(PodcastDTO podcastDTO, String id) {

        var podcast = mediaService.findById(id, Podcast.class);

        if (podcastDTO.getTitle() != null) podcast.setTitle(podcastDTO.getTitle());

        if (podcastDTO.getDescription() != null) podcast.setDescription(podcastDTO.getDescription());

        if (podcastDTO.getThumbnailUrl() != null) {
            verifyFileThumbnailUrl(podcastDTO);
            podcast.setThumbnailUrl(podcastDTO.getThumbnailUrl());
        }

        if (podcastDTO.getAnimationUrl() != null) {
            verifyFileAnimationUrl(podcastDTO);
            podcast.setAnimationUrl(podcastDTO.getAnimationUrl());
        }

        if (podcastDTO.getAudioUrl() != null) {
            verifyFileAudioUrl(podcastDTO);
            podcast.setAudioUrl(podcastDTO.getAudioUrl());
        }

        if (podcastDTO.getVideoUrl() != null) {
            verifyFileVideoUrl(podcastDTO);
            podcast.setVideoUrl(podcastDTO.getVideoUrl());
        }

        if (podcastDTO.getHosts() != null) podcast.setHosts(podcastDTO.getHosts());

        if (podcastDTO.getGuests() != null) podcast.setGuests(podcastDTO.getGuests());

        var patchedPodcast = mediaService.persist(podcast);

        return mapper.convertValue(patchedPodcast, PodcastDTO.class);
    }

    public void delete(String id) {

        deleteOrphanRatings(id);

        var podcast = findById(id);
        var podcastThumbnailUrl = podcast.getThumbnailUrl();
        var podcastAnimationUrl = podcast.getAnimationUrl();
        var podcastAudioUrl = podcast.getAudioUrl();
        var podcastVideoUrl = podcast.getVideoUrl();

        cloudStorageService.deleteFile(podcastThumbnailUrl);
        cloudStorageService.deleteFile(podcastAnimationUrl);
        cloudStorageService.deleteFile(podcastAudioUrl);
        cloudStorageService.deleteFile(podcastVideoUrl);

        mediaService.delete(id);
    }

    private void deleteOrphanRatings(String podcastId) {

        var podcast = mediaService.findById(podcastId, Podcast.class);

        ratingRepository.deleteAll(ratingRepository.findAll().stream().filter(r -> podcast.getRatings().contains(r)).toList());
    }
}
