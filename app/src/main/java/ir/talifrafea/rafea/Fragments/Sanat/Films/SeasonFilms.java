package ir.talifrafea.rafea.Fragments.Sanat.Films;

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
import ir.talifrafea.rafea.Models.Film_models.Film_Seasons;
import ir.talifrafea.rafea.Models.Film_models.Film_SoftName;
import ir.talifrafea.rafea.Models.Item_Model;
import ir.talifrafea.rafea.R;

public class SeasonFilms extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SeasonFilms() {
        // Required empty public constructor
    }
    public static SeasonFilms newInstance(String param1, String param2) {
        SeasonFilms fragment = new SeasonFilms();
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
        return inflater.inflate(R.layout.fragment_season_films, container, false);
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

        listAdapter = new ExpandableListAdapter(view.getContext(), listDataHeader, listDataChild,"#000000", "#FFFFFF", "#d1692d");

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
                                String url = activity.filmsList.get(activity.mainPos).getSeasons().get(groupPosition).getSoftNames().get(childPosition).getUrl();

                                if (!url.startsWith("http://") && !url.startsWith("https://"))
                                    url = "http://" + url;

                                try {
                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                    startActivity(browserIntent);
                                } catch (Exception e) {
                                    Toast.makeText(view.getContext(), "No application can handle this request."
                                            + " Please install a web browser",  Toast.LENGTH_LONG).show();
                                    e.printStackTrace();
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

        List<Film_Seasons> main = activity.filmsList.get(activity.mainPos).getSeasons();

        // Adding header data
        for (int i = 0; i < main.size(); i++)
        {
            listDataHeader.add(main.get(i).getSeason_name());
            List<String> list = new ArrayList<>();
            // Adding child data
            List<Film_SoftName> child = main.get(i).getSoftNames();
            for (int j = 0; j < child.size(); j++)
            {
                list.add(child.get(j).getTitle());
            }
            listDataChild.put(listDataHeader.get(i), list);
        }
    }

}
