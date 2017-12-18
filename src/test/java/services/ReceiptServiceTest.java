
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
import domain.Administrator;
import domain.Bill;
import domain.Poller;
import domain.Receipt;

@Transactional
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
public class ReceiptServiceTest extends AbstractTest {

	// The SUT
	// ====================================================

	@Autowired
	private BillService				billService;

	@Autowired
	private PollerService			pollerService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ReceiptService			receiptService;


	// Tests
	// ====================================================

	/*
	 * To check the validity of a receipt, the system must check its pdf.
	 * 
	 * En este test, se comprueba el pdf proporcionado para el recibo.
	 * Para ello introducimos valores correctos e incorrectos para observa el comportamiento de la aplicación.
	 */

	/*
	 * Show the receipt of a bill of our poll.
	 * 
	 * En este caso de uso comprobamos que los encuestadores pueden ver sus recibos de sus facturas de las encuestas creadas que existen en nuestra aplicación.
	 */

	public void showReceiptOfABillTest(final String username, Integer receiptId, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			Poller poller = pollerService.findByPrincipal();
			Receipt receipt = receiptService.findOne(receiptId);

			Assert.notNull(receipt);
			Assert.notNull(poller);
			Assert.isTrue(receipt.getBill().getPoll().getPoller().equals(poller));

			System.out.print(receipt);

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

	public void addReceiptTest(final String username, Integer billId, String pdf, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			Receipt result;
			Bill bill = billService.findOne(billId);
			Poller poller = pollerService.findByPrincipal();
			Assert.notNull(poller);
			Assert.notNull(bill);
			Assert.isTrue(bill.getReceipt() == null);
			Assert.isTrue(bill.getPoll().getPoller().equals(poller));
			result = receiptService.create(bill);

			Assert.notNull(pdf);

			result.setPdf(pdf);
			receiptService.save(result);
			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * Endorse a receipt of the system.
	 * 
	 * Para ello introducimos valores correctos e incorrectos para observa el comportamiento de la aplicación
	 */

	public void endorseAReceiptTest(final String username, Integer receiptId, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			Receipt receipt = receiptService.findOne(receiptId);
			Administrator administrator = administratorService.findByPrincipal();
			Assert.notNull(receipt);
			Assert.notNull(administrator);
			Assert.isTrue(receipt.getEndorsed() == false);

			receipt.setEndorsed(true);
			receiptService.save(receipt);
			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}

	//Drivers
	// ===================================================

	@Test
	public void driverShowReceiptTest() {

		final Object testingData[][] = {
			// Alguien sin registrar/logueado -> false
			{
				null, 63, IllegalArgumentException.class
			},
			// El recibo de un Encuestador -> true
			{
				"poller1", 63, null
			},
			// El recibo de un encuestador que no existe -> false
			{
				"poller1", 573, IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.showReceiptOfABillTest((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void driverAddAReceiptTest() {

		final Object testingData[][] = {
			// Alguien sin registrar/logueado -> false
			{
				null, 61, "https://www.thisisabill.com", IllegalArgumentException.class
			},
			// Un encuestador logueado intenta añadir un recibo a una factura -> true
			{
				"poller2", 61, "https://www.thisisabill.com", null
			},
			// Todo vacio -> false
			{
				null, null, null, NullPointerException.class
			},
			// Un encuestador intenta añadir un recibo a una factura que ya tiene un recibo -> false
			{
				"poller1", 60, "https://www.thisisabill.com", IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.addReceiptTest((String) testingData[i][0], (Integer) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	@Test
	public void driverEndorseAReceiptTest() {

		final Object testingData[][] = {
			// Alguien sin registrar/logueado -> false
			{
				null, 64, IllegalArgumentException.class
			},
			// Un encuestador intenta endorsar su propio recibo -> false
			{
				"poller1", 64, IllegalArgumentException.class
			},
			// Endorsar un recibo no endorsardo logueado como un administrador -> true
			{
				"admin", 64, null
			},
			// Endorsar un recibo ya endorsado logueado como un administrador -> false
			{
				"admin", 63, IllegalArgumentException.class
			},

		};
		for (int i = 0; i < testingData.length; i++)
			this.endorseAReceiptTest((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}

}
