
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Answer;
import domain.Poller;
import domain.Question;

@Transactional
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
public class AnswerServiceTest extends AbstractTest {

	// The SUT
	// ====================================================

	@Autowired
	private AnswerService	answerService;

	@Autowired
	private QuestionService	questionService;

	@Autowired
	private PollerService	pollerService;


	// Tests
	// ====================================================

	/*
	 * To check the validity of a answer, the system must check its answer (attribute ans).
	 * 
	 * En este test, se comprueba el texto de la respuesta de una pregunta.
	 * Para ello introducimos valores correctos e incorrectos para observa el comportamiento de la aplicación.
	 */

	public void editAnswerTest(final String username, String ans, Integer questionId, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			Answer result;
			Question question = questionService.findOne(questionId);
			Assert.notNull(question);
			result = answerService.create(question);

			Poller poller = pollerService.findByPrincipal();

			Assert.isTrue(poller.equals(result.getQuestion().getPoll().getPoller()));
			Assert.notNull(ans);

			result.setAns(ans);
			answerService.save(result);
			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}
	/*
	 * Browse the list of answers of a question.
	 * 
	 * En este caso de uso se comprobamos que sólo los encuestadores pueden listar las respuestas de las preguntas que existen en nuestra aplicación.
	 */

	public void listOfAnswersTest(final String username, Integer questionId, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			Poller poller = pollerService.findByPrincipal();

			Question question = questionService.findOne(questionId);

			Assert.notNull(question);
			Assert.isTrue(poller.equals(question.getPoll().getPoller()));

			for (Answer s : question.getAnswers()) {
				System.out.print(s.getAns());
			}

			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}

	//Drivers
	// ===================================================

	@Test
	public void driverListOfAnswersTest() {

		final Object testingData[][] = {
			// Alguien sin registrar/logueado -> false
			{
				null, 20, NullPointerException.class
			},
			// Un Encuestador que es dueño de dicha pregunta -> true
			{
				"poller2", 20, null
			},
			// Un encuestador que no es dueño de dicha pregunta -> false
			{
				"poller1", 20, IllegalArgumentException.class
			},
			// Listar las respuesta de una pregunta que no existe ->false
			{
				"poller1", 234, IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.listOfAnswersTest((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void driverEditAnswerTest() {

		final Object testingData[][] = {
			// Alguien sin registrar/logueado -> false
			{
				null, "Nueva respuesta", 20, NullPointerException.class
			},
			// Un encuestador logueado y que sea suya -> true
			{
				"poller2", "Nueva respuesta", 20, null
			},
			// Todo vacio -> false
			{
				null, null, null, NullPointerException.class
			},
			// Un encuestador que intente crear una respuesta de una pregunta que no le pertenece -> true
			{
				"poller2", "Nueva respuesta", 24, IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.editAnswerTest((String) testingData[i][0], (String) testingData[i][1], (Integer) testingData[i][2], (Class<?>) testingData[i][3]);
	}
}
