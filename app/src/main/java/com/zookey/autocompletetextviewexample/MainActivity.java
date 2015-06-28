package com.zookey.autocompletetextviewexample;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainActivity extends ActionBarActivity {
	private AutoCompleteTextView mAutoCompleteTextView;
	private ArrayList<String> addressList = new ArrayList();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mAutoCompleteTextView = initializeAutoCompleteTextView();
		setupAutoCompleteTextView(mAutoCompleteTextView);
	}

	private AutoCompleteTextView initializeAutoCompleteTextView() {
		return (AutoCompleteTextView) findViewById(R.id.activity_main_auto_complete_text_view);
	}

	public void setupAutoCompleteTextView(AutoCompleteTextView autoCompleteTextView) {
		ArrayAdapter<String>   adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, addressList);
		autoCompleteTextView.setThreshold(1);
		autoCompleteTextView.setAdapter(adapter);

		autoCompleteTextView.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(final CharSequence s, int start, int before, int count) {
				getAddressInfo(MainActivity.this, s.toString());
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}

	private void getAddressInfo(Context context, String locationName){
		Geocoder geocoder = new Geocoder(context, Locale.getDefault());

		try {
			List<Address> a = geocoder.getFromLocationName(locationName, 5);

			for(int i=0;i<a.size();i++){
				String city = a.get(0).getLocality();
				String country = a.get(0).getCountryName();
				String address = a.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
				addressList.add(address+", "+city+", "+country);
			}


		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
