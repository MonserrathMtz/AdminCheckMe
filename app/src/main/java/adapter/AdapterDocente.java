package adapter;

import android.content.Context;
import android.os.Bundle;
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

import Modelo.MCarreras;
import Modelo.MDocente;
import Volley.API;
import Volley.VolleySingleton;

public class AdapterDocente extends RecyclerView.Adapter<AdapterDocente.ViewHolderDocente>{
    private ArrayList<MDocente> lista;
    private Bundle paquete;
    private Context contexto;
    public AdapterDocente(ArrayList<MDocente> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public AdapterDocente.ViewHolderDocente onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_docentes,parent,false);
        contexto = parent.getContext();
        return new ViewHolderDocente(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDocente.ViewHolderDocente holder, int position) {
        MDocente objDoc = lista.get(position);
        paquete = new Bundle();//Creacion del Bundle

        holder.txtNombre.setText(objDoc.getNombre()+"");
        holder.txtNoTrabajador.setText(objDoc.getNumTrabajador()+"");
        holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicEliminar(objDoc.getIdDocente());
            }
        });

    }

    private void clicEliminar(int idDocente) {

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
            StringRequest solicitud= new StringRequest(Request.Method.POST, API.DELETEDOC,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            dialog.dismiss();//apaga el cuadro de dialogo
                            try {
                                //LEER AQUI EL CONTENIDO DE LA VARIABLE response
                                msg.setTitle("Eliminando");
                                msg.setMessage("Has eliminado un Docente");
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
                    param.put("idDocente",idDocente+"");
                    return param;
                }
            };
            //ENVIA LA SOLICITUD
            colaDeSolicitudes.add(solicitud);

    }

    @Override
    public int getItemCount() {
        return lista.size();    }

    public void filtro(ArrayList<MDocente> filtrados){
        this.lista = filtrados;
        notifyDataSetChanged();
    }

    public class ViewHolderDocente extends RecyclerView.ViewHolder {
        private TextView txtNombre,txtNoTrabajador;
        private ImageView btnEliminar;
        public ViewHolderDocente(@NonNull View itemView) {
            super(itemView);
            txtNombre=itemView.findViewById(R.id.docentes_nombre);
            txtNoTrabajador=itemView.findViewById(R.id.docentes_trabajador);
            btnEliminar=itemView.findViewById(R.id.docente_eliminar);

        }
    }
}
