package br.com.pds.streaming.blockfy.media.services;

import br.com.pds.streaming.blockfy.mapper.modelMapper.BlockfyMapper;
import br.com.pds.streaming.blockfy.media.model.dto.MusicDTO;
import br.com.pds.streaming.blockfy.media.repositories.MusicRepository;
import br.com.pds.streaming.framework.authentication.model.dto.domain.UserDTO;
import br.com.pds.streaming.framework.authentication.services.UserService;
import br.com.pds.streaming.framework.media.model.dto.HistoryDTO;
import br.com.pds.streaming.framework.media.model.dto.HistoryNodeDTO;
import br.com.pds.streaming.framework.media.model.entities.History;
import br.com.pds.streaming.framework.media.repositories.HistoryRepository;
import br.com.pds.streaming.framework.media.services.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BlockfyRecommendationService extends RecommendationService {


    private Map<MusicDTO, Integer> popularity = new HashMap<>();
    private BlockfyMapper mapper;
    private HistoryRepository historyRepository;
    private MusicService musicService;

    @Autowired
    public BlockfyRecommendationService(UserService userService, BlockfyMapper mapper, HistoryRepository historyRepository, MusicService musicService) {
        super(userService);
        this.mapper = mapper;
        this.historyRepository = historyRepository;
        this.musicService = musicService;
    }

    @Override
    public void setHistoryNodes(String userId) {
        var dto = userService.findById(userId);
        var user = userService.loadUserByUsername(dto.getUsername());
        var history = user.getHistory();

        List<HistoryNodeDTO> allItems = mapper.convertList(history.getNodes(), HistoryNodeDTO.class);
        historyNodes = allItems;

        historyNodes = historyNodes.subList(Math.max(historyNodes.size() - 5, 0), historyNodes.size());
    }

    @Override
    public void calculatePopularity() {

        Set<UserDTO> uniqueUsers = new HashSet<>();

        // Coletar usuários que escutaram músicas em comum
        for (HistoryNodeDTO node : historyNodes) {
            String musicId = node.getMedia().getId();

            MusicDTO musicDTO = musicService.findById(musicId);

            for (String userId : musicDTO.getUsersId()) {
                UserDTO user = userService.findById(userId);
                if (!uniqueUsers.contains(user) && uniqueUsers.size() < 5) {
                    uniqueUsers.add(user);
                    break;
                }
            }
        }

        // Pegar músicas escutadas por esses usuários e calcular sua popularidade
        for (UserDTO user : uniqueUsers) {
            History history = historyRepository.findByUserId(user.getId()).get(0);

            var historyDTO = mapper.convertValue(history, HistoryDTO.class);
            var size = history.getNodes().size();
            var nodes = historyDTO.getNodes().subList(Math.max(size - 5, 0), size);

            for (HistoryNodeDTO node : nodes) {
                String musicId = node.getMedia().getId();

                MusicDTO musicDTO = musicService.findById(musicId);

                popularity.put(musicDTO, popularity.getOrDefault(musicDTO, 0) + 1);
            }
        }

    }

    @Override
    public List<MusicDTO> generateRecommendations() {

        // Organizar por popularidade
        List<MusicDTO> recommendedItems = popularity.entrySet().stream()
                        .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                        .limit(10)
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toList());

        return recommendedItems.size() > 10 ? recommendedItems.subList(0, 10) : recommendedItems;
    }

}