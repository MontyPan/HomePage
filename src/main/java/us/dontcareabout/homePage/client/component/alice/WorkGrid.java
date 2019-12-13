package us.dontcareabout.homePage.client.component.alice;

import java.util.ArrayList;

import com.google.gwt.core.shared.GWT;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.data.shared.SortDir;
import com.sencha.gxt.data.shared.Store.StoreSortInfo;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;

import us.dontcareabout.gxt.client.component.Grid2;
import us.dontcareabout.homePage.client.data.AliceWork;

public class WorkGrid extends Grid2<AliceWork> {
	private static Properties properties = GWT.create(Properties.class);

	public WorkGrid() {
		init();
		getView().setForceFit(true);
	}

	public void refresh(ArrayList<AliceWork> data) {
		getStore().clear();
		getStore().addAll(data);
	}

	@Override
	protected ListStore<AliceWork> genListStore() {
		ListStore<AliceWork> result = new ListStore<>(new ModelKeyProvider<AliceWork>() {
			@Override
			public String getKey(AliceWork item) {
				return item.getYear() + item.getArticle();
			}
		});
		result.addSortInfo(new StoreSortInfo<>(properties.year(), SortDir.DESC));
		return result;
	}

	@Override
	protected ColumnModel<AliceWork> genColumnModel() {
		ArrayList<ColumnConfig<AliceWork, ?>> list = new ArrayList<>();
		list.add(new ColumnConfig<>(properties.year(), 50, "年份"));
		list.add(new ColumnConfig<>(properties.type(), 100, "類型"));
		list.add(new ColumnConfig<>(properties.article(), 200, "文章名稱"));
		list.add(new ColumnConfig<>(properties.book(), 200, "出處"));
		list.add(new ColumnConfig<>(properties.publish(), 100, "出版資訊"));

		ColumnModel<AliceWork> result = new ColumnModel<>(list);
		return result;
	}

	interface Properties extends PropertyAccess<AliceWork> {
		ValueProvider<AliceWork, String> type();
		ValueProvider<AliceWork, String> article();
		ValueProvider<AliceWork, String> book();
		ValueProvider<AliceWork, String> publish();
		ValueProvider<AliceWork, Integer> year();
	}

}
