package adapter;

import android.content.Context;
import android.os.Bundle;
import android.telephony.mbms.MbmsErrors;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mbgmonserrath.checkmeaaa.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Modelo.MDocente;
import Modelo.MEstudiante;
import Volley.API;
import Volley.VolleySingleton;

public class AdapterEstudiante extends RecyclerView.Adapter<AdapterEstudiante.ViewHolderEstudiante>{

    private ArrayList<MEstudiante> lista;
    private Bundle paquete;
    private Context contexto;
    public AdapterEstudiante(ArrayList<MEstudiante> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public AdapterEstudiante.ViewHolderEstudiante onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_estudiante,parent,false);
        contexto=parent.getContext();
        return new ViewHolderEstudiante(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterEstudiante.ViewHolderEstudiante holder, int position) {

        MEstudiante objEst = lista.get(position);
        paquete = new Bundle();//Creacion del Bundle

        holder.txtNombre.setText(objEst.getNombre()+"");
        holder.txtMatricula.setText(objEst.getMatricula()+"");
        holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicEliminar(objEst.getIdEstudiante());
            }
        });

    }

    private void clicEliminar(int idEstudiante) {


            AlertDialog.Builder msg = new AlertDialog.Builder(contexto);
            // Crear un ProgressBar
            ProgressBar progressBar = new ProgressBar(contexto);
            progressBar.setIndeterminate(true); // Estilo de carga indeterminada

            // Crear el AlertDialog
            AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
            builder.setTitle("Por favor, espera");
            builder.setMessage("Conectandose con el servidor...");
            builder.setView(progressBar);
            builder.setCancelable(false); // Evitar que se pueda cancelar
            AlertDialog dialog = builder.create();
            dialog.show();

            RequestQueue colaDeSolicitudes= VolleySingleton.getInstance(contexto).getRequestQueue();
            StringRequest solicitud= new StringRequest(Request.Method.POST, API.DELETEESTU,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            dialog.dismiss();//apaga el cuadro de dialogo
                            try {
                                //LEER AQUI EL CONTENIDO DE LA VARIABLE response
                                msg.setTitle("Eliminando");
                                msg.setMessage("Has eliminado un Estudiante");
                                msg.setPositiveButton("Aceptar",null);
                                AlertDialog dialog=msg.create();
                                msg.show();






                            }catch (Exception ex){
                                //DETECTA ERRORES EN LA LECTURA DEL ARCHIVO JSON

                                msg.setTitle("Error");
                                msg.setMessage("La información no se pudo eliminar");
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
                    param.put("idEstudiante",idEstudiante+"");
                    return param;
                }
            };
            //ENVIA LA SOLICITUD
            colaDeSolicitudes.add(solicitud);

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolderEstudiante extends RecyclerView.ViewHolder {
        private TextView txtNombre,txtMatricula;
        private ImageView btnEliminar;
        public ViewHolderEstudiante(@NonNull View itemView) {
            super(itemView);
            txtNombre=itemView.findViewById(R.id.estu_nombre);
            txtMatricula=itemView.findViewById(R.id.estu_matricula);
            btnEliminar=itemView.findViewById(R.id.estu_eliminar);
        }
    }
}
