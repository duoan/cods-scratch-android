package com.iteye.androidtoast;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		final Spinner mAnimSp = (Spinner) findViewById(R.id.animation_sp);
		Button mButton = (Button) findViewById(R.id.other_button);

		String[] ls = getResources().getStringArray(R.array.anim_type);
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < ls.length; i++) {
			list.add(ls[i]);
		}
		ArrayAdapter<String> animType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
		animType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mAnimSp.setAdapter(animType);
		mAnimSp.setSelection(0);

		mButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, OtherActivity.class);
				startActivity(intent);

				switch (mAnimSp.getSelectedItemPosition()) {
				case 0:
					overridePendingTransition(R.anim.fade, R.anim.hold);
					break;
				case 1:
					overridePendingTransition(R.anim.my_scale_action, R.anim.my_alpha_action);
					break;
				case 2:
					overridePendingTransition(R.anim.scale_rotate, R.anim.my_alpha_action);
					break;
				case 3:
					overridePendingTransition(R.anim.scale_translate_rotate, R.anim.my_alpha_action);
					break;
				case 4:
					overridePendingTransition(R.anim.scale_translate, R.anim.my_alpha_action);
					break;
				case 5:
					overridePendingTransition(R.anim.hyperspace_in, R.anim.hyperspace_out);
					break;
				case 6:
					overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
					break;
				case 7:
					overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
					break;
				case 8:
					overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
					break;
				case 9:
					overridePendingTransition(R.anim.wave_scale, R.anim.my_alpha_action);
					break;
				case 10:
					overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
					break;
				case 11:
					overridePendingTransition(R.anim.slide_up_in, R.anim.slide_down_out);
					break;
				}
			}
		});
	}
}