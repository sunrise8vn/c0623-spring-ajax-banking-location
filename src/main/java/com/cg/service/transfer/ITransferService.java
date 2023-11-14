package com.cg.service.transfer;

import com.cg.model.Transfer;
import com.cg.model.dto.HistoryTransferResDTO;
import com.cg.model.dto.IHistoryTransferResDTO;
import com.cg.service.IGeneralService;

import java.util.List;

public interface ITransferService extends IGeneralService<Transfer, Long> {

    List<HistoryTransferResDTO> getAllTransferHistories();

    List<IHistoryTransferResDTO> getAllITransferHistories();
}
