
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Newor extends DomainEntity {

	// Attributes
	// =====================================================

	private String	mark;
	private Date	displayMoment;
	private String	title;
	private String	description;
	private Date	momentCreated;

	private String	justification;
	private boolean	cancel;


	// Constructor
	// =====================================================

	public Newor() {
		super();
	}

	// Getters & Setters
	// =====================================================

	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Pattern(regexp = "^[a-zA-Z0-9_]{3}[0-9]{3}$")
	public String getMark() {
		return this.mark;
	}

	public void setMark(final String mark) {
		this.mark = mark;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	@NotBlank
	@Size(min = 1, max = 20)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	@NotBlank
	@Size(min = 1, max = 100)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@NotNull
	public Date getDisplayMoment() {
		return this.displayMoment;
	}

	public void setDisplayMoment(final Date displayMoment) {
		this.displayMoment = displayMoment;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getJustification() {
		return this.justification;
	}

	public void setJustification(final String justification) {
		this.justification = justification;
	}

	public boolean getCancel() {
		return this.cancel;
	}

	public void setCancel(final boolean cancel) {
		this.cancel = cancel;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@NotNull
	@Past
	public Date getMomentCreated() {
		return momentCreated;
	}

	public void setMomentCreated(Date momentCreated) {
		this.momentCreated = momentCreated;
	}


	// Relationships
	// ====================================================================================

	private Administrator	administrator;
	private Poll			poll;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	//	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	//	@NotFound(action = NotFoundAction.IGNORE)
	//	@JoinColumn(mark = "administrator_id")
	public Administrator getAdministrator() {
		return this.administrator;
	}

	public void setAdministrator(final Administrator administrator) {
		this.administrator = administrator;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Poll getPoll() {
		return this.poll;
	}

	public void setPoll(Poll poll) {
		this.poll = poll;
	}

	//import javax.persistence.FetchType;
	//import javax.persistence.JoinColumn;
	//import org.hibernate.annotations.NotFound;
	//import org.hibernate.annotations.NotFoundAction;
	//import org.hibernate.validator.constraints.Range;

}
