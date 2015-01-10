package com.example.fragments_hw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ItemsListFragment extends ListFragment {

	String[] items = new String[] { "Brussels", "Paris", "London" };
	ArrayList<String> capitals;
	ArrayList<String> adapterList;
	ArrayAdapter<String> adapter;
	private int citiesCount;
	private CityUpdateListnener mCallback;

	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		
		try {
			mCallback = (CityUpdateListnener) activity;
		}
		catch (ClassCastException e){
			throw new ClassCastException(activity.toString() + " Activity must implement CityUpdateListener");
		}
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		capitals = new ArrayList<String>();

		for (String item : items) {
			capitals.add(item);
		}

		adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, capitals);

		setHasOptionsMenu(true);

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		setListAdapter(adapter);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.add:
			try {
				addRandomCity();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case R.id.remAll:
			clearList();
		}
		return true;
	}

	private void clearList() {
		adapter.clear();
	}

	private void addRandomCity() throws IOException {

		if (adapterList == null) {
			adapterList = new ArrayList<String>();
			InputStream is = getActivity().getResources().openRawResource(
					R.raw.capitals);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String str;
			while ((str = br.readLine()) != null) {
				adapterList.add(str);
			}
			citiesCount = adapterList.size();
			Log.i("fff", "citiesCount = " + citiesCount);
		}
		String str;
		if (capitals.size() < citiesCount) {

			do {
				int randomNumber = new Random().nextInt(citiesCount);
				Log.i("fff", "randomNumber = " + randomNumber);
				str = adapterList.get(randomNumber);
			} while (capitals.contains(str));

			adapter.add(str);
		} else
			Toast.makeText(getActivity(), "All cities are already added!",
					Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

		inflater.inflate(R.menu.main, menu);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		String city = capitals.get(position);		
		mCallback.onCitySelected(city, position);
				
	}

	public void setNewCity(String newName, int position) {
		capitals.remove(position);
		capitals.add(position, newName);
		adapter.notifyDataSetChanged();
	}
}
