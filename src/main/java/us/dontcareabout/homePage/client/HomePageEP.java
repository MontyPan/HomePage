package us.dontcareabout.homePage.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.RootPanel;
import com.sencha.gxt.widget.core.client.container.Viewport;

import us.dontcareabout.gwt.client.GFEP;
import us.dontcareabout.homePage.client.ui.FtlView;

public class HomePageEP extends GFEP {
	public HomePageEP() {}

	@Override
	protected String version() { return "0.0.1"; }

	@Override
	protected String defaultLocale() { return "zh_TW"; }

	@Override
	protected void featureFail() {
		Window.alert("這個瀏覽器我不尬意，不給用..... \\囧/");
	}

	@Override
	protected void start() {
		Viewport vp = new Viewport();
		RootPanel.get().add(vp);

		switch(Location.getPath()) {
		case "/ps/ftl.html":
			vp.add(new FtlView());
			break;
		}
	}
}
