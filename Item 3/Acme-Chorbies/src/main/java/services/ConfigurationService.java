
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ConfigurationRepository;
import domain.Configuration;

@Service
@Transactional
public class ConfigurationService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ConfigurationRepository	configurationRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private AdministratorService	administratorService;


	// Constructors -----------------------------------------------------------
	public ConfigurationService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Configuration findOne(final int configurationId) {
		Assert.isTrue(configurationId != 0);

		Configuration result;

		result = this.configurationRepository.findOne(configurationId);

		return result;
	}

	public Collection<Configuration> findAll() {
		Collection<Configuration> result;

		result = this.configurationRepository.findAll();

		return result;
	}

	public Configuration save(Configuration configuration) {
		Assert.notNull(configuration);

		Assert.notNull(this.administratorService.findByPrincipal());

		configuration = this.configurationRepository.save(configuration);

		return configuration;
	}

	public Configuration findConfiguration() {
		Collection<Configuration> all;
		Configuration[] arrayAll;
		Configuration result;

		all = this.findAll();
		arrayAll = all.toArray(new Configuration[all.size()]);

		result = arrayAll[0];

		return result;
	}

	// Other business methods -------------------------------------------------

}
