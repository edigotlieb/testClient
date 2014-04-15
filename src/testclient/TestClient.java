/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testclient;

/**
 *
 * @author edi
 */
import java.io.IOException;
import testclient.Client.TYPE;

public class TestClient {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws IOException, InterruptedException {

		Client c = new Client("ld34.hopto.org", 4850, "testApp", "testKey", "idanb55", "עברית");

		//String[] data = { "idanb55","עברית" };
		//System.out.println(c.sendRequest(TYPE.USER, Client.USER_ACTION_TYPE.UPDATE_PASSWORD, data));
		
//		String[] data = {"testApp", "berkotable",
//			"col_int", "INT", "11", "true", "true",
//			"col_varchar", "VARCHAR", "50", "true", "false",
//			"col_date", "DATE", "0", "false", "false",
//			"col2_timestamp", "TIMESTAMP", "11", "false", "false"};
//		System.out.println(c.sendRequest(TYPE.APP, Client.APP_ACTION_TYPE.ADD_TABLE, data));	
//		
		//String[] data = {"testApp","edicookie"};
		//System.out.println(c.sendRequest(TYPE.APP, Client.APP_ACTION_TYPE.DROP_TABLE, data));

		//String[] data = { "testApp", "a2e4822a98337283e39f7b60acf85ec9" };
		//System.out.println(c.sendRequest(TYPE.APP, Client.APP_ACTION_TYPE.CREATE_APP, data));

		String[] data = { "berkotable", "col_int", "DESC"};
		System.out.println(c.sendRequest(TYPE.DTD, Client.DTD_ACTION_TYPE.SELECT, data));
	}
}
