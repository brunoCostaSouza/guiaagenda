package util;

import java.util.Locale;

import org.apache.wicket.AttributeModifier;

public class DataJQueryAttributeModifier extends AttributeModifier{
	private static final long serialVersionUID = 1L;
	
	public DataJQueryAttributeModifier(Locale locale) {
		super("onfocus", getFormat(locale));
	}
	
	private static String getFormat(Locale locale){
		if(locale.getCountry().equals("US")){
			return "$(this).setMask('date-us');"; //'19/39/9999'
		}else{
			return "$(this).setMask('date');"; //'39/19/9999'
		}
	}
}
