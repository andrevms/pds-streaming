package br.com.pds.streaming.yulearn.mapper.modelMapper;

import br.com.pds.streaming.framework.mapper.modelMapper.MetablockMapper;
import br.com.pds.streaming.yulearn.mapper.modelMapper.config.YulearnModelMapperConfig;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class YulearnMapper extends MetablockMapper {

    private final ModelMapper modelMapper = new YulearnModelMapperConfig().getYulearnModelMapper();
}
