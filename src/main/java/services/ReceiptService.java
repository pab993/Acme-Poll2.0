
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ReceiptRepository;
import domain.Bill;
import domain.Poller;
import domain.Receipt;

@Service
@Transactional
public class ReceiptService {

	//Managed repository

	@Autowired
	private ReceiptRepository	receiptRepository;

	//Managed Services 

	@Autowired
	private PollerService		pollerService;

	@Autowired
	private BillService			billService;


	//CRUD methods 

	public Receipt create(Bill bill) {
		Receipt result;

		Poller poller = pollerService.findByPrincipal();
		Assert.isTrue(poller.getId() == bill.getPoll().getPoller().getId());

		result = new Receipt();
		result.setEndorsed(false);
		result.setBill(bill);

		return result;
	}

	public Receipt findOne(int receiptId) {
		// TODO Auto-generated method stub
		Receipt receipt = receiptRepository.findOne(receiptId);
		return receipt;
	}

	public Receipt save(Receipt receipt) {
		Assert.notNull(receipt);
		Receipt receiptSaved = receiptRepository.save(receipt);
		Bill bill = receipt.getBill();
		bill.setReceipt(receiptSaved);
		billService.save(bill);
		return receiptSaved;
	}

	//Other bussiness methods

}
