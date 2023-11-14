package com.cg.service.transfer;

import com.cg.model.Transfer;
import com.cg.model.dto.HistoryTransferResDTO;
import com.cg.model.dto.IHistoryTransferResDTO;
import com.cg.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class TransferServiceImpl implements ITransferService {

    @Autowired
    private TransferRepository transferRepository;

    @Override
    public List<Transfer> findAll() {
        return null;
    }

    @Override
    public Optional<Transfer> findById(Long id) {
        return null;
    }

    @Override
    public List<HistoryTransferResDTO> getAllTransferHistories() {
        return transferRepository.getAllTransferHistories();
    }

    @Override
    public List<IHistoryTransferResDTO> getAllITransferHistories() {
        return transferRepository.getAllITransferHistories();
    }

    @Override
    public void save(Transfer transfer) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
