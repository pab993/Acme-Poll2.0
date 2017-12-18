
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
import domain.Administrator;
import domain.Bill;
import domain.Poll;
import domain.Poller;

@Transactional
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
public class BillServiceTest extends AbstractTest {

	// The SUT
	// ====================================================

	@Autowired
	private BillService				billService;

	@Autowired
	private PollerService			pollerService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private PollService				pollService;


	// Tests
	// ====================================================

	/*
	 * To check the validity of a bill, the system must check its amount due, its moment due and if it's paid.
	 * 
	 * En este test, se comprueba la cantidad debida, el momento de pago y si la factura está pagada.
	 * Para ello introducimos valores correctos e incorrectos para observa el comportamiento de la aplicación.
	 */

	/*
	 * Browse the list of bills of a poller.
	 * 
	 * En este caso de uso se comprobamos los encuestadores pueden listar sus facturas de las encuestas que existen en nuestra aplicación.
	 */

	public void listOfBillsOfPollerTest(final String username, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			Poller poller = pollerService.findByPrincipal();

			Assert.notNull(poller);

			Collection<Bill> bills = billService.findBillsByPoller(poller.getId());

			for (Bill b : bills) {
				System.out.print(b);
			}

			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * Browse the list of bills endorsed in the system.
	 * 
	 * En este caso de uso se comprobamos los las facturas endorsadas que existen en nuestra aplicación.
	 */

	public void listOfBillsEndorsedTest(final String username, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			Administrator administrator = administratorService.findByPrincipal();

			Assert.notNull(administrator);

			Collection<Bill> bills = billService.findBillsEndorsed();

			for (Bill b : bills) {
				System.out.print(b);
			}

			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * Browse the list of bills not endorsed in the system.
	 * 
	 * En este caso de uso se comprobamos los las facturas no endorsadas que existen en nuestra aplicación.
	 */

	public void listOfBillsNotEndorsedTest(final String username, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			Administrator administrator = administratorService.findByPrincipal();

			Assert.notNull(administrator);

			Collection<Bill> bills = billService.findBillsNotEndorsed();

			for (Bill b : bills) {
				System.out.print(b);
			}

			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * Create a bill of a poll of the system.
	 * 
	 * Para ello introducimos valores correctos e incorrectos para observa el comportamiento de la aplicación
	 */

	public void createBillTest(final String username, Double amountDue, Date momentDue, Integer pollId, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			Bill result;
			Poll poll = pollService.findOne(pollId);
			Assert.notNull(poll);
			Assert.isTrue(poll.getBill() == null);
			result = billService.create(poll);

			Administrator administrator = administratorService.findByPrincipal();

			Assert.notNull(administrator);
			Assert.notNull(amountDue);
			Assert.isTrue(amountDue >= 1.0);
			Assert.notNull(momentDue);

			final long l = 10;
			final Date actual = new Date(System.currentTimeMillis() - l);

			Assert.isTrue(momentDue.after(actual));

			result.setAmountDue(amountDue);
			result.setMomentDue(momentDue);
			billService.save(result);
			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}

	//Drivers
	// ===================================================

	@Test
	public void driverListOfBillsTest() {

		final Object testingData[][] = {
			// Alguien sin registrar/logueado -> false
			{
				null, IllegalArgumentException.class
			},
			// Un Encuestador -> true
			{
				"poller1", null
			},
			// Un administrador -> false
			{
				"admin", IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.listOfBillsOfPollerTest((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	@Test
	public void driverListOfBillsEndorsedTest() {

		final Object testingData[][] = {
			// Alguien sin registrar/logueado -> false
			{
				null, IllegalArgumentException.class
			},
			// Un Encuestador -> false
			{
				"poller1", IllegalArgumentException.class
			},
			// Un administrador -> true
			{
				"admin", null
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.listOfBillsEndorsedTest((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	@Test
	public void driverListOfBillsNotEndorsedTest() {

		final Object testingData[][] = {
			// Alguien sin registrar/logueado -> false
			{
				null, IllegalArgumentException.class
			},
			// Un Encuestador -> false
			{
				"poller1", IllegalArgumentException.class
			},
			// Un administrador -> true
			{
				"admin", null
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.listOfBillsNotEndorsedTest((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	@Test
	public void driverCreateBillTest() {

		final long l = 10;
		final Date actual = new Date(System.currentTimeMillis() - l);
		//Fecha futura (8 dias desde la fecha actual)
		final Calendar future8Date = Calendar.getInstance();
		future8Date.setTime(actual);
		future8Date.add(Calendar.DAY_OF_YEAR, 8);

		final Calendar past8Date = Calendar.getInstance();
		past8Date.setTime(actual);
		past8Date.add(Calendar.DAY_OF_YEAR, -8);

		final Object testingData[][] = {
			// Alguien sin registrar/logueado -> false
			{
				null, 12.0, future8Date.getTime(), 10, IllegalArgumentException.class
			},
			// Un encuestador logueado intenta crear una factura -> false
			{
				"poller1", 12.0, future8Date.getTime(), 10, IllegalArgumentException.class
			},
			// Todo vacio -> false
			{
				null, null, null, null, NullPointerException.class
			},
			// Un administrador crea una factura a una encuesta que no tiene factura asignada -> true
			{
				"admin", 12.0, future8Date.getTime(), 10, null
			},
			// Un administrador crea una factura a una encuesta que no tiene factura asignada pero intenta introducir un precio negativo -> false
			{
				"admin", -12.0, future8Date.getTime(), 10, IllegalArgumentException.class
			},
			// Un administrador crea una factura a una encuesta que no tiene factura asignada pero intenta introducir un precio cero -> false
			{
				"admin", 0.0, future8Date.getTime(), 10, IllegalArgumentException.class
			},
			// Un administrador crea una factura a una encuesta que no tiene factura asignada e intenta poner el precio mínimo establecido -> true
			{
				"admin", 1.0, future8Date.getTime(), 10, null
			},
			// Un administrador crea una factura a una encuesta que no tiene factura asignada pero con una fecha de vencimiento pasada -> false
			{
				"admin", 12.0, past8Date.getTime(), 10, IllegalArgumentException.class
			},
			// Un administrador crea una factura a una encuesta que Sí tiene factura asignada -> false
			{
				"admin", 12.0, future8Date.getTime(), 9, IllegalArgumentException.class
			},
			// Un administrador crea una factura a una encuesta que no exite -> false
			{
				"admin", 12.0, future8Date.getTime(), 4567, IllegalArgumentException.class
			},

		};
		for (int i = 0; i < testingData.length; i++)
			this.createBillTest((String) testingData[i][0], (Double) testingData[i][1], (Date) testingData[i][2], (Integer) testingData[i][3], (Class<?>) testingData[i][4]);
	}

}
