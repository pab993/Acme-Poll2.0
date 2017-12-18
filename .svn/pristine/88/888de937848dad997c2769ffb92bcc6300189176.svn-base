
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.SpamService;
import domain.Spam;

@Controller
@RequestMapping("/spam")
public class SpamController extends AbstractController {

	//Services =======================================================================

	@Autowired
	private SpamService				spamService;

	@Autowired
	private AdministratorService	administratorService;


	//Listing ========================================================================

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Spam> spams;

		spams = spamService.findAll();

		result = new ModelAndView("spam/list");
		result.addObject("spams", spams);
		result.addObject("requestURI", "spam/list.do");

		return result;
	}

	//Creating
	// ===========================================================================

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		try {
			Spam spam = spamService.create();
			result = this.createEditModelAndView(spam);

		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/panic/misc.do");
		}

		return result;
	}

	//Edit ==============================================================================

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int spamId) {
		ModelAndView result;

		try {
			Spam spam = spamService.findOne(spamId);

			if (spam == null) {

				result = new ModelAndView("redirect:/panic/misc.do");

			} else {

				result = this.createEditModelAndView(spam);

			}
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/panic/misc.do");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Spam spam, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = createEditModelAndView(spam);
		} else {
			try {

				spamService.save(spam);
				result = new ModelAndView("redirect:/spam/list.do");

			} catch (Throwable oops) {

				result = createEditModelAndView(spam, "spam.commit.error");
			}
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Spam spam, BindingResult binding) {
		ModelAndView result;

		Spam spamF = spamService.findOne(spam.getId());

		try {

			spamService.delete(spamF);

			result = new ModelAndView("redirect:/spam/list.do");

		} catch (Throwable oops) {
			result = createEditModelAndView(spam, "spam.commit.error");
		}
		return result;
	}

	//Ancillary methods
	// =======================================================================

	protected ModelAndView createEditModelAndView(Spam spam) {
		ModelAndView result;

		result = createEditModelAndView(spam, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(Spam spam, String message) {
		ModelAndView result;

		result = new ModelAndView("spam/edit");

		result.addObject("message", message);
		result.addObject("spam", spam);
		result.addObject("requestURI", "spam/edit.do");

		return result;
	}

}
