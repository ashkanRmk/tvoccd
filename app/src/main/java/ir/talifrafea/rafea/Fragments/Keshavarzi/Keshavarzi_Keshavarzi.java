package ir.talifrafea.rafea.Fragments.Keshavarzi;

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
public class Keshavarzi_Keshavarzi extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Keshavarzi_Keshavarzi() {
        // Required empty public constructor
    }
    public static Keshavarzi_Keshavarzi newInstance(String param1, String param2) {
        Keshavarzi_Keshavarzi fragment = new Keshavarzi_Keshavarzi();
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
        return inflater.inflate(R.layout.fragment_keshavarzi__keshavarzi, container, false);
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
        listDataHeader.add("رشته امور زراعی");
        listDataHeader.add("رشته امور باغی");
        listDataHeader.add("رشته امور دامی");
        listDataHeader.add("رشته ماشین‌های کشاورزی");
        listDataHeader.add("رشته صنایع غذایی");

        // Adding child data
        List<String> zaraee = new ArrayList<String>();
        zaraee.add("جدول سه‌ساله دروس");
        zaraee.add("سطوح صلاحیت حرفه‌ای");
        zaraee.add("کتاب‌های درسی");
        zaraee.add("ارزشیابی");

        List<String> baqi = new ArrayList<String>();
        baqi.add("جدول سه‌ساله دروس");
        baqi.add("سطوح صلاحیت حرفه‌ای");
        baqi.add("کتاب‌های درسی");
        baqi.add("ارزشیابی");

        List<String> dami = new ArrayList<String>();
        dami.add("جدول سه‌ساله دروس");
        dami.add("سطوح صلاحیت حرفه‌ای");
        dami.add("کتاب‌های درسی");
        dami.add("ارزشیابی");

        List<String> mk = new ArrayList<String>();
        mk.add("جدول سه‌ساله دروس");
        mk.add("سطوح صلاحیت حرفه‌ای");
        mk.add("کتاب‌های درسی");
        mk.add("ارزشیابی");

        List<String> sanaee = new ArrayList<String>();
        sanaee.add("جدول سه‌ساله دروس");
        sanaee.add("سطوح صلاحیت حرفه‌ای");
        sanaee.add("کتاب‌های درسی");

        listDataChild.put(listDataHeader.get(0), zaraee); // Header, Child data
        listDataChild.put(listDataHeader.get(1), baqi);
        listDataChild.put(listDataHeader.get(2), dami);
        listDataChild.put(listDataHeader.get(3), mk);
        listDataChild.put(listDataHeader.get(4), sanaee);
    }

}

