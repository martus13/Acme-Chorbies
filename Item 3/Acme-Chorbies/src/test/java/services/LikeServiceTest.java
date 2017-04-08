
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

	// A continuación se van a realizar pruebas para comprobar el correcto funcionamiento de los casos de uso relacionados con Like.

	// Darle like a un chorbi:
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

	// Eliminación del like:
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

	// Mostrar los likes que ha recibido y enviado un chorbi:
	@Test
	public void driverFind() {
		final Object testingData[][] = {
			{
				46, null
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testFindByGivenToId((int) testingData[i][0], (Class<?>) testingData[i][1]);
			this.testFindByGivenById((int) testingData[i][0], (Class<?>) testingData[i][1]);
		}
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

	protected void testFindByGivenToId(final int chorbiId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			Collection<Like> likes;

			likes = this.likeService.findByGivenToId(chorbiId);
			System.out.println("Given to " + chorbiId + ": " + likes.size());

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	protected void testFindByGivenById(final int chorbiId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			final Collection<Like> likes;

			likes = this.likeService.findByGivenById(chorbiId);
			System.out.println("Given to " + chorbiId + ": " + likes.size());

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	@Test
	public void testFindMinMaxAvgReceivedPerChorbi() {
		Object[] result;

		result = this.likeService.findMinMaxAvgReceivedPerChorbi();
		System.out.println("Min: " + result[0] + ", Max: " + result[1] + ", Avg: " + result[2]);

	}
}
