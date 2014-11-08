package xyz.anduo.intent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Other5Activity extends Activity {

	private Button mButton1;

	/** 静态变量 */
	public static String statica;
	public static String staticb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_other5);
		this.mButton1 = (Button) findViewById(R.id.button1);
		this.mButton1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Other5Activity.this,MainActivity.class);
				intent.putExtra("back", "第二个页面返回 ");
				setResult(RESULT_OK, intent);
				finish();
			}
		});

	}

}
