package ir.talifrafea.rafea.Fragments.Sanat;

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
public class Mavad_Sanat extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Mavad_Sanat() {
        // Required empty public constructor
    }
    public static Mavad_Sanat newInstance(String param1, String param2) {
        Mavad_Sanat fragment = new Mavad_Sanat();
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
        return inflater.inflate(R.layout.fragment_mavad__sanat, container, false);
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
        listDataHeader.add("رشته صنایع شیمیایی");
        listDataHeader.add("رشته معدن");
        listDataHeader.add("رشته سرامیک");
        listDataHeader.add("رشته متالوژی");
        listDataHeader.add("رشته صنایع نساجی");

        // Adding child data
        List<String> sashimi = new ArrayList<String>();
        sashimi.add("جدول سه‌ساله دروس");
        sashimi.add("سطوح صلاحیت حرفه‌ای");
        sashimi.add("کتاب‌های درسی");
        sashimi.add("ارزشیابی");

        List<String> madan = new ArrayList<String>();
        madan.add("جدول سه‌ساله دروس");
        madan.add("سطوح صلاحیت حرفه‌ای");
        madan.add("کتاب‌های درسی");
        madan.add("ارزشیابی");

        List<String> seramik = new ArrayList<String>();
        seramik.add("جدول سه‌ساله دروس");
        seramik.add("سطوح صلاحیت حرفه‌ای");
        seramik.add("کتاب‌های درسی");
        seramik.add("ارزشیابی");

        List<String> meta = new ArrayList<String>();
        meta.add("جدول سه‌ساله دروس");
        meta.add("سطوح صلاحیت حرفه‌ای");
        meta.add("کتاب‌های درسی");
        meta.add("ارزشیابی");

        List<String> nasj = new ArrayList<String>();
        nasj.add("جدول سه‌ساله دروس");
        nasj.add("کتاب‌های درسی");

        listDataChild.put(listDataHeader.get(0), sashimi); // Header, Child data
        listDataChild.put(listDataHeader.get(1), madan);
        listDataChild.put(listDataHeader.get(2), seramik);
        listDataChild.put(listDataHeader.get(3), meta);
        listDataChild.put(listDataHeader.get(4), nasj);
    }

}
