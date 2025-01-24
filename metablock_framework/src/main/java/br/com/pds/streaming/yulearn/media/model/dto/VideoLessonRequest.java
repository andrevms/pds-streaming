package br.com.pds.streaming.yulearn.media.model.dto;

import lombok.Data;

@Data
public class VideoLessonRequest extends LessonDTO {

    private String videoUrl;
    private String moduleId;
}
