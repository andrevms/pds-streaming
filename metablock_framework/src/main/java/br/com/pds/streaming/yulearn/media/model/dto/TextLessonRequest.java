package br.com.pds.streaming.yulearn.media.model.dto;

import lombok.Data;

@Data
public class TextLessonRequest extends LessonDTO {

    private String pdfUrl;
    private String moduleId;
}
