package com.mbgmonserrath.checkmeaaa;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Modelo.MDocente;
import Modelo.MEstudiante;
import Volley.API;
import Volley.VolleySingleton;
import adapter.AdapterDocente;
import adapter.AdapterEstudiante;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link frg_estudiantes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frg_estudiantes extends Fragment {
    private RecyclerView rec;
    private EditText txtFiltro;
    private AdapterEstudiante adapter;
    private ArrayList<MEstudiante> lista;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtFiltro=view.findViewById(R.id.frg_estudiante_txtbuscar);
        rec=view.findViewById(R.id.frg_estudiante_recycler_view);
        lista = llenadoDesdeBD();
    }

    private ArrayList<MEstudiante> llenadoDesdeBD() {
        ArrayList<MEstudiante> lista=new ArrayList<MEstudiante>();

        //Crea un AlertDialog
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
        StringRequest solicitud= new StringRequest(Request.Method.POST, API.READESTU,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();//apaga el cuadro de dialogo
                        try {
                            //LEER AQUI EL CONTENIDO DE LA VARIABLE response

                            JSONObject contenido = new JSONObject(response);
                            JSONArray array=contenido.getJSONArray("contenido");
                            MEstudiante obj=new MEstudiante();
                            for (int i = 0; i < array.length(); i++) {
                                obj=new MEstudiante();
                                JSONObject pos=new JSONObject(array.getString(i));


                                obj.setIdEstudiante(pos.getInt("idEstudiante"));
                                obj.setMatricula(pos.getString("matricula"));
                                obj.setNombre(pos.getString("nombre"));
                                obj.setApp(pos.getString("app"));
                                obj.setApm(pos.getString("apm"));
                                obj.setCorreo(pos.getString("correo"));
                                obj.setEdo(pos.getString("edo"));
                                obj.setMuni(pos.getString("muni"));
                                obj.setCol(pos.getString("col"));
                                obj.setGen(pos.getString("gen"));
                                obj.setContrasenia(pos.getString("contrasenia"));
                               // obj.setIdCarrera(pos.getInt("idCarrera"));

                                lista.add(obj);
                            }

                            rec.setHasFixedSize(true);
                            rec.setLayoutManager(new LinearLayoutManager(getContext()));
                            adapter=new AdapterEstudiante(lista);
                            rec.setAdapter(adapter);


                        }catch (Exception ex){
                            //DETECTA ERRORES EN LA LECTURA DEL ARCHIVO JSON

                            msg.setTitle("Error");
                            msg.setMessage("La información no se pudo leer");
                            msg.setPositiveButton("Aceptar",null);
                            AlertDialog dialog=msg.create();
                            msg.show();
                            Log.e("Error",ex.toString());

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
            protected Map<String, String> getParams() {
                Map<String, String> param = new HashMap<String, String>();
                //PASA PARAMETROS A LA SOLICITUD

                param.put("idCarrera","12");



                return param;
            }
        };
        //ENVIA LA SOLICITUD
        colaDeSolicitudes.add(solicitud);


        return lista;
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public frg_estudiantes() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frg_estudiantes.
     */
    // TODO: Rename and change types and number of parameters
    public static frg_estudiantes newInstance(String param1, String param2) {
        frg_estudiantes fragment = new frg_estudiantes();
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
        return inflater.inflate(R.layout.fragment_frg_estudiantes, container, false);
    }
}