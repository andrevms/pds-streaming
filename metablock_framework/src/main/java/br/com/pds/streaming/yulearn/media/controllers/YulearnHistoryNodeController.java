package br.com.pds.streaming.yulearn.media.controllers;

import br.com.pds.streaming.framework.media.controllers.HistoryNodeController;
import br.com.pds.streaming.yulearn.media.services.YulearnHistoryNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/yulearn-historynodes", "/api/yulearn-history-nodes", "/api/yulearn-history_nodes"})
public class YulearnHistoryNodeController extends HistoryNodeController {

    @Autowired
    public YulearnHistoryNodeController(YulearnHistoryNodeService yulearnHistoryNodeService) {
        super(yulearnHistoryNodeService);
    }
}
