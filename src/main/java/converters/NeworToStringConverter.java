
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Newor;

@Component
@Transactional
public class NeworToStringConverter implements Converter<Newor, String> {

	@Override
	public String convert(Newor newor) {

		String result;
		if (newor == null) {
			result = null;
		} else {
			result = String.valueOf(newor.getId());
		}
		return result;
	}

}
