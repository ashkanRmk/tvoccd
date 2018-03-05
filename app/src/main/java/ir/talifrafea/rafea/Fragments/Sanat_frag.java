package ir.talifrafea.rafea.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import ir.talifrafea.rafea.Fragments.Honar.Honar_Honar;
import ir.talifrafea.rafea.Fragments.Sanat.Barq_Sanat;
import ir.talifrafea.rafea.Fragments.Sanat.Mashin_Sanat;
import ir.talifrafea.rafea.Fragments.Sanat.Mavad_Sanat;
import ir.talifrafea.rafea.Fragments.Sanat.Mekanik_Sanat;
import ir.talifrafea.rafea.Fragments.Sanat.Memari_Sanat;
import ir.talifrafea.rafea.R;

public class Sanat_frag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Sanat_frag() {
        // Required empty public constructor
    }

    public static Sanat_frag newInstance(String param1, String param2) {
        Sanat_frag fragment = new Sanat_frag();
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
        return inflater.inflate(R.layout.fragment_sanat_frag, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("       زمینه صنعت                       ");


        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(view.getContext())
                .add("گروه مکانیک", Mekanik_Sanat.class)
                .add("گروه معماری و ساختمان", Memari_Sanat.class)
                .add("گروه مواد و فرآوری", Mavad_Sanat.class)
                .add("گروه برق و رایانه", Barq_Sanat.class)
                .add("گروه تعمیر و نگهداری ماشین‌آلات", Mashin_Sanat.class)
                .create());

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = (SmartTabLayout) view.findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewPager);

    }
}
