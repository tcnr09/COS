package tw.tcnr.cos;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class LoginDBConnector
{

    public static final int httpstate = 0;
    static String result = "";
    static String TAG = "COS_MEMBER=>";
    //---------------------------
    static InputStream is = null;
    static String line = null;
    static int code;
    static String mysql_code = null;
    static String connect_ip = "https://tcnr1609.000webhostapp.com/android_mysql_connect/";//Hostinger

    //------select MySQL--------------------------------------------------

    public static String executeQuery(String query_string) {

        try {
            HttpClient httpClient = new DefaultHttpClient();
//-----------localhost--------
//            HttpPost httpPost = new HttpPost("http://192.168.60.xx/android/android_connect_db.php");
//-------Web000 Hostinger-------
            HttpPost httpPost = new HttpPost("https://tcnr1609.000webhostapp.com/android_mysql_connect/android_connect_db.php");
 //-------Web000 Hostinger 組長01-------
//            HttpPost httpPost = new HttpPost("https://tcnr1608.000webhostapp.com/android_mysql_connect/android_connect_db.php");
            //-------000webhost--組長-----
//            HttpPost httpPost = new HttpPost("https://tcnr1605.000webhostapp.com/android_mysql_connect/android_connect_db.php");
//            https://tcnr1608.000webhostapp.com/android_mysql_connect/android_connect_db.php
//            https://tcnr1624.000webhostapp.com/android_mysql_connect/android_connect_db.php
//            https://tcnr1609.000webhostapp.com/android_mysql_connect/android_connect_db.php
//            https://tcnr1091601.000webhostapp.com/android_mysql_connect/android_connect_db.php
// ------------------------------
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("query_string", query_string));
            // query_string -> 給php 使用的參數
            httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            InputStream inputStream = httpEntity.getContent();
            //----------------------------------------------------------------
            BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = bufReader.readLine()) != null) {
                builder.append(line + "\n");
            }
            inputStream.close();
            result = builder.toString();
        } catch (Exception e) {
            Log.d(TAG, "Exception e" + e.toString());
        }
        return result;
    }

    //---新增資料--------------------------------------------------------------
    public static String executeInsert(String string, ArrayList<NameValuePair> nameValuePairs) {
        is = null;
        result = null;
        line = null;
        try {
            Thread.sleep(500); // 延遲Thread 睡眠0.5秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // ---- 連結MySQL-------------------
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(connect_ip + "android_connect_db_all.php");
            //-----------------------------------
            // selefunc_string -> 給php 使用的參數	query:選擇 insert:新增 update:更新 delete:刪除
            nameValuePairs.add(new BasicNameValuePair("selefunc_string", "insert"));
            //------------------------------------
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
            HttpResponse response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
        } catch (Exception e) {
            Log.d(TAG, "insert:新增錯誤1" + e.toString());
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"), 8);
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
            Log.d(TAG, "Result : " + result);
        } catch (Exception e) {
            Log.d(TAG, "insert:新增錯誤2:" + e.toString());
        }
        try {
            JSONObject json_data = new JSONObject(result);
            code = (json_data.getInt("code"));
            if (code != 1) Log.d(TAG, "insert:新增錯誤3:" + "..重試..");
        } catch (Exception e) {
            Log.d(TAG, "insert:新增錯誤4:" + e.toString());
        }
        return result;
    }

}

