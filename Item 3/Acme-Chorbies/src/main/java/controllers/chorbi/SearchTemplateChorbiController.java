
package controllers.chorbi;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ChorbiService;
import services.CreditCardService;
import services.SearchTemplateService;
import controllers.AbstractController;
import domain.Chorbi;
import domain.Genre;
import domain.RelationshipType;
import domain.SearchTemplate;

@Controller
@RequestMapping("/searchTemplate/chorbi")
public class SearchTemplateChorbiController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private SearchTemplateService	searchTemplateService;

	@Autowired
	private ChorbiService			chorbiService;

	@Autowired
	private CreditCardService		creditCardService;


	// Constructors -----------------------------------------------------------

	public SearchTemplateChorbiController() {
		super();
	}

	// Display ----------------------------------------------------------------		
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		Chorbi chorbi;
		SearchTemplate searchTemplate;

		chorbi = this.chorbiService.findByPrincipal();

		searchTemplate = this.searchTemplateService.findByChorbiId(chorbi);

		result = new ModelAndView("searchTemplate/display");
		result.addObject("searchTemplate", searchTemplate);

		return result;
	}

	// Creation ---------------------------------------------------------------		
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		SearchTemplate searchTemplate;
		Chorbi chorbi;

		chorbi = this.chorbiService.findByPrincipal();

		searchTemplate = this.searchTemplateService.create(chorbi);

		result = this.createEditModelAndView(searchTemplate);

		return result;
	}

	@RequestMapping(value = "/findBySearchTemplate", method = RequestMethod.GET)
	public ModelAndView findBySearchTemplate(@RequestParam final int searchTemplateId) {
		ModelAndView result;
		SearchTemplate searchTemplate;
		Collection<Chorbi> chorbies;

		searchTemplate = this.searchTemplateService.findOne(searchTemplateId);
		Assert.notNull(searchTemplate);

		if ((this.creditCardService.checkValidation(this.creditCardService.findByChorbi(this.chorbiService.findByPrincipal().getId()))) == false || (this.creditCardService.findByChorbi(this.chorbiService.findByPrincipal().getId())) == null) {
			System.out.println("Invalid Credit Card");
			result = new ModelAndView("master.page");
			result.addObject("message", "searchTemplate.commit.errorCC");
		} else {

			chorbies = this.searchTemplateService.findChorbiesBySearchTemplate(searchTemplate);

			result = new ModelAndView("chorbi/list");
			result.addObject("chorbies", chorbies);
			result.addObject("principalId", 0);
			result.addObject("requestURI", "searchTemplate/chorbi/findBySearchTemplate.do?searchTemplateId=" + searchTemplateId);
		}
		return result;
	}

	// Edition ----------------------------------------------------------------		
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int searchTemplateId) {
		ModelAndView result;
		SearchTemplate searchTemplate;

		searchTemplate = this.searchTemplateService.findOne(searchTemplateId);
		Assert.notNull(searchTemplate);

		result = this.createEditModelAndView(searchTemplate);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final SearchTemplate searchTemplate, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding.toString());
			result = this.createEditModelAndView(searchTemplate);

		} else
			try {
				this.searchTemplateService.save(searchTemplate);
				result = new ModelAndView("redirect:display.do");

			} catch (final Throwable oops) {
				System.out.println(oops.toString());
				result = this.createEditModelAndView(searchTemplate, "searchTemplate.commit.error");
			}
		return result;
	}

	// Deleting ---------------------------------------------------------------		
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final SearchTemplate searchTemplate, final BindingResult binding) {
		ModelAndView result;

		searchTemplate.setRelationshipType(null);
		searchTemplate.setApproximateAge(null);
		searchTemplate.setSingleKeyword(null);
		searchTemplate.setGenre(null);
		searchTemplate.setCountry(null);
		searchTemplate.setState(null);
		searchTemplate.setProvince(null);
		searchTemplate.setCity(null);
		this.searchTemplateService.save(searchTemplate);
		result = new ModelAndView("redirect:display.do");

		return result;
	}

	// Ancillary methods ------------------------------------------------------		
	protected ModelAndView createEditModelAndView(final SearchTemplate searchTemplate) {
		ModelAndView result;

		result = this.createEditModelAndView(searchTemplate, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final SearchTemplate searchTemplate, final String message) {
		ModelAndView result;
		final Genre[] genres;
		final RelationshipType[] relationshipTypes;

		genres = Genre.values();
		relationshipTypes = RelationshipType.values();

		result = new ModelAndView("searchTemplate/edit");
		result.addObject("searchTemplate", searchTemplate);
		result.addObject("genres", genres);
		result.addObject("relationshipTypes", relationshipTypes);
		result.addObject("message", message);

		return result;
	}
}
