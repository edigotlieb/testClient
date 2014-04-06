package testclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author edi
 */
public class Client {

    public final static String REQUEST_FORMAT = "{ \"RequesterCredentials\": { \"appName\":\"%s\" , \"appKey\":\"%s\" , \"username\":\"%s\", \"password\":\"%s\" } "
            + ", \"RequestInfo\": { \"requestType\":\"%s\" , \"requestAction\":\"%s\" } ,"
            + "\"RequestData\": %s }";

    public final static String SIGN_UP_FORMAT = "{ \"username\":\"%s\",\"password\":\"%s\",\"name\":\"%s\","
            + "\"display_name\":\"%s\",\"email\":\"%s\",\"year\":\"%s\",\"room\":\"%s\" }";
    public final static String SIGN_IN_FORMAT = "{}";
    
    public final static String UPDATE_INFO_FORMAT = "{ \"username\":\"%s\",\"new_name\":\"%s\","
            + "\"new_display_name\":\"%s\",\"new_email\":\"%s\",\"new_year\":\"%s\",\"new_room\":\"%s\" }";
    
    
    public final static HashMap<USER_ACTION_TYPE, String> userAction;
    public final static HashMap<APP_ACTION_TYPE, String> appAction;
    public final static HashMap<DTD_ACTION_TYPE, String> DTDAction;

    static {
        userAction = new HashMap<>();
        appAction = new HashMap<>();
        DTDAction = new HashMap<>();

        userAction.put(USER_ACTION_TYPE.SIGN_UP, SIGN_UP_FORMAT);
        userAction.put(USER_ACTION_TYPE.SIGN_IN, SIGN_IN_FORMAT);
    }

    private static final int INITIAL_TIME_OUT = 1000;
    private static final int SECONDARY_TIME_OUT = 3000;
    private static final int SLEEP_LENGTH = 10;

    private Socket socket;
    private final String dest;
    private final int port;

    BufferedReader reader;
    PrintWriter out;

    private final String appName, appKey, username, password;

    private String hashedAppKey, hashedPassword;

    public static enum TYPE {

        USER, APP, DTD
    }

    public enum APP_ACTION_TYPE {

        ADD_TABLE, DROP_TABLE, DELETE_APP, ADD_PERMISSIONGROUP, REMOVE_PERMISSIONGROUP,
        CREATE_APP, GET_TABLE_INFO, GET_TABLES, SET_PERMISSIONGROUP_ADMIN, ADD_PERMISSION_GROUP_FOR_TABLE, REMOVE_PERMISSION_GROUP_FOR_TABLE;
    }

    public enum DTD_ACTION_TYPE {

        SELECT, INSERT, UPDATE, DELETE
    }

    public enum USER_ACTION_TYPE {

        SIGN_UP, SIGN_IN, UPDATE_INFO, ADD_PERMISSION, SELECT, UPDATE_PASSWORD, REMOVE_PERMISSION, GET_GROUPS
    }

    public Client(String dest, int port, String appName, String appKey, String username, String password) throws IOException {
        this.dest = dest;
        this.port = port;
        this.appName = appName;
        this.appKey = appKey;
        this.username = username;
        this.password = password;
    }

    private String openSocket(int timeout) throws IOException, InterruptedException {
        this.socket = new Socket(this.dest, this.port);
        reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        out = new PrintWriter(this.socket.getOutputStream());
        Thread.sleep(timeout);
        if (!reader.ready()) {
            return null;
        }
        String chal = reader.readLine();
        this.hashedAppKey = Utilities.Hashing.MD5Hash(Utilities.Hashing.MD5Hash(this.appKey) + chal);
        this.hashedPassword = Utilities.Hashing.MD5Hash(Utilities.Hashing.MD5Hash(this.password) + chal);
        return chal;
    }

    public String sendRequest(TYPE type, Object action, String[] data) throws IOException, InterruptedException {
        if(data == null) {
            data = new String[0];
        }
        String dataFormat;
        switch (type) {
            case APP: {
                APP_ACTION_TYPE actionType = (APP_ACTION_TYPE) action;
                dataFormat = String.format(appAction.get(actionType), (Object[]) data);
            }
            break;
            case DTD: {
                DTD_ACTION_TYPE actionType = (DTD_ACTION_TYPE) action;
                dataFormat = String.format(DTDAction.get(actionType), (Object[]) data);
            }
            break;
            case USER: {
                USER_ACTION_TYPE actionType = (USER_ACTION_TYPE) action;
                dataFormat = String.format(userAction.get(actionType), (Object[]) data);
            }
            break;
            default: {
                return "bad type";
            }
        }
        
        String chal = openSocket(INITIAL_TIME_OUT);

        if (chal == null) {
            return "timeout";
        }
        
        String requset = String.format(REQUEST_FORMAT, appName, hashedAppKey, username, hashedPassword, TYPE.USER, action.toString(), dataFormat);

        this.out.println(requset);
        this.out.flush();

        int i = 0;
        while (!reader.ready()) {
            //System.out.println("waiting...");
            //System.exit(0);
            Thread.sleep(SLEEP_LENGTH);
            i++;
            if (i > SECONDARY_TIME_OUT / SLEEP_LENGTH) {
                return "timeout";
            }
        }

        String result = this.reader.readLine();
        this.socket.close();
        System.out.println("RESPONSE TIME:" + i*SLEEP_LENGTH+" MS");
        return result;
    }


}
