package br.com.pds.streaming.blockfy.media.controllers;

import br.com.pds.streaming.blockburst.media.services.BlockburstHistoryNodeService;
import br.com.pds.streaming.framework.media.controllers.HistoryNodeController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/blockfy-historynodes", "/api/blockfy-history-nodes", "/api/blockfy-history_nodes"})
public class BlockfyHistoryNodeController extends HistoryNodeController {

    @Autowired
    public BlockfyHistoryNodeController(BlockburstHistoryNodeService blockburstHistoryNodeService) {
        super(blockburstHistoryNodeService);
    }
}
