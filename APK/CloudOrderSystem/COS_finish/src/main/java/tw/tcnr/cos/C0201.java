package tw.tcnr.cos;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import tw.tcnr.cos.dialog.ActionListener;
import tw.tcnr.cos.dialog.BaseDialogFragment;
import tw.tcnr.cos.dialog.SimplePickerDialog;
import tw.tcnr.cos.providers.SQLiteContentProvider;
import tw.tcnr.cos.R;

public class C0201 extends AppCompatActivity implements AdapterView.OnItemClickListener, TextView.OnClickListener {

    private String[] txtOrderList1, txtOrderList2, txtpos;
    private ArrayList<Map<String, Object>> mOrderSetting;
    private TextView txtStart;
    private Dialog wheelDlg;

    private ArrayList<Map<String, Object>> mList;
    private TextView txtWhere, txtStore, txtTime;
    private int pickerviewPos, yyyy, MM, dd, HH, mm, ss ;
    private String GrabTime = "", Where = "", Store ="";
    private ArrayList<Map<String, String>> datalist;
    private ContentResolver mContRes;
    private String[] MYCOLUMN = new String[]{"PID","Type","Productname","L_price","S_price","Image"};
    private String TAG = "COS_MEMBER=>";
    private String msg;
    private int tcount;
    private String sqliteString;
    private String strsql;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c0201);
        setupViewComponent();
    }

    private void setupViewComponent() {

        //----建立http連線------
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

        //---------------------

        setTitle("點餐");
        datalist = new ArrayList<Map<String,String>>();
        txtStart = (TextView) findViewById(R.id.c0201_txtStart);
        txtStart.setOnClickListener(this);
        txtOrderList1 = getResources().getStringArray(R.array.c0201_txtOrderSetting);
        txtOrderList2 = getResources().getStringArray(R.array.c0201_txtWhichStore);
        mOrderSetting = new ArrayList<Map<String, Object>>();

//--------------把陣列內容放入MAP------------
        for (int i = 0; i < txtOrderList1.length; i++) {
            Map<String, Object> mOrderitem = new HashMap<String, Object>();

            mOrderitem.put("c0201_imgOrderSetting", R.drawable.week);
            mOrderitem.put("c0201_txtOrderSetting1", txtOrderList1[i]);
            mOrderitem.put("c0201_txtOrderSetting2","");
            mOrderSetting.add(mOrderitem);
//-----------------------------------------------
        }
//        Log.d(TAG,"morderlist"+mOrderSetting);

//--------------把陣列內容放到LAYOUT-----------
        SimpleAdapter orderAdapter = new SimpleAdapter(this,
                mOrderSetting,
                R.layout.c0201_ordersetting,
                new String[]{"c0201_imgOrderSetting", "c0201_txtOrderSetting1","c0201_txtOrderSetting2"},
                new int[]{R.id.c0201_imgOrderSetting, R.id.c0201_txtOrderSetting1,R.id.c0201_txtOrderSetting2});

//---------------------------------------------------
        ListView listView = (ListView)findViewById(R.id.c0201_listview);
        listView.setAdapter(orderAdapter);
        listView.setTextFilterEnabled(true);
        listView.setOnItemClickListener(this);

        connectDB();

    }


    @Override
    public void onClick(View view) {
        if(TextUtils.isEmpty(Where)){
            Toast.makeText(this,"請選擇取餐方式",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(GrabTime)) {
            Toast.makeText(this, "請選擇取餐時間", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(Store)) {
            Toast.makeText(this, "請選擇取餐地點", Toast.LENGTH_SHORT).show();
        }else{
            switch (view.getId()) {
                case R.id.c0201_txtStart:
                    Intent intentC1=new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("Phone","0987654321");
                    bundle.putString("Where",Where);
                    bundle.putString("GrabTime",GrabTime);
                    bundle.putString("Store",Store);
                    bundle.putSerializable("datalist",datalist);

                    intentC1.putExtras(bundle);
                    intentC1.setClass(C0201.this, C0202.class);
                    startActivity(intentC1);
                    break;
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

        switch (position){
            case 0:
                choosePicker(BaseDialogFragment.TYPE_DIALOG ,position).show(getSupportFragmentManager(), "dialog");
                txtWhere =(TextView)view.findViewById(R.id.c0201_txtOrderSetting2);
                break;
            case 1:
                //----取得手機時間------
                Calendar now = Calendar.getInstance();
                now.add(Calendar.MINUTE, 25);

                //----設定一個TimeDialoig----
                TimePickerDialog timePicDlg = new TimePickerDialog(C0201.this,
                        TimePickerDialog.THEME_HOLO_DARK,
                        (TimePickerDialog.OnTimeSetListener) timePicDlgOnTimeSelLis,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        false);
                timePicDlg.setTitle(getString(R.string.c0201_timeTitle));
                timePicDlg.setMessage(getString(R.string.c0201_timeMessage));
                timePicDlg.setButton(DatePickerDialog.BUTTON_POSITIVE,"確認",timePicDlg);
                timePicDlg.setButton(DatePickerDialog.BUTTON_NEGATIVE,"取消",timePicDlg);

                timePicDlg.setCancelable(false);
                timePicDlg.show();

                txtTime = (TextView)view.findViewById(R.id.c0201_txtOrderSetting2);
                break;
            case 2:
                choosePicker(BaseDialogFragment.TYPE_DIALOG ,position).show(getSupportFragmentManager(), "dialog");
                txtStore =(TextView)view.findViewById(R.id.c0201_txtOrderSetting2);
                break;
            case 3:
                Toast.makeText(C0201.this,"選了第"+position+"個item",Toast.LENGTH_LONG).show();
                break;
        }
    }


    //---------增加一個第三方pickerview的使用方法--------------
    BaseDialogFragment choosePicker(int type , int position) {
        pickerviewPos = position;
        BaseDialogFragment picker;
        picker = SimplePickerDialog.newInstance(type, actionListener ,position);
        return picker;
    }
    ActionListener actionListener =new ActionListener() {
        @Override
        public void onCancelClick(BaseDialogFragment dialog) {

        }

        @Override
        public void onDoneClick(BaseDialogFragment dialog) {
            switch (pickerviewPos){
                case 0:
                    Where = ((SimplePickerDialog)dialog).getSelectedItem().getText();
                    txtWhere.setText(((SimplePickerDialog) dialog).getSelectedItem().getText());
                    break;
                case 2:
                    Store =((SimplePickerDialog)dialog).getSelectedItem().getText();
                    txtStore.setText(((SimplePickerDialog) dialog).getSelectedItem().getText());
                    break;
            }
        }
    };

    //-----------------監聽所選擇的時間------------------------
    private TimePickerDialog.OnTimeSetListener timePicDlgOnTimeSelLis = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int min) {

            Calendar orderCalendar = Calendar.getInstance();
            yyyy = orderCalendar.get(Calendar.YEAR);
            MM = orderCalendar.get(Calendar.MONTH);
            dd = orderCalendar.get(Calendar.DAY_OF_MONTH);
            HH = hour;
            mm = min;
            ss = orderCalendar.get(Calendar.SECOND);

            GrabTime = yyyy+"/"+MM+"/"+dd+" "+HH+":"+mm+":"+ss;

            //----------設定ListView TIME 最後取餐的時間
            txtTime.setText(GrabTime);



        }
    };

    // 讀取MySQL 資料
//    private void dbmysql() {
//        mContRes = getContentResolver();//---------無敵重要 要先寫!!!!!!!!!!!
//        Cursor cur = mContRes.query(SQLiteContentProvider.CONTENT_URI, MYCOLUMN, null, null, null);
//        cur.moveToFirst(); // 一定要寫，不然會出錯
//        // ---------------------------
//        try {
//            String result1 = DBConnector.executeQuery("SHOW TABLES");
//            String result = DBConnector.executeQuery("SELECT * FROM Products");
//            String mcolumnPK = DBConnector.executeQuery("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE WHERE TABLE_NAME = 'OrderList'");
//            try{
//                JSONArray xxx = new JSONArray(mcolumnPK);
//            }catch (Exception e){
//                Log.d(TAG,"@@@@沒結果");
//            }
//
//
////            Log.d(TAG,result1);
//            JSONArray jsonArray1 = new JSONArray(result1);
////            Log.d(TAG,"JJJJJJJJJ"+jsonArray1);
//
//            //以下程式碼一定要放在前端藍色程式碼執行之後，才能取得狀態碼
//            //存取類別成員 DBConnector.httpstate 判定是否回應 200(連線要求成功)
//
//            if (DBConnector.httpstate == 200) {
//                Uri uri = SQLiteContentProvider.CONTENT_URI;
//                mContRes.delete(uri, null, null);
//                Toast.makeText(getBaseContext(), "已經完成由伺服器匯入資料",
//                        Toast.LENGTH_LONG).show();
//            } else {
//                int checkcode = DBConnector.httpstate / 100;
//                switch (checkcode) {
//                    case 1:
//                        msg = "資訊回應(code:" + DBConnector.httpstate + ")";
//                        break;
//                    case 2:
//                        msg = "已經完成由伺服器匯入資料(code:" + DBConnector.httpstate + ")";
//                        break;
//                    case 3:
//                        msg = "伺服器重定向訊息，請稍後再試(code:" + DBConnector.httpstate + ")";
//                        break;
//                    case 4:
//                        msg = "用戶端錯誤回應，請稍後再試(code:" + DBConnector.httpstate + ")";
//                        break;
//                    case 5:
//                        msg = "伺服器error responses，請稍後再試(code:" + DBConnector.httpstate + ")";
//                        break;
//                }
//                Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
//            }
//            // 選擇讀取特定欄位
//            // String result = DBConnector.executeQuery("SELECT id,name FROM
//            // member");
//            /*******************************************************************************************
//             * SQL 結果有多筆資料時使用JSONArray 只有一筆資料時直接建立JSONObject物件 JSONObject
//             * jsonData = new JSONObject(result);
//             *******************************************************************************************/
//            JSONArray jsonArray = new JSONArray(result);
////            Log.d(TAG,"jsonArry="+jsonArray);
//            // -------------------------------------------------------
//            if (jsonArray.length() > 0) { // MySQL 連結成功有資料
//                Uri uri = SQLiteContentProvider.CONTENT_URI;
//                mContRes.delete(uri, null, null); // 匯入前,刪除所有SQLite資料
//                // ----------------------------
//                // 處理JASON 傳回來的每筆資料
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject jsonData = jsonArray.getJSONObject(i);
//
//                    ContentValues newRow = new ContentValues();
//                    // --(1) 自動取的欄位
//                    // --取出 jsonObject
//                    // 每個欄位("key","value")-----------------------
//                    Iterator itt = jsonData.keys();
//                    while (itt.hasNext()) {
//                        String key = itt.next().toString();
//                        String value = jsonData.getString(key); // 取出欄位的值
//                        if (value == null) {
//                            continue;
//                        } else if ("".equals(value.trim())) {
//                            continue;
//                        } else {
//                            jsonData.put(key, value.trim());
//                        }
//                        // ------------------------------------------------------------------
//                        newRow.put(key, value.toString()); // 動態找出有幾個欄位
//                        // -------------------------------------------------------------------
//
//
//                    }
//                    // ---(2) 使用固定已知欄位---------------------------
//                    // newRow.put("id", jsonData.getString("id").toString());
//                    // newRow.put("name",
//                    // jsonData.getString("name").toString());
//                    // newRow.put("grp", jsonData.getString("grp").toString());
//                    // newRow.put("address", jsonData.getString("address")
//                    // .toString());
//                    // -------------------加入SQLite---------------------------------------
//                    Log.d(TAG,""+newRow);
//                    mContRes.insert(SQLiteContentProvider.CONTENT_URI, newRow);
//                }
//                // ---------------------------
//            } else {
//                Toast.makeText(C0201.this, "主機資料庫無資料", Toast.LENGTH_LONG).show();
//            }
//            // --------------------------------------------------------
//        } catch (Exception e) {
//            Log.e("log_tag", e.toString());
//        }
//        cur.close();
//        // ---------------------------------------
//        sqliteupdate(); // 抓取SQLite資料
//        // ----------------------------------------
//    }

    private void dbmysql() {
        mContRes = getContentResolver();//---------無敵重要 要先寫!!!!!!!!!!!
        try{
            //-----先連線資料庫----


            //-----抓所有TABLE-----
            String mTabString = DBConnector3.executeQuery("SHOW TABLES");
            //-----抓取

            ArrayList<String> columnList = new ArrayList();
            if(DBConnector3.httpstate == 200){
                Toast.makeText(getBaseContext(),"匯入資料成功",Toast.LENGTH_SHORT).show();
            }else {
                int checkcode = DBConnector3.httpstate / 100;
                switch (checkcode) {
                    case 1:
                        msg = "資訊回應(code:" + DBConnector3.httpstate + ")";
                        break;
                    case 2:
                        msg = "已經完成由伺服器會入資料(code:" + DBConnector3.httpstate + ")";
                        break;
                    case 3:
                        msg = "伺服器重定向訊息，請稍後在試(code:" + DBConnector3.httpstate + ")";
                        break;
                    case 4:
                        msg = "用戶端錯誤回應，請稍後在試(code:" + DBConnector3.httpstate + ")";
                        break;
                    case 5:
                        msg = "伺服器error responses，請稍後在試(code:" + DBConnector3.httpstate + ")";
                        break;
                }
                Toast.makeText(getBaseContext(),msg,Toast.LENGTH_SHORT).show();
            }

            JSONArray jaTable = new JSONArray(mTabString);

            if(jaTable.length()>0){
                //-----TABLE有幾筆  並抓取TABLE的名稱
                for(int i =0; i<jaTable.length();i++){
                    //-----抓所有欄位資訊-----

                    JSONObject tabObj = jaTable.getJSONObject(i);


                    //-----抓取KEY-----正常來說dbname只有一個值就是你的DBNAME
                    Iterator dbnameKey = tabObj.keys();

                    while(dbnameKey.hasNext()) {
                        //---------Table 名稱-------------
                        String tableKey = dbnameKey.next().toString();
                        String tablename = tabObj.getString(tableKey);  //----正式取出table的name

                        //--------抓到第一個TABLENAME之後抓取TABLE COLUMN 所有欄位資訊----------
                        String mcolumnString = DBConnector3.executeQuery("SELECT COLUMN_NAME,ORDINAL_POSITION,DATA_TYPE,CHARACTER_MAXIMUM_LENGTH FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '"+tablename+"'");
                        //--------判別Primary Key------------
                        String mcolumnPK = DBConnector3.executeQuery("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE WHERE TABLE_NAME = '"+tablename+"'");


                        try {

                            JSONArray jaColumnPK =new JSONArray(mcolumnPK);//-------如果該TABLE沒有PRIMARY KEY 這裡會拋出EXCEPTION-------
                            JSONObject columnObjPK = jaColumnPK.getJSONObject(0);
                            String namePK = columnObjPK.getString("COLUMN_NAME");

                            JSONArray jaColumn = new JSONArray(mcolumnString);

                            String command = "";
                            sqliteString = "CREATE TABLE "+tablename+" (";

                            columnList.clear();
                            for(int j =0; j<jaColumn.length();j++){
                                JSONObject columnObj = jaColumn.getJSONObject(j);
                                String DATA_TYPE = columnObj.getString("DATA_TYPE");
                                String COLUMN_NAME = columnObj.getString("COLUMN_NAME");

                                //---------判斷哪一個COLUMN NAME 是PK----------
                                if(namePK == COLUMN_NAME){
                                    strsql =COLUMN_NAME+" "+DATA_TYPE+ "PRIMARY KEY ";
                                }else {
                                    strsql =COLUMN_NAME+" "+DATA_TYPE+" ";
                                }
                                //-------把包含創建TABLE與其欄位結構的語法寫入------
                                sqliteString = sqliteString+command+strsql;
                                command = ",";
                                columnList.add(COLUMN_NAME);
                            }
                            sqliteString = sqliteString+");";
                            //-------ArrayList 轉 Array------
                            Object list[] = columnList.toArray();
                            String[] columnArray = Arrays.copyOf(list,list.length,String[].class);


                            //------在SQLiteContentProvider建立一個方法 把tablenmae 跟 sqlite語法傳送過去，並改寫crTBsql和DB_TABLE-----

//                            fcp.setSQLITE(sqliteString,tablename);
                            SQLiteContentProvider.setSQLITE(sqliteString,tablename);

                            //-----------------------------------------------


                            //----貌似是create sqliteTable的方法---
                            Cursor cur = mContRes.query(SQLiteContentProvider.CONTENT_URI, columnArray, null, null, null);
                            cur.moveToFirst();
                            cur.close();

                        }catch (Exception e){

                            String command = "";
                            sqliteString = "CREATE TABLE "+tablename+" (";
                            JSONArray jaColumn = new JSONArray(mcolumnString);
                            for(int j =0; j<jaColumn.length();j++){
                                JSONObject columnObj = jaColumn.getJSONObject(j);
//                            Iterator columnKey = columnObj.keys();
                                String DATA_TYPE = columnObj.getString("DATA_TYPE");
                                String COLUMN_NAME = columnObj.getString("COLUMN_NAME");
                                columnList.add(COLUMN_NAME);

                                strsql =COLUMN_NAME+" "+DATA_TYPE+" ";
                                sqliteString = sqliteString+command+strsql;
                                command = ",";

                            }
                            sqliteString = sqliteString+");";
                            //-----------------------
                            SQLiteContentProvider.setSQLITE(sqliteString,tablename);
//                            SQLiteContentProvider fcp = new SQLiteContentProvider();
//                            fcp.setSQLITE(sqliteString,tablename);
//                            fcp.onCreate();
                            //------------------------

                            Object list[] = columnList.toArray();
                            String[] columnArray = Arrays.copyOf(list,list.length,String[].class);
                            Cursor cur = mContRes.query(SQLiteContentProvider.CONTENT_URI, columnArray, null, null, null);
                            cur.moveToFirst();
                            cur.close();
                        }
                        String mtableResu = DBConnector3.executeQuery("SELECT * FROM "+tablename);
                        JSONArray jaResu = new JSONArray(mtableResu);
                        if(jaResu.length()>0){

                            Uri uri = SQLiteContentProvider.CONTENT_URI;
                            mContRes.delete(uri, null, null); // 匯入前,刪除所有SQLite資料
                            // ----------------------------
                            // 處理JASON 傳回來的每筆資料
                            for (int k = 0; k < jaResu.length(); k++) {
                                JSONObject jsonData = jaResu.getJSONObject(k);

                                ContentValues newRow = new ContentValues();
                                // --(1) 自動取的欄位
                                // --取出 jsonObject
                                // 每個欄位("key","value")-----------------------
                                Iterator itt = jsonData.keys();
                                while (itt.hasNext()) {
                                    String key = itt.next().toString();
                                    String value = jsonData.getString(key); // 取出欄位的值
                                    if (value == null) {
                                        continue;
                                    } else if ("".equals(value.trim())) {
                                        continue;
                                    } else {
                                        jsonData.put(key, value.trim());
                                    }
                                    // ------------------------------------------------------------------
                                    newRow.put(key, value.toString()); // 動態找出有幾個欄位
                                    // -------------------------------------------------------------------
                                }

                                mContRes.insert(SQLiteContentProvider.CONTENT_URI, newRow);
                            }
                        }else {
                            Toast.makeText(C0201.this, "主機資料庫無資料", Toast.LENGTH_LONG).show();
                        }
                    }

                }

            }else{
                Log.d(TAG,"沒有抓到TABLENAME");
            }

        }catch (Exception e){
            Log.d(TAG,"FinalEXception====>"+e.toString());
        }
    }

    private  void connectDB(){
        mContRes = getContentResolver();//---------無敵重要 要先寫!!!!!!!!!!!
        Cursor cur = mContRes.query(SQLiteContentProvider.CONTENT_URI, MYCOLUMN, null, null, null);
        cur.moveToFirst(); // 一定要寫，不然會出錯

        //----建立sqliteTable-----
        SQLiteContentProvider.setSQLITE("CREATE TABLE OrderList (Phone varchar ,ProductName varchar ,ProductDetail varchar ,Amounts int ,TradeID int );", "OrderList");
        SQLiteContentProvider.setSQLITE("CREATE TABLE Products (PID int ,Type varchar ,Productname varchar ,L_price int ,S_price int ,Image varchar);", "Products");
        SQLiteContentProvider.setSQLITE("CREATE TABLE TradeRecord (TradeID int ,Phone varchar ,Store varchar ,TakeAway int ,Ordertime timestamp ,Taketime timestamp ,Served int );", "TradeRecord");
        SQLiteContentProvider.setSQLITE("CREATE TABLE Userdata (ID int ,Name varchar ,Phone varchar ,Password varchar ,Credit int ,Permission int);", "Userdata");

        //----------
        SQLiteContentProvider.selectSQLite("Products");
        String mtableResu = DBConnector3.executeQuery("SELECT * FROM Products");
        try{
            JSONArray jaResu = new JSONArray(mtableResu);
            if(jaResu.length()>0){

                Uri uri = SQLiteContentProvider.CONTENT_URI;
                mContRes.delete(uri, null, null); // 匯入前,刪除所有SQLite資料
                // ----------------------------
                // 處理JASON 傳回來的每筆資料
                for (int k = 0; k < jaResu.length(); k++) {
                    JSONObject jsonData = jaResu.getJSONObject(k);

                    ContentValues newRow = new ContentValues();
                    // --(1) 自動取的欄位
                    // --取出 jsonObject
                    // 每個欄位("key","value")-----------------------
                    Iterator itt = jsonData.keys();
                    while (itt.hasNext()) {
                        String key = itt.next().toString();
                        String value = jsonData.getString(key); // 取出欄位的值
                        if (value == null) {
                            continue;
                        } else if ("".equals(value.trim())) {
                            continue;
                        } else {
                            jsonData.put(key, value.trim());
                        }
                        // ------------------------------------------------------------------
                        newRow.put(key, value.toString()); // 動態找出有幾個欄位
                        // -------------------------------------------------------------------
                    }

                    mContRes.insert(SQLiteContentProvider.CONTENT_URI, newRow);
                }
            }else {
                Toast.makeText(C0201.this, "主機資料庫無資料", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            Log.d(TAG,""+e.getMessage());
        }

        cur.close();
        //-----------------------------------------------------
//        SQLiteContentProvider.selectSQLite("Userdata");
//        String[] USERDATA = new String[]{"ID" ,"Name" ,"Phone" ,"Password" ,"Credit","Permission"};
//
//        Cursor cur1 = mContRes.query(SQLiteContentProvider.CONTENT_URI,USERDATA,null,null,null);
//        cur1.moveToFirst();

        //---------------抓USERDATA資料--------------

//        String mtableResu1 = DBConnector.executeQuery("SELECT * FROM Products");
//        try{
//            JSONArray jaResu = new JSONArray(mtableResu);
//            if(jaResu.length()>0){
//
//                Uri uri = SQLiteContentProvider.CONTENT_URI;
//                mContRes.delete(uri, null, null); // 匯入前,刪除所有SQLite資料
//                // ----------------------------
//                // 處理JASON 傳回來的每筆資料
//                for (int k = 0; k < jaResu.length(); k++) {
//                    JSONObject jsonData = jaResu.getJSONObject(k);
//
//                    ContentValues newRow = new ContentValues();
//                    // --(1) 自動取的欄位
//                    // --取出 jsonObject
//                    // 每個欄位("key","value")-----------------------
//                    Iterator itt = jsonData.keys();
//                    while (itt.hasNext()) {
//                        String key = itt.next().toString();
//                        String value = jsonData.getString(key); // 取出欄位的值
//                        if (value == null) {
//                            continue;
//                        } else if ("".equals(value.trim())) {
//                            continue;
//                        } else {
//                            jsonData.put(key, value.trim());
//                        }
//                        // ------------------------------------------------------------------
//                        newRow.put(key, value.toString()); // 動態找出有幾個欄位
//                        // -------------------------------------------------------------------
//                    }
//
//                    mContRes.insert(SQLiteContentProvider.CONTENT_URI, newRow);
//                }
//            }else {
//                Toast.makeText(C0201.this, "主機資料庫無資料", Toast.LENGTH_LONG).show();
//            }
//        }catch (Exception e){
//            Log.d(TAG,""+e.getMessage());
//        }
//        cur1.close();

    }

    private void sqliteupdate() { // 抓取SQLite資料
        Cursor c = mContRes.query(SQLiteContentProvider.CONTENT_URI, MYCOLUMN, null, null, null);
        tcount = c.getCount();
        // ---------------------------

        // 使用自訂義spinner layout spinner_style.xml



        c.close();
        //--設定spinner
        //------ 宣告鈴聲 ---------------------------
//        ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100); // 100=max
//        toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_NETWORK_LITE, 500);
//        toneG.release();
    }


    //----------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_item01:
                if(TextUtils.isEmpty(Where)){
                    Toast.makeText(this,"請選擇取餐方式",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(GrabTime)) {
                    Toast.makeText(this, "請選擇取餐時間", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(Store)) {
                    Toast.makeText(this, "請選擇取餐地點", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent_order = new Intent();
                    intent_order.setClass(C0201.this, C0202.class);
                    startActivity(intent_order);
                    break;
                }
            case R.id.menu_item02:
                Intent cos_member = new Intent();
                cos_member.setClass(C0201.this, C0701.class);
                startActivity(cos_member);
                break;

            case R.id.menu_item03:
                Intent cos_card = new Intent();
                cos_card.setClass(C0201.this, C0301.class);
                startActivity(cos_card);
                break;

            case R.id.menu_item04:
                Intent cos_record = new Intent();
                cos_record.setClass(C0201.this, C0401R1.class);
                startActivity(cos_record);
                break;

            case R.id.menu_item05:
                Intent cos_about = new Intent();
                cos_about.setClass(C0201.this, C0501.class);
                startActivity(cos_about);
                break;

            case R.id.menu_item06:
                Intent cos_park = new Intent();
                cos_park.setClass(C0201.this, OpenPark.class);
                startActivity(cos_park);
                break;

            case R.id.action_settings:
                this.finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
