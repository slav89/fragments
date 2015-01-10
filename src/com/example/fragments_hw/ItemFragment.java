package com.example.fragments_hw;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class ItemFragment extends Fragment implements OnEditorActionListener,
		OnClickListener {

	String currentItem;
	private static final String CITY_KEY = "cityKey";
	private static final String POSITION_KEY = "posKey";
	CityUpdateListnener mCallBack;
	EditText edit;
	private int position;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		try {
			mCallBack = (CityUpdateListnener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ "Activity should implement CityUpdateListnener!");
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		currentItem = (String) getArguments().get(CITY_KEY);
		position = getArguments().getInt(POSITION_KEY);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.item_fragment, container, false);
		edit = (EditText) v.findViewById(R.id.edit);
		Button btn = (Button) v.findViewById(R.id.saveBtn);
		btn.setOnClickListener(this);
		edit.setText(currentItem);
		edit.setOnEditorActionListener(this);
		if (edit.requestFocus()) {
			InputMethodManager imm = (InputMethodManager) getActivity()
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.toggleSoftInputFromWindow(edit.getWindowToken(), 0, 0);
		}
		return v;
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

		if (event == null || event.getAction() == KeyEvent.ACTION_DOWN)
			passItemBack();
		return true;
	}

	private void passItemBack() {
		InputMethodManager imm = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(edit.getWindowToken(), 0);
		mCallBack.onCityEdited(edit.getText().toString(), position);
	}

	private void cancelEdit() {
		InputMethodManager imm = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(edit.getWindowToken(), 0);
		mCallBack.onCityEditCancelled();
	}

	@Override
	public void onClick(View v) {
		passItemBack();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {

		case R.id.save:
			passItemBack();
			break;
		case R.id.cancel:
			cancelEdit();
		}
		return true;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		inflater.inflate(R.menu.item_actionbar, menu);

	}

}
