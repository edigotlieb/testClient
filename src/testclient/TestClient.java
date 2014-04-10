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
import testclient.Client.USER_ACTION_TYPE;
public class TestClient {

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {
                
                
        String appName = "MasterApplication";
        
        Client c = new Client("ld34.hopto.org", 4850, appName, "appkey", "Anonymous", "");
        
        String[] data = {"USERNAME","=","'edi.gotlieb'"};
        
        
        
        System.out.println(c.sendRequest(TYPE.USER, USER_ACTION_TYPE.SELECT, data));
        
        
    }
    
}
