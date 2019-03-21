package tw.tcnr.cos;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import tw.tcnr.cos.R;

public class C0501 extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c0501);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //check if the event was the back button and if there's history
        WebView webView = findViewById(R.id.c0501_w001);
        WebView webView2 = findViewById(R.id.c0502_w001);


        webView.setWebViewClient(new WebViewClient());
        webView2.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView2.getSettings().setJavaScriptEnabled(true);

        if ((keyCode==KeyEvent.KEYCODE_BACK) && webView.canGoBack()){
            webView.goBack();
            return true;


        }else if ((keyCode==KeyEvent.KEYCODE_BACK) && webView2.canGoBack()){
            webView2.goBack();
            return true;
        }

        //if it was not the back or there is no web page history, bubble up to the default system behaviour
        return super.onKeyDown(keyCode, event);
    }

//Deleted PlaceholderFragment from here

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    C0501_Tab01 tab01=new C0501_Tab01();
                    return tab01;
                case 1:
                    C0501_Tab02 tab02=new C0501_Tab02();
                    return  tab02;
                default:
                    return null;

            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
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
                cos_order.setClass(C0501.this, C0201.class);
                startActivity(cos_order);
                break;

            case R.id.menu_item02:
                Intent cos_member = new Intent();
                cos_member.setClass(C0501.this, C0701.class);
                startActivity(cos_member);
                break;

            case R.id.menu_item03:
                Intent cos_card = new Intent();
                cos_card.setClass(C0501.this, C0301.class);
                startActivity(cos_card);
                break;

            case R.id.menu_item04:
                Intent cos_record = new Intent();
                cos_record.setClass(C0501.this, C0401R1.class);
                startActivity(cos_record);
                break;

            case R.id.menu_item05:

                break;

            case R.id.menu_item06:
                Intent cos_park = new Intent();
                cos_park.setClass(C0501.this, OpenPark.class);
                startActivity(cos_park);
                break;

            case R.id.action_settings:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
