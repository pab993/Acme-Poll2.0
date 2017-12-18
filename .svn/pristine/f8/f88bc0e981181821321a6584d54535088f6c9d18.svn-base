
package forms;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

import domain.Answer;

public class PollForm {

	public PollForm() {
		super();
	}

	public PollForm(int tam, int id) {
		super();
		for (int i = 0; i < tam; i++) {
			listOfAnswers.add(new Answer());
		}
		pollId = id;

	}

	public PollForm(int tam, int id, String ci, String ge, String na) {
		super();
		for (int i = 0; i < tam; i++) {
			listOfAnswers.add(new Answer());
		}
		pollId = id;
		name = na;
		genre = ge;
		city = ci;

	}


	private Collection<Answer>	listOfAnswers	= new ArrayList<Answer>();
	private int					pollId;
	private String				name;
	private String				genre;
	private String				city;


	@Valid
	public Collection<Answer> getListOfAnswers() {
		return listOfAnswers;
	}

	public void setListOfAnswers(Collection<Answer> listOfAnswers) {
		this.listOfAnswers = listOfAnswers;
	}

	@NotNull
	public int getPollId() {
		return pollId;
	}

	public void setPollId(int pollId) {
		this.pollId = pollId;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Size(min = 0, max = 250)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Size(min = 0, max = 250)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}
