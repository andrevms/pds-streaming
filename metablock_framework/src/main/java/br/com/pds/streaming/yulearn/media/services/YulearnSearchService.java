package br.com.pds.streaming.yulearn.media.services;

import br.com.pds.streaming.framework.media.model.dto.MediaDTO;
import br.com.pds.streaming.framework.media.services.SearchService;
import br.com.pds.streaming.yulearn.mapper.modelMapper.YulearnMapper;
import br.com.pds.streaming.yulearn.media.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class YulearnSearchService extends SearchService {

    private final YulearnMapper mapper;

    @Autowired
    public YulearnSearchService(CourseRepository courseRepository, YulearnMapper mapper) {
        super(Set.of(courseRepository));
        this.mapper = mapper;
    }

    @Override
    public List<? extends MediaDTO> search(String keyWord) {
        return List.of();
    }
}
