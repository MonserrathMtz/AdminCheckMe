package com.mbgmonserrath.checkmeaaa;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link frg_home_screen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frg_home_screen extends Fragment {
    private NavController navegador;
    private ImageView logo;
    private LinearLayout img1, img2;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public frg_home_screen() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frg_home_screen.
     */
    // TODO: Rename and change types and number of parameters
    public static frg_home_screen newInstance(String param1, String param2) {
        frg_home_screen fragment = new frg_home_screen();
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
        return inflater.inflate(R.layout.fragment_frg_home_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navegador = Navigation.findNavController(view);
        logo = view.findViewById(R.id.logoP);
        img1 = view.findViewById(R.id.img1);
        img2 = view.findViewById(R.id.img2);
        AlphaAnimation fadeIn = new AlphaAnimation(0,1);
        fadeIn.setDuration(2000);
        logo.startAnimation(fadeIn);

        TranslateAnimation img1 = new TranslateAnimation(500,0,0,0);
        img1.setDuration(2000);
        img1.setFillAfter(true);
        this.img1.startAnimation(img1);

        TranslateAnimation img2 = new TranslateAnimation(-500,0,0,0);
        img2.setDuration(2000);
        img2.setFillAfter(true);
        this.img2.startAnimation(img2);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                navegador.navigate(R.id.action_frg_home_screen_to_frg_login);
            }
        },2000);
    }
}