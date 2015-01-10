package com.example.fragments_hw;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity implements CityUpdateListnener {

	ItemFragment itemFrag;
	ItemsListFragment ilFrag;
	private static final String CITY_KEY = "cityKey";
	private static final String POSITION_KEY = "posKey";
	private static final String ITEMSLISTFRAG_TAG = "ilfTag";



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getFragmentManager().findFragmentByTag(ITEMSLISTFRAG_TAG) == null) {

			ilFrag = new ItemsListFragment();
			getFragmentManager().beginTransaction()
					.add(android.R.id.content, ilFrag, ITEMSLISTFRAG_TAG).commit();
		}
		else
			ilFrag = (ItemsListFragment) getFragmentManager().findFragmentByTag(ITEMSLISTFRAG_TAG);
	}

	@Override
	public void onCitySelected(String name, int position) {
		ItemFragment itemFrag = new ItemFragment();
		Bundle args = new Bundle();
		args.putString(CITY_KEY, name);
		args.putInt(POSITION_KEY, position);
		itemFrag.setArguments(args);
		getFragmentManager().beginTransaction()
				.replace(android.R.id.content, itemFrag).addToBackStack(null)
				.commit();
	}

	@Override
	public void onCityEdited(String newName, int position) {
		ilFrag.setNewCity(newName, position);
		getFragmentManager().popBackStack();

	}

	@Override
	public void onCityEditCancelled() {
		getFragmentManager().popBackStack();
	}


}
