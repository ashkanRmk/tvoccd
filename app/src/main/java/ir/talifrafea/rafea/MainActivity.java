package ir.talifrafea.rafea;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import ir.talifrafea.rafea.Fragments.Doros_frag;
import ir.talifrafea.rafea.Fragments.Honar_frag;
import ir.talifrafea.rafea.Fragments.Keshavarzi_frag;
import ir.talifrafea.rafea.Fragments.Khadamat_frag;
import ir.talifrafea.rafea.Fragments.Sanat.Narm_Films;
import ir.talifrafea.rafea.Fragments.Sanat_frag;
import ir.talifrafea.rafea.Misc.ActionBarRtlizer;
import ir.talifrafea.rafea.Misc.BottomNavigationViewHelper;
import ir.talifrafea.rafea.Misc.RtlizeEverything;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static java.security.AccessController.getContext;


public class MainActivity extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.doros:
                    changeFragment(1);
                    return true;
                case R.id.honar:
                    changeFragment(2);
                    return true;
                case R.id.keshavarzi:
                    changeFragment(3);
                    return true;
                case R.id.khadamat:
                    changeFragment(4);
                    return true;
                case R.id.sanat:
                    changeFragment(5);
                    return true;
            }
            return false;
        }
    };

    //Change Default Font
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Change Default Font
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("BYekan.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );


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

        changeFragment(1);

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
            newFragment = new Keshavarzi_frag();
        }else if (position == 4) {
            newFragment = new Khadamat_frag();
        }else if (position == 5) {
            newFragment = new Sanat_frag();
        }


        // Create new fragment and transaction
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack if needed
        transaction.replace(R.id.fragmentContainer, newFragment,  "NewFragmentTag");
//        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    public void getFilmFragment()
    {
        Fragment newFragment = new Narm_Films();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragmentContainer, newFragment,  "NewFragmentTag");
        transaction.addToBackStack(null);

        transaction.commit();
    }


}
