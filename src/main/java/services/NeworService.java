
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

import repositories.NeworRepository;
import domain.Administrator;
import domain.Newor;
import domain.Poll;

@Transactional
@Service
public class NeworService {

	//Repository
	//======================================================================

	@Autowired
	private NeworRepository			neworRepository;

	@Autowired
	private AdministratorService	administratorService;


	//Services
	//======================================================================

	// Constructor methods
	// =====================================================================

	public NeworService() {
		super();
	}

	//CRUD methods
	//=======================================================================

	public Newor findOne(final int id) {
		Assert.isTrue(this.neworRepository.exists(id));

		final Newor newor = this.neworRepository.findOne(id);
		Assert.notNull(newor);

		return newor;
	}

	public Collection<Newor> findAll() {
		Collection<Newor> result;

		result = this.neworRepository.findAll();

		return result;
	}

	//Other bussiness methods
	//=======================================================================

	public Newor create(final Poll poll) {

		final Administrator principal = this.administratorService.findByPrincipal();

		Assert.notNull(principal);
		Assert.isInstanceOf(Administrator.class, principal);
		Assert.notNull(poll);

		final Newor newor = new Newor();

		newor.setDisplayMoment(new Date(System.currentTimeMillis() - 100));
		newor.setMark(this.codeGenerator());

		newor.setMomentCreated(new Date(System.currentTimeMillis() - 100));

		newor.setAdministrator(principal);
		newor.setPoll(poll);
		newor.setCancel(false);

		return newor;
	}

	public Newor save(final Newor newor) {
		// TODO Auto-generated method stub
		Assert.notNull(newor);
		Assert.isInstanceOf(Administrator.class, this.administratorService.findByPrincipal());

		Date currentMoment;
		currentMoment = new Date(System.currentTimeMillis());

		//		Assert.isTrue(newor.getPoll().getEndActive().after(currentMoment) && newor.getPoll().getStartActive().after(currentMoment));

		final Newor saved = this.neworRepository.save(newor);

		return saved;
	}

	//Other bussiness methods
	//=======================================================================

	public Newor findOneByAdministratorAndPoll(final int administratorId, final int pollId) {
		// TODO Auto-generated method stub

		Assert.notNull(administratorId);
		Assert.notNull(pollId);

		final Newor newor = this.neworRepository.findOneByAdministratorAndPoll(administratorId, pollId);

		Assert.notNull(newor);

		return newor;
	}

	public Collection<Newor> findByPoll(final int pollId) {
		Collection<Newor> newors = new ArrayList<Newor>();

		newors = this.neworRepository.findByPoll(pollId);

		return newors;
	}

	public Collection<Newor> findByPoll2(final int pollId) {
		Collection<Newor> newors = new ArrayList<Newor>();

		newors = this.neworRepository.findByPoll2(pollId);

		return newors;
	}

	public Collection<Newor> findByPoll3(final int pollId) {
		Collection<Newor> newors = new ArrayList<Newor>();

		newors = this.neworRepository.findByPoll3(pollId);

		return newors;
	}

	public void cancelNewor(final Newor newor) {

		Assert.notNull(newor);
		Assert.isInstanceOf(Administrator.class, this.administratorService.findByPrincipal());
		Assert.isTrue(!newor.getJustification().isEmpty());
		newor.setCancel(true);
		this.neworRepository.save(newor);

	}

	public boolean checkJustification(Newor newor, BindingResult binding) {
		FieldError error;
		String[] codigos;
		boolean result;

		if (StringUtils.isNotBlank(newor.getJustification()))
			result = true;
		else
			result = false;

		if (!result) {
			codigos = new String[1];
			codigos[0] = "justification.error";
			error = new FieldError("newor", "justification", newor.getJustification(), false, codigos, null, "");
			binding.addError(error);
		}

		return result;
	}

	public String codeGenerator() {
		String result = "";
		final String pattern = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890_";
		final String pattern2 = "0123456789";
		final Random rnd = new Random();
		//		final int nRnd = NeworService.generaNumeroAleatorio(3);
		//		final int nRnd2 = NeworService.generaNumeroAleatorio(3);

		for (int i = 0; i < 3; i++)
			result += pattern.charAt(rnd.nextInt(pattern.length()));
		for (int i = 0; i < 3; i++)
			result += pattern2.charAt(rnd.nextInt(pattern2.length()));

		final Newor newor = this.neworRepository.findByCode(result);

		if (newor != null) {
			result = "";
			result = this.codeGenerator();
		}

		return result;
	}

	public static int generaNumeroAleatorio(final int minimo, final int maximo) {

		final int num = (int) Math.floor(Math.random() * (maximo - minimo + 1) + (minimo));
		return num;
	}


	//Reconstruct
	//=======================================================================

	@Autowired
	private Validator	validator;


	public Newor reconstruct(final Newor newor, final BindingResult binding) {
		// TODO Auto-generated method stub

		Newor resul;

		if (newor.getId() == 0)
			resul = newor;
		else
			resul = this.neworRepository.findOne(newor.getId());

		this.validator.validate(resul, binding);

		return resul;
	}

}
