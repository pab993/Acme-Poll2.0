
package controllers;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PollService;
import services.PollerService;
import services.QuestionService;
import domain.Poll;
import domain.Poller;
import domain.Question;

@Controller
@RequestMapping("/question")
public class QuestionController extends AbstractController {

	//Services =======================================================================

	@Autowired
	private PollService		pollService;

	@Autowired
	private PollerService	pollerService;

	@Autowired
	private QuestionService	questionService;


	//Listing ========================================================================

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int pollId) {
		ModelAndView result;
		Collection<Question> questions;

		Poller poller = pollerService.findByPrincipal();

		Poll poll = pollService.findOne(pollId);

		if (poll == null || poller.getId() != poll.getPoller().getId()) {

			result = new ModelAndView("redirect:/panic/misc.do");

		} else {

			questions = questionService.findByPoll(pollId);

			long l = 10;
			Date actualDate = new Date(System.currentTimeMillis() - l);

			result = new ModelAndView("question/list");
			result.addObject("questions", questions);
			result.addObject("poller", poller);
			result.addObject("poll", poll);
			result.addObject("actualDate", actualDate);
			result.addObject("requestURI", "question/list.do");
		}

		return result;
	}

	//Creating
	// ===========================================================================

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int pollId) {
		ModelAndView result;

		Poll poll = pollService.findOne(pollId);
		Poller poller = pollerService.findByPrincipal();

		long l = 10;
		Date actualDate = new Date(System.currentTimeMillis() - l);

		try {

			if (poll.getPoller().getId() != poller.getId() || poll.getEndActive().before(actualDate) && poll.getStartActive().after(actualDate) || poll.getEndActive().after(actualDate) && poll.getStartActive().after(actualDate)) {
				result = new ModelAndView("redirect:/panic/misc.do");
			}

			Question question = questionService.create(poll);
			result = this.createEditModelAndView(question);

		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/panic/misc.do");
		}

		return result;
	}

	//Edit ==============================================================================

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int questionId) {
		ModelAndView result;

		Poller poller = pollerService.findByPrincipal();

		Question question = questionService.findOne(questionId);

		long l = 10;
		Date actualDate = new Date(System.currentTimeMillis() - l);

		try {
			Poll poll = pollService.findOne(question.getPoll().getId());

			if (poll.getPoller().getId() != poller.getId() || poll.getEndActive().after(actualDate) && poll.getStartActive().before(actualDate) || poll.getEndActive().before(actualDate) && poll.getStartActive().before(actualDate)) {

				result = new ModelAndView("redirect:/panic/misc.do");

			} else {

				result = this.createEditModelAndView(question);

			}
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/panic/misc.do");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Question questionPruned, BindingResult binding) {
		ModelAndView result;

		Question question = questionService.reconstruct(questionPruned, binding);

		if (binding.hasErrors()) {
			result = createEditModelAndView(question);
		} else {
			try {

				Question questionSaved = questionService.save(question);
				result = new ModelAndView("redirect:/question/list.do?pollId=" + questionSaved.getPoll().getId());

			} catch (Throwable oops) {

				result = createEditModelAndView(question, "question.commit.error");
			}
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Question questionPruned, BindingResult binding) {
		ModelAndView result;

		Question question = questionService.findOne(questionPruned.getId());

		try {
			if (question.getPoll().getQuestions().size() == 1) {
				result = createEditModelAndView(question, "question.commit.error.2");
			} else {
				questionService.delete(question);

				result = new ModelAndView("redirect:/question/list.do?pollId=" + question.getPoll().getId());
			}
		} catch (Throwable oops) {
			result = createEditModelAndView(question, "question.commit.error");
		}
		return result;
	}

	//Ancillary methods
	// =======================================================================

	protected ModelAndView createEditModelAndView(Question question) {
		ModelAndView result;

		result = createEditModelAndView(question, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(Question question, String message) {
		ModelAndView result;

		result = new ModelAndView("question/edit");

		Poll poll = pollService.findOne(question.getPoll().getId());

		long l = 10;
		Date actualDate = new Date(System.currentTimeMillis() - l);

		result.addObject("message", message);
		result.addObject("poll", poll);
		result.addObject("question", question);
		result.addObject("actualDate", actualDate);
		result.addObject("requestURI", "question/edit.do");

		return result;
	}

}
