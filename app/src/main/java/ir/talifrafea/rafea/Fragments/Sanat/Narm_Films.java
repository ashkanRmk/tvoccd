package ir.talifrafea.rafea.Fragments.Sanat;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import java.util.LinkedList;
import java.util.List;

import ir.talifrafea.rafea.MainActivity;
import ir.talifrafea.rafea.Misc.Adapter_Films;
import ir.talifrafea.rafea.Models.Item_Model;
import ir.talifrafea.rafea.R;
public class Narm_Films extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Narm_Films() {
        // Required empty public constructor
    }
    public static Narm_Films newInstance(String param1, String param2) {
        Narm_Films fragment = new Narm_Films();
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
        return inflater.inflate(R.layout.fragment_narm__films, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("فیلم‌های رشته شبکه و نرم‌افزار رایانه");

        final RecyclerView recList = (RecyclerView) view.findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new GridLayoutManager(view.getContext(), 2);

        recList.setLayoutManager(llm);

        recList.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recList.setItemAnimator(new DefaultItemAnimator());

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final int position = recList.getChildLayoutPosition(view);
                final MainActivity activity = (MainActivity) getActivity();

                String name = activity.BarqParents.get(3).getMyChilds().get(6).getMyItems().get(position).getTitle();
                final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(view.getContext());

                dialogBuilder
                        .withTitle("دریافت فیلم آموزشی")
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
                                String url = activity.BarqParents.get(3).getMyChilds().get(6).getMyItems().get(position).getUrl();

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

        List<String> list = getListData();
        Adapter_Films adapter = new Adapter_Films(R.layout.item_films, list, onClickListener);
        recList.setAdapter(adapter);


    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


    private List<String> getListData() {
        List<String> list = new LinkedList<>();
        final MainActivity activity = (MainActivity) getActivity();
        List<Item_Model> item_model = activity.BarqParents.get(3).getMyChilds().get(6).getMyItems();

        for (Item_Model itemModel : item_model) {
            list.add(itemModel.getTitle());
        }

        return list;
    }


}
