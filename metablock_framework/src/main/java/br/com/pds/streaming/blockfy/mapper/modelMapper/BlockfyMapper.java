package br.com.pds.streaming.blockfy.mapper.modelMapper;

import br.com.pds.streaming.blockfy.mapper.modelMapper.config.BlockfyModelMapperConfig;
import br.com.pds.streaming.framework.mapper.modelMapper.MetablockMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class BlockfyMapper extends MetablockMapper {

    private final ModelMapper modelMapper = new BlockfyModelMapperConfig().modelMapper();
}
