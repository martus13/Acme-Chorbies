
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Chorbi;
import domain.Like;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ChorbiServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private LikeService		likeService;

	@Autowired
	private ChorbiService	chorbiService;


	// Tests ------------------------------------------------------------------
	/////////////// Con driver:
	/////////////// Sin driver:
	@Test
	public void testFindOne() {
		Like like;

		like = this.likeService.findOne(52);
		Assert.notNull(like);
	}

	@Test
	public void testFindAll() {
		Collection<Like> likes;

		likes = this.likeService.findAll();
		Assert.isTrue(likes.size() == 5);
	}

	@Test
	public void testCreate() {
		this.authenticate("chorbi1");

		Like like;
		Chorbi chorbi;

		chorbi = this.chorbiService.findOne(43);
		like = this.likeService.create(chorbi);
		Assert.notNull(like);

		this.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNegativeLikeSameChorbi() {
		this.authenticate("chorbi1");

		Like like;
		Chorbi chorbi;

		chorbi = this.chorbiService.findOne(42);
		like = this.likeService.create(chorbi);
		Assert.notNull(like);

		this.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNegativeNotPrincipal() {
		Like like;
		Chorbi chorbi;

		chorbi = this.chorbiService.findOne(42);
		like = this.likeService.create(chorbi);
		Assert.notNull(like);

	}

	@Test
	public void testCreateAndSave() {
		this.authenticate("chorbi1");

		Like like;
		Chorbi chorbi;

		chorbi = this.chorbiService.findOne(43);
		like = this.likeService.create(chorbi);
		like.setComment("Test save");
		like = this.likeService.save(like);
		Assert.isTrue(!like.getComment().isEmpty());

		this.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNegativeEditNotGivenByPrincipal() {
		this.authenticate("chorbi1");

		Like like;

		like = this.likeService.findOne(55);
		like.setComment("Test save");
		like = this.likeService.save(like);
		Assert.isTrue(!like.getComment().isEmpty());

		this.unauthenticate();
	}

	@Test
	public void testDelete() {
		this.authenticate("chorbi1");

		Like like;
		Collection<Like> likes;

		like = this.likeService.findOne(52);
		this.likeService.delete(like);

		likes = this.likeService.findAll();
		Assert.isTrue(!likes.contains(like));

		this.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNegativeDeleteNotGivenByPrincipal() {
		this.authenticate("chorbi1");

		Like like;

		like = this.likeService.findOne(55);
		this.likeService.delete(like);

		this.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNegativeDeleteNotAuthenticated() {
		Like like;

		like = this.likeService.findOne(52);
		this.likeService.delete(like);

	}

	@Test
	public void testFindByGivenToId() {
		Collection<Like> likes;

		likes = this.likeService.findByGivenToId(43);
		Assert.isTrue(likes.size() == 2);

	}

	@Test
	public void testFindByGivenById() {
		final Collection<Like> likes;

		likes = this.likeService.findByGivenById(42);
		Assert.isTrue(likes.size() == 3);

	}

	@Test
	public void testFindMinMaxAvgReceivedPerChorbi() {
		Object[] result;

		result = this.likeService.findMinMaxAvgReceivedPerChorbi();
		System.out.println("Min: " + result[0] + ", Max: " + result[1] + ", Avg: " + result[2]);

	}
}
