package br.com.pds.streaming.yulearn.media.model.entities;

import br.com.pds.streaming.framework.media.model.entities.Media;
import lombok.Data;

@Data
public abstract class Lesson extends Media {

    private String moduleId;
}
