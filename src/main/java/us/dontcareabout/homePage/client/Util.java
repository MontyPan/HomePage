package us.dontcareabout.homePage.client;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.Window;

public class Util {
	public static final DateTimeFormat dateFormat = DateTimeFormat.getFormat("yyyy/MM/dd");
	public static NumberFormat numberFormat = NumberFormat.getFormat("##.#");

	private final static Window dialog = new Window();
	static {
		dialog.setModal(true);
		dialog.setResizable(false);
	}

	public static void closeDialog() {
		dialog.hide();
	}

	public static void showDialog(Widget widget, int width, int height) {
		dialog.clear();
		dialog.add(widget);
		dialog.show();
		dialog.setPixelSize(width, height);
		dialog.center();
	}
}
