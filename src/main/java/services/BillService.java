
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BillRepository;
import domain.Administrator;
import domain.Bill;
import domain.Poll;
import domain.Receipt;

@Service
@Transactional
public class BillService {

	//Managed repository

	@Autowired
	private BillRepository			billRepository;

	//Managed Services 

	@Autowired
	private PollerService			pollerService;

	@Autowired
	private PollService				pollService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ReceiptService			receiptService;


	//CRUD methods 

	public Bill create(Poll poll) {
		Bill result;

		Administrator administrator = administratorService.findByPrincipal();
		Assert.notNull(administrator);

		result = new Bill();
		result.setPoll(poll);

		return result;
	}

	public Bill findOne(int billId) {
		// TODO Auto-generated method stub
		Bill bill = billRepository.findOne(billId);
		return bill;
	}

	public Bill save(Bill bill) {
		Assert.notNull(bill);
		if (bill.getPaid() == true) {
			Receipt receipt = bill.getReceipt();
			receipt.setEndorsed(true);
			receiptService.save(receipt);
		}
		Bill billSaved = billRepository.save(bill);
		return billSaved;
	}

	public Bill save2(Bill bill) {
		Assert.notNull(bill);
		Bill billSaved = billRepository.save(bill);
		Poll poll = bill.getPoll();
		poll.setBill(billSaved);
		pollService.save2(poll);
		return billSaved;
	}

	//Other bussiness methods

	public Collection<Bill> findBillsByPoller(int pollerId) {
		Collection<Bill> bills;

		bills = billRepository.findBillsByPoller(pollerId);

		return bills;
	}

	public Collection<Bill> findBillsEndorsed() {
		Collection<Bill> bills;

		bills = billRepository.findBillsEndorsed();

		return bills;
	}

	public Collection<Bill> findBillsNotEndorsed() {
		Collection<Bill> bills;

		bills = billRepository.findBillsNotEndorsed();

		return bills;
	}

	public Bill findBillByPollId(int pollId) {
		// TODO Auto-generated method stub
		Bill bill = billRepository.findBillByPollId(pollId);
		return bill;
	}

	public Double forthQuery() {
		Double res = billRepository.forthQuery();
		return res;
	}

	public Double fifthQuery() {
		Double res = billRepository.fifthQuery();
		return res;
	}

	public Double sixthQuery() {
		Double res = billRepository.sixthQuery();
		return res;
	}

	public Collection<Object[]> seventhQuery() {
		Collection<Object[]> res = billRepository.seventhQuery();
		return res;
	}

}
