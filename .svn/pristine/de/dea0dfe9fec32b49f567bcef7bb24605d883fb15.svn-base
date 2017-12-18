
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
import domain.Actor;

@Transactional
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
public class ActorServiceTest extends AbstractTest {

	// The SUT
	// ====================================================

	@Autowired
	private ActorService	actorService;


	// Tests
	// ====================================================

	/*
	 * To check the validity of a profile of an actor in our system, the system must check the name, the surname, the phone, the email and the postal address.
	 * 
	 * En este test, se comprueba la edición del perfil de un actor.
	 * Para ello introducimos valores correctos e incorrectos para observa el comportamiento de la aplicación.
	 */

	/*
	 * Edit a profile of an actor.
	 * 
	 * En este caso de uso simularemos la edición de un perfil.
	 */

	public void editProfileTest(Actor actor, String name, String surname, String phoneNumber, String email, String postalAddress, final Class<?> expected) {
		Class<?> caught = null;

		try {

			authenticate(actor.getUserAccount().getUsername());
			Actor result = actor;
			if (phoneNumber != null) {
				Assert.isTrue(phoneNumber.matches("^[+][a-zA-Z]{2}([(][0-9]{1,3}[)])?[0-9]{4,25}$") || phoneNumber.matches("^[0-9]{4,25}$"));
			}
			Assert.notNull(email);
			Assert.notNull(name);
			Assert.notNull(surname);

			result.setName(name);
			result.setSurname(surname);
			result.setPhoneNumber(phoneNumber);
			result.setEmail(email);
			result.setPostalAddress(postalAddress);

			actorService.save(result);

			unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}

	//Drivers
	// ===================================================

	@Test
	public void driverEditProfileTest() {

		Actor actor = actorService.findOne(7);

		final Object testingData[][] = {
			// Alguien sin registrar/logueado -> true
			{
				actor, "pollerTestName", "pollerTestSurname", "+ES1234456", "customerTest@customerTest.com", "12345", null
			},
			// Todo vacio --> false
			{
				actor, null, null, null, null, null, IllegalArgumentException.class
			},
			// Todos los campos completados, excepto la direccion postal y el teléfono -> true
			{
				actor, "customerTestName", "customerTestSurname", null, "customerTest@customerTest.com", null, null
			},
			// Patrón del telefono erroneo -> false
			{
				actor, "customerTestName", "customerTestSurname", "12x4456", "customerTest@customerTest.com", "12345", IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.editProfileTest((Actor) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Class<?>) testingData[i][6]);
	}
}
