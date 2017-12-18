
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
import domain.Poll;
import domain.Poller;
import domain.Question;

@Transactional
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
public class QuestionServiceTest extends AbstractTest {

	// The SUT
	// ====================================================

	@Autowired
	private PollService		pollService;

	@Autowired
	private QuestionService	questionService;

	@Autowired
	private PollerService	pollerService;


	// Tests
	// ====================================================

	/*
	 * To check the validity of a question, the system must check its statement.
	 * 
	 * En este test, se comprueba el texto de la pregunta.
	 * Para ello introducimos valores correctos e incorrectos para observa el comportamiento de la aplicación.
	 */

	public void editQuestionTest(final String username, String statement, Integer pollId, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			Question result;
			Poll poll = pollService.findOne(pollId);
			Assert.notNull(poll);
			result = questionService.create(poll);

			Poller poller = pollerService.findByPrincipal();

			Assert.isTrue(poller.equals(result.getPoll().getPoller()));
			Assert.notNull(statement);

			result.setStatement(statement);
			questionService.save(result);
			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}
	/*
	 * Browse the list of questions of a poll.
	 * 
	 * En este caso de uso se comprobamos que sólo los encuestadores pueden listar las preguntas de las encuestas que existen en nuestra aplicación.
	 */

	public void listOfQuestionsTest(final String username, Integer pollId, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			Poller poller = pollerService.findByPrincipal();

			Poll poll = pollService.findOne(pollId);

			Assert.notNull(poll);
			Assert.isTrue(poller.equals(poll.getPoller()));

			for (Question s : poll.getQuestions()) {
				System.out.print(s.getStatement());
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
	public void driverListOfQuestionsTest() {

		//Poll id = 12 es la poll4
		final Object testingData[][] = {
			// Alguien sin registrar/logueado -> false
			{
				null, 12, NullPointerException.class
			},
			// Un Encuestador que es dueño de dicha encuesta -> true
			{
				"poller1", 12, null
			},
			// Un encuestador que no es dueño de dicha encuesta -> false
			{
				"poller1", 14, IllegalArgumentException.class
			},
			// Listar las respuesta de una encuesta que no existe ->false
			{
				"poller1", 234, IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.listOfQuestionsTest((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void driverEditQuestionTest() {

		final Object testingData[][] = {
			// Alguien sin registrar/logueado -> false
			{
				null, "Nueva pregunta", 12, NullPointerException.class
			},
			// Un encuestador logueado y que sea suya -> true
			{
				"poller1", "Nueva pregunta", 12, null
			},
			// Todo vacio -> false
			{
				null, null, null, NullPointerException.class
			},
			// Un encuestador que intente crear una respuesta de una pregunta que no le pertenece -> true
			{
				"poller2", "Nueva pregunta", 12, IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.editQuestionTest((String) testingData[i][0], (String) testingData[i][1], (Integer) testingData[i][2], (Class<?>) testingData[i][3]);
	}

}
