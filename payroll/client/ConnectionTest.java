package com.cg.payroll.client;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cg.payroll.util.ConnectionProvider;

public class ConnectionTest {
	public static void main(String[] args){
		if(ConnectionProvider.getdBConnection()!=null)
			System.out.println("Connection Open");
		else
			System.out.println("Some Problem");
	}
}
