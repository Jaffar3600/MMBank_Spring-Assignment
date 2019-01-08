package com.cg.app;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cg.app.account.ui.AccountCUI;

public class Bootstrap {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
		AccountCUI accountcui = context.getBean(AccountCUI.class);
		accountcui.start();
	}

}
