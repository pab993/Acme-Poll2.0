
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ConfigurationSystemRepository;
import domain.Administrator;
import domain.ConfigurationSystem;

@Service
@Transactional
public class ConfigurationSystemService {

	//Managed Repository =============================================================================

	@Autowired
	private ConfigurationSystemRepository	configurationSystemRepository;

	//Services =======================================================================================

	@Autowired
	private ActorService					actorService;


	//Constructor methods ============================================================================

	//Simple CRUD methods ============================================================================

	public Collection<ConfigurationSystem> findAll() {

		Collection<ConfigurationSystem> configurationSystems;

		configurationSystems = configurationSystemRepository.findAll();
		Assert.notNull(configurationSystems);

		return configurationSystems;
	}

	public ConfigurationSystem save(ConfigurationSystem configurationSystem) {
		Assert.notNull(configurationSystem);
		Administrator principal = (Administrator) actorService.findByPrincipal();
		Assert.notNull(principal);

		ConfigurationSystem result = configurationSystemRepository.save(configurationSystem);
		return result;

	}

	//Other Business Methods =========================================================================

	public ConfigurationSystem getCS() {
		Collection<ConfigurationSystem> configurationSystems;

		configurationSystems = this.findAll();
		return configurationSystems.iterator().next();
	}

}
