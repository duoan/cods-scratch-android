package xyz.anduo.util;

import xyz.anduo.chat.common.util.Constants;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 实现SharedPreferences存储的步骤如下： 
 * 一、根据Context获取SharedPreferences对象
 * 二、利用edit()方法获取Editor对象。 　　
 * 三、通过Editor对象存储key-value键值对数据。 
 * 四、通过commit()方法提交数据。
 * 
 * @author anduo
 * 
 */
public class SharePreferenceUtil {
	private SharedPreferences sp;
	private SharedPreferences.Editor editor;

	public SharePreferenceUtil(Context context, String file) {
		// 根据Context获取SharedPreferences对象
		sp = context.getSharedPreferences(file, Context.MODE_PRIVATE);
		// 利用edit()方法获取Editor对象。
		editor = sp.edit();
	}

	// 用户的密码
	public void setPasswd(String passwd) {
		// 通过Editor对象存储key-value键值对数据。
		editor.putString("passwd", passwd);
		// 通过commit()方法提交数据。
		editor.commit();
	}

	public String getPasswd() {
		return sp.getString("passwd", "");
	}

	// 用户的id，即QQ号
	public void setId(String id) {
		editor.putString("id", id);
		editor.commit();
	}

	public String getId() {
		return sp.getString("id", "");
	}

	// 用户的昵称
	public String getName() {
		return sp.getString("name", "");
	}

	public void setName(String name) {
		editor.putString("name", name);
		editor.commit();
	}

	// 用户的邮箱
	public String getEmail() {
		return sp.getString("email", "");
	}

	public void setEmail(String email) {
		editor.putString("email", email);
		editor.commit();
	}

	// 用户自己的头像
	public Integer getImg() {
		return sp.getInt("img", 0);
	}

	public void setImg(int i) {
		editor.putInt("img", i);
		editor.commit();
	}

	// ip
	public void setIp(String ip) {
		editor.putString("ip", ip);
		editor.commit();
	}

	public String getIp() {
		return sp.getString("ip", Constants.SERVER_IP);
	}

	// 端口
	public void setPort(int port) {
		editor.putInt("port", port);
		editor.commit();
	}

	public int getPort() {
		return sp.getInt("port", Constants.SERVER_PORT);
	}

	// 是否在后台运行标记
	public void setIsStart(boolean isStart) {
		editor.putBoolean("isStart", isStart);
		editor.commit();
	}

	public boolean getIsStart() {
		return sp.getBoolean("isStart", false);
	}

	// 是否第一次运行本应用
	public void setIsFirst(boolean isFirst) {
		editor.putBoolean("isFirst", isFirst);
		editor.commit();
	}

	public boolean getisFirst() {
		return sp.getBoolean("isFirst", true);
	}
}
