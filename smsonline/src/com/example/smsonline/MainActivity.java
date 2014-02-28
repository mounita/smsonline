package com.example.smsonline;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	Button login;
    EditText email, pass;
    Button register,forget_pass;
    TextView error_msg;

    /** Called when the activity is first created. */

    private static String SOAP_ACTION1 = "http://tempuri.org/checkUserLogin";
    private static String NAMESPACE = "http://tempuri.org/";
    private static String METHOD_NAME1 = "checkUserLogin";
    private static String URL = "http://sms.dialy.net/DialyServices/SMSQWS.asmx";

//je suis un commentaire
  @Override

  public void onCreate(Bundle savedInstanceState)

  {
      super.onCreate(savedInstanceState);

      setContentView(R.layout.activity_main);
      
      login=(Button)findViewById(R.id.connecter);
      error_msg= (TextView)findViewById(R.id.login_error);
      email=(EditText)findViewById(R.id.email_address);
      pass=(EditText)findViewById(R.id.password);
      
      login.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			
			 SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME1);
		      request.addProperty("login",email.getText().toString());
		      request.addProperty("password",pass.getText().toString());

		      SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

		      envelope.dotNet=true;
		      
		      envelope.setOutputSoapObject(request);
		      
		      AndroidHttpTransport aht=new AndroidHttpTransport(URL);
		     
			   
		      try{
		   	   
		   	   aht.call(SOAP_ACTION1,envelope);
		   	   SoapPrimitive result=(SoapPrimitive)envelope.getResponse();
		   	Toast.makeText(getApplicationContext(), "le resultat est "+result, Toast.LENGTH_SHORT).show();
		   	  
		   	if(result.toString().equals("success"))
		   	{
		   		error_msg.setText("");
                Intent dashboard = new Intent(getApplicationContext(), DashboardActivity.class);
                 
                // Close all views before launching Dashboard
                dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(dashboard);
		   	}
		   	else
		   	{
		   		error_msg.setText("Incorrect username/password");
		   	}
		   	
		   	   
		         }

		     catch(Exception e){e.printStackTrace();}
			
		}
	});

     
   
}}
