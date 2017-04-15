
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SearchTemplateRepository;
import domain.Chorbi;
import domain.Configuration;
import domain.SearchTemplate;

@Service
@Transactional
public class SearchTemplateService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private SearchTemplateRepository	searchTemplateRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ChorbiService				chorbiService;

	@Autowired
	private CreditCardService			creditCardService;

	@Autowired
	private ConfigurationService		configurationService;


	// Constructors -----------------------------------------------------------
	public SearchTemplateService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public SearchTemplate findOne(final int searchTemplateId) {
		Assert.isTrue(searchTemplateId != 0);

		SearchTemplate result;

		result = this.searchTemplateRepository.findOne(searchTemplateId);

		return result;
	}

	public Collection<SearchTemplate> findAll() {
		Collection<SearchTemplate> result;

		result = this.searchTemplateRepository.findAll();

		return result;
	}

	public SearchTemplate create(final Chorbi chorbi) {
		Collection<Chorbi> results;
		final SearchTemplate searchTemplate = new SearchTemplate();

		results = new ArrayList<Chorbi>();

		searchTemplate.setRelationshipType(null);
		searchTemplate.setApproximateAge(null);
		searchTemplate.setSingleKeyword(null);
		searchTemplate.setGenre(null);
		searchTemplate.setSearchTime(null);
		searchTemplate.setCountry(null);
		searchTemplate.setState(null);
		searchTemplate.setProvince(null);
		searchTemplate.setCity(null);
		searchTemplate.setResults(results);
		searchTemplate.setChorbi(chorbi);

		return searchTemplate;
	}
	public SearchTemplate save(SearchTemplate searchTemplate) {
		Assert.notNull(searchTemplate);

		if (searchTemplate.getId() != 0)
			Assert.isTrue(searchTemplate.getChorbi().equals(this.chorbiService.findByPrincipal()));

		if (searchTemplate.getSingleKeyword() == "")
			searchTemplate.setSingleKeyword(null);
		if (searchTemplate.getCountry() == "")
			searchTemplate.setCountry(null);
		if (searchTemplate.getState() == "")
			searchTemplate.setState(null);
		if (searchTemplate.getProvince() == "")
			searchTemplate.setProvince(null);
		if (searchTemplate.getCity() == "")
			searchTemplate.setCity(null);

		searchTemplate.setSearchTime(null);
		searchTemplate.getResults().clear();

		searchTemplate = this.searchTemplateRepository.save(searchTemplate);

		return searchTemplate;
	}

	public void delete(final SearchTemplate searchTemplate) {
		Assert.notNull(searchTemplate);

		Chorbi principal;

		principal = this.chorbiService.findByPrincipal();
		Assert.isTrue(principal.equals(searchTemplate.getChorbi()));

		this.searchTemplateRepository.delete(searchTemplate);
	}

	// Other business methods -------------------------------------------------

	public SearchTemplate findByChorbiId(final Chorbi chorbi) {
		Assert.notNull(chorbi);

		SearchTemplate result;

		result = this.searchTemplateRepository.findByChorbiId(chorbi.getId());

		return result;
	}

	public Collection<Chorbi> findChorbiesBySearchTemplate(SearchTemplate searchTemplate) {
		Collection<Chorbi> result = new ArrayList<Chorbi>();
		Calendar calendar;
		Chorbi chorbi;

		calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, -10);
		chorbi = this.chorbiService.findByPrincipal();

		// primero: comprobar que tiene creditCard y que es valida
		Assert.isTrue(this.creditCardService.checkValidation(this.creditCardService.findByChorbi(chorbi.getId())));

		// segundo: comprobar la fecha en que se hizo la busqueda
		if (searchTemplate.getSearchTime() == null) {
			result = this.chorbiService.findNotBannedBySearchTemplate(searchTemplate);

			searchTemplate.setSearchTime(calendar.getTime());
			searchTemplate.setResults(result);

		} else {
			Calendar searchTimeCalendar;
			Configuration configuration;
			Calendar configurationCalendar;

			searchTimeCalendar = Calendar.getInstance();
			configurationCalendar = Calendar.getInstance();

			configuration = this.configurationService.findConfiguration();
			configurationCalendar.setTime(configuration.getCachedTime());

			searchTimeCalendar.setTime(searchTemplate.getSearchTime());
			searchTimeCalendar.add(Calendar.HOUR_OF_DAY, configurationCalendar.get(Calendar.HOUR_OF_DAY));
			searchTimeCalendar.add(Calendar.MINUTE, configurationCalendar.get(Calendar.MINUTE));
			searchTimeCalendar.add(Calendar.SECOND, configurationCalendar.get(Calendar.SECOND));

			if (calendar.getTime().before(searchTimeCalendar.getTime()) || calendar.getTime().equals(searchTimeCalendar.getTime()))
				// si la fecha actual es menor o igual que la fecha de busqueda mas cachedTime: devolver los resultados almacenados
				result = searchTemplate.getResults();
			else {
				// si hace mas tiempo: hacer la busqueda de nuevo

				result = this.chorbiService.findNotBannedBySearchTemplate(searchTemplate);

				searchTemplate.setSearchTime(calendar.getTime());
				searchTemplate.setResults(result);
			}
		}

		searchTemplate = this.searchTemplateRepository.save(searchTemplate);

		return result;
	}
}
