
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Receipt;

@Component
@Transactional
public class ReceiptToStringConverter implements Converter<Receipt, String> {

	@Override
	public String convert(Receipt receipt) {

		String result;
		if (receipt == null) {
			result = null;
		} else {
			result = String.valueOf(receipt.getId());
		}
		return result;
	}

}
