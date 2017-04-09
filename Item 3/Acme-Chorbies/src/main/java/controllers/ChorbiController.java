
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ChorbiService;
import domain.Chorbi;
import domain.Genre;
import domain.RelationshipType;
import forms.ChorbiForm;

@Controller
@RequestMapping("/chorbi")
public class ChorbiController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private ChorbiService	chorbiService;


	// Constructors -----------------------------------------------------------

	public ChorbiController() {
		super();
	}

	// Register ---------------------------------------------------------------		
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView result;
		ChorbiForm chorbiForm;
		Chorbi chorbi;

		chorbi = this.chorbiService.create();
		chorbiForm = this.chorbiService.desreconstructCreate(chorbi);

		result = this.createEditModelAndView(chorbiForm);

		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ChorbiForm chorbiForm, final BindingResult binding) {

		ModelAndView result;
		Chorbi chorbi;

		if (binding.hasErrors()) {
			System.out.println(binding.toString());
			result = this.createEditModelAndView(chorbiForm);

		} else
			try {
				chorbi = this.chorbiService.reconstructCreate(chorbiForm);
				this.chorbiService.save(chorbi);
				result = new ModelAndView("redirect:/security/login.do");

			} catch (final Throwable oops) {
				System.out.println(oops);

				result = this.createEditModelAndView(chorbiForm, "chorbi.commit.error");

			}
		return result;

	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final ChorbiForm chorbiForm) {
		ModelAndView result;

		result = this.createEditModelAndView(chorbiForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final ChorbiForm chorbiForm, final String message) {
		ModelAndView result;
		Genre[] genres;
		RelationshipType[] relationshipTypes;

		genres = Genre.values();
		relationshipTypes = RelationshipType.values();

		result = new ModelAndView("chorbi/register");
		result.addObject("chorbiForm", chorbiForm);
		result.addObject("actorForm", "chorbiForm");
		result.addObject("genres", genres);
		result.addObject("relationshipTypes", relationshipTypes);
		result.addObject("requestURI", "chorbi/register.do");
		result.addObject("message", message);

		return result;
	}
}
