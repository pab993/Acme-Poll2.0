
package services;

import java.util.ArrayList;

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
import domain.Instance;
import domain.Poll;
import domain.Question;

@Transactional
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
public class InstanceServiceTest extends AbstractTest {

	// The SUT
	// ====================================================

	@Autowired
	private InstanceService	instanceService;

	@Autowired
	private PollService		pollService;


	// Tests
	// ====================================================

	/*
	 * To check the validity of a instance, the system must check its name, genre and city.
	 * 
	 * En este test, se comprueba el nombre, el género y la ciudad de las encuestas.
	 * Para ello introducimos valores correctos e incorrectos para observa el comportamiento de la aplicación.
	 */

	public void editInstanceTest(String name, String genre, String city, Integer pollId, final Class<?> expected) {
		Class<?> caught = null;

		try {

			Instance result;

			Poll poll = pollService.findOne(pollId);

			Assert.notNull(poll);
			if (genre != null) {
				Assert.isTrue(genre == "MAN" || genre == "WOMAN");
			}
			result = instanceService.create(pollId);

			result.setAnswers(new ArrayList<Answer>());

			for (Question q : poll.getQuestions()) {
				for (Answer a : q.getAnswers()) {
					result.getAnswers().add(a);
					break;
				}
			}

			result.setCity(city);
			result.setGenre(genre);
			result.setName(name);
			instanceService.save(result);

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverEditInstanceTest() {

		final Object testingData[][] = {
			// Alguien que ha rellenado el formulario pero no ha puesto ningún dato como su nombre, ciudad o género -> true
			{
				null, null, null, 9, null
			},
			// Alguien rellena los datos de la instancia pero no es una encuesta -> false
			{
				"Name", "MAN", "City", 33, IllegalArgumentException.class
			},
			// Un género no válido -> false
			{
				"Name", "test", "City", 9, IllegalArgumentException.class
			},

		};
		for (int i = 0; i < testingData.length; i++)
			this.editInstanceTest((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Integer) testingData[i][3], (Class<?>) testingData[i][4]);
	}

}
