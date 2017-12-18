
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

import repositories.PollRepository;
import domain.Actor;
import domain.Answer;
import domain.ConfigurationSystem;
import domain.Instance;
import domain.Poll;
import domain.Poller;
import domain.Question;
import forms.PollForm;

@Service
@Transactional
public class PollService {

	//Managed Repository =============================================================================

	@Autowired
	private PollRepository				pollRepository;

	//Services =======================================================================================

	@Autowired
	private PollerService				pollerService;

	@Autowired
	private AnswerService				answerService;

	@Autowired
	private QuestionService				questionService;

	@Autowired
	private InstanceService				instanceService;

	@Autowired
	private ActorService				actorService;

	@Autowired
	private Validator					validator;

	@Autowired
	private ConfigurationSystemService	configurationSystemService;


	//Simple CRUD methods ============================================================================

	public Poll findOne(int id) {
		Poll poll;

		poll = pollRepository.findOne(id);

		return poll;
	}

	public Collection<Poll> findAll() {
		// TODO Auto-generated method stub
		Collection<Poll> polls;

		polls = pollRepository.findAll();

		return polls;
	}

	public Poll create() {
		Poll result;

		Poller poller = pollerService.findByPrincipal();

		Assert.isTrue(poller != null);

		result = new Poll();

		Collection<Question> questions = new ArrayList<Question>();
		Collection<Instance> instances = new ArrayList<Instance>();
		Collection<Actor> actors = new ArrayList<Actor>();
		result.setQuestions(questions);
		result.setActors(actors);
		result.setInstances(instances);
		result.setPoller(poller);
		result.setTicker(codeGenerator());

		return result;
	}

	public Poll save(Poll poll) {
		Assert.notNull(poll);
		Boolean var = false;

		Poller poller = pollerService.findByPrincipal();

		Assert.isTrue(poll.getPoller().getId() == poller.getId());
		Poll result;

		if (poll.getId() == 0) {
			var = true;
		}

		result = pollRepository.save(poll);

		if (var) {
			Question question1 = new Question();
			question1.setStatement("Esta es una pregunta por defecto");
			question1.setPoll(result);

			questionService.save(question1);
		}

		return result;
	}

	public Poll save2(Poll poll) {
		Assert.notNull(poll);

		Poll result = pollRepository.save(poll);

		return result;
	}

	public void delete(Poll poll) {
		Assert.notNull(poll);

		Poller poller = pollerService.findByPrincipal();

		Assert.isTrue(poll.getPoller().getId() == poller.getId());

		for (Question question : poll.getQuestions()) {
			questionService.delete(question);
		}

		for (Instance instance : poll.getInstances()) {
			instanceService.delete(instance);
		}

		pollRepository.delete(poll);
	}

	//Other bussiness methods ===========================================================================================

	public String codeGenerator() {
		String result = "";
		final String pattern = "abcdefghijklmn�opqrstuvwxyzABCDEFGHIJKLMN�OPQRSTUVWXYZ"; //54
		final String pattern2 = "0123456789";
		final Random rnd = new Random();
		//		final int nRnd = TracksonService.generaNumeroAleatorio(3);
		//		final int nRnd2 = TracksonService.generaNumeroAleatorio(3);

		for (int i = 0; i < 2; i++)
			result += pattern.charAt(rnd.nextInt(pattern.length()));

		result += "-";

		for (int i = 0; i < 5; i++)
			result += pattern2.charAt(rnd.nextInt(pattern2.length()));

		Poll poll = pollRepository.findByCode(result);

		if (poll != null) {
			result = "";
			result = this.codeGenerator();
		}

		return result;
	}

	public Collection<Poll> findAllMyPolls(int pollerId) {
		Collection<Poll> polls;
		polls = pollRepository.findAllMyPolls(pollerId);

		return polls;

	}

	public void savePollAnswered(PollForm pollForm) {
		// TODO Auto-generated method stub

		Actor actor = actorService.findByPrincipal();
		Poll poll = pollRepository.findOne(pollForm.getPollId());

		if (actor != null) {
			poll.getActors().add(actor);
			pollRepository.save(poll);
		}

		for (Answer answer : pollForm.getListOfAnswers()) {

			answer.setCounter(answer.getCounter() + 1);
			answerService.save(answer);

		}

		Instance instance = instanceService.create(pollForm.getPollId());
		instance.setName(pollForm.getName());
		instance.setGenre(pollForm.getGenre());
		instance.setCity(pollForm.getCity());
		instance.setAnswers(pollForm.getListOfAnswers());

		instanceService.save(instance);

	}

	public Poll reconstruct(Poll poll, BindingResult binding) {
		Poll result;

		Poller poller = pollerService.findByPrincipal();

		if (poll.getId() == 0) {
			result = poll;
			result.setPoller(poller);

			if (poll.getEndActive() != null && poll.getStartActive() != null) {

				checkEndAndStart(poll.getEndActive(), poll.getStartActive(), binding);
			}
			if (poll.getEndActive() != null) {
				checkEndAndActual(poll.getEndActive(), binding);
			}
			if (poll.getStartActive() != null) {
				checkStartAndActual(poll.getStartActive(), binding);
			}

			//			if (!check) {
			//				checkEndAndMin(poll.getEndActive(), poll.getStartActive(), configurationSystemService.getCS().getMinimumPeriodActive(), binding);
			//			}

			validator.validate(result, binding);
		} else {
			Poll res = pollRepository.findOne(poll.getId());
			result = poll;
			result.setTitle(poll.getTitle());
			result.setDescription(poll.getDescription());
			result.setBanner(poll.getBanner());
			result.setEndActive(poll.getEndActive());
			result.setStartActive(poll.getStartActive());

			result.setQuestions(res.getQuestions());
			result.setActors(res.getActors());
			result.setInstances(res.getInstances());
			result.setPoller(poller);
			result.setTicker(res.getTicker());

			if (poll.getEndActive() != null && poll.getStartActive() != null) {

				checkEndAndStart(poll.getEndActive(), poll.getStartActive(), binding);
			}
			if (poll.getEndActive() != null) {
				checkEndAndActual(poll.getEndActive(), binding);
			}
			if (poll.getStartActive() != null) {
				checkStartAndActual(poll.getStartActive(), binding);
			}

			//			if (!check) {
			//				checkEndAndMin(poll.getEndActive(), poll.getStartActive(), configurationSystemService.getCS().getMinimumPeriodActive(), binding);
			//			}

			validator.validate(result, binding);
		}

		return result;
	}

	private boolean checkEndAndStart(Date endActive, Date startActive, BindingResult binding) {
		FieldError error;
		String[] codigos;
		boolean result = false;

		if (endActive.before(startActive)) {
			result = true;
		}

		if (result) {
			codigos = new String[1];
			codigos[0] = "poll.dates.inverted.1";
			error = new FieldError("poll", "startActive", startActive, false, codigos, null, "");
			binding.addError(error);

			codigos = new String[1];
			codigos[0] = "poll.dates.inverted.2";
			error = new FieldError("poll", "endActive", endActive, false, codigos, null, "");
			binding.addError(error);
		}

		//		if (startActive.before(actualDate)) {
		//			codigos = new String[1];
		//			codigos[0] = "poll.startActive.future";
		//			error = new FieldError("poll", "startActive", startActive, false, codigos, null, "");
		//			binding.addError(error);
		//		}

		return result;
	}

	private boolean checkEndAndActual(Date endActive, BindingResult binding) {
		FieldError error;
		String[] codigos;
		boolean result = false;

		long l = 10;
		Date actualDate = new Date(System.currentTimeMillis() - l);

		if (endActive.before(actualDate)) {
			codigos = new String[1];
			codigos[0] = "poll.endActive.future";
			error = new FieldError("poll", "endActive", endActive, false, codigos, null, "");
			binding.addError(error);
		}

		return result;
	}

	private boolean checkStartAndActual(Date startActive, BindingResult binding) {
		FieldError error;
		String[] codigos;
		boolean result = false;

		long l = 10;
		Date actualDate = new Date(System.currentTimeMillis() - l);

		if (startActive.before(actualDate)) {
			codigos = new String[1];
			codigos[0] = "poll.startActive.future";
			error = new FieldError("poll", "startActive", startActive, false, codigos, null, "");
			binding.addError(error);
		}

		return result;
	}

	public Collection<Poll> pollsByKeyWord(String keyword) {
		Collection<Poll> polls;

		ConfigurationSystem cs = configurationSystemService.getCS();

		polls = pollRepository.findPollByKeyword(keyword);

		Collection<Poll> pollsFiltered = new ArrayList<Poll>();

		for (Poll poll : polls) {
			if ((poll.getEndActive().getTime() - poll.getStartActive().getTime()) / (1000 * 60 * 60 * 24) >= cs.getMinimumPeriodActive()) {
				pollsFiltered.add(poll);
			}
		}

		return pollsFiltered;

	}

	public Collection<Poll> findOnlyActives() {
		Collection<Poll> polls;

		polls = pollRepository.findOnlyActives();

		ConfigurationSystem cs = configurationSystemService.getCS();

		Collection<Poll> pollsFiltered = new ArrayList<Poll>();

		for (Poll poll : polls) {
			if ((poll.getEndActive().getTime() - poll.getStartActive().getTime()) / (1000 * 60 * 60 * 24) >= cs.getMinimumPeriodActive()) {
				pollsFiltered.add(poll);
			}
		}

		return pollsFiltered;
	}
	public PollForm reconstruct(PollForm pollForm, BindingResult binding) {
		// TODO Auto-generated method stub

		PollForm pollFormValid = pollForm;

		validator.validate(pollFormValid, binding);

		return pollFormValid;
	}

	public Collection<Object[]> firstQuery() {
		Collection<Object[]> firstQuery;

		firstQuery = pollRepository.firstQuery();

		return firstQuery;
	}

}
