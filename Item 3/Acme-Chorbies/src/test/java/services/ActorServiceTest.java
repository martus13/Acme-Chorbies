
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
	public void testFindOne() {
		Actor actor;

		actor = this.actorService.findOne(56);
		Assert.notNull(actor);
	}

	@Test
	public void testFindAll() {
		Collection<Actor> actors;

		actors = this.actorService.findAll();
		Assert.isTrue(actors.size() == 6);
	}

	@Test
	public void testSave() {
		Actor actor;

		actor = this.actorService.findOne(52);
		actor.setName("prueba");

		actor = this.actorService.save(actor);
		Assert.isTrue(actor.getName() == "prueba");
	}

	@Test
	public void testCheckAuthority() {
		Actor actor;

		actor = this.actorService.findOne(56);
		Assert.isTrue(this.actorService.checkAuthority(actor, "CHORBI"));
	}
}
