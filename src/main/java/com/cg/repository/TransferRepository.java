package com.cg.repository;

import com.cg.model.Transfer;
import com.cg.model.dto.HistoryTransferResDTO;
import com.cg.model.dto.IHistoryTransferResDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {


    @Query("SELECT NEW com.cg.model.dto.HistoryTransferResDTO (" +
                "trf.sender.id, " +
                "trf.sender.fullName, " +
                "trf.recipient.id, " +
                "trf.recipient.fullName, " +
                "trf.createdAt, " +
                "trf.transferAmount, " +
                "trf.fees, " +
                "trf.feesAmount" +
            ") " +
            "FROM Transfer AS trf"
    )
    List<HistoryTransferResDTO> getAllTransferHistories();

    @Query(value = "SELECT * FROM vw_get_all_transfers", nativeQuery = true)
    List<IHistoryTransferResDTO> getAllITransferHistories();
}
