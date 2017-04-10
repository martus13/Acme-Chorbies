
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
		final Collection<Chorbi> results;
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
		final Collection<Chorbi> chorbies = new ArrayList<Chorbi>();
		Calendar calendar;

		calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, -10);

		for (final Chorbi c : this.chorbiService.findAll()) {
			Boolean aux = false;
			if (searchTemplate.getCountry() != null)
				aux = aux && (searchTemplate.getCountry().toLowerCase().contains(c.getCoordinates().getCountry().toLowerCase()));
			if (searchTemplate.getState() != null)
				aux = aux && (searchTemplate.getState().toLowerCase().contains(c.getCoordinates().getState().toLowerCase()));
			if (searchTemplate.getCity() != null)
				aux = aux && (searchTemplate.getCity().toLowerCase().contains(c.getCoordinates().getCity().toLowerCase()));
			if (searchTemplate.getProvince() != null)
				aux = aux && (searchTemplate.getProvince().toLowerCase().contains(c.getCoordinates().getProvice().toLowerCase()));

			if (aux)
				chorbies.add(c);
		}

		searchTemplate.setSearchTime(calendar.getTime());
		searchTemplate.setResults(chorbies);

		searchTemplate = this.searchTemplateRepository.save(searchTemplate);

		return chorbies;
	}
}
