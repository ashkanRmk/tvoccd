package ir.talifrafea.rafea;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import ir.talifrafea.rafea.Fragments.Doros_frag;
import ir.talifrafea.rafea.Fragments.Honar_frag;
import ir.talifrafea.rafea.Fragments.Keshavarzi_frag;
import ir.talifrafea.rafea.Fragments.Khadamat_frag;
import ir.talifrafea.rafea.Fragments.Sanat.Memari_Sanat;
import ir.talifrafea.rafea.Fragments.Sanat.Narm_Films;
import ir.talifrafea.rafea.Fragments.Sanat_frag;
import ir.talifrafea.rafea.Misc.ActionBarRtlizer;
import ir.talifrafea.rafea.Misc.BottomNavigationViewHelper;
import ir.talifrafea.rafea.Misc.HttpHandler;
import ir.talifrafea.rafea.Misc.RtlizeEverything;
import ir.talifrafea.rafea.Models.Child_Model;
import ir.talifrafea.rafea.Models.Item_Model;
import ir.talifrafea.rafea.Models.Parent_Model;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

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

    private String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;


    public List<Parent_Model> BarqParents = new LinkedList<>();
    public List<Parent_Model> MachineParent = new LinkedList<>();
    public List<Parent_Model> MemariParent = new LinkedList<>();
    public List<Parent_Model> MavadParent = new LinkedList<>();
    public List<Parent_Model> MekanikParent = new LinkedList<>();

    public List<Parent_Model> BazarganiParent = new LinkedList<>();
    public List<Parent_Model> BehdashtParent = new LinkedList<>();
    public List<Parent_Model> KhadamatParent = new LinkedList<>();
    public List<Parent_Model> KeshavarziParent = new LinkedList<>();
    public List<Parent_Model> HonarParent = new LinkedList<>();

    public List<Item_Model> DorosParents = new LinkedList<>();



    //Change Default Font
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        final String PREFS_NAME = "MyPrefsFile";
//
//        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
//
//        if (settings.getBoolean("my_first_time", true)) {
//            Log.d("Comments", "First time");
//            Intent intent = new Intent(MainActivity.this, IntroActivity.class);
//            startActivity(intent);
//            settings.edit().putBoolean("my_first_time", false).apply();
//        }
//
//
//        //Change Default Font
//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                .setDefaultFontPath("BYekan.ttf")
//                .setFontAttrId(R.attr.fontPath)
//                .build()
//        );

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

        new GetJSON().execute();

        changeFragment(1);
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetJSON extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("لطفا صبر کنید...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String url1 = "http://tvoccd.ir/JSON_files/sanat.json";
            String jsonStr = sh.makeServiceCall(url1);
            String url_doros = "http://tvoccd.ir/JSON_files/doros.json";
            String jsonStr_doros = sh.makeServiceCall(url_doros);
            String url_honar_kesh = "http://tvoccd.ir/JSON_files/honar_keshavarzi.json";
            String jsonStr_honar = sh.makeServiceCall(url_honar_kesh);
            String url_khadam = "http://tvoccd.ir/JSON_files/khadamat.json";
            String jsonStr_khadam = sh.makeServiceCall(url_khadam);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null && jsonStr_doros != null && jsonStr_honar != null &&
            jsonStr_khadam != null) {
                try {

                    JSONObject jsonObj_khadam = new JSONObject(jsonStr_khadam);
                    JSONArray khadam = jsonObj_khadam.getJSONArray("khadamat");
                    for (int i = 0; i < khadam.length(); i++){
                        JSONObject goroh = khadam.getJSONObject(i);
                        String name = goroh.getString("name");
                        switch (name){
                            case "bazargani":
                                JSONArray bazargani = goroh.getJSONArray("reshte");

                                // looping through All
                                for (int m = 0; m < bazargani.length(); m++) {
                                    JSONObject item = bazargani.getJSONObject(m);

                                    String groupName = item.getString("group");
                                    Parent_Model parentModel = new Parent_Model(groupName);

                                    JSONArray child = item.getJSONArray("child");
                                    BazarganiParent.add(parentModel);
                                    List<Child_Model> childModels = new LinkedList<>();

                                    for (int j = 0; j < child.length(); j++) {
                                        JSONObject childItem = child.getJSONObject(j);

                                        String childName = childItem.getString("title");
                                        childModels.add(new Child_Model(childName));

                                        List<Item_Model> itemModels = new LinkedList<>();
                                        JSONArray urls = childItem.getJSONArray("url");
                                        for (int k = 0; k < urls.length(); k++) {
                                            JSONObject url = urls.getJSONObject(k);

                                            String fileTitle = url.getString("file");
                                            String adr = url.getString("adr");
                                            itemModels.add(new Item_Model(fileTitle, adr));
                                        }
                                        childModels.get(j).setMyItems(itemModels);
                                    }
                                    parentModel.setMyChilds(childModels);
                                }
                                break;
                            case "behdasht":
                                JSONArray behdasht = goroh.getJSONArray("reshte");

                                // looping through All
                                for (int m = 0; m < behdasht.length(); m++) {
                                    JSONObject item = behdasht.getJSONObject(m);

                                    String groupName = item.getString("group");
                                    Parent_Model parentModel = new Parent_Model(groupName);

                                    JSONArray child = item.getJSONArray("child");
                                    BehdashtParent.add(parentModel);
                                    List<Child_Model> childModels = new LinkedList<>();

                                    for (int j = 0; j < child.length(); j++) {
                                        JSONObject childItem = child.getJSONObject(j);

                                        String childName = childItem.getString("title");
                                        childModels.add(new Child_Model(childName));

                                        List<Item_Model> itemModels = new LinkedList<>();
                                        JSONArray urls = childItem.getJSONArray("url");
                                        for (int k = 0; k < urls.length(); k++) {
                                            JSONObject url = urls.getJSONObject(k);

                                            String fileTitle = url.getString("file");
                                            String adr = url.getString("adr");
                                            itemModels.add(new Item_Model(fileTitle, adr));
                                        }
                                        childModels.get(j).setMyItems(itemModels);
                                    }
                                    parentModel.setMyChilds(childModels);
                                }
                                break;
                            case "khadamat":
                                JSONArray khadamat = goroh.getJSONArray("reshte");

                                // looping through All
                                for (int m = 0; m < khadamat.length(); m++) {
                                    JSONObject item = khadamat.getJSONObject(m);

                                    String groupName = item.getString("group");
                                    Parent_Model parentModel = new Parent_Model(groupName);

                                    JSONArray child = item.getJSONArray("child");
                                    KhadamatParent.add(parentModel);
                                    List<Child_Model> childModels = new LinkedList<>();

                                    for (int j = 0; j < child.length(); j++) {
                                        JSONObject childItem = child.getJSONObject(j);

                                        String childName = childItem.getString("title");
                                        childModels.add(new Child_Model(childName));

                                        List<Item_Model> itemModels = new LinkedList<>();
                                        JSONArray urls = childItem.getJSONArray("url");
                                        for (int k = 0; k < urls.length(); k++) {
                                            JSONObject url = urls.getJSONObject(k);

                                            String fileTitle = url.getString("file");
                                            String adr = url.getString("adr");
                                            itemModels.add(new Item_Model(fileTitle, adr));
                                        }
                                        childModels.get(j).setMyItems(itemModels);
                                    }
                                    parentModel.setMyChilds(childModels);
                                }
                                break;
                        }
                    }


                    JSONObject jsonObj_honar_kesh = new JSONObject(jsonStr_honar);
                    JSONArray honar_kesh = jsonObj_honar_kesh.getJSONArray("honar_keshavarzi");
                    for (int i = 0; i < honar_kesh.length(); i++){
                        JSONObject goroh = honar_kesh.getJSONObject(i);
                        String name = goroh.getString("name");
                        switch (name){
                            case "honar":
                                JSONArray honar = goroh.getJSONArray("reshte");

                                // looping through All
                                for (int m = 0; m < honar.length(); m++) {
                                    JSONObject item = honar.getJSONObject(m);

                                    String groupName = item.getString("group");
                                    Parent_Model parentModel = new Parent_Model(groupName);

                                    JSONArray child = item.getJSONArray("child");
                                    HonarParent.add(parentModel);
                                    List<Child_Model> childModels = new LinkedList<>();

                                    for (int j = 0; j < child.length(); j++) {
                                        JSONObject childItem = child.getJSONObject(j);

                                        String childName = childItem.getString("title");
                                        childModels.add(new Child_Model(childName));

                                        List<Item_Model> itemModels = new LinkedList<>();
                                        JSONArray urls = childItem.getJSONArray("url");
                                        for (int k = 0; k < urls.length(); k++) {
                                            JSONObject url = urls.getJSONObject(k);

                                            String fileTitle = url.getString("file");
                                            String adr = url.getString("adr");
                                            itemModels.add(new Item_Model(fileTitle, adr));
                                        }
                                        childModels.get(j).setMyItems(itemModels);
                                    }
                                    parentModel.setMyChilds(childModels);
                                }
                                break;
                            case "keshavarzi":
                                JSONArray keshavarzi = goroh.getJSONArray("reshte");

                                // looping through All
                                for (int m = 0; m < keshavarzi.length(); m++) {
                                    JSONObject item = keshavarzi.getJSONObject(m);

                                    String groupName = item.getString("group");
                                    Parent_Model parentModel = new Parent_Model(groupName);

                                    JSONArray child = item.getJSONArray("child");
                                    KeshavarziParent.add(parentModel);
                                    List<Child_Model> childModels = new LinkedList<>();

                                    for (int j = 0; j < child.length(); j++) {
                                        JSONObject childItem = child.getJSONObject(j);

                                        String childName = childItem.getString("title");
                                        childModels.add(new Child_Model(childName));

                                        List<Item_Model> itemModels = new LinkedList<>();
                                        JSONArray urls = childItem.getJSONArray("url");
                                        for (int k = 0; k < urls.length(); k++) {
                                            JSONObject url = urls.getJSONObject(k);

                                            String fileTitle = url.getString("file");
                                            String adr = url.getString("adr");
                                            itemModels.add(new Item_Model(fileTitle, adr));
                                        }
                                        childModels.get(j).setMyItems(itemModels);
                                    }
                                    parentModel.setMyChilds(childModels);
                                }
                                break;
                        }
                    }




                    JSONObject jsonObj = new JSONObject(jsonStr);


                    JSONArray sanat = jsonObj.getJSONArray("sanat");
                    for(int i = 0; i < sanat.length(); i++){
                        JSONObject goroh = sanat.getJSONObject(i);

                        String name = goroh.getString("name");
                        switch (name){
                            case "barq":
                                JSONArray barq = goroh.getJSONArray("reshte");

                                // looping through All
                                for (int m = 0; m < barq.length(); m++) {
                                    JSONObject item = barq.getJSONObject(m);

                                    String groupName = item.getString("group");
                                    Parent_Model parentModel = new Parent_Model(groupName);

                                    JSONArray child = item.getJSONArray("child");
                                    BarqParents.add(parentModel);
                                    List<Child_Model> childModels = new LinkedList<>();

                                    for (int j = 0; j < child.length(); j++) {
                                        JSONObject childItem = child.getJSONObject(j);

                                        String childName = childItem.getString("title");
                                        childModels.add(new Child_Model(childName));

                                        List<Item_Model> itemModels = new LinkedList<>();
                                        JSONArray urls = childItem.getJSONArray("url");
                                        for (int k = 0; k < urls.length(); k++) {
                                            JSONObject url = urls.getJSONObject(k);

                                            String fileTitle = url.getString("file");
                                            String adr = url.getString("adr");
                                            itemModels.add(new Item_Model(fileTitle, adr));
                                        }
                                        childModels.get(j).setMyItems(itemModels);
                                    }
                                    parentModel.setMyChilds(childModels);
                                }
                                break;
                            case "machine":
                                JSONArray machine = goroh.getJSONArray("reshte");

                                // looping through All
                                for (int m = 0; m < machine.length(); m++) {
                                    JSONObject item = machine.getJSONObject(m);

                                    String groupName = item.getString("group");
                                    Parent_Model parentModel = new Parent_Model(groupName);

                                    JSONArray child = item.getJSONArray("child");
                                    MachineParent.add(parentModel);
                                    List<Child_Model> childModels = new LinkedList<>();

                                    for (int j = 0; j < child.length(); j++) {
                                        JSONObject childItem = child.getJSONObject(j);

                                        String childName = childItem.getString("title");
                                        childModels.add(new Child_Model(childName));

                                        List<Item_Model> itemModels = new LinkedList<>();
                                        JSONArray urls = childItem.getJSONArray("url");
                                        for (int k = 0; k < urls.length(); k++) {
                                            JSONObject url = urls.getJSONObject(k);

                                            String fileTitle = url.getString("file");
                                            String adr = url.getString("adr");
                                            itemModels.add(new Item_Model(fileTitle, adr));
                                        }
                                        childModels.get(j).setMyItems(itemModels);
                                    }
                                    parentModel.setMyChilds(childModels);
                                }
                                break;
                            case "memari":
                                JSONArray memari = goroh.getJSONArray("reshte");

                                // looping through All
                                for (int m = 0; m < memari.length(); m++) {
                                    JSONObject item = memari.getJSONObject(m);

                                    String groupName = item.getString("group");
                                    Parent_Model parentModel = new Parent_Model(groupName);

                                    JSONArray child = item.getJSONArray("child");
                                    MemariParent.add(parentModel);
                                    List<Child_Model> childModels = new LinkedList<>();

                                    for (int j = 0; j < child.length(); j++) {
                                        JSONObject childItem = child.getJSONObject(j);

                                        String childName = childItem.getString("title");
                                        childModels.add(new Child_Model(childName));

                                        List<Item_Model> itemModels = new LinkedList<>();
                                        JSONArray urls = childItem.getJSONArray("url");
                                        for (int k = 0; k < urls.length(); k++) {
                                            JSONObject url = urls.getJSONObject(k);

                                            String fileTitle = url.getString("file");
                                            String adr = url.getString("adr");
                                            itemModels.add(new Item_Model(fileTitle, adr));
                                        }
                                        childModels.get(j).setMyItems(itemModels);
                                    }
                                    parentModel.setMyChilds(childModels);
                                }
                                break;
                            case "mavad":
                                JSONArray mavad = goroh.getJSONArray("reshte");

                                // looping through All
                                for (int m = 0; m < mavad.length(); m++) {
                                    JSONObject item = mavad.getJSONObject(m);

                                    String groupName = item.getString("group");
                                    Parent_Model parentModel = new Parent_Model(groupName);

                                    JSONArray child = item.getJSONArray("child");
                                    MavadParent.add(parentModel);
                                    List<Child_Model> childModels = new LinkedList<>();

                                    for (int j = 0; j < child.length(); j++) {
                                        JSONObject childItem = child.getJSONObject(j);

                                        String childName = childItem.getString("title");
                                        childModels.add(new Child_Model(childName));

                                        List<Item_Model> itemModels = new LinkedList<>();
                                        JSONArray urls = childItem.getJSONArray("url");
                                        for (int k = 0; k < urls.length(); k++) {
                                            JSONObject url = urls.getJSONObject(k);

                                            String fileTitle = url.getString("file");
                                            String adr = url.getString("adr");
                                            itemModels.add(new Item_Model(fileTitle, adr));
                                        }
                                        childModels.get(j).setMyItems(itemModels);
                                    }
                                    parentModel.setMyChilds(childModels);
                                }
                                break;
                            case "mekanik":
                                JSONArray mekanik = goroh.getJSONArray("reshte");

                                // looping through All
                                for (int m = 0; m < mekanik.length(); m++) {
                                    JSONObject item = mekanik.getJSONObject(m);

                                    String groupName = item.getString("group");
                                    Parent_Model parentModel = new Parent_Model(groupName);

                                    JSONArray child = item.getJSONArray("child");
                                    MekanikParent.add(parentModel);
                                    List<Child_Model> childModels = new LinkedList<>();

                                    for (int j = 0; j < child.length(); j++) {
                                        JSONObject childItem = child.getJSONObject(j);

                                        String childName = childItem.getString("title");
                                        childModels.add(new Child_Model(childName));

                                        List<Item_Model> itemModels = new LinkedList<>();
                                        JSONArray urls = childItem.getJSONArray("url");
                                        for (int k = 0; k < urls.length(); k++) {
                                            JSONObject url = urls.getJSONObject(k);

                                            String fileTitle = url.getString("file");
                                            String adr = url.getString("adr");
                                            itemModels.add(new Item_Model(fileTitle, adr));
                                        }
                                        childModels.get(j).setMyItems(itemModels);
                                    }
                                    parentModel.setMyChilds(childModels);
                                }
                                break;
                        }
                    }

                    JSONObject jsonObj_doros = new JSONObject(jsonStr_doros);

                    // Getting JSON Array node For Doros Moshtarak :D
                    JSONArray doros = jsonObj_doros.getJSONArray("doros");

                    for (int i = 0; i < doros.length(); i++) {
                        JSONObject item = doros.getJSONObject(i);

                        String name = item.getString("file");
                        String url = item.getString("adr");
                        DorosParents.add(new Item_Model(name, url));
                    }

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        View parentLayout = findViewById(R.id.container);

                        Snackbar snackbar = Snackbar
                                .make(parentLayout, "مشکل در برقراری اتصال به اینترنت", Snackbar.LENGTH_INDEFINITE)
                                .setAction("تنظیمات WIFI", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent=new Intent(Settings.ACTION_WIFI_SETTINGS);
                                        startActivity(intent);
                                    }
                                });
                        snackbar.show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
        }
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
        } else if (position == 4) {
            newFragment = new Khadamat_frag();
        } else if (position == 5) {
            newFragment = new Sanat_frag();
        }

        // Create new fragment and transaction
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack if needed
        transaction.replace(R.id.fragmentContainer, newFragment, "NewFragmentTag");
//        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    public void getFilmFragment() {
        Fragment newFragment = new Narm_Films();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragmentContainer, newFragment, "NewFragmentTag");
        transaction.addToBackStack(null);

        transaction.commit();
    }


}
