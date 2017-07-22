package ir.talifrafea.rafea.Fragments.Honar;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ir.talifrafea.rafea.Misc.ExpandableListAdapter;
import ir.talifrafea.rafea.R;
public class Honar_Honar extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Honar_Honar() {
        // Required empty public constructor
    }
    public static Honar_Honar newInstance(String param1, String param2) {
        Honar_Honar fragment = new Honar_Honar();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_honar__honar, container, false);
    }

    //Public Var for Expandable List View
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // get the listview
        expListView = (ExpandableListView) view.findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(view.getContext(), listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
    }


    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding header data
        listDataHeader.add("رشته فوتو - گرافیک");
        listDataHeader.add("رشته طراحی و دوخت");
        listDataHeader.add("رشته معماری داخلی");
        listDataHeader.add("رشته صنایع‌دستی (فرش)");
        listDataHeader.add("رشته پویانمایی (انیمیشن)");
        listDataHeader.add("رشته تولید برنامه تلویزیونی");
        listDataHeader.add("رشته گرافیک");
        listDataHeader.add("رشته نقاشی");
        listDataHeader.add("رشته موسیقی (جهانی)");
        listDataHeader.add("رشته موسیقی (ایرانی)");
        listDataHeader.add("رشته نقشه‌کشی معماری");

        // Adding child data
        List<String> graph = new ArrayList<String>();
        graph.add("جدول سه‌ساله دروس");
        graph.add("سطوح صلاحیت حرفه‌ای");
        graph.add("کتاب‌های درسی");

        List<String> tarahi = new ArrayList<String>();
        tarahi.add("جدول سه‌ساله دروس");
        tarahi.add("سطوح صلاحیت حرفه‌ای");
        tarahi.add("کتاب‌های درسی");
        tarahi.add("ارزشیابی");

        List<String> memari = new ArrayList<String>();
        memari.add("جدول سه‌ساله دروس");
        memari.add("سطوح صلاحیت حرفه‌ای");
        memari.add("کتاب‌های درسی");
        memari.add("ارزشیابی");

        List<String> dasti = new ArrayList<String>();
        dasti.add("جدول سه‌ساله دروس");
        dasti.add("سطوح صلاحیت حرفه‌ای");
        dasti.add("کتاب‌های درسی");
        dasti.add("ارزشیابی");

        List<String> ani = new ArrayList<String>();
        ani.add("جدول سه‌ساله دروس");

        List<String> tele = new ArrayList<String>();
        tele.add("جدول سه‌ساله دروس");

        List<String> graphic = new ArrayList<String>();
        graphic.add("کتاب‌های درسی");
        graphic.add("ارزشیابی");

        List<String> nagh = new ArrayList<String>();
        nagh.add("کتاب‌های درسی");

        List<String> mj = new ArrayList<String>();
        mj.add("کتاب‌های درسی");

        List<String> mi = new ArrayList<String>();
        mi.add("کتاب‌های درسی");

        List<String> namayesh = new ArrayList<String>();
        namayesh.add("کتاب‌های درسی");

        List<String> cinema = new ArrayList<String>();
        cinema.add("کتاب‌های درسی");

        List<String> naqshe = new ArrayList<String>();
        naqshe.add("کتاب‌های درسی");

        listDataChild.put(listDataHeader.get(0), graph); // Header, Child data
        listDataChild.put(listDataHeader.get(1), tarahi);
        listDataChild.put(listDataHeader.get(2), memari);
        listDataChild.put(listDataHeader.get(3), dasti);
        listDataChild.put(listDataHeader.get(4), ani);
        listDataChild.put(listDataHeader.get(5), tele);
        listDataChild.put(listDataHeader.get(6), graphic);
        listDataChild.put(listDataHeader.get(7), nagh);
        listDataChild.put(listDataHeader.get(8), mj);
        listDataChild.put(listDataHeader.get(9), mi);
        listDataChild.put(listDataHeader.get(10), naqshe);
    }

}
