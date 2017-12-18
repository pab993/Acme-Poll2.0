
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Receipt extends DomainEntity {

	//Attributes 
	// =================================================================

	private String	pdf;
	private boolean	endorsed;


	@SafeHtml(whitelistType = WhiteListType.NONE)
	@URL
	@NotBlank
	@Size(min = 0, max = 250)
	public String getPdf() {
		return pdf;
	}

	public void setPdf(String pdf) {
		this.pdf = pdf;
	}

	public boolean getEndorsed() {
		return endorsed;
	}

	public void setEndorsed(boolean endorsed) {
		this.endorsed = endorsed;
	}


	//Relationships
	// =================================================================

	private Bill	bill;


	@OneToOne(optional = false)
	@Valid
	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

}
