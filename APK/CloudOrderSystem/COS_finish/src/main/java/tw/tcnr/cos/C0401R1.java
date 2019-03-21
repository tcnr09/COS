package tw.tcnr.cos;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tw.tcnr.cos.providers.FriendsContentProvider;
import tw.tcnr.cos.R;

public class C0401R1 extends ListActivity implements View.OnClickListener
{
    //----------------------------------------
    static ListActivity ActivityC0401R1;
    //-----------------------------------------
    private Intent intent = new Intent();
    private Button okBtn;
    private RadioButton changeBtn01,changeBtn02;
//---------------------------------
    String sqlctl;
    String TAG = "tncr=>";
    //--------------------------------
    private ArrayList<Map<String, Object>> mList= new ArrayList<Map<String, Object>>();
    private  String  rec_Date,rec_Tel,rec_Item,rec_Price ;
    private Integer flag=0;
    private EditText editTxt01;
//---------------------------------------------------------
    private static ContentResolver mContRes;
    private String[] MYCOLUMN = new String[]{"recordID", "Date", "Item", "Price","Tel"};
    private ArrayList<String> recSet;

    public static String myselection = "";
    public static String myargs[] = new String[]{};
    public static String myorder = "recordID ASC"; // 排序欄位

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityC0401R1=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c0401r1);
        setupViewComponent();
    }

    private void setupViewComponent()
    {
        //	--設置標題--
        Intent intent=this.getIntent();
        String mode_title = intent.getStringExtra("class_title");
        this.setTitle(mode_title);
        //-----------------------------
        okBtn=(Button)findViewById(R.id.c0401_b001);
        changeBtn01=(RadioButton)findViewById(R.id.c0401_r001);
        changeBtn02=(RadioButton)findViewById(R.id.c0401_r002);

        editTxt01=(EditText)findViewById(R.id.editText);

        recSet = u_selectdb(myselection, myargs, myorder);

        changeBtn01.setOnClickListener(this);
        changeBtn02.setOnClickListener(this);

        okBtn.setOnClickListener(this);
        //-------------抓取遠端資料庫設定執行續------------------------------
        StrictMode.setThreadPolicy(new
                StrictMode.
                        ThreadPolicy.Builder().
                detectDiskReads().
                detectDiskWrites().
                detectNetwork().
                penaltyLog().
                build());
        StrictMode.setVmPolicy(
                new
                        StrictMode.
                                VmPolicy.
                                Builder().
                        detectLeakedSqlLiteObjects().
                        penaltyLog().
                        penaltyDeath().
                        build());
//---------------------------------------------------------------------
    }
    //----------寫入SQLite------------------------------------------------------------------------------------------------
    private ArrayList<String> u_selectdb(String myselection, String[] myargs, String myorder) {
        ArrayList<String> recAry = new ArrayList<String>();
        mContRes = getContentResolver();
        Cursor c = mContRes.query(FriendsContentProvider.CONTENT_URI, MYCOLUMN, null, null, null);
        int columnCount = c.getColumnCount();
        while (c.moveToNext()) {
            String fldSet = "";
            for (int ii = 0; ii < columnCount; ii++)
                fldSet += c.getString(ii) + "#";
            recAry.add(fldSet);
        }
        c.close();
        return recAry;
    }

    //-----menu--------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.menu_item01:
                Intent cos_order = new Intent();
                cos_order.setClass(C0401R1.this, C0201.class);
                startActivity(cos_order);
                break;

            case R.id.menu_item02:
                Intent cos_member = new Intent();
                cos_member.setClass(C0401R1.this, C0701.class);
                startActivity(cos_member);
                break;

            case R.id.menu_item03:
                Intent cos_card = new Intent();
                cos_card.setClass(C0401R1.this, C0301.class);
                startActivity(cos_card);
                break;

            case R.id.menu_item04:

                break;

            case R.id.menu_item05:
                Intent cos_about = new Intent();
                cos_about.setClass(C0401R1.this, C0501.class);
                startActivity(cos_about);
                break;

            case R.id.menu_item06:
                Intent cos_park = new Intent();
                cos_park.setClass(C0401R1.this, OpenPark.class);
                startActivity(cos_park);
                break;

            case R.id.action_settings:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v)
    {

        switch (v.getId()){
            case R.id.c0401_b001:
                sqlctl="SELECT * FROM recordtest";
                Mysqlsel(sqlctl);
                //-------------------------------------------------------------
                recSet = u_query();
                //===========取SQLite 資料=============
                List<Map<String, Object>> mList;
                mList = new ArrayList<Map<String, Object>>();
                for (int i = 0; i < recSet.size(); i++) {
                    Map<String, Object> item = new HashMap<String, Object>();
                    String[] fld = recSet.get(i).split("#");
                    item.put("imgView", R.drawable.coscard01);
                    item.put("txtView",
                            "儲值日期:" + fld[1]
                                    + "\n儲值金額:" + fld[3]);
                    mList.add(item);
                }
                //=========設定listview========

                SimpleAdapter adapter = new SimpleAdapter(
                        this,
                        mList,
                        R.layout.c0401_list_item,
                        new String[]{"imgView", "txtView"},
                        new int[]{R.id.imgView, R.id.txtView}
                );
                setListAdapter(adapter);
                ListView listview = getListView();
                listview.setTextFilterEnabled(true);

                break;
            case R.id.c0401_r001:
                intent.putExtra("class_se", 0);
                intent.putExtra("class_title", getString(R.string.c0401_r001));
                intent.setClass(C0401R1.this, C0401R1.class);
                // 在要判斷關閉的時機點，填寫以下這段即可關閉Activity
                transformPage();
                //  執行指定的class
                startActivity(intent);
                break;
            case R.id.c0401_r002:
                intent.putExtra("class_se", 0);
                intent.putExtra("class_title", getString(R.string.c0401_r002));
                intent.setClass(C0401R1.this, C0401R2.class);
                // 在要判斷關閉的時機點，填寫以下這段即可關閉Activity
                transformPage();
                //  執行指定的class
                startActivity(intent);
                break;

        }
    }

    private void transformPage()
    {
        // 在要判斷關閉的時機點，填寫以下這段即可關閉Activity
        if(!C0401R1.ActivityC0401R1.isFinishing())
            C0401R1.ActivityC0401R1.finish();
    }
//--------查詢-------------------------------------------------------------
    private ArrayList<String> u_query()
    {
        ArrayList<String> recAry = new ArrayList<String>();
        myselection = "";
        myorder = "recordID ASC"; // 排序欄位
        mContRes = getContentResolver();
        myselection = " Tel LIKE ? ";
        myargs = new String[]{
                 editTxt01.getText().toString().trim() };
        Cursor c = mContRes.query(FriendsContentProvider.CONTENT_URI, MYCOLUMN, myselection, myargs, myorder);

        int columnCount = c.getColumnCount();
        while (c.moveToNext()) {
            String fldSet = "";
            for (int ii = 0; ii < columnCount; ii++)
                fldSet += c.getString(ii) + "#";
            recAry.add(fldSet);
        }
        c.close();
        return recAry;
    }

    //讀資料庫
    private void Mysqlsel(String sqlctl) {


        mContRes = getContentResolver(); //
        Cursor cur = mContRes.query(FriendsContentProvider.CONTENT_URI, MYCOLUMN, null, null, null);
        cur.moveToFirst(); // 一定要寫，不然會出錯
        // ---------------------------
        //        清空mList
        mList.clear();
        try {
            String result = DBConnector.executeQuery(sqlctl);
            /**************************************************************************
             * SQL 結果有多筆資料時使用JSONArray
             * 只有一筆資料時直接建立JSONObject物件 JSONObject
             * jsonData = new JSONObject(result);
             **************************************************************************/
            JSONArray jsonArray = new JSONArray(result);
            // ---
            Log.d(TAG, jsonArray.toString());
            // --
            // -------------------------------------------------------
            if (jsonArray.length() > 0) { // MySQL 連結成功有資料
                Uri uri = FriendsContentProvider.CONTENT_URI;
                mContRes.delete(uri, null, null); // 匯入前,刪除所有SQLite資料

                // ----------------------------
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonData = jsonArray.getJSONObject(i);
                //
                ContentValues newRow = new ContentValues();

//                    if(jsonData.getString("Tel").toString().equals(editTxt01.getText().toString().trim())){
//                        rec_Date =jsonData.getString("Date").toString();
//                        rec_Item=jsonData.getString("Item").toString();
//                        rec_Price=jsonData.getString("Price").toString();
//                        flag=1;
//                    }
//                if(flag==1){
//                    Map<String, Object> item = new HashMap<String, Object>();
//                    item.put("imgView", R.drawable.coscard01);
//                    item.put("txtView", "日期 : " +rec_Date+"\n儲值金額 : "+rec_Price);
//                    mList.add(item);
//                }
//                    SimpleAdapter adapter = new SimpleAdapter(this, mList,
//                            R.layout.c0401_list_item,
//                            new String[]{"imgView", "txtView"},                  //增加欄位名稱
//                            new int[]{R.id.imgView, R.id.txtView});            //
//                    setListAdapter(adapter);
//                    ListView listview = getListView();
//                    listview.setTextFilterEnabled(true);
                // ---(2) 使用固定已知欄位---------------------------
                 newRow.put("recordID", jsonData.getString("recordID").toString());
                 newRow.put("Date", jsonData.getString("Date").toString());
                 newRow.put("Item", jsonData.getString("Item").toString());
                 newRow.put("Price", jsonData.getString("Price").toString());
                 newRow.put("Tel", jsonData.getString("Tel").toString());
                // -------------------加入SQLite---------------------------------------
                mContRes.insert(FriendsContentProvider.CONTENT_URI, newRow);

            }
                // ---------------------------
            } else {
                Toast.makeText(C0401R1.this, "主機資料庫無資料", Toast.LENGTH_LONG).show();
            }
            // --------------------------------------------------------
        } catch (Exception e) {
            // Log.d(TAG, e.toString());
        }
        cur.close();
        setupViewComponent();// 重構
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        this.finish();
    }


}



