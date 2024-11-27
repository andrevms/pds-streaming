package br.com.pds.streaming.media.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MovieDTO extends MediaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String videoUrl;
    private List<String> categories = new ArrayList<>();
    private List<RatingDTO> ratings = new ArrayList<>();
    private String ratingsAverage;
}
