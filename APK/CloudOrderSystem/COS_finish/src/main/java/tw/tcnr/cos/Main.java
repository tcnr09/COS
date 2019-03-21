package tw.tcnr.cos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Timer;
import java.util.TimerTask;

import tw.tcnr.cos.R;


public class Main extends AppCompatActivity implements View.OnClickListener
{
    private Intent intent = new Intent();
    private ImageButton orderBtn,memberBtn,coscardBtn,recordBtn,cosmsgBtn,aboutusBtn;
    // 圖庫的圖片資源索引
    ViewPager viewPager;
    SliderViewPagerAdapter adapter;
    LinearLayout sliderDots;
    private int dotCounts;
    private ImageView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //viewPager
        viewPager = findViewById(R.id.c0401_img01);
        adapter = new SliderViewPagerAdapter(this);
        viewPager.setAdapter(adapter);
        sliderDots = findViewById(R.id.SliderDots);
        dotCounts = adapter.getCount();
        dots = new ImageView[dotCounts];
        for(int i=0;i<dotCounts;i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.slideshow_nonactive_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 2, 0, 0);
            //params.setMargins(8, 2, 8, 0);
            sliderDots.addView(dots[i], params);
        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.slideshow_dot));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                for(int i=0;i<dotCounts;i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.slideshow_nonactive_dot));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.slideshow_dot));
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(),3000,6000);

        setupViewComponent();
    }
    //viewPager
    private class MyTimerTask extends TimerTask
    {
        @Override
        public void run() {
            Main.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(viewPager.getCurrentItem()==0){
                        viewPager.setCurrentItem(1);
                    }else if(viewPager.getCurrentItem()==1){
                        viewPager.setCurrentItem(2);
                    }else{
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }

    private void setupViewComponent()
    {
        orderBtn=(ImageButton)findViewById(R.id.main_imgbtn01);
        memberBtn=(ImageButton)findViewById(R.id.main_imgbtn02);
        coscardBtn=(ImageButton)findViewById(R.id.main_imgbtn03);
        recordBtn=(ImageButton)findViewById(R.id.main_imgbtn04);
        cosmsgBtn=(ImageButton)findViewById(R.id.main_imgbtn05);
        aboutusBtn=(ImageButton)findViewById(R.id.main_imgbtn06);
        orderBtn.setOnClickListener(this);
        memberBtn.setOnClickListener(this);
        coscardBtn.setOnClickListener(this);
        recordBtn.setOnClickListener(this);
        cosmsgBtn.setOnClickListener(this);
        aboutusBtn.setOnClickListener(this);

    }



    @Override
    public void onClick(View v)
    {
        // 在 v 這個layout 選擇哪個按鈕被使用
        switch (v.getId()){
                case R.id.main_imgbtn01:
                    intent.putExtra("class_se", 0);
                    intent.putExtra("class_title", getString(R.string.c0401_cos01));
                    intent.setClass(Main.this, C0201.class);
                      //執行指定的class
                    startActivity(intent);
                    break;
            case R.id.main_imgbtn02:
                intent.putExtra("class_se", 0);
                intent.putExtra("class_title", getString(R.string.c0401_cos02));
                intent.setClass(Main.this, C0701.class);
                startActivity(intent);
                break;
            case R.id.main_imgbtn03:
                intent.putExtra("class_se", 0);
                intent.putExtra("class_title", getString(R.string.c0401_cos03));
                intent.setClass(Main.this, C0301.class);
                startActivity(intent);
                break;
            case R.id.main_imgbtn04:
                intent.putExtra("class_se", 0);
                intent.putExtra("class_title", getString(R.string.c0401_cos04));
                intent.setClass(Main.this, C0401R1.class);
                startActivity(intent);
                break;
            case R.id.main_imgbtn05:
                intent.putExtra("class_se", 0);
                intent.putExtra("class_title", getString(R.string.c0401_cos05));
               intent.setClass(Main.this, C0501.class);
                startActivity(intent);
                break;
            case R.id.main_imgbtn06:
                intent.putExtra("class_se", 0);
                intent.putExtra("class_title", getString(R.string.c0401_cos06));
                intent.setClass(Main.this, OpenPark.class);
                startActivity(intent);
                break;
        }
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
                cos_order.setClass(Main.this, C0201.class);
                startActivity(cos_order);
                break;

            case R.id.menu_item02:
                Intent cos_member = new Intent();
                cos_member.setClass(Main.this, C0701.class);
                startActivity(cos_member);
                break;

            case R.id.menu_item03:
                Intent cos_card = new Intent();
                cos_card.setClass(Main.this, C0301.class);
                startActivity(cos_card);
                break;

            case R.id.menu_item04:
                Intent cos_record = new Intent();
                cos_record.setClass(Main.this, C0401R1.class);
                startActivity(cos_record);
                break;

            case R.id.menu_item05:
                Intent cos_about = new Intent();
                cos_about.setClass(Main.this, C0501.class);
                startActivity(cos_about);
                break;

            case R.id.menu_item06:
                Intent cos_park = new Intent();
                cos_park.setClass(Main.this, OpenPark.class);
                startActivity(cos_park);
                break;

            case R.id.action_settings:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        this.finish();
    }

}
