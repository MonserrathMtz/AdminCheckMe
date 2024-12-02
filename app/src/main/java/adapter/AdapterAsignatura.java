package adapter;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;

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

import Modelo.MAsignatura;
import Volley.API;
import Volley.VolleySingleton;

public class AdapterAsignatura extends RecyclerView.Adapter<AdapterAsignatura.ViewHolderAsignatura>{


    private ArrayList<MAsignatura> lista;
    private Bundle paquete;
    public AdapterAsignatura(ArrayList<MAsignatura> lista) {
        this.lista = lista;
    }
    private Context contexto;

    @NonNull
    @Override
    public AdapterAsignatura.ViewHolderAsignatura onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_asignatura,parent,false);
        contexto = parent.getContext();
        return new ViewHolderAsignatura(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAsignatura.ViewHolderAsignatura holder, int position) {
        MAsignatura objAsig = lista.get(position);
        paquete = new Bundle();//Creacion del Bundle

        holder.txtNombre.setText(objAsig.getNombreCorto()+"");
        holder.txtClave.setText(objAsig.getClave()+"");
        holder.txtNombreL.setText(objAsig.getNombreLargo()+"");
        holder.txtIdCarrera.setText(objAsig.getIdCarrera()+"");


        holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicEliminar(objAsig.getIdAsignatura());
            }
        });


        holder.txtNombre.setEnabled(false);
        holder.txtClave.setEnabled(false);
        holder.txtNombreL.setEnabled(false);
        holder.btnEditar.setEnabled(false);
        holder.txtIdCarrera.setEnabled(false);




        holder.eticEdidar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
                holder.txtClave.setEnabled(true);
                holder.txtClave.setBackground(contexto.getResources().getDrawable(R.drawable.fondo_gris));
                holder.txtNombreL.setEnabled(true);
                holder.txtNombreL.setBackground(contexto.getResources().getDrawable(R.drawable.fondo_gris));
                holder.btnEditar.setEnabled(true);
                holder.txtIdCarrera.setEnabled(true);
                holder.txtIdCarrera.setBackground(contexto.getResources().getDrawable(R.drawable.fondo_gris));

            }
        });


        holder.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicEditar(objAsig.getIdAsignatura(),holder.txtClave.getText(),holder.txtNombre.getText(),holder.txtNombreL.getText(),holder.txtIdCarrera.getText());
            }
        });
    }

    private void clicEditar(int idAsignatura, Editable clave, Editable nombreCorto, Editable nombreLargo, Editable idCarrera) {

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
        StringRequest solicitud= new StringRequest(Request.Method.POST, API.UPDATEASIG,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();//apaga el cuadro de dialogo
                        try {
                            //LEER AQUI EL CONTENIDO DE LA VARIABLE response
                            msg.setTitle("EDICIÓN");
                            msg.setMessage("Has editado una Asignatura");
                            msg.setPositiveButton("Aceptar",null);
                            AlertDialog dialog=msg.create();
                            msg.show();


                        }catch (Exception ex){
                            //DETECTA ERRORES EN LA LECTURA DEL ARCHIVO JSON

                            msg.setTitle("Error");
                            msg.setMessage("La información no se pudo editar");
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
                param.put("id",idAsignatura+"");
                param.put("clave",clave+"");
                param.put("nombreCorto",nombreCorto+"");
                param.put("nombreLargo",nombreLargo+"");
                param.put("idCarrera",idCarrera+"");
                return param;
            }
        };
        //ENVIA LA SOLICITUD
        colaDeSolicitudes.add(solicitud);
    }

    private void clicEliminar(int idAsignatura) {

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
        StringRequest solicitud= new StringRequest(Request.Method.POST, API.DELETEASIG,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();//apaga el cuadro de dialogo
                        try {
                            //LEER AQUI EL CONTENIDO DE LA VARIABLE response
                            msg.setTitle("Eliminando");
                            msg.setMessage("Has eliminado una Asignatura");
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
                param.put("id",idAsignatura+"");
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

    public void filtro(ArrayList<MAsignatura> filtrados){
        this.lista = filtrados;
        notifyDataSetChanged();
    }

    public class ViewHolderAsignatura extends RecyclerView.ViewHolder {
        private EditText txtNombre,txtClave,txtNombreL,txtIdCarrera;

        private ImageView btnEliminar,btnEditar;
        private Switch eticEdidar;
        public ViewHolderAsignatura(@NonNull View itemView) {
            super(itemView);
            txtClave=itemView.findViewById(R.id.asignatura_clave);
            txtNombre=itemView.findViewById(R.id.asignatura_nombre);
            txtNombreL=itemView.findViewById(R.id.asignatura_nomL);
            txtIdCarrera=itemView.findViewById(R.id.asignatura_idCarrera);
            btnEliminar=itemView.findViewById(R.id.asignatura_eliminar);
            btnEditar=itemView.findViewById(R.id.asignatura_editar);
            eticEdidar = itemView.findViewById(R.id.sw_asig_editar);
        }
    }
}
