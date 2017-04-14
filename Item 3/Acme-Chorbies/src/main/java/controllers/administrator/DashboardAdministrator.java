
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ChirpService;
import services.ChorbiService;
import services.LikeService;
import controllers.AbstractController;
import domain.Chorbi;

@Controller
@RequestMapping("/administrator")
public class DashboardAdministrator extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ChorbiService	chorbiService;

	@Autowired
	private LikeService		likeService;

	@Autowired
	private ChirpService	chirpService;


	// Constructors -----------------------------------------------------------

	public DashboardAdministrator() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;

		// C

		Collection<Object[]> numChorbiesPerCountryAndCity;
		Object[] minMaxAvgAges;
		Double ratioInvalidCreditCard;
		Collection<Object[]> ratioActivitiesLoveFriendship;

		// B

		Collection<Chorbi> chorbiesSortedByNumLikes;
		Object[] minMaxAvgLikesPerChorbi;

		// A

		Double[] minMaxAvgReceivedChirps;
		Double[] minMaxAvgChirpsSentPerChorbi;
		Collection<Object[]> chorbiWithMostReceivedChirps;
		Collection<Object[]> chorbiWithMostSentChirps;

		numChorbiesPerCountryAndCity = this.chorbiService.findGroupByCountryAndCity();
		minMaxAvgAges = this.chorbiService.findMinMaxAvgAges();
		ratioInvalidCreditCard = this.chorbiService.findRatioInvalidCreditCard();
		ratioActivitiesLoveFriendship = this.chorbiService.findRatioActivitiesLoveFriendship();

		
		chorbiesSortedByNumLikes = this.chorbiService.findAllSortedByReceivedLikes();
		minMaxAvgLikesPerChorbi = this.likeService.findMinMaxAvgReceivedPerChorbi();

		minMaxAvgReceivedChirps = this.chirpService.findMinMaxAvgReceived();
		
		minMaxAvgChirpsSentPerChorbi = this.chirpService.findMinMaxAvgSent();
		chorbiWithMostReceivedChirps = this.chorbiService.findChorbiWithMostReceivedChirps();
		chorbiWithMostSentChirps	= this.chorbiService.findChorbiWithMostSentChirps();
		
		result = new ModelAndView("administrator/dashboard");
		result.addObject("requestURI", "administrator/dashboard.do");
		result.addObject("numChorbiesPerCountryAndCity", numChorbiesPerCountryAndCity);
		result.addObject("minMaxAvgAges", minMaxAvgAges);
		result.addObject("ratioInvalidCreditCard", ratioInvalidCreditCard);
		result.addObject("ratioActivitiesLoveFriendship", ratioActivitiesLoveFriendship);

		result.addObject("chorbiesSortedByNumLikes", chorbiesSortedByNumLikes);
		result.addObject("minMaxAvgLikesPerChorbi", minMaxAvgLikesPerChorbi);

		result.addObject("minMaxAvgReceivedChirps", minMaxAvgReceivedChirps);
		result.addObject("minMaxAvgChirpsSentPerChorbi", minMaxAvgChirpsSentPerChorbi);
		result.addObject("chorbiWithMostReceivedChirps", chorbiWithMostReceivedChirps);
		result.addObject("chorbiWithMostSentChirps", chorbiWithMostSentChirps);

		return result;
	}
}
