package com.mbgmonserrath.checkmeaaa;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link frg_login#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frg_login extends Fragment {
    private EditText txtUsuario, txtContrasenia;
    private CardView btnEntrar;
    private NavController navegador;
    private Bundle paquete;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public frg_login() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frg_login.
     */
    // TODO: Rename and change types and number of parameters
    public static frg_login newInstance(String param1, String param2) {
        frg_login fragment = new frg_login();
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
        return inflater.inflate(R.layout.fragment_frg_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnEntrar = view.findViewById(R.id.login_button);
        navegador = Navigation.findNavController(view);
        txtContrasenia = view.findViewById(R.id.contrasenia);
        txtUsuario = view.findViewById(R.id.username);
        paquete = new Bundle();
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicEntrar();
            }
        });
    }

        private void clicEntrar() {
            //navegador.navigate(R.id.action_frg_login_to_frg_menu);
            String correo = this.txtUsuario.getText().toString();
            //this.recuperarEstudiante(correo);
        }

        /*private void recuperarEstudiante(String correo) {

            //MEstudiante obj = new MEstudiante();
            //ArrayList<MEstudiante> lista = new ArrayList<MEstudiante>();
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
            StringRequest solicitud= new StringRequest(Request.Method.POST, API.BUSCARE,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            dialog.dismiss();//apaga el cuadro de dialogo
                            try {
                                //LEER AQUI EL CONTENIDO DE LA VARIABLE response
                                JSONObject contenido = new JSONObject(response);
                                JSONArray array=contenido.getJSONArray("contenido");
                                MEstudiante obj = new MEstudiante();
                                for (int i = 0; i < array.length(); i++) {
                                    obj = new MEstudiante();
                                    JSONObject pos = new JSONObject(array.getString(i));
                                    obj.setIdEstudiante(pos.getInt("idEstudiante"));
                                    obj.setMatricula(pos.getInt("matricula"));
                                    obj.setNombre(pos.getString("nombre"));
                                    obj.setApp(pos.getString("app"));
                                    obj.setApm(pos.getString("apm"));
                                    obj.setCorreo(pos.getString("correo"));
                                    obj.setEdo(pos.getString("edo"));
                                    obj.setMuni(pos.getString("muni"));
                                    obj.setCol(pos.getString("col"));
                                    obj.setGen(pos.getInt("gen"));
                                    obj.setContrasenia(pos.getString("contrasenia"));
                                    obj.setIdCarrera(pos.getInt("idCarrera"));
                                    //lista.add(obj);
                                }
                                if(obj.getCorreo()==null){//Si el docente es nulo
                                    AlertDialog.Builder msg = new AlertDialog.Builder(getContext());//Crea un cuadro de dialogo
                                    msg.setTitle("Error");//Le pone un titulo
                                    msg.setMessage("El Usuario no Existe");//Le pone un mensaje
                                    msg.setPositiveButton("Aceptar",null);//Le pone un boton
                                    AlertDialog dialog = msg.create();//Crea el cuadro de dialogo
                                    dialog.show();//Muestra el cuadro de dialogo
                                } //Si el docente no es nulo

                                if(txtContrasenia.getText().toString().equals(obj.getContrasenia())){//Si la contraseña es correcta
                                    paquete.putSerializable("user",obj);//Pasa el nombre del usuario a la siguiente pantalla
                                    navegador.navigate(R.id.action_frg_loginn_to_frg_menu,paquete);//Navega a la siguiente pantalla

                                }else{//Si la contraseña es incorrecta
                                    AlertDialog.Builder msg = new AlertDialog.Builder(getContext());//Crea un cuadro de dialogo
                                    msg.setTitle("Error");//Le pone un titulo
                                    msg.setMessage("Contraseña incorrecta");//Le pone un mensaje
                                    msg.setPositiveButton("Aceptar",null);//Le pone un boton
                                    AlertDialog dialog = msg.create();//Crea el cuadro de dialogo
                                    dialog.show();//Muestra el cuadro de dialogo
                                }//Si la contraseña es incorrecta
                            }catch (Exception ex){
                                //DETECTA ERRORES EN LA LECTURA DEL ARCHIVO JSON
                                msg.setTitle("Error");
                                msg.setMessage("No se pudo leer el archivo JSON*-*");
                                msg.setPositiveButton("Aceptar",null);
                                AlertDialog dialog = msg.create();
                                msg.show();

                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.dismiss();
                    // DETECTA ERRORES EN LA COMUNICACIÓN
                    msg.setTitle("Error");
                    msg.setMessage("No se pudo leer el archivo JSON:(");
                    msg.setPositiveButton("Aceptar",null);
                    AlertDialog dialog = msg.create();
                    msg.show();
                }
            }){
                @Override
                protected Map<String, String> getParams(){
                    Map<String, String> param=new HashMap<String,String>();
                    //PASA PARAMETROS A LA SOLICITUD
                    param.put("correo",correo);
                    return param;
                }
            };
            //ENVIA LA SOLICITUD
            colaDeSolicitudes.add(solicitud);
        }*/
    }
