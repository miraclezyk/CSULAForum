package com.csula.forum;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	EditText uname;
	EditText pass;
	String result;
	String[] res;
	public static final String MyPREFERENCES = "MyPrefs" ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Button login = (Button) findViewById(R.id.btnLogin);
        TextView registerScreen = (TextView) findViewById(R.id.link_to_register);
         uname =(EditText) findViewById(R.id.uname);
         pass = (EditText) findViewById(R.id.pass);
        //StrictMode.enableDefaults(); //STRICT MODE ENABLED
        //getData();
        // Listening to register new account link
        login.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 try{
			            String username = uname.getText().toString();
			            String password = pass.getText().toString();
			            String link="http://gaurav.byethost3.com/CSULAForum/login.php";//"http://10.0.2.2:80/CSULAForum/login.php";
			            String data  = URLEncoder.encode("username", "UTF-8") 
			            + "=" + URLEncoder.encode(username, "UTF-8");
			            data += "&" + URLEncoder.encode("password", "UTF-8") 
			            + "=" + URLEncoder.encode(password, "UTF-8");
			            URL url = new URL(link);
			            URLConnection conn = url.openConnection(); 
			            conn.setDoOutput(true); 
			            OutputStreamWriter wr = new OutputStreamWriter
			            (conn.getOutputStream()); 
			            wr.write( data ); 
			            wr.flush(); 
			            BufferedReader reader = new BufferedReader
			            (new InputStreamReader(conn.getInputStream()));
			            StringBuilder sb = new StringBuilder();
			            String line = null;
			            // Read Server Response
			            while((line = reader.readLine()) != null)
			            {
			               sb.append(line);
			               break;
			            }
			          //  int uid=0;
			            result= sb.toString();
			           
			         //   uid=Integer.parseInt(result);
			        // Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
			            //Log.i("result tag", result);
			            if(result.equals("")){
			            	 Toast.makeText(getApplicationContext(), "Invalid Username/Password", Toast.LENGTH_LONG).show();
			            }else{
			            	res=result.split(",");
			            	SharedPreferences pref = getSharedPreferences(MyPREFERENCES, 0);
			            	Editor editor = pref.edit();
			            	editor.putString("userid", res[0]);
			            	editor.putString("name", res[1]);
			            	editor.putString("major", res[2]);
			            	editor.commit();
			            	Intent i = new Intent(getApplicationContext(),Courses.class);
			            	startActivity(i);
			            }
			         }catch(Exception e){
			        	  Log.e("log_tag", "Error  converting result "+e.toString());
			         }
				
			}
		});
        registerScreen.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// Switching to Register screen
				Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
				startActivity(i);
			}
		});
    }

    
}
