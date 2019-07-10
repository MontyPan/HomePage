package us.dontcareabout.homePage.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.Window;

import us.dontcareabout.gwt.client.GFEP;

public class HomePageEP extends GFEP {
	static final RpcServiceAsync rpc = GWT.create(RpcService.class);

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

	}
}
