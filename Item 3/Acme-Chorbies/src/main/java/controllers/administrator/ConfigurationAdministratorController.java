
package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import controllers.AbstractController;
import domain.Configuration;

@Controller
@RequestMapping("/configuration/administrator")
public class ConfigurationAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private ConfigurationService	configurationService;


	// Constructors -----------------------------------------------------------

	public ConfigurationAdministratorController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	// Edition ----------------------------------------------------------------		
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Configuration configuration;

		configuration = this.configurationService.findConfiguration();
		Assert.notNull(configuration);

		result = this.createEditModelAndView(configuration);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Configuration configuration, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding.toString());
			result = this.createEditModelAndView(configuration);

		} else
			try {
				configuration = this.configurationService.save(configuration);
				result = new ModelAndView("redirect:../../");

			} catch (final Throwable oops) {
				System.out.println(oops);

				result = this.createEditModelAndView(configuration, "configuration.commit.error");

			}
		return result;

	}

	// Ancillary methods ------------------------------------------------------		
	protected ModelAndView createEditModelAndView(final Configuration configuration) {
		ModelAndView result;

		result = this.createEditModelAndView(configuration, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Configuration configuration, final String message) {
		ModelAndView result;

		result = new ModelAndView("configuration/edit");
		result.addObject("configuration", configuration);
		result.addObject("message", message);

		return result;
	}
}
