
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Instance;

@Repository
public interface InstanceRepository extends JpaRepository<Instance, Integer> {

	@Query("select min(p.instances.size), avg(p.instances.size), max(p.instances.size), stddev(p.instances.size) from Poll p")
	Collection<Object[]> secondQuery();

}
