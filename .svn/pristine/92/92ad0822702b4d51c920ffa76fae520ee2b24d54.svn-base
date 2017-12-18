/*
 * AdministratorController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.InstanceService;
import services.PollService;
import services.PollerService;
import services.QuestionService;
import domain.Poller;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}


	// Services ---------------------------------------------------------------

	@Autowired
	private PollerService	pollerService;

	@Autowired
	private PollService		pollService;

	@Autowired
	private InstanceService	instanceService;

	@Autowired
	private QuestionService	questionService;


	@RequestMapping(value = "/ban", method = RequestMethod.GET)
	public ModelAndView ban(@RequestParam int pollerId) {
		ModelAndView resul;
		try {
			pollerService.ban(pollerId);
			resul = new ModelAndView("redirect:/administrator/listOfPollers.do");
		} catch (Throwable exception) {
			resul = new ModelAndView("redirect:/administrator/listOfPollers.do");
		}

		return resul;
	}

	@RequestMapping(value = "/listOfPollers", method = RequestMethod.GET)
	public ModelAndView listManagers() {
		ModelAndView result;
		Collection<Poller> pollers;

		pollers = pollerService.findAll();

		result = new ModelAndView("administrator/listOfPollers");

		result.addObject("requestURI", "administrator/listOfManagers.do");
		result.addObject("pollers", pollers);

		return result;
	}

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;

		Collection<Object[]> firstQuery = new ArrayList<Object[]>();
		Collection<Object[]> secondQuery = new ArrayList<Object[]>();
		Collection<Object[]> thirstQuery = new ArrayList<Object[]>();
		//		Collection<Gym> forthQuery = new ArrayList<Gym>();
		//		Collection<Customer> fifthQuery = new ArrayList<Customer>();
		//		Collection<Object[]> sixthQuery = new ArrayList<Object[]>();
		//		Collection<Object[]> seventhQuery = new ArrayList<Object[]>();
		//		Collection<Workout> eighthQuery = new ArrayList<Workout>();

		firstQuery = pollService.firstQuery();
		secondQuery = instanceService.secondQuery();
		thirstQuery = questionService.thirstQuery();
		//		forthQuery = gymService.forthQuery();
		//		fifthQuery = customerService.fifthQuery();
		//		sixthQuery = workoutService.sixthQuery();
		//		seventhQuery = stepService.seventhQuery();
		//		eighthQuery = workoutService.eighthQuery();

		result = new ModelAndView("administrator/dashboard");
		result.addObject("firstQuery", firstQuery);
		result.addObject("secondQuery", secondQuery);
		result.addObject("thirstQuery", thirstQuery);
		//		result.addObject("forthQuery", forthQuery);
		//		result.addObject("fifthQuery", fifthQuery);
		//		result.addObject("sixthQuery", sixthQuery);
		//		result.addObject("seventhQuery", seventhQuery);
		//		result.addObject("eighthQuery", eighthQuery);

		result.addObject("requestURI", "administrator/dashboard.do");

		return result;
	}

}
