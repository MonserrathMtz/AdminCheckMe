package adapter;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
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

import Modelo.MPeriodo;
import Volley.API;
import Volley.VolleySingleton;

public class AdapterPeriodo  extends RecyclerView.Adapter<AdapterPeriodo.ViewHolderPeriodo>{

    private ArrayList<MPeriodo> lista;
    private Bundle paquete;
    private Context contexto;
    public AdapterPeriodo(ArrayList<MPeriodo> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public AdapterPeriodo.ViewHolderPeriodo onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_periodos,parent,false);
        contexto = parent.getContext();
        return new ViewHolderPeriodo(v);
    }
    @Override
    public void onBindViewHolder(@NonNull AdapterPeriodo.ViewHolderPeriodo holder, int position) {
        MPeriodo objPer = lista.get(position);



        holder.txtNombre.setText(objPer.getNombre()+"");
        holder.txtNombre.setEnabled(false);
        holder.txtFechaI.setText(objPer.getFechaI()+"");
        holder.txtFechaI.setEnabled(false);
        holder.txtFechaF.setText(objPer.getFechaF()+"");
        holder.txtFechaF.setEnabled(false);


        holder.enaEditar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                AlertDialog.Builder msg = new AlertDialog.Builder(contexto);
                msg.setTitle("EDITAR");
                msg.setMessage("Ahora Puedes editar");
                msg.setPositiveButton("Aceptar",null);
                AlertDialog dialog=msg.create();
                msg.show();
                holder.txtNombre.setEnabled(true);
                holder.txtNombre.setBackground(contexto.getResources().getDrawable(R.drawable.fondo_gris));
                holder.txtFechaI.setEnabled(true);
                holder.txtFechaI.setBackground(contexto.getResources().getDrawable(R.drawable.fondo_gris));
                holder.txtFechaF.setEnabled(true);
                holder.txtFechaF.setBackground(contexto.getResources().getDrawable(R.drawable.fondo_gris));
            }
        });

        holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicEliminar(objPer.getIdPeriodo());
            }
        });

        holder.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicEditar(objPer.getIdPeriodo(),holder.txtNombre.getText(),holder.txtFechaI.getText(),holder.txtFechaF.getText());
            }
        });
    }
    private void clicEliminar(int idPeriodo) {
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
        StringRequest solicitud= new StringRequest(Request.Method.POST, API.DELETEPER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();//apaga el cuadro de dialogo
                        try {
                            //LEER AQUI EL CONTENIDO DE LA VARIABLE response
                            msg.setTitle("Eliminando");
                            msg.setMessage("Has eliminado un Periodo");
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
                param.put("idPeriodo",idPeriodo+"");
                return param;
            }
        };
        //ENVIA LA SOLICITUD
        colaDeSolicitudes.add(solicitud);

    }

    private void clicEditar(int idPeriodo, Editable nombre, Editable fechaI, Editable fechaF) {

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
        StringRequest solicitud= new StringRequest(Request.Method.POST, API.UPDATEPER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();//apaga el cuadro de dialogo
                        try {
                            //LEER AQUI EL CONTENIDO DE LA VARIABLE response
                            msg.setTitle("ACTUALIZANDO");
                            msg.setMessage("Has actualizaste un Periodo");
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
                param.put("idPeriodo",idPeriodo+"");
                param.put("nombre",nombre+"");
                param.put("fechaI",fechaI+"");
                param.put("fechaF",fechaF+"");

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
    public void filtro(ArrayList<MPeriodo> filtrados){
        this.lista = filtrados;
        notifyDataSetChanged();
    }

    public class ViewHolderPeriodo extends RecyclerView.ViewHolder {
        private EditText txtNombre,txtFechaI,txtFechaF;
        private ImageView btnEditar,btnEliminar;
        private Switch enaEditar;


        public ViewHolderPeriodo(@NonNull View itemView) {
            super(itemView);
            txtNombre=itemView.findViewById(R.id.periodos_nombre);
            txtFechaI=itemView.findViewById(R.id.periodo_fecha);
            txtFechaF=itemView.findViewById(R.id.periodo_fecha_final);
            btnEditar=itemView.findViewById(R.id.periodo_editar);
            btnEliminar=itemView.findViewById(R.id.periodo_eliminar);
            enaEditar=itemView.findViewById(R.id.sw_editar_periodo);


        }
    }
}

