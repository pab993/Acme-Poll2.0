
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import repositories.AnswerRepository;
import domain.Answer;
import domain.Poller;
import domain.Question;

@Service
@Transactional
public class AnswerService {

	//Managed repository

	@Autowired
	private AnswerRepository	answerRepository;

	//Managed Services 

	@Autowired
	private PollerService		pollerService;


	//CRUD methods 

	public Answer create(Question question) {
		Answer result;

		Poller poller = pollerService.findByPrincipal();

		Assert.isTrue(question.getPoll().getPoller().getId() == poller.getId());

		result = new Answer();
		result.setQuestion(question);

		return result;
	}

	public Answer findOne(int answerId) {
		// TODO Auto-generated method stub
		Answer answer = answerRepository.findOne(answerId);
		return answer;
	}

	public Answer save(Answer answer) {
		Assert.notNull(answer);
		Answer answerSaved = answerRepository.save(answer);
		return answerSaved;
	}

	public void delete(Answer answer) {
		Assert.notNull(answer);

		Poller poller = pollerService.findByPrincipal();

		Assert.isTrue(answer.getQuestion().getPoll().getPoller().getId() == poller.getId());

		answerRepository.delete(answer);

	}

	//Other bussiness methods

	public Collection<Answer> findAnswersOfAPoll(int pollId) {
		Collection<Answer> answers;

		answers = answerRepository.findAnswersOfAPoll(pollId);

		return answers;
	}

	public Collection<Answer> findByQuestion(int questionId) {
		// TODO Auto-generated method stub
		Collection<Answer> answers;

		answers = answerRepository.findByQuestion(questionId);

		return answers;
	}

	public Answer reconstruct(Answer answerPruned, BindingResult binding) {
		Answer result;

		if (answerPruned.getId() == 0) {
			result = answerPruned;
			//			validator.validate(result, binding);
		} else {

			Answer res = answerRepository.findOne(answerPruned.getId());
			result = new Answer();

			result.setAns(answerPruned.getAns());
			result.setId(res.getId());
			result.setVersion(res.getVersion());
			result.setQuestion(res.getQuestion());

			//			validator.validate(result, binding);
		}

		return result;
	}

}
