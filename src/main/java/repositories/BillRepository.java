
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Bill;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {

	@Query("select b from Bill b where b.poll.poller.id = ?1")
	Collection<Bill> findBillsByPoller(int pollerId);

	@Query("select b from Bill b where b.receipt.endorsed = true")
	Collection<Bill> findBillsEndorsed();

	@Query("select b from Bill b where b.receipt.endorsed = false")
	Collection<Bill> findBillsNotEndorsed();

	@Query("select b from Bill b where b.poll.id = ?1")
	Bill findBillByPollId(int pollId);

	@Query("select 1.0*count(b)/(select count(bt) from Bill bt) from Bill b where b.receipt.endorsed = false")
	Double forthQuery();

	@Query("select 1.0*count(b)/(select count(bt) from Bill bt) from Bill b where b.receipt.endorsed = true")
	Double fifthQuery();

	@Query("select 1.0*count(b)/(select count(bt) from Bill bt) from Bill b where b.momentDue < current_timestamp")
	Double sixthQuery();

	@Query("select min(b.amountDue), avg(b.amountDue), max(b.amountDue) from Bill b")
	Collection<Object[]> seventhQuery();

}
