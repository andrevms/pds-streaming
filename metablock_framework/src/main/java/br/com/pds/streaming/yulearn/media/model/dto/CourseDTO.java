package br.com.pds.streaming.yulearn.media.model.dto;

import br.com.pds.streaming.framework.media.model.dto.MediaDTO;
import br.com.pds.streaming.framework.media.model.dto.StarRatingDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CourseDTO extends MediaDTO {

    private List<ModuleDTO> modules = new ArrayList<>();
    private List<StarRatingDTO> ratings = new ArrayList<>();
    private double ratingsAverage;
}
