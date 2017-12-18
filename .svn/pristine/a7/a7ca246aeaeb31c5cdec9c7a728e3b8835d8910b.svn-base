
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Poll;

@Repository
public interface PollRepository extends JpaRepository<Poll, Integer> {

	@Query("select p from Poll p where p.ticker = ?1")
	Poll findByCode(String code);

	@Query("select p from Poll p where p.poller.id = ?1")
	Collection<Poll> findAllMyPolls(int pollerId);

	@Query("select p from Poll p, ConfigurationSystem cs where (p.title like %?1% or p.description like %?1% or p.ticker like %?1%) and p.questions is not empty and p.startActive < current_timestamp()")
	Collection<Poll> findPollByKeyword(String keyWord);

	//select p from Poll p, ConfigurationSystem cs where p.questions is not empty and (p.startActive < current_date()) and ((p.endActive - p.startActive)/1000*60*60*24) >= cs.minimumPeriodActive;
	@Query("select p from Poll p, ConfigurationSystem cs where p.questions is not empty and (p.startActive < current_timestamp())")
	Collection<Poll> findOnlyActives();

	@Query("select min(pr.polls.size), avg(pr.polls.size), max(pr.polls.size), stddev(pr.polls.size) from Poller pr")
	Collection<Object[]> firstQuery();

	//select p.title, p.startActive, p.endActive, (p.endActive - p.startActive)/(1000*60*60*24), cs.minimumPeriodActive from Poll p, ConfigurationSystem cs where (p.endActive - p.startActive)/(1000*60*60*24) <= cs.minimumPeriodActive;
	//select p.title,(p.endActive - p.startActive)/(1000*60*60*24) from Poll p, ConfigurationSystem cs where p.questions is not empty and p.startActive < current_timestamp();
}
