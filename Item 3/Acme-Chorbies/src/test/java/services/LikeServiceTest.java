
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
public class LikeServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private LikeService		likeService;

	@Autowired
	private ChorbiService	chorbiService;


	// Tests ------------------------------------------------------------------

	@Test
	public void driverCreateAndSave() {
		final Object testingData[][] = {
			{
				"chorbi1", 47, "Test save", null
			}, {
				"chorbi1", 46, "Test save", IllegalArgumentException.class
			}, {
				null, 46, "Test save", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testCreate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][3]);
			this.testCreateAndSave((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
		}
	}

	@Test
	public void driverDelete() {
		final Object testingData[][] = {
			{
				"chorbi1", 56, null
			}, {
				"chorbi1", 59, IllegalArgumentException.class
			}, {
				null, 56, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testDelete((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void testCreate(final String username, final int chrobiId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			Like like;
			Chorbi chorbi;

			chorbi = this.chorbiService.findOne(chrobiId);
			like = this.likeService.create(chorbi);
			Assert.notNull(like);

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	protected void testCreateAndSave(final String username, final int chrobiId, final String commentText, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			Like like;
			Chorbi chorbi;

			chorbi = this.chorbiService.findOne(chrobiId);
			like = this.likeService.create(chorbi);
			like.setComment(commentText);
			like = this.likeService.save(like);
			Assert.isTrue(!like.getComment().isEmpty());

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	protected void testDelete(final String username, final int likeId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			Like like;
			Collection<Like> likes;

			like = this.likeService.findOne(likeId);
			this.likeService.delete(like);

			likes = this.likeService.findAll();
			Assert.isTrue(!likes.contains(like));

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	/////////////// Sin driver:
	@Test
	public void testFindOne() {
		Like like;

		like = this.likeService.findOne(56);
		Assert.notNull(like);
	}

	@Test
	public void testFindAll() {
		Collection<Like> likes;

		likes = this.likeService.findAll();
		Assert.isTrue(likes.size() == 5);
	}

	//	@Test
	//	public void testCreate() {
	//		this.authenticate("chorbi1");
	//
	//		Like like;
	//		Chorbi chorbi;
	//
	//		chorbi = this.chorbiService.findOne(47);
	//		like = this.likeService.create(chorbi);
	//		Assert.notNull(like);
	//
	//		this.unauthenticate();
	//	}
	//
	//	@Test(expected = IllegalArgumentException.class)
	//	public void testNegativeLikeSameChorbi() {
	//		this.authenticate("chorbi1");
	//
	//		Like like;
	//		Chorbi chorbi;
	//
	//		chorbi = this.chorbiService.findOne(46);
	//		like = this.likeService.create(chorbi);
	//		Assert.notNull(like);
	//
	//		this.unauthenticate();
	//	}
	//
	//	@Test(expected = IllegalArgumentException.class)
	//	public void testNegativeNotPrincipal() {
	//		Like like;
	//		Chorbi chorbi;
	//
	//		chorbi = this.chorbiService.findOne(46);
	//		like = this.likeService.create(chorbi);
	//		Assert.notNull(like);
	//
	//	}
	//
	//	@Test
	//	public void testCreateAndSave() {
	//		this.authenticate("chorbi1");
	//
	//		Like like;
	//		Chorbi chorbi;
	//
	//		chorbi = this.chorbiService.findOne(47);
	//		like = this.likeService.create(chorbi);
	//		like.setComment("Test save");
	//		like = this.likeService.save(like);
	//		Assert.isTrue(!like.getComment().isEmpty());
	//
	//		this.unauthenticate();
	//	}
	//
	//	@Test(expected = IllegalArgumentException.class)
	//	public void testNegativeEditNotGivenByPrincipal() {
	//		this.authenticate("chorbi1");
	//
	//		Like like;
	//
	//		like = this.likeService.findOne(59);
	//		like.setComment("Test save");
	//		like = this.likeService.save(like);
	//		Assert.isTrue(!like.getComment().isEmpty());
	//
	//		this.unauthenticate();
	//	}
	//
	//	@Test
	//	public void testDelete() {
	//		this.authenticate("chorbi1");
	//
	//		Like like;
	//		Collection<Like> likes;
	//
	//		like = this.likeService.findOne(56);
	//		this.likeService.delete(like);
	//
	//		likes = this.likeService.findAll();
	//		Assert.isTrue(!likes.contains(like));
	//
	//		this.unauthenticate();
	//	}
	//
	//	@Test(expected = IllegalArgumentException.class)
	//	public void testNegativeDeleteNotGivenByPrincipal() {
	//		this.authenticate("chorbi1");
	//
	//		Like like;
	//
	//		like = this.likeService.findOne(59);
	//		this.likeService.delete(like);
	//
	//		this.unauthenticate();
	//	}
	//
	//	@Test(expected = IllegalArgumentException.class)
	//	public void testNegativeDeleteNotAuthenticated() {
	//		Like like;
	//
	//		like = this.likeService.findOne(56);
	//		this.likeService.delete(like);
	//
	//	}

	@Test
	public void testFindByGivenToId() {
		Collection<Like> likes;

		likes = this.likeService.findByGivenToId(47);
		Assert.isTrue(likes.size() == 2);

	}

	@Test
	public void testFindByGivenById() {
		final Collection<Like> likes;

		likes = this.likeService.findByGivenById(46);
		Assert.isTrue(likes.size() == 3);

	}

	@Test
	public void testFindMinMaxAvgReceivedPerChorbi() {
		Object[] result;

		result = this.likeService.findMinMaxAvgReceivedPerChorbi();
		System.out.println("Min: " + result[0] + ", Max: " + result[1] + ", Avg: " + result[2]);

	}
}
