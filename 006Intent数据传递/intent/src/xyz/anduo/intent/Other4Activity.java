package xyz.anduo.intent;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Other4Activity extends Activity {

	private TextView mTextView1;
	private TextView mTextView2;
	
	/**静态变量*/
	public static String statica;
	public static String staticb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_other4);
		
		this.mTextView1 = (TextView) findViewById(R.id.textView1);
		this.mTextView2 = (TextView) findViewById(R.id.textView2);
		
		mTextView1.setText(statica);
		mTextView2.setText(staticb);

	}

}
