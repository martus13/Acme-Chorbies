
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Integer> {

	@Query("select l from Like l where l.givenTo.id=?1")
	Collection<Like> findByGivenToId(int chorbiToId);

	@Query("select l from Like l where l.givenBy.id=?1")
	Collection<Like> findByGivenById(int chorbiToId);
}