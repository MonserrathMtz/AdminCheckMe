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
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
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
import Volley.API;
import Volley.VolleySingleton;

public class AdapterCarreras extends RecyclerView.Adapter<AdapterCarreras.ViewHolderCarreras>{

    private ArrayList<MCarreras> lista;
    private Bundle paquete;
    public AdapterCarreras(ArrayList<MCarreras> lista) {
        this.lista = lista;
    }
    private Context contexto;




    @NonNull
    @Override
    public AdapterCarreras.ViewHolderCarreras onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carreras,parent,false);
        contexto = parent.getContext();
        return new ViewHolderCarreras(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCarreras.ViewHolderCarreras holder, int position) {
            MCarreras objCar = lista.get(position);
            paquete = new Bundle();//Creacion del Bundle
            MCarreras tut=lista.get(position);
            holder.txtClave.setText(objCar.getClave()+"");
            holder.txtClave.setEnabled(false);
            holder.txtNombre.setText(objCar.getNombreCorto()+"");
            holder.txtNombre.setEnabled(false);
            holder.txtNombreCompleto.setText(objCar.getNombreLargo()+"");
            holder.txtNombreCompleto.setEnabled(false);
            holder.btnEditar.setEnabled(false);

            holder.btnEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicEditar(objCar.getIdCarrera(),holder.txtNombre.getText(),holder.txtNombreCompleto.getText(),holder.txtClave.getText());
                }
            });
            holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicEliminar(objCar.getIdCarrera());
                }
            });
            holder.editar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    AlertDialog.Builder msg = new AlertDialog.Builder(contexto);
                    msg.setTitle("EDITAR");
                    msg.setMessage("Ahora Puedes editar");
                    msg.setPositiveButton("Aceptar",null);
                    AlertDialog dialog=msg.create();
                    msg.show();


                    holder.txtClave.setEnabled(true);
                    holder.txtClave.setBackground(contexto.getResources().getDrawable(R.drawable.fondo_gris));
                    holder.txtNombre.setEnabled(true);
                    holder.txtNombre.setBackground(contexto.getResources().getDrawable(R.drawable.fondo_gris));
                    holder.txtNombreCompleto.setEnabled(true);
                    holder.txtNombreCompleto.setBackground(contexto.getResources().getDrawable(R.drawable.fondo_gris));
                    holder.btnEditar.setEnabled(true);
                }
            });
            holder.btnAsignaturas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    paquete.putSerializable("objeto" , tut);
                    clicAsignaturas(v);
                }
            });
    }

    private void clicEditar(int idCarrera, Editable text, Editable txtNombreCompleto, Editable clave) {
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
        StringRequest solicitud= new StringRequest(Request.Method.POST, API.UPDATECARRERA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();//apaga el cuadro de dialogo
                        try {
                            //LEER AQUI EL CONTENIDO DE LA VARIABLE response
                            msg.setTitle("ACTUALIZANDO");
                            msg.setMessage("Has actualizaste una Carrera");
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
                param.put("idCarrera",idCarrera+"");
                param.put("nombreCorto",text+"");
                param.put("nombreLargo",txtNombreCompleto+"");
                param.put("clave",clave+"");
                return param;
            }
        };
        //ENVIA LA SOLICITUD
        colaDeSolicitudes.add(solicitud);

    }

    private void clicEliminar(int idCarrera) {

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
        StringRequest solicitud= new StringRequest(Request.Method.POST, API.DELETECARRERA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();//apaga el cuadro de dialogo
                        try {
                            //LEER AQUI EL CONTENIDO DE LA VARIABLE response
                            msg.setTitle("Eliminando");
                            msg.setMessage("Has eliminado una Carrera");
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
                param.put("idCarrera",idCarrera+"");
                return param;
            }
        };
        //ENVIA LA SOLICITUD
        colaDeSolicitudes.add(solicitud);
    }

    private void clicAsignaturas(View v) {
        NavController nav = Navigation.findNavController(v);
        nav.navigate(R.id.action_frg_carreras_to_frg_asignaturas,paquete);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
    public void filtro(ArrayList<MCarreras> filtrados){
        this.lista = filtrados;
        notifyDataSetChanged();
    }


    public class ViewHolderCarreras extends RecyclerView.ViewHolder {
    private EditText txtClave,txtNombre,txtNombreCompleto;
    private CardView btnAsignaturas;
    private ImageView btnEditar,btnEliminar;
    private Switch editar;
        public ViewHolderCarreras(@NonNull View itemView) {
            super(itemView);

            txtClave=itemView.findViewById(R.id.carreras_clave);

            txtNombre=itemView.findViewById(R.id.carreras_nombre);
            btnAsignaturas=itemView.findViewById(R.id.carreras_btn_asignaturas);
            btnEditar=itemView.findViewById(R.id.carreras_editar);
            btnEliminar=itemView.findViewById(R.id.carreras_eliminar);
            txtNombreCompleto=itemView.findViewById(R.id.carreras_nomc);
            editar=itemView.findViewById(R.id.sw_editar_carrera);

        }
    }
}
