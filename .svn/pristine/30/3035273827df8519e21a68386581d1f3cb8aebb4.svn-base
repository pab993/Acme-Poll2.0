
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.BillService;
import services.PollService;
import services.PollerService;
import domain.Administrator;
import domain.Bill;
import domain.Poll;
import domain.Poller;

@Controller
@RequestMapping("/bill")
public class BillController extends AbstractController {

	//Services =======================================================================

	@Autowired
	private BillService				billService;

	@Autowired
	private PollerService			pollerService;

	@Autowired
	private PollService				pollService;

	@Autowired
	private AdministratorService	administratorService;


	//Listing ========================================================================

	@RequestMapping(value = "/myList", method = RequestMethod.GET)
	public ModelAndView myList() {
		ModelAndView result;
		Collection<Bill> bills;

		Poller poller = pollerService.findByPrincipal();

		try {

			Assert.notNull(poller);

			bills = billService.findBillsByPoller(poller.getId());

			result = new ModelAndView("bill/list");
			result.addObject("bills", bills);
			result.addObject("poller", poller);
			result.addObject("requestURI", "bill/myList.do");

		} catch (Throwable Oops) {
			result = new ModelAndView("redirect:/panic/misc.do");
		}

		return result;
	}

	@RequestMapping(value = "/endorsedList", method = RequestMethod.GET)
	public ModelAndView endorsedList() {
		ModelAndView result;
		Collection<Bill> bills;

		Administrator admin = administratorService.findByPrincipal();

		try {

			Assert.notNull(admin);

			bills = billService.findBillsEndorsed();

			result = new ModelAndView("bill/list");
			result.addObject("bills", bills);
			result.addObject("admin", admin);
			result.addObject("requestURI", "bill/endorsedList.do");

		} catch (Throwable Oops) {
			result = new ModelAndView("redirect:/panic/misc.do");
		}

		return result;
	}

	@RequestMapping(value = "/notEndorsedList", method = RequestMethod.GET)
	public ModelAndView notEndorsedList() {
		ModelAndView result;
		Collection<Bill> bills;

		Administrator admin = administratorService.findByPrincipal();

		try {

			Assert.notNull(admin);

			bills = billService.findBillsNotEndorsed();

			result = new ModelAndView("bill/list");
			result.addObject("bills", bills);
			result.addObject("admin", admin);
			result.addObject("requestURI", "bill/notEndorsedList.do");

		} catch (Throwable Oops) {
			result = new ModelAndView("redirect:/panic/misc.do");
		}

		return result;
	}

	//Creating
	// =======================================================================

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int pollId) {
		ModelAndView result;

		Poll poll = pollService.findOne(pollId);
		Administrator admin = administratorService.findByPrincipal();

		try {

			Assert.notNull(poll);
			Assert.notNull(admin);

			Bill bill = billService.create(poll);
			result = this.createEditModelAndView2(bill);

		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/panic/misc.do");
		}

		return result;
	}

	//Saving
	// =======================================================================

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Bill bill, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = createEditModelAndView(bill);
		} else {
			try {

				Bill billSaved = billService.save(bill);
				result = new ModelAndView("redirect:/bill/endorsedList.do");

			} catch (Throwable oops) {

				result = createEditModelAndView(bill, "bill.commit.error");
			}
		}
		return result;
	}

	@RequestMapping(value = "/editC", method = RequestMethod.POST, params = "save")
	public ModelAndView saveC(@Valid Bill bill, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = createEditModelAndView2(bill);
		} else {
			try {

				Bill billSaved = billService.save2(bill);
				result = new ModelAndView("redirect:/poll/list.do");

			} catch (Throwable oops) {

				result = createEditModelAndView2(bill, "bill.commit.error");
			}
		}
		return result;
	}

	//Display
	// =======================================================================

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int pollId) {
		ModelAndView result;

		try {

			Administrator admin = administratorService.findByPrincipal();
			Bill bill = billService.findBillByPollId(pollId);
			Assert.notNull(admin);
			Assert.notNull(bill);
			result = new ModelAndView("bill/display");
			result.addObject("bill", bill);

		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/panic/misc.do");
		}

		return result;
	}
	//Ancillary methods
	// =======================================================================

	protected ModelAndView createEditModelAndView(Bill bill) {
		ModelAndView result;

		result = createEditModelAndView(bill, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(Bill bill, String message) {
		ModelAndView result;

		result = new ModelAndView("receipt/display");

		result.addObject("message", message);
		result.addObject("bill", bill);
		result.addObject("receipt", bill.getReceipt());
		result.addObject("requestURI", "receipt/display.do");

		return result;
	}

	protected ModelAndView createEditModelAndView2(Bill bill) {
		ModelAndView result;

		result = createEditModelAndView2(bill, null);
		return result;
	}

	protected ModelAndView createEditModelAndView2(Bill bill, String message) {
		ModelAndView result;

		result = new ModelAndView("bill/edit");

		result.addObject("message", message);
		result.addObject("bill", bill);
		result.addObject("poll", bill.getPoll());
		result.addObject("requestURI", "bill/edit.do");

		return result;
	}

}
