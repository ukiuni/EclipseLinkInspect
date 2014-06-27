package org.ukiuni.jpaInspect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class SelectMain {
	public static void main(String[] args) throws Exception {
		Class.forName("org.h2.Driver");
		Connection conn = DriverManager.getConnection("jdbc:h2:file:./db/myH2.db", "test", "test");
		Statement statement = conn.createStatement();
		String[] selects = { "select * from item", "select * from cart", "select * from INFORMATION_SCHEMA.tables", "select * from CART_ITEM" };
		for (String query : selects) {

			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				ResultSetMetaData meta = result.getMetaData();
				for (int i = 1; i <= meta.getColumnCount(); i++) {
					System.out.print(meta.getTableName(i) + ": " + meta.getColumnName(i) + "(" + meta.getColumnClassName(i) + ") = " + result.getString(i) + ",");
				}
				System.out.println();
			}
			System.out.println();
		}
	}
}
