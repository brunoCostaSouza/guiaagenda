package util;

import org.apache.wicket.AttributeModifier;

public class CpfJQueryAttributeModifier extends AttributeModifier{
	private static final long serialVersionUID = 1L;
	
	public CpfJQueryAttributeModifier() {
		super("onfocus", "$(this).setMask('cpf');");
	}
}
