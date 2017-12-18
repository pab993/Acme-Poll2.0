
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.QuestionRepository;
import domain.Answer;
import domain.Poll;
import domain.Poller;
import domain.Question;

@Service
@Transactional
public class QuestionService {

	//Managed Repository =============================================================================

	@Autowired
	private QuestionRepository	questionRepository;

	//Services
	// ===============================================================================================

	@Autowired
	private AnswerService		answerService;

	@Autowired
	private PollerService		pollerService;

	@Autowired
	private Validator			validator;


	//SCRUDs Methods
	//===============================================================================================

	public Question create(Poll poll) {
		Question result;

		Poller poller = pollerService.findByPrincipal();

		Assert.isTrue(poll.getPoller().getId() == poller.getId());

		result = new Question();
		result.setPoll(poll);

		return result;
	}

	//	public Question save(QuestionForm questionForm) {
	//		Assert.notNull(questionForm);
	//
	//		Poller poller = pollerService.findByPrincipal();
	//		
	//		Assert.isTrue(questionForm.getPoll().getPoller().getId() == poller.getId());
	//		
	//		Question result = questionRepository.save(question);
	//		
	//		for(String answer : questionForm.answers){
	//			Answer res = answerService.create(answer, result);
	//			answerService.save(res);
	//		}
	//
	//		return result;
	//	}

	public void delete(Question question) {
		Assert.notNull(question);

		Poller poller = pollerService.findByPrincipal();

		Assert.isTrue(question.getPoll().getPoller().getId() == poller.getId());

		for (Answer answer : question.getAnswers()) {
			answerService.delete(answer);
		}

		questionRepository.delete(question);

	}

	public Question findOne(int questionId) {
		// TODO Auto-generated method stub
		Question question;

		question = questionRepository.findOne(questionId);

		return question;
	}

	public Collection<Question> findAll() {
		// TODO Auto-generated method stub
		Collection<Question> questions;

		questions = questionRepository.findAll();

		return questions;
	}

	public Collection<Question> findByPoll(int pollId) {
		// TODO Auto-generated method stub
		Collection<Question> questions;
		questions = questionRepository.findByPollId(pollId);
		return questions;
	}

	public Question reconstruct(Question questionPruned, BindingResult binding) {
		Question result;

		if (questionPruned.getId() == 0) {
			result = questionPruned;
			//			validator.validate(result, binding);
		} else {

			Question res = questionRepository.findOne(questionPruned.getId());
			result = new Question();

			result.setStatement(questionPruned.getStatement());
			result.setId(res.getId());
			result.setVersion(res.getVersion());
			result.setPoll(res.getPoll());
			result.setAnswers(res.getAnswers());

			validator.validate(result, binding);
		}

		return result;
	}

	public Question save(Question question) {
		Assert.notNull(question);
		Boolean var = false;

		Poller poller = pollerService.findByPrincipal();

		Assert.isTrue(question.getPoll().getPoller().getId() == poller.getId());
		Question result;

		if (question.getId() == 0) {
			var = true;
		}

		result = questionRepository.save(question);

		if (var) {
			Answer answer1 = new Answer();
			answer1.setAns("Esta es una respuesta por defecto.");
			answer1.setCounter(0);
			answer1.setQuestion(result);

			Answer answer2 = new Answer();
			answer2.setAns("Esta también es una respuesta por defecto.");
			answer2.setCounter(0);
			answer2.setQuestion(result);

			answerService.save(answer1);
			answerService.save(answer2);
		}

		return result;

	}

	public Collection<Object[]> thirstQuery() {
		Collection<Object[]> thirstQuery;

		thirstQuery = questionRepository.thirstQuery();

		return thirstQuery;
	}

}
