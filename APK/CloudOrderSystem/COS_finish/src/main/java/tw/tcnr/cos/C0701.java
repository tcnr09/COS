package tw.tcnr.cos;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import tw.tcnr.cos.R;


public class C0701 extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "COS=>";
    private static final int RC_SIGN_IN = 9001;

    private TextView mStatusTextView;
    private GoogleSignInClient mGoogleSignInClient;
    private Uri User_IMAGE;
    private CircleImageView img;

    //permission
    private String[] permit = new String[]{
            Manifest.permission.INTERNET,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,



    };
    //--------------------------
    private static ContentResolver mContRes;
    private String[] MYCOLUMN = new String[]{"id", "name", "grp", "address"};

    String strAPIKey = "563364486085-7d7nfc7aoakd3ir4a0u9p6nf5gtrddap.apps.googleusercontent.com";

    private static final int REQUEST_CODE = 100;
    private String mNumber;


    private String g_Email,g_GivenName,g_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c0701);
        setupViewComponent();


    }

    private void setupViewComponent() {
        //	--設置標題--
        Intent intent=this.getIntent();
        String mode_title = intent.getStringExtra("class_title");
        this.setTitle(mode_title);
        //-----------------------------
        mStatusTextView = findViewById(R.id.status);
        // Button listeners
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);
        findViewById(R.id.disconnect_button).setOnClickListener(this);

        findViewById(R.id.add).setOnClickListener(this);

        // START configure_signin
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        //END configure_signin

        //START build_client
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        //END build_client

        // START customize_button
        // Set the dimensions of the sign-in button.
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setColorScheme(SignInButton.COLOR_LIGHT);
        // END customize_button


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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                //permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { //SDK 23 Android 6.0
                    List<String> mPermissionList = new ArrayList<>();
                    for (String permission : permit) {
                        // 检查权限
                        // ContextCompat.checkSelfPermission(Context context, String permission)
                        // 返回值PackageManager常量有两种情况：
                        // PackageManager.PERMISSION_GRANTED（有权限）
                        // PackageManager.PERMISSION_DENIED（无权限）

                        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {//权限判断
                            mPermissionList.add(permission);
                        }
                    }
                    if (!mPermissionList.isEmpty()) {
                        // 请求权限
                        // ActivityCompat.requestPermissions(Activity activity, String[] permissions, int requestCode)
                        ActivityCompat.requestPermissions(this, mPermissionList.toArray(new String[mPermissionList.size()]), REQUEST_CODE);
                    } else { //表示全都授权了
                        signIn();
                    }

                } else {//SDK < 23 Android 6.0
                    signIn();
                }
                break;
            case R.id.sign_out_button:
                signOut();
                break;
            case R.id.disconnect_button:
                revokeAccess();
                break;
            case  R.id.add:
                //新增

                //-------直接增加到MySQL 20190215-------------------------------
                mysql_insert();
                //----------------------------------------
                break;
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // START_EXCLUDE
                        updateUI(null);
                        // END_EXCLUDE
                    }
                });
    }

    private void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // START_EXCLUDE
                        updateUI(null);
                        // END_EXCLUDE
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();

        // START on_start_sign_in
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
        // END on_start_sign_in
    }

    // START onActivityResult
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    // END onActivityResult

    // START handleSignInResult
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.d(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    // END handleSignInResult
    //--------------------------------------------
    public static Bitmap getBitmapFromURL(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    private void updateUI(GoogleSignInAccount account) {
        if (account != null) {
            mStatusTextView.setText(getString(R.string.signed_in_fmt, account.getDisplayName()) + "\n"     //登入變登出
                    + getString(R.string.email, account.getEmail()) + "\n"  //字串要格式化,在value/m1507 + %s
                    + getString(R.string.first_name, account.getGivenName()) + "\n"
                    + getString(R.string.last_name, account.getFamilyName()) + "\n"
                    + getString(R.string.id, account.getId())

            );
            //登入之後將下方資料寫進自己的資料庫
            g_id=account.getId();
            g_GivenName=account.getGivenName();
            g_Email=account.getEmail();

//            mysql_insert();
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("name",account.getGivenName()));
            nameValuePairs.add(new BasicNameValuePair("grp",account.getId()));
            nameValuePairs.add(new BasicNameValuePair("address",account.getEmail()));

            try {
                Thread.sleep(500);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

            //-----------------------------------------------
            String result = LoginDBConnector.executeInsert("SELECT * FROM member", nameValuePairs);
            //-----------------------------------------------


            //-------改變圖像--------------
            User_IMAGE = account.getPhotoUrl();
            if (User_IMAGE == null) {
                return;
            }
            img = (CircleImageView) findViewById(R.id.google_icon);
            new AsyncTask<String, Void, Bitmap>() {
                @Override
                protected Bitmap doInBackground(String... params) {
                    String url = params[0];
                    return getBitmapFromURL(url);
                }

                @Override
                protected void onPostExecute(Bitmap result) {
                    img.setImageBitmap(result);
                    super.onPostExecute(result);
                }
            }.execute(User_IMAGE.toString().trim());
            //-------------------------

            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
        } else {
            mStatusTextView.setText(R.string.signed_out);

            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        }
    }
    private void mysql_insert() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("name",g_GivenName));
        nameValuePairs.add(new BasicNameValuePair("grp",g_id));
        nameValuePairs.add(new BasicNameValuePair("address",g_Email));

        try {
            Thread.sleep(500);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        //-----------------------------------------------
        String result = LoginDBConnector.executeInsert("", nameValuePairs);
        //-----------------------------------------------

        //Refresh匯入MySQL
//        dbmysql();



    }

    //处理权限请求结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0) {
                //存放没授权的权限
                List<String> deniedPermissions = new ArrayList<>();
                for (int i = 0; i < grantResults.length; i++) {
                    int grantResult = grantResults[i];
                    String permission = permissions[i];
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        deniedPermissions.add(permission);
                    }
                }
                if (deniedPermissions.isEmpty()) {
                    //都授权了
                    getPhone();
                } else { //没有授权
                    Toast.makeText(C0701.this, "被拒绝的权限：" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }//END Request Code

    }//onRequestPermissionsResult

    //取得手機號碼
    private void getPhone() {
        TelephonyManager mTelManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        //下面給出獲取手機本機號碼的程式碼:

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        //取得手機號碼 ，因哭狗隱私政策問題現在無法獲取只會得到null
        mNumber = mTelManager.getLine1Number();
        //取得手機IMEI碼
        String IMEI = mTelManager.getDeviceId();

//取得手機IMSI碼
        String IMSI = mTelManager.getSubscriberId();

//取得手機漫遊狀態
        String mRoaming = mTelManager.isNetworkRoaming() ? "漫遊中" : "非漫遊";

//取得電信網路國別
        String mCountry = mTelManager.getNetworkCountryIso();

//取得電信公司編號
        String mOperator = mTelManager.getNetworkOperator();

//取得電信公司名稱
        String mOperatorName = mTelManager.getNetworkOperatorName();

//取得通訊傳輸類型
        String[] mPhoneType = {"NONE", "GSM", "CDMA"};
        String mTelPhoneType = mPhoneType[mTelManager.getPhoneType()];

        Toast.makeText(C0701.this, "TEL：" +mNumber, Toast.LENGTH_SHORT).show();


    }

}


