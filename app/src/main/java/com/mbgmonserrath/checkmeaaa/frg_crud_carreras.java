package com.mbgmonserrath.checkmeaaa;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import Volley.API;
import Volley.VolleySingleton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link frg_crud_carreras#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frg_crud_carreras extends Fragment {
    private EditText txtNomCorto,txtNomLargo,txtClave;
    private CardView btnAgregar;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtNomCorto=view.findViewById(R.id.crudcarrera_nombrecorto);
        txtNomLargo=view.findViewById(R.id.crudcarrera_nombrelargo);
        txtClave=view.findViewById(R.id.crudcarrera_clave);
        btnAgregar=view.findViewById(R.id.crud_carrera_agregar);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicAgregar();
            }
        });
    }

    private void clicAgregar() {

        AlertDialog.Builder msg = new AlertDialog.Builder(this.getContext());

        // Crear un ProgressBar
        ProgressBar progressBar = new ProgressBar(this.getContext());
        progressBar.setIndeterminate(true); // Estilo de carga indeterminada

        // Crear el AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("Por favor, espera");
        builder.setMessage("Conectandose con el servidor...");
        builder.setView(progressBar);
        builder.setCancelable(false); // Evitar que se pueda cancelar
        AlertDialog dialog = builder.create();
        dialog.show();

        RequestQueue colaDeSolicitudes= VolleySingleton.getInstance(this.getContext()).getRequestQueue();
        StringRequest solicitud= new StringRequest(Request.Method.POST, API.CREATECARRERA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();//apaga el cuadro de dialogo
                        try {
                            //LEER AQUI EL CONTENIDO DE LA VARIABLE response
                            msg.setTitle("Guardado");
                            msg.setMessage("Haz creado una carrera");
                            msg.setPositiveButton("Aceptar",null);
                            AlertDialog dialog=msg.create();
                            msg.show();



                        }catch (Exception ex){
                            //DETECTA ERRORES EN LA LECTURA DEL ARCHIVO JSON

                            msg.setTitle("Error");
                            msg.setMessage("La información no se pudo leer");
                            msg.setPositiveButton("Aceptar",null);
                            AlertDialog dialog=msg.create();
                            msg.show();

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                // DETECTA ERRORES EN LA COMUNICACIÓN
                msg.setTitle("Error");
                msg.setMessage("No se pudo conectar con el servidor");
                msg.setPositiveButton("Aceptar",null);
                AlertDialog dialog=msg.create();
                msg.show();
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> param=new HashMap<String,String>();
                //PASA PARAMETROS A LA SOLICITUD


                param.put("nombreCorto",txtNomCorto.getText().toString());
                param.put("nombreLargo",txtNomLargo.getText().toString());
                param.put("clave",txtClave.getText()+"");


                return param;
            }
        };
        //ENVIA LA SOLICITUD
        colaDeSolicitudes.add(solicitud);


    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public frg_crud_carreras() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frg_crud_carreras.
     */
    // TODO: Rename and change types and number of parameters
    public static frg_crud_carreras newInstance(String param1, String param2) {
        frg_crud_carreras fragment = new frg_crud_carreras();
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
        return inflater.inflate(R.layout.fragment_frg_crud_carreras, container, false);
    }
}