package com.mbgmonserrath.checkmeaaa;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Modelo.MAsignatura;
import Modelo.MCarreras;
import Volley.API;
import Volley.VolleySingleton;
import adapter.AdapterAsignatura;
import adapter.AdapterCarreras;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link frg_asignaturas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frg_asignaturas extends Fragment {

    private EditText txtFiltro;
    private AdapterAsignatura adapter;
    private ArrayList<MAsignatura> lista;
    private Bundle paquete;
    private RecyclerView rec;
    private MAsignatura objAsig;
    private ImageView btnActualizar;
    private MCarreras objCar;
    private FloatingActionButton btnAdd;
    private NavController navegador;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtFiltro=view.findViewById(R.id.frg_asignatura_txtbuscar);
        rec=view.findViewById(R.id.frg_asignaturas_recycler_view);
        btnActualizar=view.findViewById(R.id.asignatura_btnactualizar);
        paquete = new Bundle();//Creacion del Bundle
        btnAdd=view.findViewById(R.id.add_asignatura);
        navegador= Navigation.findNavController(view);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paquete=getArguments();
                if (paquete!=null){
                    objCar= (MCarreras) paquete.getSerializable("objeto");

                }
                paquete.putSerializable("objeto",objCar);
                navegador.navigate(R.id.action_frg_asignaturas_to_agregarAsig,paquete);
            }
        });



        paquete=getArguments();
        if (paquete!=null){
            objCar= (MCarreras) paquete.getSerializable("objeto");

        }

        lista=llenadoDesdeBD();

        txtFiltro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                buscador(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lista=llenadoDesdeBD();
            }
        });


    }

    private ArrayList<MAsignatura> llenadoDesdeBD() {
        ArrayList<MAsignatura> lista=new ArrayList<MAsignatura>();

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
        StringRequest solicitud= new StringRequest(Request.Method.POST, API.READASIG,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();//apaga el cuadro de dialogo
                        try {
                            //LEER AQUI EL CONTENIDO DE LA VARIABLE response

                            JSONObject contenido = new JSONObject(response);
                            JSONArray array=contenido.getJSONArray("contenido");
                            MAsignatura obj=new MAsignatura();
                            for (int i = 0; i < array.length(); i++) {
                                obj=new MAsignatura();
                                JSONObject pos=new JSONObject(array.getString(i));


                                obj.setIdAsignatura(pos.getInt("idAsignatura"));
                                obj.setNombreCorto(pos.getString("nombreCorto"));
                                obj.setNombreLargo(pos.getString("nombreLargo"));
                                obj.setClave(pos.getString("clave"));
                                obj.setIdCarrera(pos.getInt("idCarrera"));
                                Log.e("LLENADO DEL OBEJTO",obj.toString());

                                lista.add(obj);
                            }

                            rec.setHasFixedSize(true);
                            rec.setLayoutManager(new LinearLayoutManager(getContext()));
                            adapter=new AdapterAsignatura(lista);
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
                param.put("idCarrera",objCar.getIdCarrera()+"");
                return param;
            }
        };
        //ENVIA LA SOLICITUD
        colaDeSolicitudes.add(solicitud);


        return lista;
    }
    private void buscador(String s) {
        ArrayList<MAsignatura> lista2 = new ArrayList<>();

        if (s != null && !s.isEmpty()) {
            // Convertimos 's' a minúsculas para una búsqueda más flexible
            String searchTerm = s.toLowerCase();

            for (MAsignatura gpo : lista) {
                // Verificamos que los valores no sean nulos antes de hacer contains()
                if (gpo.getClave().toLowerCase().contains(searchTerm) ||
                        gpo.getNombreCorto().toLowerCase().contains(searchTerm)) {

                    lista2.add(gpo);
                }
            }
        }

        // Enviar la lista filtrada al adaptador
        adapter.filtro(lista2);
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public frg_asignaturas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frg_asignaturas.
     */
    // TODO: Rename and change types and number of parameters
    public static frg_asignaturas newInstance(String param1, String param2) {
        frg_asignaturas fragment = new frg_asignaturas();
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
        return inflater.inflate(R.layout.fragment_frg_asignaturas, container, false);
    }
}