package xyz.anduo.client;

import xyz.anduo.chat.common.tran.bean.TranObject;

/**
 * 消息监听接口
 * 
 * @author way
 * 
 */
public interface MessageListener {
	public void Message(TranObject msg);
}
