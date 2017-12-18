
package services;

import java.util.Calendar;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Newor;
import domain.Poll;

@Transactional
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class NeworServiceTest extends AbstractTest {

	// The SUT
	// ====================================================

	@Autowired
	private NeworService	neworService;

	@Autowired
	private PollService		pollService;


	// Tests
	// ====================================================

	/*
	 * Create a newor.
	 * 
	 * En este caso de uso crearemos una newor sobre un poll y comprobaremos
	 * que solo se puede crear si este no ha pasado.
	 */

	public void createNeworTest(final String username, final int pollId, String name, Date displayMoment, String title, String description, Integer score, String justification, boolean cancel, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {

			this.authenticate(username);

			final Poll poll = this.pollService.findOne(pollId);
			final Newor newor = this.neworService.create(poll);
			//			newor.setName(neworService.codeGenerator());
			newor.setMark(name);
			newor.setTitle(title);
			newor.setDescription(description);
			newor.setJustification(justification);
			newor.setCancel(cancel);

			Assert.isTrue(newor.getTitle().length() >= 1 && newor.getTitle().length() <= 20);
			Assert.isTrue(newor.getDescription().length() >= 1 && newor.getTitle().length() <= 100);

			this.neworService.save(newor);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);

	}

	public void cancelNeworTest(String username, Newor newor, String justification, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {

			this.authenticate(username);

			newor.setJustification(justification);
			Assert.isTrue(newor.getCancel() == false);
			neworService.cancelNewor(newor);

			this.neworService.save(newor);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);

	}

	// Drivers
	// ====================================================

	@Test
	public void driverCreateNeworTest() {

		long l = 10;
		Date actual = new Date(System.currentTimeMillis() - l);
		Calendar lastSearch = Calendar.getInstance();
		lastSearch.setTime(actual);

		final Object testingData[][] = {
			// Creacion correcta, usando un admin y una encuesta valido -> true
			{
				"admin", 13, "uyr453", actual, "titulo", "descripcion", 0, null, false, null
			},
			// Creacion incorrecta, usando un admin y una encuesta no valida -> false		
			{
				"admin", 56, "uur454", actual, "titulo", "descripcion", 0, null, false, IllegalArgumentException.class
			},
			// Creacion correcta con el otro admin -> true
			{
				"matt", 13, "utt493", actual, "titulo", "descripcion", 0, null, false, null
			}, {
				// Si no estamos autentificados -> false
				null, 13, "uyr466", actual, "titulo", "descripcion", 0, null, false, IllegalArgumentException.class
			}, {
				//
				"admin", 13, "uy6453", actual, "titulo", "descripcion", 0, "justificado", true, null
			}, {
				//
				"admin", 13, "6yr453", actual, "titulo", "d", 0, "justificado", true, null
			}, {
				//
				"admin", 13, "uyr773", actual, "titulo1234567890123", "description", 0, null, false, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createNeworTest((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (Date) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Integer) testingData[i][6], (String) testingData[i][7],
				(boolean) testingData[i][8], (Class<?>) testingData[i][9]);
	}

	@Test
	public void driverCancelNeworTest() {

		//		long l = 10;
		//		Date actual = new Date(System.currentTimeMillis() - l);
		//		Calendar lastSearch = Calendar.getInstance();
		//		lastSearch.setTime(actual);

		Newor newor = neworService.findOne(94);

		final Object testingData[][] = {
			// No se puede cancelar sin una justificación
			{
				"admin", newor, "", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.cancelNeworTest((String) testingData[i][0], (Newor) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

}
