package tw.tcnr.cos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TableRow;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import tw.tcnr.cos.R;


public class OpenPark extends AppCompatActivity {

    private ListView listView;
    private TableRow tab01;
    private String check_t="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.c0602);

        setupViewComponent();

    }



    private void setupViewComponent() {
        //	--設置標題--
        Intent intent=this.getIntent();
        String mode_title = intent.getStringExtra("class_title");
        this.setTitle(mode_title);
        //-----------------------------
        // 動態調整高度 抓取使用裝置尺寸
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int newscrollheight = displayMetrics.heightPixels * 90 / 100; // 設定ScrollView使用尺寸的4/5
        //----------------------------------------------

        listView = (ListView) findViewById(R.id.listView1);
        listView.getLayoutParams().height = newscrollheight;
        listView.setLayoutParams(listView.getLayoutParams()); // 重定ScrollView大小
        tab01=(TableRow)findViewById(R.id.tab01);

        //----------------------------------------------
        try {

            Gson gson = new Gson();

            String MyJson  = new TransTask().execute("http://datacenter.taichung.gov.tw/swagger/OpenData/6531113e-ac69-4f37-bec3-8b4dc66b7967").get();

            // JSON文件解析
            MyPark mypark = gson.fromJson(MyJson, MyPark.class);
            mypark.getROOT().getRECORD().size();

            mypark.getROOT().getRECORD().get(0).get停車場();


            //===== 設定 opendata 網址===============
/**
 * "ROOT": {
 *                 "RECORD": [
 *                 {
 *                     "停車場": "(公2-2)市政公園地下停車場",
 *                         "緯度": "24.15938",
 *                         "經度": "120.64716",
 *                         "位置": "臺中市西屯區文心路與惠中路間"
 *                 },
  */




            List<Map<String, Object>> mList;
            mList = new ArrayList<Map<String, Object>>();

            //----+表頭---------
            tab01.setVisibility(View.VISIBLE);
            Map<String, Object> item_title = new HashMap<String, Object>();
            item_title.put("park", "停車場");
            item_title.put("Longitude", "緯度");
            item_title.put("Latitude", "經度");
            item_title.put("location", "位置");
            item_title.put("time", "更新時間");

            mList.add(item_title);

            //-----開始逐筆轉換-----
            for (int i = 0; i <mypark.getROOT().getRECORD().size(); i++) {

                Map<String, Object> item = new HashMap<String, Object>();
                String County =mypark.getROOT().getRECORD().get(i).get停車場();
                String SiteName =mypark.getROOT().getRECORD().get(i).get經度();
                String Pm25 =mypark.getROOT().getRECORD().get(i).get緯度();
                String Status =mypark.getROOT().getRECORD().get(i).get位置();

                if (County.equals(check_t)) {
                    County = "...";
                } else {
                    check_t = County;
                }

                item.put("County", County);
                item.put("SiteName", SiteName);
                item.put("Pm25", Pm25);
                item.put("Status", Status);

                mList.add(item);

            }

            //=========設定listview========
            SimpleAdapter adapter = new SimpleAdapter(
                    this,
                    mList,
                    R.layout.list,
                    new String[]{"County", "SiteName", "Pm25", "Status"},
                    new int[]{R.id.t001, R.id.t002, R.id.t003, R.id.t004 }
            );
            listView.setAdapter(adapter);
            //----------------------------------------------------



        }//END TRY-CATCH
        catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }



    private JSONArray sortJsonArray(JSONArray jsonArray) {
        ArrayList<JSONObject> jsons = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                jsons.add(jsonArray.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }//END TRY

        }//END FOR

        Collections.sort(jsons, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject t1, JSONObject t2) {
                String lid = "";//未排序
                String rid = "";//排序
                try {
                    lid = t1.getString("停車場");
                    rid = t2.getString("停車場");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return lid.compareTo(rid);
            }
        });
        return new JSONArray(jsons);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.park_item01:
                Intent cos_maps = new Intent();
                cos_maps.setClass(OpenPark.this, Maps.class);
                startActivity(cos_maps);
                break;

            case R.id.park_item02:

                break;

            case R.id.park_finish:
                this.finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.park, menu);
        return true;
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }
}
