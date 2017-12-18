
package services;

import java.util.ArrayList;
import java.util.Collection;

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
import domain.Message;
import domain.Priority;

@Transactional
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
public class MessageServiceTest extends AbstractTest {

	// The SUT
	// ====================================================

	@Autowired
	private FolderService	folderService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private MessageService	messageService;


	// Tests
	// ====================================================

	/*
	 * To check the validity of a message, the system must check its subjects, its body, its priority and its recipients.
	 * 
	 * En este test, se comprueba el titulo del mensaje, el cuerpo, la prioridad y los receptores.
	 * Para ello introducimos valores correctos e incorrectos para observa el comportamiento de la aplicación.
	 */

	/*
	 * Create a message.
	 * 
	 * Para ello introducimos valores correctos e incorrectos para observa el comportamiento de la aplicación
	 */

	public void editMessageTest(final String username, String subject, String body, String priority, Integer actorId, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			Message result;
			Actor actor = actorService.findByPrincipal();
			Actor actorR = actorService.findOne(actorId);
			Assert.notNull(actor);
			Assert.notNull(actorR);
			Assert.notNull(subject);
			Assert.notNull(body);
			result = messageService.create();

			result.setSubject(subject);
			result.setBody(body);
			Collection<Actor> receives = new ArrayList<Actor>();
			receives.add(actorR);
			result.setReceives(receives);
			result.setPriority(Priority.valueOf(priority));
			messageService.sendMessage(result);
			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * Create a message.
	 * 
	 * Para ello introducimos valores correctos e incorrectos para observa el comportamiento de la aplicación
	 */

	public void moveMessageTest(final String username, Integer messageId, Integer folderId, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			Message message;
			Actor actor = actorService.findByPrincipal();
			message = messageService.findOne(messageId);
			Folder folder = folderService.findOne(folderId);
			Assert.notNull(actor);
			Assert.notNull(message);
			Assert.notNull(folder);
			Assert.isTrue(folder.getActor().equals(actor));
			Assert.isTrue(message.getFolder().getActor().equals(actor));

			messageService.moveMessage(folder, message);
			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}

	// Drivers
	// ====================================================

	@Test
	public void driverEditMessageTest() {

		final Object testingData[][] = {
			// Alguien sin registrar/logueado -> false
			{
				null, "SubjectTest", "BodyTest", "HIGH", 8, IllegalArgumentException.class
			},
			// Un actor logueado con todo correcto -> true
			{
				"poller1", "SubjectTest", "BodyTest", "HIGH", 8, null
			},
			// Todo vacio -> false
			{
				null, null, null, null, null, NullPointerException.class
			},
			// Un actor logueado con el titulo del mensaje vacio -> false
			{
				"poller1", null, "BodyTest", "HIGH", 8, IllegalArgumentException.class
			},
			// Un actor logueado con el cuerpo vacío -> false
			{
				"poller1", "SubjectTest", null, "HIGH", 8, IllegalArgumentException.class
			},
			// Un actor logueado con una prioridad inválida -> false
			{
				"poller1", "SubjectTest", "BodyTest", "test", 8, IllegalArgumentException.class
			},

		};
		for (int i = 0; i < testingData.length; i++)
			this.editMessageTest((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Integer) testingData[i][4], (Class<?>) testingData[i][5]);
	}

	@Test
	public void driverMoveMessageTest() {

		final Object testingData[][] = {
			// Alguien sin registrar/logueado -> false
			{
				null, 85, 68, IllegalArgumentException.class
			},
			// Un actor logueado con todo correcto -> true
			{
				"admin", 85, 68, null
			},
			// Todo vacio -> false
			{
				null, null, null, NullPointerException.class
			},
			// Mover a una carpeta ajena -> false
			{
				"admin", 85, 75, IllegalArgumentException.class
			},

		};
		for (int i = 0; i < testingData.length; i++)
			this.moveMessageTest((String) testingData[i][0], (Integer) testingData[i][1], (Integer) testingData[i][2], (Class<?>) testingData[i][3]);
	}

}
