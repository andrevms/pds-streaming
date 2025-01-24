package br.com.pds.streaming.yulearn.media.model.dto;

import lombok.Data;

@Data
public class TextLessonResponse extends LessonDTO {

    private String pdfUrl;
}
