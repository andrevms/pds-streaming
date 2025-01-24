package br.com.pds.streaming.yulearn.media.model.dto;

import lombok.Data;

@Data
public class VideoLessonResponse extends LessonDTO {

    private String videoUrl;
}
