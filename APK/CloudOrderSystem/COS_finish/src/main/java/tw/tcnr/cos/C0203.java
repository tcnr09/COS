package tw.tcnr.cos;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import tw.tcnr.cos.providers.SQLiteContentProvider;
import tw.tcnr.cos.R;

public class C0203 extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener , TextWatcher {
    private RadioButton amount1, amount2, amount3, amount4, amount5;
    private TextView AddCartButton;
    private EditText edtxtAmount;
    private String txtSize ="", txtAmount="", txtSweetness="", txtIceLevel="";
    private RadioGroup sizeRG, amountRG, iceRG, sweetnessRG;
    private Integer numAmount;
    private Bundle bundle;
    private String txtTitle, costL, costS;
    private ArrayList<Map<String, String>> datalist;
    private String[] MYCOLUMN = new String[]{"PID","Type","Productname","L_price","S_price","Image"};
    private ContentResolver mContRes;
    private ArrayList<String> dataArray;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c0203);
        setupViewComponent();
        //-------取得bundle--------
        bundle = this.getIntent().getExtras();
        datalist =((ArrayList<Map<String, String>>) bundle.getSerializable("datalist"));

    }

    private void setupViewComponent() {
        //---------設定radio group的監聽-----------
        sizeRG =(RadioGroup)findViewById(R.id.c0203_radioBG_Size);
        amountRG =(RadioGroup)findViewById(R.id.c0203_radioBG_Amount);
        iceRG =(RadioGroup)findViewById(R.id.c0203_radioBG_IceLevel);
        sweetnessRG =(RadioGroup)findViewById(R.id.c0203_radioBG_Sweetness);

        sizeRG.setOnCheckedChangeListener(this);
        amountRG.setOnCheckedChangeListener(this);
        iceRG.setOnCheckedChangeListener(this);
        sweetnessRG.setOnCheckedChangeListener(this);

        AddCartButton = (TextView)findViewById(R.id.c0203_cart);

        amount1=(RadioButton)findViewById(R.id.c0203_amount_1);
        amount2=(RadioButton)findViewById(R.id.c0203_amount_2);
        amount3=(RadioButton)findViewById(R.id.c0203_amount_3);
        amount4=(RadioButton)findViewById(R.id.c0203_amount_4);
        amount5=(RadioButton)findViewById(R.id.c0203_amount_5);


        edtxtAmount=(EditText)findViewById(R.id.c0203_edtxtAmount);
        edtxtAmount.addTextChangedListener(this);
//        sizeL=(RadioButton)findViewById(R.id.c0203_radioB_SizeLarge);
//        sizeS=(RadioButton)findViewById(R.id.c0203_radioB_SizeSmall);


        AddCartButton.setOnClickListener(this);

        Intent intent02 =this.getIntent();
        txtTitle = intent02.getStringExtra("txtTitle");
        this.setTitle(txtTitle);

        //------讀sqlite-------
        String[] productName= {""};
        productName[0] = txtTitle;
        dataArray = getsqlite("Productname=?", productName, null);
        String[] result = dataArray.get(0).split("#");
        costL = result[3];
        costS = result[4];


    }

    @Override
    public void onClick(View view) {
        if(TextUtils.isEmpty(txtSize)){
            Toast.makeText(C0203.this, "請選擇大小",Toast.LENGTH_LONG).show();
        }else if(TextUtils.isEmpty(txtAmount)){
            Toast.makeText(C0203.this, "請選擇數量",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(txtSweetness)){
            Toast.makeText(C0203.this, "請選擇甜度",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(txtIceLevel)){
            Toast.makeText(C0203.this, "請選擇冰塊量",Toast.LENGTH_SHORT).show();
        }else {
            switch (view.getId()){
                case R.id.c0203_cart:

                    //------用MAP存取訂單資料------
                    Map<String, String> data = new HashMap<String, String>();
                    data.put("txtTitle",txtTitle);
                    data.put("txtDetail",txtSize+"/"+txtSweetness+"/"+txtIceLevel);
                    data.put("txtAmount",txtAmount);
                    if(txtSize == "L"){
                        data.put("cost",costL);
                    }else if(txtSize == "S"){
                        data.put("cost",costS);
                    }
                    //-----用ArrayList存取map------
//                    ArrayList<Map<String,String>> datalist = new ArrayList<Map<String,String>>();//------這行草你媽寫在這裡永遠只有一筆資料
                    datalist.add(data);
                    //-----用Budle存取ArrayList-------
                    bundle.putSerializable("datalist",datalist);


                    Intent intentC3=new Intent();
                    intentC3.putExtras(bundle);
                    intentC3.setClass(C0203.this, C0202.class);
                    startActivity(intentC3);
                    break;
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkID) {
        switch (radioGroup.getId()){
            case R.id.c0203_radioBG_Size:
                if (checkID == R.id.c0203_radioB_SizeLarge){
                    txtSize = "L";
                }else if(checkID == R.id.c0203_radioB_SizeSmall){
                    txtSize = "S";
            }
                break;
            case R.id.c0203_radioBG_Amount:
                switch(checkID){
                    case R.id.c0203_amount_1:
                        edtxtAmount.setText("1");
                        break;
                    case R.id.c0203_amount_2:
                        edtxtAmount.setText("2");
                        break;
                    case R.id.c0203_amount_3:
                        edtxtAmount.setText("3");
                        break;
                    case R.id.c0203_amount_4:
                        edtxtAmount.setText("4");
                        break;
                    case R.id.c0203_amount_5:
                        edtxtAmount.setText("5");
                        break;
                }
                break;
            case R.id.c0203_radioBG_Sweetness:
                RadioButton rbSweetness = sweetnessRG.findViewById(checkID);
                txtSweetness =(String) rbSweetness.getText();
                break;
            case R.id.c0203_radioBG_IceLevel:
                RadioButton rbIceLevel = iceRG.findViewById(checkID);
                txtIceLevel =(String) rbIceLevel.getText();
                break;
        }

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
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        txtAmount = edtxtAmount.getText().toString();

        if(!TextUtils.isEmpty(txtAmount)) {
//                    edtxtAmount.setError("請輸入數量");
            numAmount = Integer.valueOf(txtAmount);

            if(numAmount<6 && numAmount>0) {
                switch (numAmount) {
                    case 1:
                        amountRG.check(amount1.getId());
                        break;
                    case 2:
                        amountRG.check(amount2.getId());
                        break;
                    case 3:
                        amountRG.check(amount3.getId());
                        break;
                    case 4:
                        amountRG.check(amount4.getId());
                        break;
                    case 5:
                        amountRG.check(amount5.getId());
                        break;
                }
            }else{
                amountRG.clearCheck();
            }
        }
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

}
