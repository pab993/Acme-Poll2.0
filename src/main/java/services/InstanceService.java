
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.InstanceRepository;
import domain.Instance;
import domain.Poll;
import domain.Poller;

@Service
@Transactional
public class InstanceService {

	//Managed Repository =============================================================================

	@Autowired
	private InstanceRepository	instanceRepository;

	//Managed Services ===============================================================================

	@Autowired
	private PollService			pollService;

	@Autowired
	private PollerService		pollerService;


	//Constructor methods ============================================================================

	//Simple CRUD methods ============================================================================

	public Instance create(int pollId) {
		Instance result;

		Poll poll = pollService.findOne(pollId);

		result = new Instance();

		result.setPoll(poll);

		return result;
	}

	public Instance save(Instance instance) {
		Assert.notNull(instance);

		Instance result;

		result = instanceRepository.save(instance);

		return result;
	}

	public Instance findOne(int instanceId) {
		// TODO Auto-generated method stub
		Instance instance;

		instance = instanceRepository.findOne(instanceId);

		return instance;
	}

	public Collection<Instance> findAll() {
		// TODO Auto-generated method stub
		Collection<Instance> instances;

		instances = instanceRepository.findAll();

		return instances;
	}

	public void delete(Instance instance) {
		Assert.notNull(instance);

		Poller poller = pollerService.findByPrincipal();

		Assert.isTrue(instance.getPoll().getPoller().getId() == poller.getId());

		instanceRepository.delete(instance);

	}

	public Collection<Object[]> secondQuery() {
		Collection<Object[]> secondQuery;

		secondQuery = instanceRepository.secondQuery();

		return secondQuery;
	}

}
