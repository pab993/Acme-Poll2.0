
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import domain.Actor;
import forms.ActorForm;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

	//Services
	// =============================================================================

	@Autowired
	private ActorService	actorService;


	// Constructors
	// ==============================================================================

	public ActorController() {
		super();
	}

	//Profile
	// ==============================================================================

	@RequestMapping(value = "/seeProfile", method = RequestMethod.GET)
	public ModelAndView verProfile(@RequestParam(required = false) final Integer actorId) {
		ModelAndView result;
		Actor principal;

		if (actorId != null) {
			Actor actor = actorService.findOne(actorId);

			if (actor == null) {
				result = new ModelAndView("redirect:/panic/misc.do");
			} else {

				principal = actorService.findByPrincipal();
				result = new ModelAndView("actor/seeProfile");

				result.addObject("principal", principal);
				result.addObject("actor", actor);
			}
		} else {
			principal = actorService.findByPrincipal();
			result = new ModelAndView("redirect:/actor/seeProfile.do?actorId=" + principal.getId());
		}

		return result;
	}

	@RequestMapping(value = "/seeProfileUnregistered", method = RequestMethod.GET)
	public ModelAndView verProfileUnregistred(@RequestParam(required = false) final Integer actorId) {
		ModelAndView result;
		Actor principal;

		if (actorId != null) {
			Actor actor = actorService.findOne(actorId);

			result = new ModelAndView("actor/seeProfile");

			result.addObject("actor", actor);
		} else {
			principal = actorService.findByPrincipal();
			result = new ModelAndView("redirect:/actor/seeProfile.do?actorId=" + principal.getId());
		}

		return result;
	}

	//Edition
	// ================================================================================

	@RequestMapping(value = "/editProfile", method = RequestMethod.GET)
	public ModelAndView editProfile() {
		ModelAndView result;
		Actor principal;

		principal = actorService.findByPrincipal();
		ActorForm actorForm = actorService.reconstructToForm(principal);
		result = new ModelAndView("actor/editProfile");
		result.addObject("actorForm", actorForm);

		return result;
	}

	@RequestMapping(value = "/editProfile", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid ActorForm actorForm, final BindingResult binding) {
		ModelAndView result;
		Actor actor;

		try {
			actor = actorService.reconstruct1(actorForm, binding);

			if (binding.hasErrors())
				result = this.createEditModelAndView(actorForm, "actor.save.error");
			else {
				actor = actorService.reconstruct2(actorForm, binding);
				result = new ModelAndView("redirect:/actor/seeProfile.do");
				actor = this.actorService.save(actor);

			}
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(actorForm, "actor.save.error");
		}

		return result;
	}

	// Ancillary Methods
	// ===============================================================================

	protected ModelAndView createEditModelAndView(ActorForm actorForm) {
		ModelAndView result;

		result = createEditModelAndView(actorForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(ActorForm actorForm, String message) {
		ModelAndView result;
		result = new ModelAndView("actor/editProfile");

		result.addObject("actorForm", actorForm);
		result.addObject("message", message);

		return result;
	}

}
