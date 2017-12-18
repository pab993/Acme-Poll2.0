
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Poll;

@Component
@Transactional
public class PollToStringConverter implements Converter<Poll, String> {

	@Override
	public String convert(Poll poll) {

		String result;
		if (poll == null) {
			result = null;
		} else {
			result = String.valueOf(poll.getId());
		}
		return result;
	}
}
