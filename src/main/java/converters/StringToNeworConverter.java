
package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.NeworRepository;
import domain.Newor;

@Component
@Transactional
public class StringToNeworConverter implements Converter<String, Newor> {

	@Autowired
	NeworRepository	neworRepository;


	@Override
	public Newor convert(String text) {
		Newor result;
		int id;

		try {
			if (StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = neworRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
