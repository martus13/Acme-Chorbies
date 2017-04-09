
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Chirp;

@Repository
public interface ChirpRepository extends JpaRepository<Chirp, Integer> {
	
	@Query("select c from Chirp c where c.sender=?1 and c.copy=false")
	Collection<Chirp> findAllMySentChirps(int myId);
	
	@Query("select c from Chirp c where c.recipient=?1 and c.copy=true")
	Collection<Chirp> findAllMyReceivedChirps(int myId);
			

	// A1: The minimum, the maximum, and the average number of chirps that a chorbi receives from other chorbies.

	// A2: The minimum, the maximum, and the average number of chirps that a chorbi sends to other chorbies.
}
