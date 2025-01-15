package br.com.pds.streaming.framework.media.services;

import br.com.pds.streaming.framework.authentication.model.entities.User;
import br.com.pds.streaming.framework.authentication.repositories.UserRepository;
import br.com.pds.streaming.framework.exceptions.EntityNotFoundException;
import br.com.pds.streaming.framework.mapper.modelMapper.MetablockMapper;
import br.com.pds.streaming.framework.media.model.dto.HistoryDTO;
import br.com.pds.streaming.framework.media.model.entities.History;
import br.com.pds.streaming.framework.media.repositories.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryService {

    private final HistoryRepository historyRepository;
    private final UserRepository userRepository;
    private final MetablockMapper mapper;

    @Autowired
    public HistoryService(HistoryRepository historyRepository, UserRepository userRepository, MetablockMapper mapper) {
        this.historyRepository = historyRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public List<HistoryDTO> findAll() {

        var histories = historyRepository.findAll();

        return mapper.convertList(histories, HistoryDTO.class);
    }

    public List<HistoryDTO> findByUserId(String userId) {

        var histories = historyRepository.findByUserId(userId);

        return mapper.convertList(histories, HistoryDTO.class);
    }

    public HistoryDTO findById(String id) {

        var history = historyRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(History.class));

        return mapper.convertValue(history, HistoryDTO.class);
    }

    public HistoryDTO insert(String userId) {

        var user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(User.class));

        var history = new History();
        history.setUserId(userId);

        var createdHistory = historyRepository.save(history);

        user.setHistory(history);

        userRepository.save(user);

        return mapper.convertValue(createdHistory, HistoryDTO.class);
    }

    public void delete(String id) {
        historyRepository.deleteById(id);
    }
}
