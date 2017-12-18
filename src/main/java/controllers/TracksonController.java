//
// package controllers;
//
// import java.util.Collection;
// import java.util.Date;
//
// import javax.validation.Valid;
//
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.util.Assert;
// import org.springframework.validation.BindingResult;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestMethod;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.servlet.ModelAndView;
//
// import services.ActorService;
// import services.PollService;
// import services.TracksonService;
// import domain.Actor;
// import domain.Poll;
// import domain.Poller;
// import domain.Newor;
//
// @Controller
// @RequestMapping("/trackson")
// public class TracksonController extends AbstractController {
//
// // Services
// // ===============================================================================
//
// @Autowired
// private TracksonService tracksonService;
//
// @Autowired
// private PollService pollService;
//
// // @Autowired
// // private AdministratorService administratorService;
//
// @Autowired
// private ActorService actorService;
//
//
// // Constructor
// // ============================================================================
//
// public TracksonController() {
// super();
// }
//
// // Listing
// // ============================================================================
//
// @RequestMapping(value = "/list", method = RequestMethod.GET)
// public ModelAndView list() {
// ModelAndView result;
//
// final Actor principal = this.actorService.findByPrincipal();
// Assert.notNull(principal);
//
// Date currentMoment;
// currentMoment = new Date(System.currentTimeMillis());
//
// Collection<Newor> tracksons;
//
// tracksons = this.tracksonService.findAll();
//
// result = new ModelAndView("trackson/list");
//
// result.addObject("tracksons", tracksons);
// result.addObject("currentMoment", currentMoment);
// result.addObject("principal", principal);
// // result.addObject("auts", auts);
// // result.addObject("myAuth", myAuth);
//
// result.addObject("requestURI", "trackson/list.do");
//
// return result;
// }
//
// @RequestMapping(value = "/listByPoll", method = RequestMethod.GET)
// public ModelAndView listByEvent(@RequestParam final int pollId) {
// ModelAndView result;
// try {
// Boolean cancel = false;
// Collection<Newor> tracksons;
//
// final Actor principal = this.actorService.findByPrincipal();
// Boolean actorVar = false;
// if (principal instanceof Poller || principal == null)
// actorVar = true;
//
// Date currentMoment;
// currentMoment = new Date(System.currentTimeMillis());
//
// Poll poll = pollService.findOne(pollId);
// Assert.notNull(poll);
//
// tracksons = this.tracksonService.findByPoll2(pollId);
//
// // if (tracksons.isEmpty()) {
// // cancel = true;
// // } else {
// // for (Trackson trackson : tracksons) {
// // Assert.isTrue(trackson.getDisplayMoment().before(currentMoment));
// // break;
// // }
// //
// // }
// Assert.isTrue(!tracksons.isEmpty());
//
// for (Newor trackson : tracksons) {
// Assert.isTrue(trackson.getDisplayMoment().before(currentMoment));
// Newor var = trackson;
// if (var.getCancel() == true) {
// cancel = true;
// }
// break;
// }
//
// result = new ModelAndView("trackson/list");
//
// result.addObject("tracksons", tracksons);
// result.addObject("currentMoment", currentMoment);
// result.addObject("principal", principal);
// result.addObject("actorVar", actorVar);
// result.addObject("cancel", cancel);
// result.addObject("requestURI", "trackson/listByPoll.do");
// } catch (Throwable oops) {
// result = new ModelAndView("redirect:/panic/misc.do");
// }
//
// return result;
// }
//
// //Creating
// // ===========================================================================
//
// @RequestMapping(value = "/create", method = RequestMethod.GET)
// public ModelAndView create(@RequestParam final int pollId) {
// ModelAndView result;
// Newor trackson;
// try {
// final Poll poll = this.pollService.findOne(pollId);
// Assert.notNull(poll);
// Assert.isTrue(poll.getTracksons().isEmpty() || poll.getTracksons() == null);
// trackson = this.tracksonService.create(poll);
//
// result = this.createEditModelAndView(trackson);
// } catch (Throwable oops) {
// result = new ModelAndView("redirect:/panic/misc.do");
// }
//
// return result;
// }
//
// //Editing
// // ===============================================================================
//
// @RequestMapping(value = "/editCancel", method = RequestMethod.GET)
// public ModelAndView edit(@RequestParam final int tracksonId) {
// ModelAndView result;
// Newor trackson;
// try {
// trackson = this.tracksonService.findOne(tracksonId);
// Assert.notNull(trackson);
//
// result = this.createCancelEditModelAndView(trackson);
// } catch (Throwable oops) {
// result = new ModelAndView("redirect:/panic/misc.do");
// }
//
// return result;
// }
//
// @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
// public ModelAndView save(@Valid final Newor trackson, final BindingResult binding) {
// ModelAndView result;
//
// if (binding.hasErrors())
// result = this.createEditModelAndView(trackson);
// else
// try {
//
// this.tracksonService.save(trackson);
// result = new ModelAndView("redirect:/trackson/list.do");
//
// } catch (final Throwable oops) {
// result = this.createEditModelAndView(trackson, "trackson.commit.error");
// }
//
// return result;
// }
//
// @RequestMapping(value = "/editCancel", method = RequestMethod.POST, params = "save")
// public ModelAndView saveCancel(@Valid final Newor trackson, final BindingResult binding) {
// ModelAndView result;
//
// // trackson.setAdministrator(this.administratorService.findByTrackson(trackson));
// tracksonService.checkJustification(trackson, binding);
//
// if (binding.hasErrors())
// result = this.createCancelEditModelAndView(trackson);
// else
// try {
//
// this.tracksonService.cancelTrackson(trackson);
// result = new ModelAndView("redirect:/trackson/list.do");
//
// } catch (final Throwable oops) {
//
// result = this.createCancelEditModelAndView(trackson, "trackson.commit.error");
//
// }
//
// return result;
// }
//
// // Ancillary Methods
// // ===============================================================================
//
// protected ModelAndView createEditModelAndView(final Newor trackson) {
// ModelAndView result;
//
// result = this.createEditModelAndView(trackson, null);
// return result;
// }
//
// protected ModelAndView createEditModelAndView(final Newor trackson, final String message) {
// ModelAndView result;
//
// result = new ModelAndView("trackson/edit");
//
// result.addObject("trackson", trackson);
// result.addObject("message", message);
//
// return result;
// }
//
// protected ModelAndView createCancelEditModelAndView(final Newor trackson) {
// ModelAndView result;
//
// result = this.createCancelEditModelAndView(trackson, null);
// return result;
// }
//
// protected ModelAndView createCancelEditModelAndView(final Newor trackson, final String message) {
// ModelAndView result;
//
// result = new ModelAndView("trackson/editCancel");
//
// result.addObject("trackson", trackson);
// result.addObject("message", message);
//
// return result;
// }
//
//}
