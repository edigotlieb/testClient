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


		String appName = "MasterApplication";
		Client c = new Client("ld34.hopto.org", 4850, "testApp", "", "idanb55", "12345678");

		//String[] data = {"testApp", "edinotcookie", "col1", "INT", "20", "true", "false", "col2", "VARCHAR", "11", "true", "false"};
		//System.out.println(c.sendRequest(TYPE.APP, Client.APP_ACTION_TYPE.ADD_TABLE, data));
		
		//String[] data = { "testApp", "edinotcookie" };
		//System.out.println(c.sendRequest(TYPE.APP, Client.APP_ACTION_TYPE.DROP_TABLE, data));
		
		String[] data = { };
		System.out.println(c.sendRequest(TYPE.APP, Client.APP_ACTION_TYPE.GET_ALL_APPS, data));
		
		//String[] data = { "tuser", "11", "haha","hahahah dsdf", "id@f.d","34","245"};
		//System.out.println(c.sendRequest(TYPE.USER, Client.USER_ACTION_TYPE.SIGN_UP, data));
	}
}
