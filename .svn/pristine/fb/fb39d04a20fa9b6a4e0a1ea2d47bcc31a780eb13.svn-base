
package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

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

@Transactional
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
public class PollServiceTest extends AbstractTest {

	// The SUT
	// ====================================================

	@Autowired
	private PollerService	pollerService;

	@Autowired
	private PollService		pollService;


	// Tests
	// ====================================================

	/*
	 * To check the validity of a poll, the system must check its title, description, banner, start active date, end active date and its ticker.
	 * 
	 * En este test, se comprueba el título, la descripción, la imagen(banner), el ticker y la fecha inicial y final activa de la encuesta.
	 * Para ello introducimos valores correctos e incorrectos para observa el comportamiento de la aplicación.
	 */

	public void editPollTest(String username, String title, String description, String banner, Date startActive, Date endActive, Integer pollId, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			final long l = 10;
			final Date actual = new Date(System.currentTimeMillis() - l);

			Poll result = pollService.findOne(pollId);
			Assert.notNull(result);

			Poller poller = pollerService.findByPrincipal();

			Assert.isTrue(poller.equals(result.getPoller()));
			Assert.notNull(title);
			Assert.notNull(description);
			Assert.notNull(banner);
			Assert.notNull(startActive);
			Assert.notNull(endActive);
			Assert.isTrue(startActive.before(endActive));
			Assert.isTrue(actual.before(startActive) && actual.before(endActive));

			result.setTitle(title);
			result.setDescription(description);
			result.setBanner(banner);
			result.setStartActive(startActive);
			result.setEndActive(endActive);
			pollService.save(result);
			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * Browse the list of polls.
	 * 
	 * En este caso de uso se comprobamos que cualquiera puede ver las encuestas disponibles.
	 */

	public void listOfPollsTest(final String username, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			Collection<Poll> polls = pollService.findOnlyActives();

			for (Poll p : polls) {
				System.out.print(p.getTitle());
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
	public void driverListOfPollsTest() {

		final Object testingData[][] = {
			// Alguien sin registrar/logueado -> true
			{
				null, null
			},
			// Un Encuestador que es dueño de dicha encuesta -> true
			{
				"poller1", null
			},
			// Un administrador -> true
			{
				"admin", null
			},

		};
		for (int i = 0; i < testingData.length; i++)
			this.listOfPollsTest((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	@Test
	public void driverEditPollTest() {

		final long l = 10;
		final Date actual = new Date(System.currentTimeMillis() - l);
		//Fecha futura (8 dias desde la fecha actual)
		final Calendar future8Date = Calendar.getInstance();
		future8Date.setTime(actual);
		future8Date.add(Calendar.DAY_OF_YEAR, 8);

		//Fecha futura (15 dias desde la fecha actual)
		final Calendar future15Date = Calendar.getInstance();
		future15Date.setTime(actual);
		future15Date.add(Calendar.DAY_OF_YEAR, 15);

		//Fecha pasada (15 dias menos a la fecha actual)
		final Calendar past8Date = Calendar.getInstance();
		past8Date.setTime(actual);
		past8Date.add(Calendar.DAY_OF_YEAR, -8);

		//Fecha pasada (15 dias menos a la fecha actual)
		final Calendar past15Date = Calendar.getInstance();
		past15Date.setTime(actual);
		past15Date.add(Calendar.DAY_OF_YEAR, -15);

		final Object testingData[][] = {
			// Alguien sin registrar/logueado -> false
			{
				null, "title", "description", "http://www.picture.com", future8Date.getTime(), future15Date.getTime(), 11, NullPointerException.class
			},
			// Un encuestador logueado y que sea suya -> true
			{
				"poller1", "title", "description", "http://www.picture.com", future8Date.getTime(), future15Date.getTime(), 11, null
			},
			// Todo vacio -> false
			{
				null, null, null, null, null, null, null, NullPointerException.class
			},
			// Un encuestador que intente editar una encuesta que no le pertenece -> false
			{
				"poller2", "title", "description", "http://www.picture.com", future8Date.getTime(), future15Date.getTime(), 11, IllegalArgumentException.class
			},
			// Un encuestador introduce una fecha activa final anterior a la fecha activa inicial --> false
			{
				"poller1", "title", "description", "http://www.picture.com", future15Date.getTime(), future8Date.getTime(), 11, IllegalArgumentException.class
			},
			// Un administrador intenta editar una encuesta ajena --> false
			{
				"admin", "title", "description", "http://www.picture.com", future8Date.getTime(), future15Date.getTime(), 11, NullPointerException.class
			},
			// Un poller intenta introducir una fecha anterior a la actual en la fecha activa inicial --> false
			{
				"poller1", "title", "description", "http://www.picture.com", past8Date.getTime(), future8Date.getTime(), 11, IllegalArgumentException.class
			},
			// Un poller intenta introducir una fecha anterior a la actual en la fecha activa final --> false
			{
				"poller1", "title", "description", "http://www.picture.com", past8Date.getTime(), past15Date.getTime(), 11, IllegalArgumentException.class
			},
			// Un poller intenta editar una encuesta introduciendo una id que no es una encuesta --> false
			{
				"poller1", "title", "description", "http://www.picture.com", future8Date.getTime(), future15Date.getTime(), 110, IllegalArgumentException.class
			},
			// Un poller intenta edita una encuesta dejando uno de los campo obligatorios vacio --> false
			{
				"poller1", null, "description", "http://www.picture.com", future8Date.getTime(), future15Date.getTime(), 110, IllegalArgumentException.class
			},
			// Un poller intenta edita una encuesta dejando uno de los campo obligatorios vacio --> false
			{
				"poller1", "title", null, "http://www.picture.com", future8Date.getTime(), future15Date.getTime(), 110, IllegalArgumentException.class
			},
			// Un poller intenta edita una encuesta dejando uno de los campo obligatorios vacio --> false
			{
				"poller1", "title", "description", null, future8Date.getTime(), future15Date.getTime(), 110, IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.editPollTest((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Date) testingData[i][4], (Date) testingData[i][5], (Integer) testingData[i][6], (Class<?>) testingData[i][7]);
	}
}
