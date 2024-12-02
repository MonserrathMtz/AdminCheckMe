package com.mbgmonserrath.checkmeaaa;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import Modelo.MDocente;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link frg_perfil#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frg_perfil extends Fragment {

    private NavController navegador;

    private Bundle paquete;
    private CardView btnBack;
    private MDocente obj;
    private TextView txtNombre, txtTitulo, txtCorreo, txtNumT, txtGenero;
    private ImageView btnMenu, btnGrupos, btnTutorados, btnAddTut, imgPerfil;
    private int genero;
    private String gen;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public frg_perfil() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frg_perfil.
     */
    // TODO: Rename and change types and number of parameters
    public static frg_perfil newInstance(String param1, String param2) {
        frg_perfil fragment = new frg_perfil();
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
        return inflater.inflate(R.layout.fragment_frg_perfil, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navegador = Navigation.findNavController(view);
        txtNombre = view.findViewById(R.id.perfil_nombre);
        txtTitulo = view.findViewById(R.id.miPerfil_titulo);
        txtCorreo = view.findViewById(R.id.miPerfil_correo);
        txtNumT = view.findViewById(R.id.miPerfil_NumTrabajador);
        txtGenero = view.findViewById(R.id.per_doc_txtgenero);
        imgPerfil = view.findViewById(R.id.perfil_doc_img);

        paquete = this.getArguments();
        if (paquete != null) {
            obj = (MDocente) paquete.getSerializable("user");
            txtNombre.setText(obj.getGrado() + " " + obj.getApp() + " " + obj.getApm() + " " + obj.getNombre());
            txtTitulo.setText("Titulo: " + obj.getGrado() + " " + obj.getTitulo());
            txtCorreo.setText("Correo: " + obj.getCorreo());
            txtNumT.setText("Numero de trabajador: " + obj.getNumTrabajador());


            genero = obj.getGenero();
            if (genero == 0) {
                imgPerfil.setImageResource(R.drawable.perfil_mtro);
                gen = "Masculino";
            } else if (genero == 1) {
                imgPerfil.setImageResource(R.drawable.perfil_mtra);
                gen = "Femenino";
            }
            txtGenero.setText("Genero:  " + gen);
        }
    }
}