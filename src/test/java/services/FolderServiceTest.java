
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
import domain.Folder;

@Transactional
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
public class FolderServiceTest extends AbstractTest {

	// The SUT
	// ====================================================

	@Autowired
	private FolderService	folderService;

	@Autowired
	private ActorService	actorService;


	// Tests
	// ====================================================

	/*
	 * To check the validity of a folder, the system must check its name.
	 * 
	 * En este test, se comprueba el nombre proporcionado por el usuario.
	 * Para ello introducimos valores correctos e incorrectos para observa el comportamiento de la aplicación.
	 */

	/*
	 * Edit/Create a folder.
	 * 
	 * Para ello introducimos valores correctos e incorrectos para observa el comportamiento de la aplicación
	 */

	public void editFolderTest(final String username, String name, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			Folder result;
			Actor actor = actorService.findByPrincipal();
			Assert.notNull(actor);
			result = folderService.create(actor);

			Assert.notNull(name);

			result.setName(name);
			folderService.save(result);
			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}

	// Drivers
	// ====================================================

	@Test
	public void driverEditFolderTest() {

		final Object testingData[][] = {
			// Alguien sin registrar/logueado -> false
			{
				null, "folderName", IllegalArgumentException.class
			},
			// Un encuestador logueado intenta crear una carpeta nueva -> true
			{
				"poller1", "folderName", null
			},
			// Un administrador logueado intenta crear una carpeta nueva -> true
			{
				"admin", "folderName", null
			},
			// Un actor logueado intenta crear una carpeta nueva sin un nombre -> false
			{
				"poller2", null, IllegalArgumentException.class
			},

		};
		for (int i = 0; i < testingData.length; i++)
			this.editFolderTest((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

}
