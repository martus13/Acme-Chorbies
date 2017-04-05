
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Chirp;

@Repository
public interface ChirpRepository extends JpaRepository<Chirp, Integer> {

	// A1: The minimum, the maximum, and the average number of chirps that a chorbi receives from other chorbies.

	// A2: The minimum, the maximum, and the average number of chirps that a chorbi sends to other chorbies.
}
