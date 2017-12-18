
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.QuestionRepository;
import domain.Question;

@Component
@Transactional
public class StringToQuestionConverter implements Converter<String, Question> {

	@Autowired
	QuestionRepository	questionRepository;


	@Override
	public Question convert(String text) {
		Question result;
		int id;

		try {
			if (StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = questionRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
