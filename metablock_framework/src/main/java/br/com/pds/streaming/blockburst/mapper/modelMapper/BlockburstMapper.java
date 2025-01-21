package br.com.pds.streaming.blockburst.mapper.modelMapper;

import br.com.pds.streaming.blockburst.mapper.modelMapper.config.BlockburstModelMapperConfig;
import br.com.pds.streaming.framework.mapper.modelMapper.MetablockMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class BlockburstMapper extends MetablockMapper {

    private final ModelMapper modelMapper = new BlockburstModelMapperConfig().getBlockburstModelMapper();

//    @Override
//    public <O, D> D convertValue(O origin, Class<D> destination) {
//        return modelMapper.map(origin, destination);
//    }
//
//    @Override
//    public <O, D> List<D> convertList(List<O> origins, Class<D> destination) {
//
//        List<D> destinations = new ArrayList<>();
//
//        origins.stream().map(origin -> convertValue(origin, destination)).forEach(destinations::add);
//
//        return destinations;
//    }
}
