package ir.talifrafea.rafea;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.doros:
                    mTextMessage.setText("دروس مشترک فنی و حرفه‌ای");
                    changeFragment(1);
                    return true;
                case R.id.honar:
                    mTextMessage.setText("زمینه هنر");
                    return true;
                case R.id.keshavarzi:
                    mTextMessage.setText("زمینه کشاورزی");
                    return true;
                case R.id.khadamat:
                    mTextMessage.setText("زمینه خدمات");
                    return true;
                case R.id.sanat:
                    mTextMessage.setText("زمینه صنعت");
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set Manually Title of Action Bar
        getSupportActionBar().setTitle("معرفی رشته‌های شاخه فنی‌حرفه‌ای");

        //getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL); //API +17

        // RTL action bar API +7
        ActionBarRtlizer rtlizer = new ActionBarRtlizer(this);
        ViewGroup actionBarView = rtlizer.getActionBarView();
        ViewGroup homeView = (ViewGroup) rtlizer.findViewByClass("HomeView", actionBarView);

        rtlizer.flipActionBarUpIconIfAvailable(homeView);
        RtlizeEverything.rtlize(actionBarView);
        RtlizeEverything.rtlize(homeView);

        mTextMessage = (TextView) findViewById(R.id.message);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Make icon bigger in navigation bottom
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigation.getChildAt(0);
        for (int i = 0; i < menuView.getChildCount(); i++) {
            final View iconView = menuView.getChildAt(i).findViewById(android.support.design.R.id.icon);
            final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
            final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            // set your height here
            layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, displayMetrics);
            // set your width here
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, displayMetrics);
            iconView.setLayoutParams(layoutParams);
        }

        //Disable animate of Navigation bottom
        BottomNavigationViewHelper.disableShiftMode(navigation);

    }

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "برای خروج دوباره کلیک کنید", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    private void changeFragment(int position) {

        Fragment newFragment = null;

        if (position == 1) {
            newFragment = new Doros_frag();
        } else if (position == 2) {
            newFragment = new Honar_frag();
        } else if (position == 3) {
            newFragment = new Sanat_frag();
        }else if (position == 4) {
            newFragment = new Keshavarzi_frag();
        }else if (position == 5) {
            newFragment = new Khadamat_frag();
        }

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragmentContainer, newFragment).commit();
    }




}
