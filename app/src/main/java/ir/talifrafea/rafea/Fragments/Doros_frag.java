package ir.talifrafea.rafea.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

import ir.talifrafea.rafea.Misc.List_Adapter;
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

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("دروس مشترک فنی‌حرفه‌ای   ");

        final RecyclerView recList = (RecyclerView) view.findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(view.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = recList.getChildLayoutPosition(view);

                Toast.makeText(view.getContext(), "you click on "+ (position + 1) + " item!", Toast.LENGTH_SHORT).show();
            }
        };

        List<String> list = getListData();
        List_Adapter adapter = new List_Adapter(R.layout.item_card, list, onClickListener);
        recList.setAdapter(adapter);


    }

    private List<String> getListData() {
        List<String> list = new LinkedList<>();
        list.add("فیزیک 1");
        list.add("فیزیک 2");
        list.add("فیزیک 3");
        list.add("فیزیک 4");
        list.add("فارسی و نگارش 1");
        list.add("تفکر و سواد رسانه‌ای");
        list.add("زیست شناسی");
        list.add("راهنمای هنرآموز زیست‌شناسی");
        list.add("الزامات محیط کار");
        list.add("ریاضی 1");
        list.add("عربی، زبان و قرآن 1");
        list.add("آمادگی دفاعی");
        list.add("جغرافیای ایران");
        list.add("شیمی");
        list.add("مبانی و کاربرد رایانه");
        return list;
    }
}
