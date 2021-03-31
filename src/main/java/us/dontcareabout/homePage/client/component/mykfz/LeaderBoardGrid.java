package us.dontcareabout.homePage.client.component.mykfz;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.data.shared.SortDir;
import com.sencha.gxt.data.shared.Store.StoreSortInfo;
import com.sencha.gxt.widget.core.client.grid.CheckBoxSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;

import us.dontcareabout.gxt.client.component.Grid2;
import us.dontcareabout.homePage.client.data.Mykfz;
import us.dontcareabout.homePage.client.ui.MykfzView;

public class LeaderBoardGrid extends Grid2<Mykfz> {
	private static final Properties properties = GWT.create(Properties.class);

	private CheckBoxSelectionModel<Mykfz> selectionModel = new CheckBoxSelectionModel<>(new IdentityValueProvider<>());

	public LeaderBoardGrid() {
		init();

		selectionModel.addSelectionChangedHandler(new SelectionChangedHandler<Mykfz>() {
			@Override
			public void onSelectionChanged(SelectionChangedEvent<Mykfz> event) {
				MykfzView.selectPlayerChange(event.getSelection());
			}
		});

		setSelectionModel(selectionModel);
		getView().setForceFit(true);
	}

	public void refresh(List<Mykfz> data) {
		if (data.isEmpty()) {
			store.clear();
			mask("尚未有資料");
			return;
		}

		unmask();
		HashMap<String, Mykfz> map = new HashMap<>();

		for (Mykfz mykfz : data) {
			Mykfz inMap = map.get(mykfz.getPlayer());

			if (inMap == null) {
				map.put(mykfz.getPlayer(), mykfz);
			} else {
				if (ChartUtil.outputWeight(mykfz.getLevel(), mykfz.getMantissa()) > ChartUtil.outputWeight(inMap.getLevel(), inMap.getMantissa())) {
					map.put(mykfz.getPlayer(), mykfz);
				}
			}
		}

		store.replaceAll(new ArrayList<>(map.values()));
		selectionModel.selectAll();
	}
	@Override
	protected ListStore<Mykfz> genListStore() {
		ListStore<Mykfz> result = new ListStore<>(properties.key());
		result.addSortInfo(new StoreSortInfo<>(new Comparator<Mykfz>() {
			@Override
			public int compare(Mykfz o1, Mykfz o2) {
				if (o1.getLevel() != o2.getLevel()) { return o1.getLevel() - o2.getLevel(); }
				return (int)(o1.getMantissa() - o2.getMantissa());
			}
		}, SortDir.DESC));
		return result;
	}

	@Override
	protected ColumnModel<Mykfz> genColumnModel() {
		ColumnConfig<Mykfz, String> name = new ColumnConfig<>(properties.player(), 10, "隊員名稱");

		ArrayList<ColumnConfig<Mykfz, ?>> list = new ArrayList<>();
		list.add(selectionModel.getColumn());
		list.add(name);

		ColumnModel<Mykfz> result = new ColumnModel<>(list);
		return result;
	}

	interface Properties extends PropertyAccess<Mykfz> {
		@Path("player")
		ModelKeyProvider<Mykfz> key();

		ValueProvider<Mykfz, String> player();
	}
}
