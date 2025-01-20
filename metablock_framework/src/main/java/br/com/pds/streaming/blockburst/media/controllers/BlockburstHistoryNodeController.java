package br.com.pds.streaming.blockburst.media.controllers;

import br.com.pds.streaming.blockburst.media.services.BlockburstHistoryNodeService;
import br.com.pds.streaming.framework.media.controllers.HistoryNodeController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/historynodes", "/api/history-nodes", "/api/history_nodes"})
public class BlockburstHistoryNodeController extends HistoryNodeController {

    @Autowired
    public BlockburstHistoryNodeController(BlockburstHistoryNodeService blockburstHistoryNodeService) {
        super(blockburstHistoryNodeService);
    }
}
