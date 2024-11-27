package br.com.pds.streaming.media.model.dto;

import lombok.Data;

@Data
public class MediaDTO {

    private String id;
    private String title;
    private String description;
    private String thumbnailUrl;
    private String animationUrl;
}
