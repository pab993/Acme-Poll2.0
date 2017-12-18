
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.ReceiptRepository;
import domain.Receipt;

@Component
@Transactional
public class StringToReceiptConverter implements Converter<String, Receipt> {

	@Autowired
	ReceiptRepository	receiptRepository;


	@Override
	public Receipt convert(String text) {
		Receipt result;
		int id;

		try {
			if (StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = receiptRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
