package tw.tcnr.cos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import tw.tcnr.cos.R;

public class C0301 extends AppCompatActivity {


    private cardpoint_Fragment cardpoint;
    private pointRecord_Fragment pointrec;
    private BottomNavigationView navigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c0301);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        showNav(R.id.navigation_home);


    }

    private void showNav(int navigation_home) {

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    getSupportFragmentManager().beginTransaction()
                            .remove(cardpoint)
                            .commit();
                    getSupportFragmentManager().beginTransaction()
                            .remove(pointrec)
                            .commit();
                    return true;
                case R.id.navigation_dashboard:
                    // 在FrameLayout新增Fragment片段
                    cardpoint = new cardpoint_Fragment(); // 建立片段物件
                    // 取得FragmentManager物件
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame, cardpoint)
                            .commit();           // 確認交易
                    return true;
                case R.id.navigation_notifications:
                    pointrec = new pointRecord_Fragment(); // 建立片段物件
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame, pointrec)
                            .commit();
                    return true;
            }
            return false;
        }
    };
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return false;
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
                cos_order.setClass(C0301.this, C0201.class);
                startActivity(cos_order);
                break;

            case R.id.menu_item02:
                Intent cos_member = new Intent();
                cos_member.setClass(C0301.this, C0701.class);
                startActivity(cos_member);
                break;

            case R.id.menu_item03:

                break;

            case R.id.menu_item04:
                Intent cos_record = new Intent();
                cos_record.setClass(C0301.this, C0401R1.class);
                startActivity(cos_record);
                break;

            case R.id.menu_item05:
                Intent cos_about = new Intent();
                cos_about.setClass(C0301.this, C0501.class);
                startActivity(cos_about);
                break;

            case R.id.menu_item06:
                Intent cos_park = new Intent();
                cos_park.setClass(C0301.this, OpenPark.class);
                startActivity(cos_park);
                break;

            case R.id.action_settings:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
