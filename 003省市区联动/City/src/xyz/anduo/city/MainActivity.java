package xyz.anduo.city;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

	@SuppressWarnings("unused")
	private CityPicker cityPicker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.cityPicker = (CityPicker) findViewById(R.id.citypicker);
	}

}
