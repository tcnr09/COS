package tw.tcnr.cos;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import tw.tcnr.cos.providers.SQLiteContentProvider;
import tw.tcnr.cos.recyclerAdpter.MyAdpter;
import tw.tcnr.cos.recyclerAdpter.MyItem;
import tw.tcnr.cos.R;


public class C0202 extends AppCompatActivity implements TextView.OnClickListener {

    private TextView txt_amount, topCoffee,topLightfood, topDessert, txt_Cart;

    private ArrayList<Map<String, Object>> mcoffeeList;
    private String flag;
    private String TAG ="tcnr11=>";
    private Bundle bundle;
    private ContentResolver mContRes;
    private String[] MYCOLUMN = new String[]{"PID","Type","Productname","L_price","S_price","Image"};
    private ArrayList<String> dataArray;
    private RequestQueue mRequestQueue;
    private RecyclerView mRecyclerView;
    private ArrayList<MyItem> mMyItemList = new ArrayList<>();
    private MyAdpter mMyAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c0202);
        setupViewComponent();

        //----------取得bundle-----------
        bundle = this.getIntent().getExtras();

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


        //-----------top Setting----------

        topCoffee=(TextView)findViewById(R.id.c0202_txtCoffeeTop);
        topLightfood=(TextView)findViewById(R.id.c0202_txtLightFoodTop);
        topDessert=(TextView)findViewById(R.id.c0202_txtDessertTop);
        txt_amount=(TextView)findViewById(R.id.c0202_txtAmount);
        txt_Cart =(TextView)findViewById(R.id.c0202_txtCart);
        this.setTitle(R.string.c0202_txtCoffeeTop);

//        topCoffee.setBackgroundResource(R.color.Lime);

        topCoffee.setOnClickListener(this);
        topLightfood.setOnClickListener(this);
        topDessert.setOnClickListener(this);
        txt_Cart.setOnClickListener(this);

        //----------------------------------
        setcoffee();

        String[] productname= {"招牌雞排"};

        dataArray = getsqlite("Productname=?", productname, null);

        Log.d(TAG,"dataArray = "+dataArray);

    }

    private void setcoffee() {
        //-----------v1.0 with Recyclerview and picaso start-------------

        mRecyclerView = findViewById(R.id.c0202_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRequestQueue = Volley.newRequestQueue(this);
        mMyItemList.clear();

        //-------------Cursor 實做---------------
        topCoffee.setBackgroundResource(R.color.Lime);
        topLightfood.setBackgroundResource(R.color.white);
        topDessert.setBackgroundResource(R.color.white);
        String[] argCof= {"c"};
        dataArray = getsqlite("Type=?", argCof, null);

        //---------拆解ArrayList--------

        for(int i = 0; i<dataArray.size();i++){
            Map<String, Object> item = new HashMap<>();
            String[] data = dataArray.get(i).split("#");
            String title =data[2];
            String img =data[5];

            mMyItemList.add(new MyItem(img,title,R.drawable.icons8_forward_30));

        }
        mMyAdapter = new MyAdpter(C0202.this,mMyItemList);
        mRecyclerView.setAdapter(mMyAdapter);

        mMyAdapter.setOnItemClickListener(new MyAdpter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mMyItemList.get(position).getTitle();
                Log.d(TAG,"txtTitle = "+mMyItemList.get(position).getTitle());
                Intent intentC2=new Intent();
                intentC2.putExtras(bundle);
                intentC2.setClass(C0202.this, C0203.class);
                intentC2.putExtra("txtTitle",mMyItemList.get(position).getTitle());
                startActivity(intentC2);
            }
        });
    }

    private void setLightFood(){
        //-----更改top設定 換個顏色-----
        this.setTitle(R.string.c0202_txtLightFoodTop);
        topCoffee.setBackgroundResource(R.color.white);
        topLightfood.setBackgroundResource(R.color.Lime);
        topDessert.setBackgroundResource(R.color.white);
        //------讀sqlite-----
        String[] argCof= {"b"};
        dataArray = getsqlite("Type=?", argCof, null);

        //---------拆解ArrayList--------
        Log.d(TAG,"dataArray = "+dataArray);
        mMyItemList.clear();
        for(int i = 0; i<dataArray.size();i++){
            Map<String, Object> item = new HashMap<>();
            String[] result = dataArray.get(i).split("#");
            String title =result[2];
            String img =result[5];
            Log.d(TAG,"title"+result[2]);
            Log.d(TAG,"img"+result[5]);

            mMyItemList.add(new MyItem(img,title,R.drawable.icons8_forward_30));
            Log.d(TAG,"mMyItemList"+mMyItemList);
        }

        mMyAdapter = new MyAdpter(C0202.this,mMyItemList);
        mRecyclerView.setAdapter(mMyAdapter);

        mMyAdapter.setOnItemClickListener(new MyAdpter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mMyItemList.get(position).getTitle();
                Log.d(TAG,"txtTitle = "+mMyItemList.get(position).getTitle());
                Intent intentC2=new Intent();
                intentC2.putExtras(bundle);
                intentC2.setClass(C0202.this, C0203.class);
                intentC2.putExtra("txtTitle",mMyItemList.get(position).getTitle());
                startActivity(intentC2);
            }
        });
    }

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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.c0202_txtCoffeeTop:
                setcoffee();

                flag = "coffee";
                break;
            case R.id.c0202_txtLightFoodTop:
                setLightFood();

                flag = "lightfood";
                break;
            case R.id.c0202_txtDessertTop:
                this.setTitle(R.string.c0202_txtDessertTop);
                topCoffee.setBackgroundResource(R.color.white);
                topLightfood.setBackgroundResource(R.color.white);
                topDessert.setBackgroundResource(R.color.Lime);

                flag = "dessert";

                break;
            case R.id.c0202_txtCart:
                Intent intentC4=new Intent();
                intentC4.putExtras(bundle);
                intentC4.setClass(C0202.this, C0204.class);
                startActivity(intentC4);

                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub

        if (keyCode == KeyEvent.KEYCODE_BACK) { // 攔截返回鍵
            new AlertDialog.Builder(C0202.this)
                    .setTitle("確認視窗")
                    .setMessage("確定要取消訂單嗎?")
                    .setPositiveButton("確定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Intent intent = new Intent();
                                intent.setClass(C0202.this, C0201.class);
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
        }
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item01:
                    Intent intent_order = new Intent();
                    intent_order.setClass(C0202.this, C0201.class);
                    startActivity(intent_order);
                    break;

            case R.id.menu_item02:
                Intent cos_member = new Intent();
                cos_member.setClass(C0202.this, C0701.class);
                startActivity(cos_member);
                break;

            case R.id.menu_item03:
                Intent cos_card = new Intent();
                cos_card.setClass(C0202.this, C0301.class);
                startActivity(cos_card);
                break;

            case R.id.menu_item04:
                Intent cos_record = new Intent();
                cos_record.setClass(C0202.this, C0401R1.class);
                startActivity(cos_record);
                break;

            case R.id.menu_item05:
                Intent cos_about = new Intent();
                cos_about.setClass(C0202.this, C0501.class);
                startActivity(cos_about);
                break;

            case R.id.menu_item06:
                Intent cos_park = new Intent();
                cos_park.setClass(C0202.this, OpenPark.class);
                startActivity(cos_park);
                break;

            case R.id.action_settings:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
