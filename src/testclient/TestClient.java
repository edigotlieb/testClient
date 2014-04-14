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
		//Pedig1506
		Client c = new Client("85.250.72.192", 4850, "testApp", "", "edi.gotlieb", "Pedig1506");

		String[] data = {"shagmach", "iddobron"};



		System.out.println(c.sendRequest(TYPE.APP, Client.APP_ACTION_TYPE.SET_PERMISSIONGROUP_ADMIN, data));


	}
}
