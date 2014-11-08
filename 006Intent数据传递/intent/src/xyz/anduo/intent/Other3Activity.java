package xyz.anduo.intent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Other3Activity extends Activity {

	private TextView mTextView1;
	private TextView mTextView2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_other3);
		
		this.mTextView1 = (TextView) findViewById(R.id.textView1);
		this.mTextView2 = (TextView) findViewById(R.id.textView2);
		Intent intent = getIntent();
		String name = intent.getStringExtra("name");
		int age =  intent.getIntExtra("age", 0);
		mTextView1.setText(name);
		mTextView2.setText(String.valueOf(age));//这个地方一定要转换成String

	}

}
