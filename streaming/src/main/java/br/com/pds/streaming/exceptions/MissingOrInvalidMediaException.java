package br.com.pds.streaming.exceptions;

import br.com.pds.streaming.authentication.model.entities.User;
import br.com.pds.streaming.media.model.dto.HistoryNodeDTO;
import br.com.pds.streaming.media.model.entities.HistoryNode;

public class MissingOrInvalidMediaException extends RuntimeException {

    public MissingOrInvalidMediaException(HistoryNode historyNode) {
        super("History node with id " + historyNode.getId() + " does not have episode or movie, which is required to work correctly.");
    }

    public MissingOrInvalidMediaException(HistoryNodeDTO<?> historyNodeDTO) {
        super("History node with id " + historyNodeDTO.getId() + " does not have episode or movie, which is required to work correctly.");
    }

    public MissingOrInvalidMediaException(User user) {
        super("User with id " + user.getId() + " have a media in watch later list that's not Movie or TvShow, which is invalid.");
    }
}
