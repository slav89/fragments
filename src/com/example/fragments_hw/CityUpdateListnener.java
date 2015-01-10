package com.example.fragments_hw;

public interface CityUpdateListnener {
	
	public void onCityEditCancelled();
	void onCityEdited(String newName, int position);
	void onCitySelected(String name, int position);

}
