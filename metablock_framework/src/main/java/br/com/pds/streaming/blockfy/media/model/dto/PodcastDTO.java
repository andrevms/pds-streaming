package br.com.pds.streaming.blockfy.media.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PodcastDTO extends AudioDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String videoUrl;
    private List<String> hosts = new ArrayList<>();
    private List<String> guests = new ArrayList<>();
}
