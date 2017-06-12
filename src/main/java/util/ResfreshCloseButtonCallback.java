package util;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow.CloseButtonCallback;

public abstract class ResfreshCloseButtonCallback implements CloseButtonCallback, IAjaxIndicatorAware{
	private static final long serialVersionUID = 1L;

	@Override
	public boolean onCloseButtonClicked(AjaxRequestTarget target) {
		target.appendJavaScript("var DIV = document.createElement('DIV');$(DIV).attr('class','jGrowl-notification');$('#divFeedbackJGrowl').find('div.jGrowl-notification').remove();$('#divFeedbackJGrowl').append(DIV);");
		return true;
	}
	
	public String getAjaxIndicatorMarkupId() {
		return "indicator";
	}
	
}
