package xyz.anduo.intent;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Other1Activity extends Activity {

	private MyApp myApp;
	private TextView mTextView1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_other1);
		this.mTextView1 = (TextView) findViewById(R.id.textView1);

		this.myApp = (MyApp) getApplication();
		this.mTextView1.setText(this.myApp.getName());
	}

}
