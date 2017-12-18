
package controllers;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.NeworService;
import services.PollService;
import domain.Actor;
import domain.Administrator;
import domain.Newor;
import domain.Poll;
import domain.Poller;

@Controller
@RequestMapping("/newor")
public class NeworController extends AbstractController {

	// Services
	// ===============================================================================

	@Autowired
	private NeworService	neworService;

	@Autowired
	private PollService		pollService;

	//	@Autowired
	//	private AdministratorService	administratorService;

	@Autowired
	private ActorService	actorService;


	// Constructor
	// ============================================================================

	public NeworController() {
		super();
	}

	// Listing
	// ============================================================================

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		final Actor principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		Date currentMoment;
		currentMoment = new Date(System.currentTimeMillis());

		Collection<Newor> newors;

		newors = this.neworService.findAll();

		result = new ModelAndView("newor/list");

		result.addObject("newors", newors);
		result.addObject("currentMoment", currentMoment);
		result.addObject("principal", principal);
		//		result.addObject("auts", auts);
		//		result.addObject("myAuth", myAuth);

		result.addObject("requestURI", "newor/list.do");

		return result;
	}

	@RequestMapping(value = "/listByPoll", method = RequestMethod.GET)
	public ModelAndView listByEvent(@RequestParam final int pollId) {
		ModelAndView result;
		try {
			Boolean adminFounded = false;
			Collection<Newor> newors;

			final Actor principal = this.actorService.findByPrincipal();
			Boolean actorVar = false;
			if (principal instanceof Poller || principal == null)
				actorVar = true;

			Date currentMoment;
			currentMoment = new Date(System.currentTimeMillis());

			Poll poll = pollService.findOne(pollId);
			Assert.notNull(poll);

			newors = this.neworService.findByPoll3(pollId);

			Collection<Newor> sobreite = neworService.findByPoll2(pollId);

			//			if (newors.isEmpty()) {
			//				cancel = true;
			//			} else {
			//				for (Newor newor : newors) {
			//					Assert.isTrue(newor.getDisplayMoment().before(currentMoment));
			//					break;
			//				}
			//
			//			}
			//Assert.isTrue(!newors.isEmpty());

			//			for (Newor newor : newors) {
			//				Assert.isTrue(newor.getDisplayMoment().before(currentMoment));
			//				Newor var = newor;
			//				if (var.getCancel() == true) {
			//					cancel = true;
			//				}
			//				break;
			//			}

			if (principal != null) {
				for (Newor newor : sobreite) {
					if (newor.getAdministrator().getId() == principal.getId()) {
						adminFounded = true;
						break;
					}

				}
			}

			result = new ModelAndView("newor/list");

			result.addObject("newors", newors);
			result.addObject("currentMoment", currentMoment);
			result.addObject("principal", principal);
			result.addObject("actorVar", actorVar);
			result.addObject("poll", poll);
			result.addObject("adminFounded", adminFounded);
			result.addObject("requestURI", "newor/listByPoll.do");
			if (principal != null) {
				if (principal instanceof Administrator) {
					Boolean isAdmin = true;
					result.addObject("isAdmin", isAdmin);
				}
			}

		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/panic/misc.do");
		}

		return result;
	}

	//Creating
	// ===========================================================================

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int pollId) {
		ModelAndView result;
		Newor newor;
		try {
			final Poll poll = this.pollService.findOne(pollId);
			Assert.notNull(poll);
			Actor principal = actorService.findByPrincipal();
			Assert.isTrue(principal instanceof Administrator);

			for (Newor n : poll.getNewors()) {
				if (n.getAdministrator().getId() == principal.getId()) {
					Assert.isTrue(false);
				}

			}
			newor = this.neworService.create(poll);

			result = this.createEditModelAndView(newor);
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/panic/misc.do");
		}

		return result;
	}

	//Editing
	// ===============================================================================

	@RequestMapping(value = "/editCancel", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int neworId) {
		ModelAndView result;
		Newor newor;
		try {
			newor = this.neworService.findOne(neworId);
			Assert.notNull(newor);

			result = this.createCancelEditModelAndView(newor);
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/panic/misc.do");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Newor newor, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(newor);
		else
			try {

				this.neworService.save(newor);
				result = new ModelAndView("redirect:/newor/list.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(newor, "newor.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/editCancel", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCancel(@Valid final Newor newor, final BindingResult binding) {
		ModelAndView result;

		//		newor.setAdministrator(this.administratorService.findByNewor(newor));
		neworService.checkJustification(newor, binding);

		if (binding.hasErrors())
			result = this.createCancelEditModelAndView(newor);
		else
			try {

				this.neworService.cancelNewor(newor);
				result = new ModelAndView("redirect:/newor/list.do");

			} catch (final Throwable oops) {

				result = this.createCancelEditModelAndView(newor, "newor.commit.error");

			}

		return result;
	}

	// Ancillary Methods
	// ===============================================================================

	protected ModelAndView createEditModelAndView(final Newor newor) {
		ModelAndView result;

		result = this.createEditModelAndView(newor, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Newor newor, final String message) {
		ModelAndView result;

		result = new ModelAndView("newor/edit");

		result.addObject("newor", newor);
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView createCancelEditModelAndView(final Newor newor) {
		ModelAndView result;

		result = this.createCancelEditModelAndView(newor, null);
		return result;
	}

	protected ModelAndView createCancelEditModelAndView(final Newor newor, final String message) {
		ModelAndView result;

		result = new ModelAndView("newor/editCancel");

		result.addObject("newor", newor);
		result.addObject("message", message);

		return result;
	}

}
