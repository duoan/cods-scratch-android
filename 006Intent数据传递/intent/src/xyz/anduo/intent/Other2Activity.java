package xyz.anduo.intent;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Base64;
import android.widget.TextView;

public class Other2Activity extends Activity {

	private TextView mTextView1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_other1);
		this.mTextView1 = (TextView) findViewById(R.id.textView1);

		ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
		// 1.从剪切板中获得字符串
		String name = clipboardManager.getText().toString();
		//this.mTextView1.setText(name);

		byte[] base64Byte = Base64.decode(name, Base64.DEFAULT);
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(base64Byte);
		try {
			ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
			MyData myData = (MyData) objectInputStream.readObject();
			
			this.mTextView1.setText(myData.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
