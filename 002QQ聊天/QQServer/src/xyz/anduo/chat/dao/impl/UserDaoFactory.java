package xyz.anduo.chat.dao.impl;

import xyz.anduo.chat.dao.UserDao;

public class UserDaoFactory {
	private static UserDao dao;

	public static UserDao getInstance() {
		if (dao == null) {
			dao = new UserDaoImpl();
		}
		return dao;
	}
}
