
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

import services.AnswerService;
import services.PollService;
import services.PollerService;
import services.QuestionService;
import domain.Answer;
import domain.Poll;
import domain.Poller;
import domain.Question;

@Controller
@RequestMapping("/answer")
public class AnswerController extends AbstractController {

	//Services =======================================================================

	@Autowired
	private AnswerService	answerService;

	@Autowired
	private PollService		pollService;

	@Autowired
	private PollerService	pollerService;

	@Autowired
	private QuestionService	questionService;


	//Listing ========================================================================

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int questionId) {
		ModelAndView result;
		Collection<Answer> answers;

		Poller poller = pollerService.findByPrincipal();

		try {
			Question question = questionService.findOne(questionId);

			if (question == null || poller.getId() != question.getPoll().getPoller().getId()) {

				result = new ModelAndView("redirect:/panic/misc.do");

			} else {

				answers = answerService.findByQuestion(questionId);

				long l = 10;
				Date actualDate = new Date(System.currentTimeMillis() - l);

				result = new ModelAndView("answer/list");
				result.addObject("answers", answers);
				result.addObject("poller", poller);
				result.addObject("question", question);
				result.addObject("poll", question.getPoll());
				result.addObject("actualDate", actualDate);
				result.addObject("requestURI", "answer/list.do");
			}
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/panic/misc.do");
		}

		return result;
	}

	//Creating
	// ===========================================================================

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int questionId) {
		ModelAndView result;

		Question question = questionService.findOne(questionId);
		Poller poller = pollerService.findByPrincipal();

		long l = 10;
		Date actualDate = new Date(System.currentTimeMillis() - l);

		try {

			if (question.getPoll().getPoller().getId() != poller.getId() || question.getPoll().getEndActive().before(actualDate) && question.getPoll().getStartActive().after(actualDate) || question.getPoll().getEndActive().after(actualDate)
				&& question.getPoll().getStartActive().after(actualDate)) {
				result = new ModelAndView("redirect:/panic/misc.do");
			}

			Answer answer = answerService.create(question);
			result = this.createEditModelAndView(answer);

		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/panic/misc.do");
		}

		return result;
	}

	//Edit ==============================================================================

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int answerId) {
		ModelAndView result;

		Poller poller = pollerService.findByPrincipal();

		Answer answer = answerService.findOne(answerId);

		long l = 10;
		Date actualDate = new Date(System.currentTimeMillis() - l);

		try {
			Poll poll = pollService.findOne(answer.getQuestion().getPoll().getId());

			if (poll.getPoller().getId() != poller.getId() || poll.getEndActive().after(actualDate) && poll.getStartActive().before(actualDate) || poll.getEndActive().before(actualDate) && poll.getStartActive().before(actualDate)) {

				result = new ModelAndView("redirect:/panic/misc.do");

			} else {

				result = this.createEditModelAndView(answer);

			}
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/panic/misc.do");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Answer answer, BindingResult binding) {
		ModelAndView result;

		//		Answer answer = answerService.reconstruct(answerPruned, binding);

		if (binding.hasErrors()) {
			result = createEditModelAndView(answer);
		} else {
			try {

				Answer answerSaved = answerService.save(answer);
				result = new ModelAndView("redirect:/answer/list.do?questionId=" + answerSaved.getQuestion().getId());

			} catch (Throwable oops) {

				result = createEditModelAndView(answer, "answer.commit.error");
			}
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Answer answerPruned, BindingResult binding) {
		ModelAndView result;

		Answer answer = answerService.findOne(answerPruned.getId());

		try {

			if (answer.getQuestion().getAnswers().size() == 2) {
				result = createEditModelAndView(answer, "answer.commit.error.2");
			} else {
				answerService.delete(answer);

				result = new ModelAndView("redirect:/answer/list.do?questionId=" + answer.getQuestion().getId());
			}
		} catch (Throwable oops) {
			result = createEditModelAndView(answer, "answer.commit.error");
		}
		return result;
	}

	//Ancillary methods
	// =======================================================================

	protected ModelAndView createEditModelAndView(Answer answer) {
		ModelAndView result;

		result = createEditModelAndView(answer, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(Answer answer, String message) {
		ModelAndView result;

		result = new ModelAndView("answer/edit");

		Question question = questionService.findOne(answer.getQuestion().getId());

		long l = 10;
		Date actualDate = new Date(System.currentTimeMillis() - l);

		result.addObject("message", message);
		result.addObject("question", question);
		result.addObject("answer", answer);
		result.addObject("actualDate", actualDate);
		result.addObject("requestURI", "answer/edit.do");

		return result;
	}

}
