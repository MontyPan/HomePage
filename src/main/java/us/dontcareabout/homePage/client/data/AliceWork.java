package us.dontcareabout.homePage.client.data;

import us.dontcareabout.gwt.client.google.SheetEntry;

public final class AliceWork extends SheetEntry {
	protected AliceWork() {}

	public String getType() {
		return stringField("類型");
	}

	public String getArticle() {
		return stringField("文章名稱");
	}

	public String getBook() {
		return stringField("書籍名稱");
	}

	public String getPublish() {
		return stringField("出版資訊");
	}

	public Integer getYear() {
		return intField("年份");
	}
}
