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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ir.talifrafea.rafea.Misc.ExpandableListAdapter;
import ir.talifrafea.rafea.R;
public class Mekanik_Sanat extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Mekanik_Sanat() {
        // Required empty public constructor
    }
    public static Mekanik_Sanat newInstance(String param1, String param2) {
        Mekanik_Sanat fragment = new Mekanik_Sanat();
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
        return inflater.inflate(R.layout.fragment_mekanik__sanat, container, false);
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

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        v.getContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });
    }


    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding header data
        listDataHeader.add("رشته مکاترونیک");
        listDataHeader.add("رشته صنایع فلزی");
        listDataHeader.add("رشته تاسیسات مکانیکی");
        listDataHeader.add("رشته مکانیک خودرو");
        listDataHeader.add("رشته ماشین‌ابزار");
        listDataHeader.add("رشته صنایع چوب و مبلمان");
        listDataHeader.add("رشته چاپ");

        // Adding child data
        List<String> mekat = new ArrayList<String>();
        mekat.add("جدول سه‌ساله دروس");
        mekat.add("سطوح صلاحیت حرفه‌ای");
        mekat.add("کتاب‌های درسی");
        mekat.add("ارزشیابی");

        List<String> felez = new ArrayList<String>();
        felez.add("جدول سه‌ساله دروس");
        felez.add("سطوح صلاحیت حرفه‌ای");
        felez.add("کتاب‌های درسی");
        felez.add("ارزشیابی");

        List<String> tas = new ArrayList<String>();
        tas.add("جدول سه‌ساله دروس");
        tas.add("سطوح صلاحیت حرفه‌ای");
        tas.add("کتاب‌های درسی");
        tas.add("ارزشیابی");

        List<String> khodro = new ArrayList<String>();
        khodro.add("جدول سه‌ساله دروس");
        khodro.add("سطوح صلاحیت حرفه‌ای");
        khodro.add("کتاب‌های درسی");
        khodro.add("ارزشیابی");

        List<String> abzar = new ArrayList<String>();
        abzar.add("جدول سه‌ساله دروس");
        abzar.add("سطوح صلاحیت حرفه‌ای");
        abzar.add("کتاب‌های درسی");

        List<String> choob = new ArrayList<String>();
        choob.add("سطوح صلاحیت حرفه‌ای");
        choob.add("کتاب‌های درسی");
        choob.add("ارزشیابی");

        List<String> chap = new ArrayList<String>();
        chap.add("جدول سه‌ساله دروس");
        chap.add("کتاب‌های درسی");

        listDataChild.put(listDataHeader.get(0), mekat); // Header, Child data
        listDataChild.put(listDataHeader.get(1), felez);
        listDataChild.put(listDataHeader.get(2), tas);
        listDataChild.put(listDataHeader.get(3), khodro);
        listDataChild.put(listDataHeader.get(4), abzar);
        listDataChild.put(listDataHeader.get(5), choob);
        listDataChild.put(listDataHeader.get(6), chap);
    }

}
