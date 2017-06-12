package util;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;

public class IntegerJQueryTextField extends TextField<Integer>{
	private static final long serialVersionUID = 1L;
	
	public IntegerJQueryTextField(String id) {
		super(id);
		add(new AttributeModifier("OnKeypress", "return verificaNumero(event);"));
	}
	
	public IntegerJQueryTextField(String id, IModel<Integer> model) {
		super(id, model);
		add(new AttributeModifier("OnKeypress", "return verificaNumero(event);"));
	}
}
