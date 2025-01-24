package br.com.pds.streaming.blockburst.mapper.modelMapper;

import br.com.pds.streaming.blockburst.mapper.modelMapper.config.BlockburstModelMapperConfig;
import br.com.pds.streaming.framework.mapper.modelMapper.MetablockMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class BlockburstMapper extends MetablockMapper {

    private final ModelMapper modelMapper = new BlockburstModelMapperConfig().getBlockburstModelMapper();
}
