package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SpamRepository;
import domain.Spam;

@Service
@Transactional
public class SpamService {
	
	@Autowired
	private SpamRepository spamRepository ;
	
	public SpamService(){
		super();
	}
	
	// CRUD methods --------------------------------------------------------------------------------
	public Spam create() {
		Spam res = new Spam();
		
		//TODO
		
		return res;
	}

	public Spam findOne(int spamId) {
		return spamRepository.findOne(spamId);
	}

	public Collection<Spam> findAll(){
		return spamRepository.findAll();
	}
		
	public Spam save(Spam spam) {
		Assert.notNull(spam);
		return spamRepository.save(spam);
	}

	public void delete(Spam spam) {
		Assert.notNull(spam);
		Assert.isTrue(spamRepository.exists(spam.getId()));
		
		spamRepository.delete(spam);
		
		Assert.isTrue(!spamRepository.exists(spam.getId()));
	}

}