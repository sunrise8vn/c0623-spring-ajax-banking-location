package com.cg.controller.rest;

import com.cg.model.dto.HistoryTransferResDTO;
import com.cg.model.dto.IHistoryTransferResDTO;
import com.cg.service.transfer.ITransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/histories")
public class HistoryRestController {

    @Autowired
    private ITransferService transferService;

    @GetMapping("/transfer")
    public ResponseEntity<?> getAllTransfers() {
//        List<HistoryTransferResDTO> historyTransferResDTOS = transferService.getAllTransferHistories();

        List<IHistoryTransferResDTO> historyTransferResDTOS = transferService.getAllITransferHistories();

        return new ResponseEntity<>(historyTransferResDTOS, HttpStatus.OK);
    }
}
