package br.com.pds.streaming.framework.media.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class MediaDTO {

    private String id;
    private String title;
    private String description;
    private String thumbnailUrl;
    private String animationUrl;
    private List<String> categories;
}
