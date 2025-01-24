package br.com.pds.streaming.yulearn.media.model.dto;

import br.com.pds.streaming.framework.media.model.dto.MediaDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ModuleDTO extends MediaDTO {

    private List<LessonDTO> lessons = new ArrayList<>();
}
