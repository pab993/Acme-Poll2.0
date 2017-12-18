
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ConfigurationSystemService;
import services.PollService;
import services.PollerService;
import domain.Actor;
import domain.Answer;
import domain.Poll;
import domain.Poller;
import forms.PollForm;

@Controller
@RequestMapping("/poll")
public class PollController extends AbstractController {

	//Services =======================================================================

	@Autowired
	private PollService					pollService;

	@Autowired
	private PollerService				pollerService;

	@Autowired
	private ActorService				actorService;

	@Autowired
	private ConfigurationSystemService	configurationSystemService;


	//Listing ========================================================================

	@RequestMapping(value = "/myList", method = RequestMethod.GET)
	public ModelAndView myList() {
		ModelAndView result;
		Collection<Poll> polls;

		Poller poller = pollerService.findByPrincipal();

		polls = pollService.findAllMyPolls(poller.getId());

		int minCs = configurationSystemService.getCS().getMinimumPeriodActive();

		long l = 10;
		Date actualDate = new Date(System.currentTimeMillis() - l);

		result = new ModelAndView("poll/list");
		result.addObject("polls", polls);
		result.addObject("minCS", minCs);
		result.addObject("poller", poller);
		result.addObject("actualDate", actualDate);
		result.addObject("requestURI", "poll/myList.do");

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Poll> polls;

		polls = pollService.findOnlyActives();

		int minCs = configurationSystemService.getCS().getMinimumPeriodActive();

		Poller poller = pollerService.findByPrincipal();

		long l = 10;
		Date actualDate = new Date(System.currentTimeMillis() - l);

		result = new ModelAndView("poll/list");
		result.addObject("polls", polls);
		result.addObject("poller", poller);
		result.addObject("minCs", minCs);
		result.addObject("actualDate", actualDate);
		result.addObject("requestURI", "poll/list.do");

		return result;
	}

	//Display ========================================================================

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int pollId, @CookieValue(value = "hitCounter", defaultValue = "test") String hitCounter, HttpServletResponse response) {
		ModelAndView result;
		Poll poll;

		poll = pollService.findOne(pollId);

		Actor actor = actorService.findByPrincipal();

		long l = 10;
		Date actualDate = new Date(System.currentTimeMillis() - l);

		if (poll == null) {

			result = new ModelAndView("redirect:/panic/misc.do");

		} else {

			if (checkCookie(hitCounter, pollId) || poll.getActors().contains(actor) || minTimeActive(poll.getStartActive(), poll.getEndActive())) {

				result = new ModelAndView("redirect:/poll/accessDeny.do");

			} else if (poll.getEndActive().before(actualDate)) {

				result = new ModelAndView("redirect:/poll/pollOver.do");

			} else if (poll.getStartActive().after(actualDate)) {

				result = new ModelAndView("redirect:/poll/pollNotStarted.do");

			} else {

				result = new ModelAndView("poll/display");
				result.addObject("pollForm", new PollForm(poll.getQuestions().size(), poll.getId()));
				result.addObject("poll", poll);
				result.addObject("actualDate", actualDate);
				result.addObject("requestURI", "poll/display.do");

			}
		}

		return result;
	}

	//	@RequestMapping(value = "/display", method = RequestMethod.POST, params = "save")
	//	public ModelAndView save(PollForm pollForm, @CookieValue(value = "hitCounter", defaultValue = "test") String hitCounter, HttpServletResponse response, BindingResult binding) {
	//		ModelAndView result;
	//
	//		//		pollService.reconstruct(pollForm, binding);
	//
	//		if (binding.hasErrors()) {
	//			result = createEditModelAndView(pollForm, "poll.commit.error.2");
	//		} else if (checkAnswers(pollForm)) {
	//			result = createEditModelAndView(pollForm, "poll.commit.error");
	//
	//		} else {
	//			try {
	//
	//				pollService.savePollAnswered(pollForm);
	//
	//				for (Answer answer : pollForm.getListOfAnswers()) {
	//					hitCounter = hitCounter + "," + String.valueOf(answer.getQuestion().getPoll().getId());
	//					break;
	//				}
	//
	//				response.addCookie(new Cookie("hitCounter", hitCounter));
	//				result = new ModelAndView("redirect:/poll/list.do");
	//
	//			} catch (Throwable oops) {
	//
	//				result = createEditModelAndView(pollForm, "poll.commit.error");
	//			}
	//		}
	//		return result;
	//	}

	@RequestMapping(value = "/display", method = RequestMethod.POST, params = "save")
	public ModelAndView save(PollForm pollForm, @CookieValue(value = "hitCounter", defaultValue = "test") String hitCounter, HttpServletResponse response, BindingResult binding) {
		ModelAndView result;

		PollForm pollFormValid = pollService.reconstruct(pollForm, binding);

		if (binding.hasErrors()) {
			result = createEditModelAndView(pollFormValid, "poll.commit.error.2");
		} else if (checkAnswers(pollFormValid)) {
			result = createEditModelAndView(pollFormValid, "poll.commit.error");

		} else {
			try {

				pollService.savePollAnswered(pollFormValid);

				for (Answer answer : pollFormValid.getListOfAnswers()) {
					hitCounter = hitCounter + "," + String.valueOf(answer.getQuestion().getPoll().getId());
					break;
				}

				response.addCookie(new Cookie("hitCounter", hitCounter));
				result = new ModelAndView("redirect:/poll/list.do");

			} catch (Throwable oops) {

				result = createEditModelAndView(pollFormValid, "poll.commit.error");
			}
		}
		return result;
	}

	//Results ==========================================================================

	@RequestMapping(value = "/results", method = RequestMethod.GET)
	public ModelAndView results(@RequestParam int pollId) {
		ModelAndView result;
		Poll poll;

		poll = pollService.findOne(pollId);

		long l = 10;
		Date actualDate = new Date(System.currentTimeMillis() - l);

		if (poll == null) {

			result = new ModelAndView("redirect:/panic/misc.do");

		} else {

			if (poll.getStartActive().after(actualDate)) {

				result = new ModelAndView("redirect:/poll/pollNotStarted.do");

			} else if (minTimeActive(poll.getStartActive(), poll.getEndActive())) {

				result = new ModelAndView("redirect:/poll/accessDeny.do");

			} else {

				result = new ModelAndView("poll/results");
				result.addObject("poll", poll);
				result.addObject("requestURI", "poll/results.do");
			}
		}

		return result;
	}

	//Edit ==============================================================================

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int pollId) {
		ModelAndView result;
		Poll poll = pollService.findOne(pollId);

		Poller poller = pollerService.findByPrincipal();

		if (poll == null || poller == null || poll.getPoller().getId() != poller.getId()) {

			result = new ModelAndView("redirect:/panic/misc.do");

		} else {

			result = this.createEditModelAndView(poll);

		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Poll pollPruned, BindingResult binding) {
		ModelAndView result;

		Poll poll = pollService.reconstruct(pollPruned, binding);

		if (binding.hasErrors()) {
			result = createEditModelAndView(poll);
		} else {
			try {

				pollService.save(poll);
				result = new ModelAndView("redirect:/poll/myList.do");

			} catch (Throwable oops) {

				result = createEditModelAndView(poll, "poll.commit.error.2");
			}
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Poll pollPruned, BindingResult binding) {
		ModelAndView result;

		Poll poll = pollService.findOne(pollPruned.getId());

		try {
			pollService.delete(poll);

			result = new ModelAndView("redirect:/poll/myList.do");
		} catch (Throwable oops) {
			result = createEditModelAndView(poll, "poll.commit.error.2");
		}
		return result;
	}

	//Creating
	// ===========================================================================

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		Poll poll = pollService.create();

		result = this.createEditModelAndView(poll);

		return result;
	}

	@RequestMapping("/accessDeny")
	public ModelAndView accessDeny() {
		ModelAndView result;

		result = new ModelAndView("poll/accessDeny");

		return result;
	}

	@RequestMapping("/pollOver")
	public ModelAndView pollOver() {
		ModelAndView result;

		result = new ModelAndView("poll/pollOver");

		return result;
	}

	@RequestMapping("/pollNotStarted")
	public ModelAndView pollNotStarted() {
		ModelAndView result;

		result = new ModelAndView("poll/pollNotStarted");

		return result;
	}

	//Search
	// ===============================================================================

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView listSearch() {
		ModelAndView result;
		Collection<Poll> polls = new ArrayList<Poll>();

		long l = 10;
		Date actualDate = new Date(System.currentTimeMillis() - l);

		result = new ModelAndView("poll/search");
		result.addObject("polls", polls);
		result.addObject("actualDate", actualDate);
		result.addObject("requestURI", "poll/search.do");

		return result;
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView search(@RequestParam("keyword") String keyword) {
		ModelAndView result;
		Collection<Poll> polls;

		polls = pollService.pollsByKeyWord(keyword);

		long l = 10;
		Date actualDate = new Date(System.currentTimeMillis() - l);

		Poller poller = pollerService.findByPrincipal();

		result = new ModelAndView("poll/search");
		result.addObject("polls", polls);
		result.addObject("poller", poller);
		result.addObject("actualDate", actualDate);
		result.addObject("requestURI", "poll/search.do");

		return result;
	}

	// Ancillary Methods
	// ===============================================================================

	protected ModelAndView createEditModelAndView(PollForm pollForm, int pollId) {
		ModelAndView result;

		result = createEditModelAndView(pollForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(PollForm pollForm, String message) {
		ModelAndView result;

		Poll poll = pollService.findOne(pollForm.getPollId());

		long l = 10;
		Date actualDate = new Date(System.currentTimeMillis() - l);

		result = new ModelAndView("poll/display");

		result.addObject("message", message);
		//		result.addObject("pollForm", new PollForm(poll.getQuestions().size(), pollForm.getPollId(), pollForm.getCity(), pollForm.getGenre(), pollForm.getName()));
		result.addObject("pollForm", pollForm);
		result.addObject("poll", poll);
		result.addObject("actualDate", actualDate);
		result.addObject("requestURI", "poll/display.do");

		return result;
	}

	protected ModelAndView createEditModelAndView(Poll poll) {
		ModelAndView result;

		result = createEditModelAndView(poll, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(Poll poll, String message) {
		ModelAndView result;

		result = new ModelAndView("poll/edit");

		long l = 10;
		Date actualDate = new Date(System.currentTimeMillis() - l);

		result.addObject("message", message);
		result.addObject("poll", poll);
		result.addObject("actualDate", actualDate);
		result.addObject("requestURI", "poll/edit.do");

		return result;
	}

	public Boolean checkCookie(String hitCounter, int pollId) {
		Boolean res = false;
		String id = String.valueOf(pollId);
		String[] parts = hitCounter.split(",");
		for (String subString : parts) {
			if (subString.equals(id)) {
				res = true;
			}
		}
		return res;
	}

	public Boolean checkAnswers(PollForm pollForm) {
		Boolean res = false;
		if (pollForm.getListOfAnswers().isEmpty()) {
			res = true;
		} else {
			for (Answer answer : pollForm.getListOfAnswers()) {
				try {
					if (answer.getQuestion().getPoll().getQuestions().size() != pollForm.getListOfAnswers().size()) {
						res = true;
						break;
					}
				} catch (Throwable oops) {
					res = true;
					break;
				}

			}
		}

		return res;
	}

	public Boolean minTimeActive(Date startActive, Date endActive) {
		Boolean result = false;
		long totalTime = (endActive.getTime() - startActive.getTime()) / (1000 * 60 * 60 * 24);

		int minCs = configurationSystemService.getCS().getMinimumPeriodActive();

		if (totalTime < minCs) {
			result = true;
		}

		return result;
	}

}
