package xyz.anduo.intent;

import android.app.Application;

public class MyApp extends Application {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public void onCreate() {
		/**这里初始化一个全局的变量*/
		super.onCreate();
		setName("张三");
	}

}
