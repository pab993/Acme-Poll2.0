
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Poller;

@Transactional
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
public class PollerServiceTest extends AbstractTest {

	// The SUT
	// ====================================================

	@Autowired
	private PollerService	pollerService;


	// Tests
	// ====================================================

	/*
	 * To check the validity of a new poller in our system, the system must check the username,
	 * the passwords, the name, the surname, the phone, the email and the postal address.
	 * 
	 * En este test, se comprueba el registro de un nuevo encuestador.
	 * Para ello introducimos valores correctos e incorrectos para observa el comportamiento de la aplicación.
	 */

	/*
	 * Register a new poller.
	 * 
	 * En este caso de uso simularemos el registro de un encuestador.
	 */

	public void pollerRegisterTest(String username, String password, String passwordRepeat, String name, String surname, String phoneNumber, String email, String postalAddress, final Class<?> expected) {
		Class<?> caught = null;

		try {

			Poller result = pollerService.create();

			Assert.notNull(username);
			Assert.notNull(password);
			Assert.notNull(passwordRepeat);
			Assert.isTrue(password.equals(passwordRepeat));
			if (phoneNumber != null) {
				Assert.isTrue(phoneNumber.matches("^[+][a-zA-Z]{2}([(][0-9]{1,3}[)])?[0-9]{4,25}$") || phoneNumber.matches("^[0-9]{4,25}$"));
			}
			Assert.notNull(email);
			Assert.notNull(name);
			Assert.notNull(surname);

			result.getUserAccount().setUsername(username);
			result.setName(name);
			result.setSurname(surname);
			result.setPhoneNumber(phoneNumber);
			result.setEmail(email);
			result.setPostalAddress(postalAddress);
			result.getUserAccount().setPassword(new Md5PasswordEncoder().encodePassword(password, null));

			pollerService.save(result);

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}

	//Drivers
	// ===================================================

	@Test
	public void driverPollerRegisterTest() {

		final Object testingData[][] = {
			// Alguien sin registrar/logueado -> true
			{
				"pollerTest", "pollerTest", "pollerTest", "pollerTestName", "pollerTestSurname", "+ES1234456", "customerTest@customerTest.com", "12345", null
			},
			// Todo vacio --> false
			{
				null, null, null, null, null, null, null, null, IllegalArgumentException.class
			},
			// Las contraseñas no coinciden -> false
			{
				"pollerTest", "pollerTest", "notequal", "pollerTestName", "pollerTestSurname", "+ES1234456", "customerTest@customerTest.com", "12345", IllegalArgumentException.class
			},
			// Todos los campos completados, excepto la direccion postal y el teléfono -> true
			{
				"customerTest", "customerTest", "customerTest", "customerTestName", "customerTestSurname", null, "customerTest@customerTest.com", null, null
			},
			// Patrón del telefono erroneo -> false
			{
				"customerTest", "customerTest", "customerTest", "customerTestName", "customerTestSurname", "12x4456", "customerTest@customerTest.com", "12345", IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.pollerRegisterTest((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (Class<?>) testingData[i][8]);
	}

}
