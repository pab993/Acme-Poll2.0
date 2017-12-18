
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.BillService;
import services.PollerService;
import services.ReceiptService;
import domain.Administrator;
import domain.Bill;
import domain.Poller;
import domain.Receipt;

@Controller
@RequestMapping("/receipt")
public class ReceiptController extends AbstractController {

	//Services =======================================================================

	@Autowired
	private ReceiptService			receiptService;

	@Autowired
	private BillService				billService;

	@Autowired
	private PollerService			pollerService;

	@Autowired
	private AdministratorService	administratorService;


	//Displaying
	// ===========================================================================

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int receiptId) {
		ModelAndView result;
		Receipt receipt;

		receipt = receiptService.findOne(receiptId);

		Poller poller = pollerService.findByPrincipal();
		Administrator admin = administratorService.findByPrincipal();

		if (admin != null) {
			result = new ModelAndView("receipt/display");
			result.addObject("receipt", receipt);
			result.addObject("bill", receipt.getBill());
			result.addObject("requestURI", "receipt/display.do");
		} else if (poller != null) {
			if (receipt.getBill().getPoll().getPoller().getId() != poller.getId()) {
				result = new ModelAndView("redirect:/panic/misc.do");
			} else {
				result = new ModelAndView("receipt/display");
				result.addObject("receipt", receipt);
				result.addObject("requestURI", "receipt/display.do");
			}
		} else {
			result = new ModelAndView("redirect:/panic/misc.do");
		}

		return result;
	}

	//Creating
	// ===========================================================================

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int billId) {
		ModelAndView result;

		Bill bill = billService.findOne(billId);
		Poller poller = pollerService.findByPrincipal();

		try {

			if (bill == null || poller == null || poller.getId() != bill.getPoll().getPoller().getId()) {
				result = new ModelAndView("redirect:/panic/misc.do");
			}

			Receipt receipt = receiptService.create(bill);
			result = this.createEditModelAndView(receipt);

		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/panic/misc.do");
		}

		return result;
	}

	//Saving
	// =======================================================================

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Receipt receipt, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = createEditModelAndView(receipt);
		} else {
			try {

				Receipt receiptSaved = receiptService.save(receipt);
				result = new ModelAndView("redirect:/receipt/display.do?receiptId=" + receiptSaved.getId());

			} catch (Throwable oops) {

				result = createEditModelAndView(receipt, "receipt.commit.error");
			}
		}
		return result;
	}

	//Ancillary methods
	// =======================================================================

	protected ModelAndView createEditModelAndView(Receipt receipt) {
		ModelAndView result;

		result = createEditModelAndView(receipt, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(Receipt receipt, String message) {
		ModelAndView result;

		result = new ModelAndView("receipt/edit");

		result.addObject("message", message);
		result.addObject("receipt", receipt);
		result.addObject("requestURI", "receipt/edit.do");

		return result;
	}

}
