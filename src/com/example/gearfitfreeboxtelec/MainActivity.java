package com.example.gearfitfreeboxtelec;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.samsung.android.sdk.cup.Scup;

public class MainActivity extends Activity {
	
	private final static String CODES_FILENAME = "gearfitfreetelec_codes";
	HashMap<String, String> codes = new HashMap<String, String>();
	String current_uid = null;
	String current_code = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		if (!mWifi.isConnected()) {

			AlertDialog.Builder alert = new AlertDialog.Builder(this);

			alert.setTitle("Wifi Not Connected - Closing");
			alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					System.exit(0);
				  }
				});
			alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
				
				@Override
				public void onDismiss(DialogInterface dialog) {
					System.exit(0);
					
				}
			});
			alert.show();
			return;
		}
		
		 
		
		Thread thread = new Thread(new Runnable(){
		    @Override
		    public void run() {
		        try {
		        	//testHTTP();
		        	//sendVolDown();
		        	getCurrentUid();
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
		});

		thread.start(); 
		try {
			thread.join(4000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		readCodesFile();

		if(current_uid != null && codes.containsKey(current_uid)){
			EditText txt = (EditText) findViewById(R.id.editText1);
			current_code = codes.get(current_uid);
			txt.setText(current_code);
		}
		//checkUID();
		Button bt = (Button) findViewById(R.id.okmens);
		bt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
					checkUID();
				
				}
			
			});

		
		Scup scup = new Scup();
		try {
		// initialize an instance of Scup
			scup.initialize(this);
			GearFitFreeboxRemoteDialog mHelloCupDialog = new GearFitFreeboxRemoteDialog(
					getApplicationContext(),this);
		} catch (Exception e) {
			Toast t = Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG);
			t.show();
		}
		
	}
	
	private void readCodesFile(){
		FileInputStream fis;
		try {
			fis = openFileInput(CODES_FILENAME);
			String toParse = convertStreamToString(fis);
			
			JSONArray jsonArray = new JSONArray(toParse);
			
			for(int i =0;i<jsonArray.length();i++){
				JSONObject obj = jsonArray.getJSONObject(i);
				String uid = obj.getString("uid");
				String code = obj.getString("code");
				codes.put(uid, code);
			}
			
	    	fis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
	}
	
	private void checkUID(){
		if(current_uid != null){
			String code = codes.get(current_uid);
			if(code == null){
				/*AlertDialog.Builder alert = new AlertDialog.Builder(this);

				alert.setTitle("Freebox Remote Code Required");
				alert.setMessage("Entrez le code telecommande de votre Freebox :");

				// Set an EditText view to get user input 
				final EditText input = new EditText(this);
				alert.setView(input);

				alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
				  MainActivity.this.current_code = ((EditText)input).getText().toString();
				  // Do something with value!
				  }
				});

				alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				  public void onClick(DialogInterface dialog, int whichButton) {
				    // Canceled.
				  }
				});

				alert.show();*/
				EditText txt = (EditText) findViewById(R.id.editText1);
				current_code = txt.getText().toString();
				if(current_code != null){
					codes.put(current_uid, current_code);
					updateCodesFile();
				}
			}
		}
	}
	
	private void updateCodesFile(){
		try{
	
			deleteFile(CODES_FILENAME);
			FileOutputStream fos = openFileOutput(CODES_FILENAME,Context.MODE_PRIVATE);
			
			JSONArray array = new JSONArray();
			for(Map.Entry<String, String> entry : codes.entrySet()){
				JSONObject obj = new JSONObject();
				obj.put("uid", entry.getKey());
				obj.put("code", entry.getValue());
				array.put(obj);
			}
			
	    	fos.write(array.toString().getBytes());
	    	fos.close();
	    	
	    	Toast t = Toast.makeText(getApplicationContext(), "Code Update OK", Toast.LENGTH_LONG);
			t.show();
	    	
	    	/*AlertDialog.Builder alert = new AlertDialog.Builder(this);

			alert.setTitle("Freebox Remote Code Required");
			alert.setMessage("Entrez le code telecommande de votre Freebox :");

			// Set an EditText view to get user input 
			final EditText input = new EditText(this);
			alert.setView(input);

			alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
			  MainActivity.this.current_code = ((EditText)input).getText().toString();
			  // Do something with value!
			  }
			});

			alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			  public void onClick(DialogInterface dialog, int whichButton) {
			    // Canceled.
			  }
			});

			alert.show();*/
		}
		catch(Exception e){
			
		}
	}

	
	public String sendOrder(String button,String isLong){
		if(current_code == null){
			return "Code Not Set";
		}
		try{
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet("http://hd1.freebox.fr/pub/remote_control?code="+current_code+"&key="+button+"&long="+isLong); 
        	HttpResponse response = httpclient.execute(httpget);
	        HttpEntity entity = response.getEntity();
	        
	        if (entity != null) {

	            // A Simple JSON Response Read
	            InputStream instream = entity.getContent();
	            String result= convertStreamToString(instream);
	            
	            //Toast t = Toast.makeText(getApplicationContext(),"RESP : "+result, Toast.LENGTH_LONG);
				//t.show();
	            // now you have the string representation of the HTML request
	            instream.close();
	            
	            if(result.contains("Forbidden")){
	            	return "Invalid Code";
	            }
	            
	            return "OK";
	        }
	        return "Communication Error";
			}
			catch(Exception e){
				e.printStackTrace();
				return e.getMessage();
			}
	}
	
	private void getCurrentUid(){
		HttpClient httpclient = new DefaultHttpClient();

	    // Prepare a request object
	    HttpGet httpget = new HttpGet("http://mafreebox.freebox.fr/api_version"); 

	    // Execute the request
	    HttpResponse response;
	    try {
	        response = httpclient.execute(httpget);

	        // Get hold of the response entity
	        HttpEntity entity = response.getEntity();
	        // If the response does not enclose an entity, there is no need
	        // to worry about connection release

	        if (entity != null) {

	            // A Simple JSON Response Read
	            InputStream instream = entity.getContent();
	            String result= convertStreamToString(instream);
	            JSONObject jsonresp = new JSONObject(result);
	            current_uid = jsonresp.getString("uid");
	            
	            instream.close();
	        }
	    }
	    catch(Exception e){
	    	
	    }
	}
	
	private void testHTTP(){
		HttpClient httpclient = new DefaultHttpClient();

	    // Prepare a request object
	    HttpGet httpget = new HttpGet("http://mafreebox.freebox.fr/api_version"); 

	    // Execute the request
	    HttpResponse response;
	    try {
	        response = httpclient.execute(httpget);

	        // Get hold of the response entity
	        HttpEntity entity = response.getEntity();
	        // If the response does not enclose an entity, there is no need
	        // to worry about connection release

	        if (entity != null) {

	            // A Simple JSON Response Read
	            InputStream instream = entity.getContent();
	            String result= convertStreamToString(instream);
	            //Toast t = Toast.makeText(getApplicationContext(),"RESP : "+result, Toast.LENGTH_LONG);
				//t.show();
	            // now you have the string representation of the HTML request
	            instream.close();
	        }

	        String challenge = null;
	        HttpPost post = new HttpPost("http://mafreebox.freebox.fr/api/v3/login/authorize/");
	        JSONObject json = new JSONObject();
	        json.put("app_id", "gear.fit.telec");
	        json.put("app_name", "GearFitFreeboxtelec");
	        json.put("app_version", "0.0.1");
	        json.put("device_name", android.os.Build.MODEL);
	        HttpEntity e = new StringEntity(json.toString());
	        post.setEntity(e);
	        
	        String trackid = null;
	        String app_token = null;
	        boolean isAuthorized = true;
	        String toParse = null;
	        try{
	        	FileInputStream fis = openFileInput("gitfitfreetelec");
	        	toParse = convertStreamToString(fis);
	        	fis.close();
	        	
	        }
	        catch(FileNotFoundException fnfe){
	        	isAuthorized = false;
	        }
	        String authentString = null;
	        if(!isAuthorized){
	        	response = httpclient.execute(post);

		        entity = response.getEntity();
		        
		        if (entity != null) {

		            // A Simple JSON Response Read
		            InputStream instream = entity.getContent();
		            toParse = convertStreamToString(instream);
		            authentString = toParse;
		            instream.close();
		        }
	        }
	        
	        JSONObject authenticationResp = new JSONObject(toParse);
            JSONObject resultObj = (JSONObject)authenticationResp.get("result");
            Object trackidObj = resultObj.get("track_id");
            trackid = trackidObj.toString();
            Object apptokenObj = resultObj.get("app_token");
            app_token = apptokenObj.toString();
            
            if(!isAuthorized){
	        
		        String status = "pending";
		        
		        while(status.equals("pending")){
		        	httpget = new HttpGet("http://mafreebox.freebox.fr/api/v3/login/authorize/"+trackid);
			        
			        response = httpclient.execute(httpget);
			        entity = response.getEntity();
			        
			        if (entity != null) {
	
			            // A Simple JSON Response Read
			            InputStream instream = entity.getContent();
			            String result= convertStreamToString(instream);
			            JSONObject jsonresp = new JSONObject(result);
			            resultObj = (JSONObject)jsonresp.get("result");
			            Object statusObj = resultObj.get("status");
			            status = statusObj.toString();
			            Object challengeObj = resultObj.get("challenge");
			            challenge = challengeObj.toString();
			            //Toast t = Toast.makeText(getApplicationContext(),"RESP : "+result, Toast.LENGTH_LONG);
						//t.show();
			            // now you have the string representation of the HTML request
			            instream.close();
			        }
		        }
		        
		        if(status.equals("granted")){
		        	isAuthorized = true;
		        	
		        	FileOutputStream fos = openFileOutput("gitfitfreetelec",Context.MODE_PRIVATE);
		        	fos.write(authentString.getBytes());
		        	fos.close();
		        }
            }
            if(isAuthorized){
	            if(challenge == null){
	            	httpget = new HttpGet("http://mafreebox.freebox.fr/api/v3/login/"); 
	            	response = httpclient.execute(httpget);
			        entity = response.getEntity();
			        
			        if (entity != null) {
	
			            // A Simple JSON Response Read
			            InputStream instream = entity.getContent();
			            String result= convertStreamToString(instream);
			            JSONObject jsonresp = new JSONObject(result);
			            resultObj = (JSONObject)jsonresp.get("result");
			            Object challengeObj = resultObj.get("challenge");
			            challenge = challengeObj.toString();
			            //Toast t = Toast.makeText(getApplicationContext(),"RESP : "+result, Toast.LENGTH_LONG);
						//t.show();
			            // now you have the string representation of the HTML request
			            instream.close();
			        }
	            }
	            String password = hmacSha1(challenge,app_token);
	            
	            post = new HttpPost("http://mafreebox.freebox.fr/api/v3/login/session/");
		        json = new JSONObject();
		        json.put("app_id", "gear.fit.telec");
		        json.put("password", password);
		        e = new StringEntity(json.toString());
		        post.setEntity(e);
		        
		        response = httpclient.execute(post);

		        entity = response.getEntity();
		        String session_token = null;
		        if (entity != null) {

		            // A Simple JSON Response Read
		        	InputStream instream = entity.getContent();
		            String result= convertStreamToString(instream);
		            JSONObject jsonresp = new JSONObject(result);
		            boolean isSuccess = jsonresp.getBoolean("success");
		            if(isSuccess){
		            	resultObj = (JSONObject)jsonresp.get("result");
		            	Object sessionObj = resultObj.get("session_token");
		            	session_token = sessionObj.toString();
		            }
		        }
		        
		        /*httpget = new HttpGet("http://hd1.freebox.fr/pub/remote_control?code=12696172&key=power&long=false"); 
            	response = httpclient.execute(httpget);
		        entity = response.getEntity();
		        
		        if (entity != null) {

		            // A Simple JSON Response Read
		            InputStream instream = entity.getContent();
		            String result= convertStreamToString(instream);

		            //Toast t = Toast.makeText(getApplicationContext(),"RESP : "+result, Toast.LENGTH_LONG);
					//t.show();
		            // now you have the string representation of the HTML request
		            instream.close();
		        }*/
	        
            }
	    } catch (Exception e) {
	    	Toast t = Toast.makeText(getApplicationContext(), "ERROR : "+e.getMessage(), Toast.LENGTH_LONG);
			t.show();
	    }
	}

	private static String hmacSha1(String data, String key){
		String result;

		try {
		        // Get an hmac_sha1 key from the raw key bytes
		        byte[] keyBytes = key.getBytes();
		        SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HmacSHA1");

		        // Get an hmac_sha1 Mac instance and initialize with the signing key
		        Mac mac = Mac.getInstance("HmacSHA1");
		        mac.init(signingKey);

		        // Compute the hmac on input data bytes
		        byte[] rawHmac = mac.doFinal(data.getBytes());

		        // Convert raw bytes to Hex
		        result = byteArrayToHex(rawHmac);

		}
		catch (Exception e) {
			return null;
		}
	return result;
	}
	
	public static String byteArrayToHex(byte[] a) {
		   StringBuilder sb = new StringBuilder(a.length * 2);
		   for(byte b: a)
		      sb.append(String.format("%02x", b & 0xff));
		   return sb.toString();
		}

	
	private String convertStreamToString(InputStream is) {
	    /*
	     * To convert the InputStream to String we use the BufferedReader.readLine()
	     * method. We iterate until the BufferedReader return null which means
	     * there's no more data to read. Each line will appended to a StringBuilder
	     * and returned as String.
	     */
	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();

	    String line = null;
	    try {
	        while ((line = reader.readLine()) != null) {
	            sb.append(line + "\n");
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            is.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    return sb.toString();
	}
	
	
}
