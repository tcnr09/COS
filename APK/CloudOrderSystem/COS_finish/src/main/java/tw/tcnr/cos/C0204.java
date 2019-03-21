package tw.tcnr.cos;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Map;

import tw.tcnr.cos.providers.SQLiteContentProvider;
import tw.tcnr.cos.recyclerAdpter.CartAdapter;
import tw.tcnr.cos.recyclerAdpter.CartItem;
import tw.tcnr.cos.R;

public class C0204 extends AppCompatActivity implements View.OnClickListener {
    private Bundle bundle;
    private TextView store, where, grabTime, amount1, amount2, sent;
    private String Store, GrabTime, Where, Phone;
    private ArrayList<Map<String, String>> datalist;
    private String[] MYCOLUMN = new String[]{"PID","Type","Productname","L_price","S_price","Image"};
    private ContentResolver mContRes;
    private ArrayList<CartItem> mCartItemList =new ArrayList<>();
    private int totalcost, cost, costAll;
    private String TAG = "tcnr11=>";
    private CartAdapter mCartAdapter;
    private RecyclerView mRecyclerView;
    private RequestQueue mRequestQueue;
    private int flag = 0;
    private CheckBox plasticCheck;

    //------------
    private String title, detail;
    private int amount;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c0204);
        setupViewComponent();
    }

    private void setupViewComponent() {
        //----------取得bundle-----------
        bundle = this.getIntent().getExtras(); //----很重要!!很重要!!很重要!!所以說3遍 要讀取bundle的內容 必須要跟他在同一個方法裡面

        Log.d("tcnr11=>",""+bundle);

        plasticCheck = (CheckBox)findViewById(R.id.c0204_cb001);
        store =(TextView)findViewById(R.id.c0204_Store);
        where = (TextView)findViewById(R.id.c0204_Where);
        grabTime = (TextView)findViewById(R.id.c0204_GrabTime);
        amount1 = (TextView)findViewById(R.id.c0204_Amount1);
        amount2 = (TextView)findViewById(R.id.c0204_Amount2);
        sent = (TextView)findViewById(R.id.c0204_sent);
        sent.setOnClickListener(this);


        //---------開始讀取bundle資料-----------
        Store = bundle.getString("Store");

        Where = bundle.getString("Where");
        Phone = bundle.getString("Phone");
        GrabTime = bundle.getString("GrabTime");
        datalist =((ArrayList<Map<String, String>>) bundle.getSerializable("datalist"));


        //--------設定content外觀-------

        this.setTitle("購物車");
        store.setText(Store);
        where.setText(Where);
        grabTime.setText(GrabTime);

        //--------v1.0 設定RecyclerView--------
        mRecyclerView = findViewById(R.id.c0204_recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRequestQueue = Volley.newRequestQueue(this);

        createCartList();

        //---------讀完bundle資料後 設定金額-------------
        Log.d(TAG,"totalcost = "+totalcost);
        amount1.setText(Integer.toString(totalcost));
        amount2.setText(Integer.toString(totalcost));

        //------設定checkBox 監聽------------
        setCheckBox();


//        --------------把陣列內容放到LAYOUT-----------
//        orderAdapter = new SimpleAdapter(this,
//                datalist,
//                R.layout.c0204_cartlist,
//                new String[]{"txtAmount", "txtSize", "txtTitle", "txtDetail"},
//                new int[]{R.id.c0204_txtAmount, R.id.c0204_txtSize,R.id.c0204_txtTitle,R.id.c0204_txtDetail});
        //---------------------------------------------------
//        listView = (ListView)findViewById(R.id.c0204_listview1);
//        btnview = View.inflate(C0204.this, R.layout.listview_button, null);
//        btnview.findViewById(R.id.c0204_btnNext).setOnClickListener(this);
//        listView.addFooterView(btnview);
//        listView.setTextFilterEnabled(true);
//        listView.setOnItemClickListener(this);
//        listView.setAdapter(orderAdapter);
//
//        if(datalist.size() == 0){
//            btnview.setVisibility(View.INVISIBLE);
//        }else{
//            btnview.setVisibility(View.VISIBLE);
//        }
    }

    private void createCartList() {
        amount = 0;
        cost = 0;
        costAll = 0;
        totalcost =0;//----訂單總金額----
        for(int i = 0; i < datalist.size(); i++){

            String[] productname = {""};//---同上 因為getsqlite的方法是傳遞陣列 所以....case by case 如果需要可以多個條件
            title = datalist.get(i).get("txtTitle");
            detail = datalist.get(i).get("txtDetail");
            amount = Integer.parseInt(datalist.get(i).get("txtAmount"));//---把取出的字串轉數字
            cost = Integer.parseInt(datalist.get(i).get("cost"));
            costAll = amount*cost;

            productname[0] = title;
            Log.d(TAG,"title = "+title);
            ArrayList<String> dataArray = getsqlite("Productname=?", productname, null);
            String[] result = dataArray.get(0).split("#");
            Log.d(TAG,"result = "+result[2]);

            mCartItemList.add(new CartItem(title, amount, cost, costAll, result[5], detail, R.drawable.ic_delete, R.drawable.ic_edit));

            totalcost += costAll;//---總金額跟其他餐點的金額先設定0
        }
        Log.d(TAG, "totalcost = "+totalcost);

        mCartAdapter = new CartAdapter(this, mCartItemList);
        mRecyclerView.setAdapter(mCartAdapter);

        //-------設定監聽--------
        mCartAdapter.setOnItemClickListener(new CartAdapter.OnItemClickListener() {
            @Override
            public void onUpdateClick(int position) {

            }

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }
        });
    }

    private void setCheckBox() {

        plasticCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(flag == 0){
                    int plastic = Integer.parseInt(amount2.getText().toString())+2;
                    amount1.setText(Integer.toString(plastic));
                    amount2.setText(Integer.toString(plastic));
                    flag =1;
                }else if(flag == 1){
                    int plastic = Integer.parseInt(amount2.getText().toString())-2;
                    amount1.setText(Integer.toString(plastic));
                    amount2.setText(Integer.toString(plastic));
                    flag =0;
                }
            }
        });
    }

    private void removeItem(int position) {
        mCartItemList.remove(position);
        mCartAdapter.notifyItemRemoved(position);
        datalist.remove(position);
        totalcost=0;
        if(datalist.size()>0){
            for(int i = 0; i < datalist.size(); i++){

                amount = Integer.parseInt(datalist.get(i).get("txtAmount"));//---把取出的字串轉數字
                cost = Integer.parseInt(datalist.get(i).get("cost"));
                costAll = amount*cost;

                totalcost += costAll;//---總金額跟其他餐點的金額先設定0
            }
            amount1.setText(String.valueOf(totalcost));
            amount2.setText(String.valueOf(totalcost));
        }else {
            amount1.setText(0);
            amount2.setText(0);
        }


    }

//    @Override
//    public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id) {
//
//        Log.d("tcnr11=>","checkbutton");
//
//                new AlertDialog.Builder(C0204.this)
//                        .setTitle("請選擇選項")
//                        .setPositiveButton("刪除此筆",
//                                new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog,
//                                                        int which) {
//                                        datalist.remove(position);
//
//                                        if(datalist.size() == 0){
//                                            btnview.setVisibility(View.INVISIBLE);
//                                        }else{
//                                            btnview.setVisibility(View.VISIBLE);
//                                        }
//                                        orderAdapter.notifyDataSetChanged();
//                                    }
//                                })
//                        .setNegativeButton("取消",
//                                new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog,
//                                                        int which) {
//                                        // TODO Auto-generated method stub
//                                    }
//                                })
//                        .setNeutralButton("編輯", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        })
//                        .show();
//
//
//    }

    private ArrayList<String> getsqlite(String selection, String[] arg, String order) {
        //-------------Cursor 實做---------------
        ArrayList<String> sqlitedata = new ArrayList<>();
        mContRes = getContentResolver();
        SQLiteContentProvider.selectSQLite("Products");
        Cursor c =mContRes.query(SQLiteContentProvider.CONTENT_URI, MYCOLUMN, selection, arg,null);
        int columncount = c.getColumnCount();
        while (c.moveToNext()){
            String data = "";
            for(int i =0; i<columncount;i++){
                data += c.getString(i)+ "#";
            }
            sqlitedata.add(data);
        }
        c.close();
        return sqlitedata;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.c0204_sent:
                if(datalist.size() == 0){
                    Toast.makeText(C0204.this,"您還沒有訂餐喔!!",Toast.LENGTH_LONG).show();
                }else if(datalist.size() >= 1){

                    new AlertDialog.Builder(C0204.this)
                        .setTitle("確定要將餐點送出?")
                        .setPositiveButton("確定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        String xxx = "\n" +
                                                "        set @FK_ID := \n" +
                                                "        (SELECT `AUTO_INCREMENT` FROM  INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'mydb' AND   TABLE_NAME   = 'traderecord');\n" +
                                                "\n" +
                                                "        INSERT INTO `traderecord`\n" +
                                                "        (Phone, Store, TakeAway, Served)VALUES(\"0987654321\", \"某某店\", \"0\", \"0\");\n" +
                                                "\n" +
                                                "        INSERT INTO `orderlist`\n" +
                                                "        (Phone, ProductName, ProductDetail, Amounts, TradeID)VALUES(\"0987654321\",\"產品\",\"就這樣\",\"5\", @FK_ID);\n" +
                                                "\n" +
                                                "        INSERT INTO `orderlist`\n" +
                                                "        (Phone, ProductName, ProductDetail, Amounts, TradeID)VALUES(\"0987654321\",\"產品\",\"就這樣\",\"5\", @FK_ID);\n";
                                        //------------------------------------------------------------------------------------------------------------------------------------
                                        int takeaway = 0;
                                        switch (Where){
                                            case "外帶":
                                                takeaway=0;
                                                break;
                                            case "內用":
                                                takeaway=1;
                                                break;
                                            case "訂餐":
                                                takeaway=2;
                                                break;

                                        }


                                        String sqlquery = "set @FK_ID = \n" +
                                        "        (SELECT `AUTO_INCREMENT` FROM  INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'id7769686_ordersys' AND   TABLE_NAME   = 'TradeRecord');\n"
                                                +
                                                "        INSERT INTO `TradeRecord`\n" +
                                                "        (Phone, Store, TakeAway, Taketime)VALUES(\""+Phone+"\", \""+Store+"\", \""+takeaway+"\", \""+GrabTime+"\");\n";

                                        for(int i = 0; i<datalist.size(); i++){

                                            title = datalist.get(i).get("txtTitle");
                                            detail = datalist.get(i).get("txtDetail");
                                            amount = Integer.parseInt(datalist.get(i).get("txtAmount"));//---把取出的字串轉數字

                                            sqlquery+= "        INSERT INTO `OrderList`\n" +
                                                    "        (Phone, ProductName, ProductDetail, Amounts, TradeID)VALUES(\""+Phone+"\",\""+title+"\",\""+detail+"\",\""+amount+"\", @FK_ID);\n" ;
                                        }

                                        String orderTable = "INSERT INTO `TradeRecord` " +
                                                "(Phone, Store, TakeAway, Taketime)VALUES(\""+Phone+"\", \""+Store+"\", \""+takeaway+"\", \""+GrabTime+"\");";

//                                        mysql_insert(Phone, Store, String.valueOf(takeaway), GrabTime);

                                        DBConnector.executeQuery(sqlquery);

                                        //-------
                                        datalist.clear();
                                        bundle.putSerializable("datalist", datalist);
                                        Toast.makeText(C0204.this,"您的訂單已成功送出",Toast.LENGTH_LONG);
                                        Intent intent04 = new Intent();
                                        intent04.putExtras(bundle);
                                        intent04.setClass(C0204.this, C0202.class);
                                        startActivity(intent04);


                                    }
                                })
                        .setNegativeButton("取消",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        // TODO Auto-generated method stub
                                    }
                                }).show();
                }
                break;
        }
    }

    private void mysql_insert(String phone, String store, String takeaway, String grabTime) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("Phone", phone));
        nameValuePairs.add(new BasicNameValuePair("Store", store));
        nameValuePairs.add(new BasicNameValuePair("TakeAway", takeaway));
        nameValuePairs.add(new BasicNameValuePair("Taketime", grabTime));
        //-----------------------------------------------
        String result = DBConnector3.executeInsert("SELECT * FROM TradeRecord", nameValuePairs);
        //-----------------------------------------------
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub

        if (keyCode == KeyEvent.KEYCODE_BACK) { // 攔截返回鍵
            bundle.remove("datalist");
            bundle.putSerializable("datalist", datalist);
            Intent intent04 =new Intent();
            intent04.putExtras(bundle);
            intent04.setClass(C0204.this, C0202.class);
            startActivity(intent04);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item01:
                Intent intent_order=new Intent();
                intent_order.setClass(this, C0202.class);
                startActivity(intent_order);
                break;
            case R.id.menu_item02:
                new AlertDialog.Builder(C0204.this)
                        .setTitle("確認視窗")
                        .setMessage("確定要取消訂單嗎?")
                        .setPositiveButton("確定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        Intent intent = new Intent();
                                        intent.setClass(C0204.this, C0201.class);
                                        startActivity(intent);
                                    }
                                })
                        .setNegativeButton("取消",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        // TODO Auto-generated method stub
                                    }
                                }).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
