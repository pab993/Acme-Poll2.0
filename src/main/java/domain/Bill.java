
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Bill extends DomainEntity {

	//Attributes 
	// =================================================================

	private double	amountDue;
	private Date	momentDue;
	private boolean	paid;


	@NotNull
	@Min(1)
	@Max(100)
	@Digits(integer = 12, fraction = 2)
	public double getAmountDue() {
		return amountDue;
	}

	public void setAmountDue(double amountDue) {
		this.amountDue = amountDue;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Future
	public Date getMomentDue() {
		return momentDue;
	}

	public void setMomentDue(Date momentDue) {
		this.momentDue = momentDue;
	}

	public boolean getPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}


	//Relationships
	// =================================================================

	private Poll	poll;
	private Receipt	receipt;


	@OneToOne(optional = false)
	@Valid
	public Poll getPoll() {
		return poll;
	}

	public void setPoll(Poll poll) {
		this.poll = poll;
	}

	@OneToOne(optional = true)
	@Valid
	public Receipt getReceipt() {
		return receipt;
	}

	public void setReceipt(Receipt receipt) {
		this.receipt = receipt;
	}

}
