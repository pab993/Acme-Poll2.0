
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.PollerService;
import domain.Poller;
import forms.PollerForm;

@Controller
@RequestMapping("/poller")
public class PollerController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public PollerController() {
		super();
	}


	//Services
	// ============================================================================

	@Autowired
	private PollerService	pollerService;


	//Edition
	//=============================================================================

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView edit() {

		ModelAndView result;
		result = new ModelAndView("poller/edit");

		result.addObject("pollerForm", new PollerForm());

		return result;
	}

	// Save
	// ====================================================================

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final PollerForm pollerForm, final BindingResult binding) {
		ModelAndView result;
		Poller poller;

		try {
			poller = pollerService.reconstruct(pollerForm, binding);

			if (binding.hasErrors())
				result = this.createEditModelAndView(pollerForm, "poller.save.error");
			else {
				result = new ModelAndView("redirect:/welcome/index.do");
				poller = pollerService.save(poller);

			}
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(pollerForm, "poller.save.error");
		}

		return result;
	}

	// Ancilliary methods
	// =================================================================================================

	private ModelAndView createEditModelAndView(PollerForm pollerForm, String message) {

		ModelAndView resul = new ModelAndView("poller/edit");

		resul.addObject("pollerForm", pollerForm);
		resul.addObject("message", message);
		return resul;
	}

}
