
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
import domain.ConfigurationSystem;

@Transactional
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
public class ConfigurationSystemServiceTest extends AbstractTest {

	// The SUT
	// ====================================================

	@Autowired
	private ConfigurationSystemService	configurationSystemService;


	// Tests
	// ====================================================

	/*
	 * In this test, we are going to check the validity of the minimum period active of the polls in the system.
	 * 
	 * En este test, se comprueba el mínimo periodo activo de las encuestas en el sistema
	 * Para ello introducimos valores correctos e incorrectos para observa el comportamiento de la aplicación.
	 */

	/*
	 * Edit the configuration system.
	 * 
	 * En este caso de uso simularemos la edición de la configuración del sistema.
	 */

	public void editCSTest(String username, Integer minimum, final Class<?> expected) {
		Class<?> caught = null;

		try {

			authenticate(username);
			ConfigurationSystem cs = configurationSystemService.getCS();

			Assert.notNull(minimum);
			Assert.isTrue(minimum >= 0);

			cs.setMinimumPeriodActive(minimum);

			configurationSystemService.save(cs);

			unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}

	//Drivers
	// ===================================================

	@Test
	public void driverEditCSTest() {

		final Object testingData[][] = {
			// Alguien sin registrar/logueado -> false
			{
				"", 5, IllegalArgumentException.class
			},
			// Periodo mínimo vacío--> false
			{
				"admin", null, IllegalArgumentException.class
			},
			// Todos los campos completados perfectamente -> true
			{
				"admin", 5, null
			},
			// Periodo mínimo negativo -> false
			{
				"admin", -2, IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.editCSTest((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}

}
