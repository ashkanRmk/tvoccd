package ir.talifrafea.rafea.Fragments.Sanat;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ir.talifrafea.rafea.MainActivity;
import ir.talifrafea.rafea.Misc.ExpandableListAdapter;
import ir.talifrafea.rafea.R;

import static android.content.ContentValues.TAG;

public class Barq_Sanat extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Barq_Sanat() {
        // Required empty public constructor
    }
    public static Barq_Sanat newInstance(String param1, String param2) {
        Barq_Sanat fragment = new Barq_Sanat();
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
        return inflater.inflate(R.layout.fragment_barq__sanat, container, false);
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

                //Some works for NarmAfzar Films
                MainActivity activity = (MainActivity) getActivity();

                if (groupPosition == 3 && childPosition == 4)
                {
                    activity.getFilmFragment();
                    return true;
                }

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
        listDataHeader.add("رشته الکتروتکنیک");
        listDataHeader.add("رشته الکترونیک");
        listDataHeader.add("رشته الکترونیک و مخابرات‌دریایی");
        listDataHeader.add("رشته شبکه و نرم‌افزار رایانه");

        // Adding child data
        List<String> elec = new ArrayList<String>();
        elec.add("جدول سه‌ساله دروس");
        elec.add("سطوح صلاحیت حرفه‌ای");
        elec.add("کتاب‌های درسی");
        elec.add("ارزشیابی");

        List<String> electronic = new ArrayList<String>();
        electronic.add("جدول سه‌ساله دروس");
        electronic.add("سطوح صلاحیت حرفه‌ای");
        electronic.add("کتاب‌های درسی");
        electronic.add("ارزشیابی");

        List<String> mokh = new ArrayList<String>();
        mokh.add("جدول سه‌ساله دروس");
        mokh.add("کتاب‌های درسی");

        List<String> narm = new ArrayList<String>();
        narm.add("جدول سه‌ساله دروس");
        narm.add("سطوح صلاحیت حرفه‌ای");
        narm.add("کتاب‌های درسی");
        narm.add("ارزشیابی");
        narm.add("فیلم‌های رشته");


        listDataChild.put(listDataHeader.get(0), elec); // Header, Child data
        listDataChild.put(listDataHeader.get(1), electronic);
        listDataChild.put(listDataHeader.get(2), mokh);
        listDataChild.put(listDataHeader.get(3), narm);
    }

}
