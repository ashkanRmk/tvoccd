package ir.talifrafea.rafea;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;
import com.orhanobut.hawk.LogLevel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import javax.crypto.EncryptedPrivateKeyInfo;

import ir.talifrafea.rafea.Fragments.Doros_frag;
import ir.talifrafea.rafea.Fragments.Honar_frag;
import ir.talifrafea.rafea.Fragments.Keshavarzi_frag;
import ir.talifrafea.rafea.Fragments.Khadamat_frag;
import ir.talifrafea.rafea.Fragments.Sanat.Films.SeasonFilms;
import ir.talifrafea.rafea.Fragments.Sanat.Memari_Sanat;
import ir.talifrafea.rafea.Fragments.Sanat.Narm_Films;
import ir.talifrafea.rafea.Fragments.Sanat_frag;
import ir.talifrafea.rafea.Misc.ActionBarRtlizer;
import ir.talifrafea.rafea.Misc.BottomNavigationViewHelper;
import ir.talifrafea.rafea.Misc.HttpHandler;
import ir.talifrafea.rafea.Misc.RtlizeEverything;
import ir.talifrafea.rafea.Models.Child_Model;
import ir.talifrafea.rafea.Models.Film_models.Film_Seasons;
import ir.talifrafea.rafea.Models.Film_models.Film_SoftName;
import ir.talifrafea.rafea.Models.Film_models.Film_main;
import ir.talifrafea.rafea.Models.Item_Model;
import ir.talifrafea.rafea.Models.Middle_CHild;
import ir.talifrafea.rafea.Models.Parent_Model;
import ir.talifrafea.rafea.Models.Season_Modal;
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.resume) {
            String url = "https://goo.gl/forms/iUdXLphe9BWErEHI3";
            try {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "No application can handle this request."
                        + " Please install a web browser",  Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
            return true;
        }
        if (id == R.id.exit) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }




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

    public List<Film_main> filmsList = new LinkedList<>();

    public int mainPos;
    public int seasonPos;

    //Change Default Font
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String PREFS_NAME = "MyPrefsFile";

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        if (settings.getBoolean("my_first_time", true)) {
            Log.d("Comments", "First time");
            Intent intent = new Intent(MainActivity.this, IntroActivity.class);
            startActivity(intent);
            settings.edit().putBoolean("my_first_time", false).commit();
        }



        Hawk.init(getApplicationContext())
                .setEncryptionMethod(HawkBuilder.EncryptionMethod.NO_ENCRYPTION)
                .setStorage(HawkBuilder.newSharedPrefStorage(getApplicationContext()))
                .setLogLevel(LogLevel.NONE)
                .build();


        //Set Manually Title of Action Bar
        getSupportActionBar().setTitle("     معرفی رشته‌های شاخه فنی‌حرفه‌ای");

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

            String jsonStr;
            String jsonStr_doros;
            String jsonStr_honar;
            String jsonStr_khadam;
            String jsonStr_Films;

            if (!isNetworkConnected() && Hawk.count() > 2)
            {
                jsonStr = Hawk.get("sanat");
                jsonStr_doros = Hawk.get("doros");
                jsonStr_honar = Hawk.get("honar");
                jsonStr_khadam = Hawk.get("khadamat");
                jsonStr_Films = Hawk.get("films");
            }
            else {
                // Making a request to url and getting response
                String url1 = "http://tvoccd.ir/JSON_files/sanat.json";
                jsonStr = sh.makeServiceCall(url1);
                String url_doros = "http://tvoccd.ir/JSON_files/doros.json";
                jsonStr_doros = sh.makeServiceCall(url_doros);
                String url_honar_kesh = "http://tvoccd.ir/JSON_files/honar_keshavarzi.json";
                jsonStr_honar = sh.makeServiceCall(url_honar_kesh);
                String url_khadam = "http://tvoccd.ir/JSON_files/khadamat.json";
                jsonStr_khadam = sh.makeServiceCall(url_khadam);
                String url_Films = "http://tvoccd.ir/JSON_files/Films.json";
                jsonStr_Films = sh.makeServiceCall(url_Films);

                Hawk.put("sanat", jsonStr);
                Hawk.put("doros", jsonStr_doros);
                Hawk.put("honar", jsonStr_honar);
                Hawk.put("khadamat", jsonStr_khadam);
                Hawk.put("films", jsonStr_Films);
            }

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null && jsonStr_doros != null && jsonStr_honar != null &&
            jsonStr_khadam != null) {
                try {

                    //JSON Parsing for Films Section
                    JSONObject jsonObj_films = new JSONObject(jsonStr_Films);
                    JSONArray films = jsonObj_films.getJSONArray("films");
                    for (int i = 0; i < films.length(); i++)
                    {
                        JSONObject mainFilm = films.getJSONObject(i);
                        String mainFilmName = mainFilm.getString("file");
                        filmsList.add(new Film_main(mainFilmName));

                        JSONArray seasons = mainFilm.getJSONArray("adr");
                        List<Film_Seasons> filmSeasons = new LinkedList<>();
                        for (int j = 0; j < seasons.length(); j++)
                        {
                            JSONObject seasonGroup = seasons.getJSONObject(j);
                            String SeasonName = seasonGroup.getString("season_group");
                            filmSeasons.add(new Film_Seasons(SeasonName));

                            JSONArray softFilms = seasonGroup.getJSONArray("season_child");
                            List<Film_SoftName> softNameList = new LinkedList<>();
                            for (int k = 0; k < softFilms.length(); k++)
                            {
                                JSONObject softName = softFilms.getJSONObject(k);
                                String Title = softName.getString("file");
                                String Url = softName.getString("adr");
                                softNameList.add(new Film_SoftName(Title, Url));
                            }
                            filmSeasons.get(j).setSoftNames(softNameList);
                        }
                        filmsList.get(i).set_seasons(filmSeasons);
                    }


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

                                    boolean ch = false;

                                    for (int j = 0; j < child.length(); j++) {
                                        ch = false;

                                        JSONObject childItem = child.getJSONObject(j);

                                        String childName = childItem.getString("title");
                                        if (childName == "فیلم‌های رشته")
                                            ch = true;

                                        childModels.add(new Child_Model(childName));

                                        List<Item_Model> itemModels = new LinkedList<>();
                                        List<Middle_CHild> middle_cHilds = new LinkedList<>();
                                        JSONArray urls = childItem.getJSONArray("url");
                                        for (int k = 0; k < urls.length(); k++) {
                                            JSONObject url = urls.getJSONObject(k);

                                            String fileTitle = url.getString("file");
////////////////
                                            if (ch) {
                                                middle_cHilds.add(new Middle_CHild(fileTitle));

                                                List<Season_Modal> season_modals = new LinkedList<>();
                                                JSONArray seasons = url.getJSONArray("adr");
                                                for (int v = 0; v < seasons.length(); v++) {
                                                    JSONObject seasonObj = seasons.getJSONObject(v);

                                                    String season_group = seasonObj.getString("season_group");
                                                    itemModels.add(new Item_Model(season_group));

                                                    JSONArray child_Season = seasonObj.getJSONArray("season_child");
                                                    for (int l = 0; l < child_Season.length(); l++) {
                                                        JSONObject seas_child = child_Season.getJSONObject(l);

                                                        String filename = seas_child.getString("file");
                                                        String fileadr = seas_child.getString("adr");

                                                        season_modals.add(new Season_Modal(filename, fileadr));
                                                    }
                                                    itemModels.get(v).setMySeasons(season_modals);
                                                }
                                                middle_cHilds.get(k).setMyItems(itemModels);
                                            }
                                            else {
                                                String adr = url.getString("adr");
                                                itemModels.add(new Item_Model(fileTitle, adr));
                                            }
                                        }
                                        if (ch)
                                        {
                                            childModels.get(j).setMiddle_cHildList(middle_cHilds);
                                        }
                                        else {
                                            childModels.get(j).setMyItems(itemModels);
                                        }
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

/*
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
*/


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
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

    public void getSeasonFragment() {
        Fragment newFragment = new SeasonFilms();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragmentContainer, newFragment, "NewFragmentTag");
        transaction.addToBackStack(null);

        transaction.commit();
    }
}
