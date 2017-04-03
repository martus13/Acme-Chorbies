
package services;

import java.util.ArrayList;
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
		searchTemplate.setCoordinates(null);
		searchTemplate.setSearchTime(null);
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

}
