package xyz.anduo.intent;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	/** 按钮1 */
	private Button mButton1;

	/** 按钮2 */
	private Button mButton2;

	/** 按钮3 */
	private Button mButton3;

	/** 按钮4 */
	private Button mButton4;

	/** 按钮5 */
	private Button mButton5;


	/** 全局变量 */
	private MyApp myApp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.mButton1 = (Button) findViewById(R.id.button1);
		this.mButton2 = (Button) findViewById(R.id.button2);
		this.mButton3 = (Button) findViewById(R.id.button3);
		this.mButton4 = (Button) findViewById(R.id.button4);
		this.mButton5 = (Button) findViewById(R.id.button5);
		// 为按钮添加监听事件
		this.mButton1.setOnClickListener(MainActivity.this);
		this.mButton2.setOnClickListener(MainActivity.this);
		this.mButton3.setOnClickListener(MainActivity.this);
		this.mButton4.setOnClickListener(MainActivity.this);
		this.mButton5.setOnClickListener(MainActivity.this);
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.button1) {
			/** 是用全局变量传递数据*/
			this.myApp = (MyApp) getApplication();
			this.myApp.setName("anduo");
			Intent intent = new Intent(MainActivity.this, Other1Activity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.button2) {
			/** 从android操作系统的剪切板传递数据 */
			ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

			// 1.传递文本对象
			// CharSequence name= "anduo";
			// clipboardManager.setText(name);

			// 2.传递序列化对象
			MyData data = new MyData("anduo", 25);
			// 将对象转换为字符串
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			String base64String = "";
			try {
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
				objectOutputStream.writeObject(data);
				base64String = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
				objectOutputStream.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
			clipboardManager.setText(base64String);

			Intent intent = new Intent(MainActivity.this, Other2Activity.class);
			startActivity(intent);
			
		} else if (v.getId() == R.id.button3) {
			/** 通用的意图之间传递数据 */
			Intent intent = new Intent(MainActivity.this, Other3Activity.class);
			intent.putExtra("name", "anduo");
			intent.putExtra("age", 25);
			startActivity(intent);
		} else if (v.getId() == R.id.button4) {
			/**静态变量传递数据*/
			Intent intent = new Intent(MainActivity.this, Other4Activity.class);
			Other4Activity.statica = "aaa";
			Other4Activity.staticb = "bbb";
			startActivity(intent);
		} else if (v.getId() == R.id.button5) {
			/**获得activity的数据返回*/
			Intent intent = new Intent(MainActivity.this, Other5Activity.class);
			startActivityForResult(intent, RESULT_OK);
		}

	}
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// 注册一个回调函数 startActivityForResult 调用页面返回时发生。
		// requestCode startActivityForResult他后面的常数 ，resultCode 被调用页面返回时传递的常数
		// 这两个常数可以判断是谁在上面时间调用，可以作为判断标志。data 返回携带信息
		if (requestCode == RESULT_OK) {
			Bundle bundle = data.getExtras();
			String back = bundle.getString("back");
			Toast.makeText(MainActivity.this, back, Toast.LENGTH_LONG).show();
		}
	}

}
