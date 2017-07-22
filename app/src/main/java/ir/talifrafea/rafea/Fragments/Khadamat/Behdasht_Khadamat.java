package ir.talifrafea.rafea.Fragments.Khadamat;

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

public class Behdasht_Khadamat extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Behdasht_Khadamat() {
        // Required empty public constructor
    }
    public static Behdasht_Khadamat newInstance(String param1, String param2) {
        Behdasht_Khadamat fragment = new Behdasht_Khadamat();
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
        return inflater.inflate(R.layout.fragment_behdasht__khadamat, container, false);
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
        listDataHeader.add("رشته تربیت کودک");
        listDataHeader.add("رشته تربیت‌بدنی");

        // Adding child data
        List<String> tarbiat = new ArrayList<String>();
        tarbiat.add("جدول سه‌ساله دروس");
        tarbiat.add("سطوح صلاحیت حرفه‌ای");
        tarbiat.add("کتاب‌های درسی");
        tarbiat.add("ارزشیابی");

        List<String> badani = new ArrayList<String>();
        badani.add("جدول سه‌ساله دروس");
        badani.add("سطوح صلاحیت حرفه‌ای");
        badani.add("کتاب‌های درسی");
        badani.add("ارزشیابی");

        listDataChild.put(listDataHeader.get(0), tarbiat); // Header, Child data
        listDataChild.put(listDataHeader.get(1), badani);
    }


}
