package br.com.pds.streaming.framework.media.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "star_rating")
public class StarRating extends Rating implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private double starRating;
}
