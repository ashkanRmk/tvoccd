package ir.talifrafea.rafea.Fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import java.util.LinkedList;
import java.util.List;

import ir.talifrafea.rafea.MainActivity;
import ir.talifrafea.rafea.Misc.List_Adapter;
import ir.talifrafea.rafea.Models.Item_Model;
import ir.talifrafea.rafea.R;

public class Doros_frag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Doros_frag() {
        // Required empty public constructor
    }

    public static Doros_frag newInstance(String param1, String param2) {
        Doros_frag fragment = new Doros_frag();
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
        return inflater.inflate(R.layout.fragment_doros_frag, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("دروس مشترک فنی‌حرفه‌ای                ");

        final RecyclerView recList = (RecyclerView) view.findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(view.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        final List<String> list = getListData();

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final int position = recList.getChildLayoutPosition(view);
                String name = list.get(position);
                final MainActivity activity = (MainActivity) getActivity();


                final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(view.getContext());

                dialogBuilder
                        .withTitle("دریافت فایل")
                        .withMessage("نام فایل: " + name)
                        .withButton1Text("شروع دانلود")
                        .withButton2Text("لغو")
                        .withMessageColor("#FFFFFFFF")
                        .withDialogColor("#FF459969")
                        .isCancelableOnTouchOutside(true)
                        .setButton1Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(v.getContext(), "دانلود فایل آغاز شد!", Toast.LENGTH_SHORT).show();
                                String url = activity.DorosParents.get(position).getUrl();

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

            }
        };

        List_Adapter adapter = new List_Adapter(R.layout.item_card, list, onClickListener);
        recList.setAdapter(adapter);
    }

    private List<String> getListData() {
        List<String> list = new LinkedList<>();
        final MainActivity activity = (MainActivity) getActivity();

        List<Item_Model> itemModels = activity.DorosParents;
        for (Item_Model itemModel : itemModels) {
            list.add(itemModel.getTitle());
        }

        return list;
    }
}
