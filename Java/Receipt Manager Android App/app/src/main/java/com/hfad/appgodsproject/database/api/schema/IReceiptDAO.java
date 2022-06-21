package com.hfad.appgodsproject.database.api.schema;

import com.hfad.appgodsproject.pojos.Receipt;

import java.util.List;

public interface IReceiptDAO {
    public Receipt fetchReceiptById(long receiptId);
    public List<Receipt> fetchAllReceipts();
    public long addReceipt(Receipt receipt);
    public List<Long> addReceipts(List<Receipt> receiptList);
    public List<Receipt> fetchReceiptsInDateRange(String range);
}
