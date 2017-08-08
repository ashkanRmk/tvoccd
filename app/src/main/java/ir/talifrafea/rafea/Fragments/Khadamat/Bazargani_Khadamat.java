package ir.talifrafea.rafea.Fragments.Khadamat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ir.talifrafea.rafea.MainActivity;
import ir.talifrafea.rafea.Misc.ExpandableListAdapter;
import ir.talifrafea.rafea.Models.Child_Model;
import ir.talifrafea.rafea.Models.Item_Model;
import ir.talifrafea.rafea.Models.Parent_Model;
import ir.talifrafea.rafea.R;

public class Bazargani_Khadamat extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Bazargani_Khadamat() {
        // Required empty public constructor
    }

    public static Bazargani_Khadamat newInstance(String param1, String param2) {
        Bazargani_Khadamat fragment = new Bazargani_Khadamat();
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
        return inflater.inflate(R.layout.fragment_bazargani__khadamat, container, false);
    }


    //Public Var for Expandable List View
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
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
                                        final int groupPosition, final int childPosition, long id) {
                final MainActivity activity = (MainActivity) getActivity();

                final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(v.getContext());

                dialogBuilder
                        .withTitle("دریافت فایل")
                        .withMessage("نام فایل: " + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition) + "\n\n" + listDataHeader.get(groupPosition))
                        .withButton1Text("شروع دانلود")
                        .withButton2Text("لغو")
                        .withMessageColor("#FFFFFFFF")
                        .withDialogColor("#FF459969")
                        .isCancelableOnTouchOutside(true)
                        .setButton1Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(v.getContext(), "دانلود فایل آغاز شد!", Toast.LENGTH_SHORT).show();
                                List<Item_Model> item_models = activity.BazarganiParent.get(groupPosition).getMyChilds().get(childPosition).getMyItems();
                                for (Item_Model item_model : item_models) {
                                    String url = item_model.getUrl();

                                    if (!url.startsWith("http://") && !url.startsWith("https://"))
                                        url = "http://" + url;

                                    try {
                                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                        startActivity(browserIntent);
                                    } catch (Exception e) {
                                        Toast.makeText(view.getContext(), "No application can handle this request."
                                                + " Please install a web browser", Toast.LENGTH_LONG).show();
                                        e.printStackTrace();
                                    }

                                }
                                dialogBuilder.dismiss();
                            }
                        })
                        .setButton2Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(v.getContext(), "عملیات لغو شد!", Toast.LENGTH_SHORT).show();
                                dialogBuilder.dismiss();
                            }
                        })
                        .show();
                return false;
            }
        });
    }


    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        MainActivity activity = (MainActivity) getActivity();

        List<Parent_Model> main = activity.BazarganiParent;

        // Adding header data
        for (int i = 0; i < main.size(); i++) {
            listDataHeader.add(main.get(i).getParentTitle());
            List<String> list = new ArrayList<>();
            // Adding child data
            List<Child_Model> child = main.get(i).getMyChilds();
            for (int j = 0; j < child.size(); j++) {
                list.add(child.get(j).getChildTitle());
            }
            listDataChild.put(listDataHeader.get(i), list);
        }
    }
}
