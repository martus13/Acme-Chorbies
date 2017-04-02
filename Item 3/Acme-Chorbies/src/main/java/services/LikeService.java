
package services;

import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.LikeRepository;
import domain.Chorbi;
import domain.Like;

@Service
@Transactional
public class LikeService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private LikeRepository	likeRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ChorbiService	chorbiService;


	// Constructors -----------------------------------------------------------
	public LikeService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Like findOne(final int likeId) {
		Assert.isTrue(likeId != 0);

		Like result;

		result = this.likeRepository.findOne(likeId);

		return result;
	}

	public Collection<Like> findAll() {
		Collection<Like> result;

		result = this.likeRepository.findAll();

		return result;
	}

	public Like create(final Chorbi givenTo) {
		Assert.notNull(givenTo);

		Like like;
		Chorbi principal;
		Calendar calendar;

		principal = this.chorbiService.findByPrincipal();
		Assert.notNull(principal);

		calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, -10);

		like = new Like();

		like.setGivenBy(principal);
		like.setGivenTo(givenTo);
		like.setLikeMoment(calendar.getTime());

		return like;
	}

	public Like save(Like like) {
		Assert.notNull(like);

		Chorbi principal;

		principal = this.chorbiService.findByPrincipal();
		Assert.isTrue(principal.equals(like.getGivenBy()));

		like = this.likeRepository.save(like);

		return like;
	}

	public void delete(final Like like) {
		Assert.notNull(like);

		Chorbi principal;

		principal = this.chorbiService.findByPrincipal();
		Assert.isTrue(principal.equals(like.getGivenBy()));

		this.likeRepository.delete(like);
	}

	// Other business methods -------------------------------------------------

	public Collection<Like> findByGivenToId(final int chorbiToId) {
		Collection<Like> result;

		result = this.likeRepository.findByGivenToId(chorbiToId);

		return result;
	}

	public Collection<Like> findByGivenById(final int chorbiToId) {
		Collection<Like> result;

		result = this.likeRepository.findByGivenById(chorbiToId);

		return result;
	}
}
