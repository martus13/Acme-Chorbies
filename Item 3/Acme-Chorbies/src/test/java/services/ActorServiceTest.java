
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
import domain.Actor;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ActorServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private ActorService	actorService;


	// Tests ------------------------------------------------------------------

	@Test
	public void driverFind() {
		final Object testingData[][] = {
			{
				50, "CHORBI", null
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testFindOne((int) testingData[i][0], (Class<?>) testingData[i][2]);
			this.testCheckAuthority((int) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
			this.testEdit((int) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void testFindOne(final int actorId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			Actor actor;

			actor = this.actorService.findOne(actorId);
			Assert.notNull(actor);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	protected void testCheckAuthority(final int actorId, final String authority, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			Actor actor;

			actor = this.actorService.findOne(actorId);
			Assert.isTrue(this.actorService.checkAuthority(actor, authority));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	protected void testEdit(final int actorId, final String text, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			Actor actor;

			actor = this.actorService.findOne(actorId);
			actor.setName(text);

			actor = this.actorService.save(actor);
			Assert.isTrue(actor.getName() == text);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	@Test
	public void testFindAll() {
		Collection<Actor> actors;

		actors = this.actorService.findAll();
		Assert.isTrue(actors.size() == 6);
	}
}
