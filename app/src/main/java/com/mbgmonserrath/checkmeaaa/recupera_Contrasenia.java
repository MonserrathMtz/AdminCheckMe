package com.mbgmonserrath.checkmeaaa;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

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

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import Correo.correo;
import Modelo.MDocente;
import Volley.API;
import Volley.VolleySingleton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link recupera_Contrasenia#newInstance} factory method to
 * create an instance of this fragment.
 */
public class recupera_Contrasenia extends Fragment {
    private EditText txtCorreo;
    private CardView btnEnviar;
    private AlertDialog cuadro2;
    private String newPassword;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtCorreo = view.findViewById(R.id.recupera_txtcorreo);
        btnEnviar = view.findViewById(R.id.recupera_btn_entrar);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarCorreo(v);
            }
        });
    }

    private void buscarCorreo(View contexto) {
        MDocente obj = new MDocente();

        //ArrayList<MDocente> lista=new ArrayList<MDocente>();

        //Crea un AlertDialog
        androidx.appcompat.app.AlertDialog.Builder msg = new androidx.appcompat.app.AlertDialog.Builder(this.getContext());

        // Crear un ProgressBar
        ProgressBar progressBar = new ProgressBar(this.getContext());
        progressBar.setIndeterminate(true); // Estilo de carga indeterminada

        // Crear el AlertDialog
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this.getContext());
        builder.setTitle("Por favor, espera");
        builder.setMessage("Conectandose con el servidor...");
        builder.setView(progressBar);
        builder.setCancelable(false); // Evitar que se pueda cancelar
        androidx.appcompat.app.AlertDialog dialog = builder.create();
        dialog.show();

        RequestQueue colaDeSolicitudes= VolleySingleton.getInstance(this.getContext()).getRequestQueue();
        StringRequest solicitud= new StringRequest(Request.Method.POST, API.BUSCARCORREO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();//apaga el cuadro de dialogo
                        try {
                            //LEER AQUI EL CONTENIDO DE LA VARIABLE response
                            Log.e("Response",response);
                            JSONObject contenido = new JSONObject(response);
                            JSONArray array=contenido.getJSONArray("contenido");
                            MDocente obj = new MDocente();
                            for (int i = 0; i < array.length(); i++) {
                                obj = new MDocente();
                                JSONObject pos = new JSONObject(array.getString(i));

                                obj.setCorreo(pos.getString("correo"));
                                obj.setIdDocente(pos.getInt("idDocente"));


                            }

                            if (obj.getCorreo()==null){// Esto es en caso de que el usuario no exista

                                msg.setTitle("Error");
                                msg.setMessage("El correo no esta registrado");
                                msg.setPositiveButton("Aceptar",null);
                                androidx.appcompat.app.AlertDialog dialog=msg.create();
                                msg.show();

                            }else {
                                generaContraseniaAleatoria();
                                recuperaContra();

                                actualizaEnBD();

                            }

                        }catch (Exception ex){
                            //DETECTA ERRORES EN LA LECTURA DEL ARCHIVO JSON

                            msg.setTitle("Error");
                            msg.setMessage("La información no se pudo leer");
                            msg.setPositiveButton("Aceptar",null);
                            androidx.appcompat.app.AlertDialog dialog=msg.create();
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
                androidx.appcompat.app.AlertDialog dialog=msg.create();
                msg.show();
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> param=new HashMap<String,String>();
                //PASA PARAMETROS A LA SOLICITUD
                param.put("correo",txtCorreo.getText().toString());
                return param;
            }
        };
        //ENVIA LA SOLICITUD
        colaDeSolicitudes.add(solicitud);


    }
    private void recuperaContra(){
        // Datos para el Worker
        Data inputData = new Data.Builder()
                .putString("email", txtCorreo.getText().toString())
                .putString("password",newPassword)
                .build();

        // Configura el WorkRequest
        OneTimeWorkRequest emailRequest = new OneTimeWorkRequest.Builder(correo.class)
                .setInputData(inputData)
                .build();

        // Muestra el AlertDialog con ProgressBar
        muestraCuadro(this.getContext());

        // Observa el estado del WorkRequest
        WorkManager.getInstance(this.getContext()).enqueue(emailRequest);
        WorkManager.getInstance(this.getContext()).getWorkInfoByIdLiveData(emailRequest.getId())
                .observe(this.getActivity(), workInfo -> {
                    if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                        // Cierra el dialogo cuando la tarea ha terminado
                        cuadro2.dismiss();
                        // Notifica al usuario que el correo fue enviado
                        new AlertDialog.Builder(this.getContext())
                                .setTitle("Éxito")
                                .setMessage("Correo enviado con éxito")
                                .setPositiveButton("OK", null)
                                .show();
                    } else if (workInfo.getState() == WorkInfo.State.FAILED) {
                        // Cierra el dialogo si la tarea falló
                        cuadro2.dismiss();
                        // Notifica al usuario que hubo un error
                        new AlertDialog.Builder(this.getContext())
                                .setTitle("Error")
                                .setMessage("No se pudo enviar el correo")
                                .setPositiveButton("OK", null)
                                .show();
                    }
                });

    }
    private void muestraCuadro(Context context) {
        AlertDialog.Builder cuadro = new AlertDialog.Builder(context);
        cuadro.setTitle("Por favor espera");
        cuadro.setMessage("Enviando correo...");

        ProgressBar progressBar = new ProgressBar(context);
        cuadro.setView(progressBar);
        cuadro.setCancelable(false);

        cuadro2 = cuadro.create();
        cuadro2.show();
    }
    private void actualizaEnBD() {
        androidx.appcompat.app.AlertDialog.Builder msg = new androidx.appcompat.app.AlertDialog.Builder(this.getContext());

        // Crear un ProgressBar
        ProgressBar progressBar = new ProgressBar(this.getContext());
        progressBar.setIndeterminate(true); // Estilo de carga indeterminada

        // Crear el AlertDialog
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this.getContext());
        builder.setTitle("Por favor, espera");
        builder.setMessage("Conectandose con el servidor...");
        builder.setView(progressBar);
        builder.setCancelable(false); // Evitar que se pueda cancelar
        androidx.appcompat.app.AlertDialog dialog = builder.create();
        dialog.show();

        RequestQueue colaDeSolicitudes= VolleySingleton.getInstance(this.getContext()).getRequestQueue();
        StringRequest solicitud= new StringRequest(Request.Method.POST, API.RECUPERA_CONTRA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();//apaga el cuadro de dialogo
                        try {
                            //LEER AQUI EL CONTENIDO DE LA VARIABLE response
                            msg.setTitle("Guardado");
                            msg.setMessage("La información se modifico correctamente");
                            msg.setPositiveButton("Aceptar",null);
                            androidx.appcompat.app.AlertDialog dialog=msg.create();
                            msg.show();



                        }catch (Exception ex){
                            //DETECTA ERRORES EN LA LECTURA DEL ARCHIVO JSON

                            msg.setTitle("Error");
                            msg.setMessage("La información no se pudo leer");
                            msg.setPositiveButton("Aceptar",null);
                            androidx.appcompat.app.AlertDialog dialog=msg.create();
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
                androidx.appcompat.app.AlertDialog dialog=msg.create();
                msg.show();
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> param=new HashMap<String,String>();
                //PASA PARAMETROS A LA SOLICITUD
                param.put("correo",txtCorreo.getText().toString());
                param.put("contrasenia",newPassword);

                return param;
            }
        };
        //ENVIA LA SOLICITUD
        colaDeSolicitudes.add(solicitud);
    }
    private void generaContraseniaAleatoria() {
        // Genera una contraseña aleatoria (esto es solo un ejemplo)

        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int longitud = 8;
        StringBuilder contrasenia = new StringBuilder(longitud);
        Random random = new Random();

        for (int i = 0; i < longitud; i++) {
            int indice = random.nextInt(caracteres.length());
            contrasenia.append(caracteres.charAt(indice));
        }

        newPassword = contrasenia.toString();



    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public recupera_Contrasenia() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment recupera_Contrasenia.
     */
    // TODO: Rename and change types and number of parameters
    public static recupera_Contrasenia newInstance(String param1, String param2) {
        recupera_Contrasenia fragment = new recupera_Contrasenia();
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
        return inflater.inflate(R.layout.fragment_recupera__contrasenia, container, false);
    }
}