package SPEC.PKG;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject> {

    String url1,SELENFERMERA;
    private String[] EVENTOSDATOS = { "HABITACION: 101 ALAN PEREZ RIVAS", "HABITACION: 102 LUIS PEREZ RIVAS", "HABITACION: 105 RUBEN PEREZ RIVAS",
                                "HABITACION: 104 JOSE PEREZ RIVAS", "HABITACION: 115 DANIEL PEREZ RIVAS" };


    private String[] ENFERMERASDATOS = { "ANA", "LUISA", "ROCIO", "PERLA" };

    private TextView CHECARDATOS,PACIENTEASISTIR,ENFEASISTIR;
    private ListView LISTAEVENTOS,LISTAENFERMERAS;
    RequestQueue request1;////////////////////////////////////////////////////////////json webservices/////////////////
    JsonObjectRequest jsonrequest;////////////////////////////////////////////////////////////json webservices/////////////////
    String FOLIOGENERAL,FECHA,HORA,TURNO,HABITACION;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);////////////////marcaba error solo por dar gusto al usuario en la orientacion no problem
        setContentView(R.layout.activity_main);

        Button MODENFERMERAS = (Button) findViewById(R.id.MODENFERMERAS);
        Button ASISTIR = (Button)findViewById(R.id.ASISTIRPACIENTE);
        MODENFERMERAS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), modificarActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        request1=Volley.newRequestQueue(getApplicationContext());////////////////////////////////////////////////////////////json webservices/////////////////
        CHECARDATOS=(TextView)findViewById(R.id.textView);
        PACIENTEASISTIR=(TextView)findViewById(R.id.pacieAsisir);
        ENFEASISTIR = (TextView)findViewById(R.id.enfeAsistir);
        LISTAEVENTOS =(ListView)findViewById(R.id.eventosList);
        LISTAENFERMERAS=(ListView)findViewById(R.id.enfermerasList);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, EVENTOSDATOS);
        LISTAEVENTOS.setAdapter(adapter);
        LISTAEVENTOS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                PACIENTEASISTIR.setText(""+ LISTAEVENTOS.getItemAtPosition(i));
                // CHECARDATOS.setText("Población de "+ LISTAEVENTOS.getItemAtPosition(i) + " es "+ habitantes[i]);
            }
        });
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, ENFERMERASDATOS);
        LISTAENFERMERAS.setAdapter(adapter2);
        LISTAENFERMERAS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                ENFEASISTIR.setText(""+ LISTAENFERMERAS.getItemAtPosition(i));
                SELENFERMERA = ENFEASISTIR.getText().toString();
            }
        });
        ASISTIR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //updateEnfermera();
                consulEnfermera();
            }
        });
    }
    public void agregar() {
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);

        url1 = "http://192.168.0.15/BDSEP/INSERTENFERMERASNOCHE.PHP?NOMBRE=" + "NOMBRE + &PRIMERA= + PRIMERA + &SEGUNDOA= + SEGUNDOA";
        url1 = url1.replace(" ", "%20");

        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "ENFERMERA AGREGADA", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "NO SE AGREGO ENFERMERA, ERROR WEBSERVICE"+ url1, Toast.LENGTH_LONG).show();
            }
        }) {
        };
        MyRequestQueue.add(MyStringRequest);
    }
    public void alert() {
        AlertDialog.Builder alertmodificarenfermeras = new AlertDialog.Builder(this);
        alertmodificarenfermeras.setTitle("REGISTRO DE ENFERMERAS");
        alertmodificarenfermeras.setMessage("DESEA CONTINUAR CON LA MODIFICACION?");
        alertmodificarenfermeras.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertmodificarenfermeras.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "PROCEDIMIENTO CANCELADO", Toast.LENGTH_LONG).show();
            }
        });
        alertmodificarenfermeras.show();
    }
    public void alert2(){
        AlertDialog.Builder alertmodificarenfermeras = new AlertDialog.Builder(this);
        alertmodificarenfermeras.setTitle("REGISTRO DE ENFERMERAS");
        alertmodificarenfermeras.setMessage("DESEA CONTINUAR CON LA MODIFICACION?");
        alertmodificarenfermeras.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertmodificarenfermeras.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "PROCEDIMIENTO CANCELADO", Toast.LENGTH_LONG).show();
            }
        });
        alertmodificarenfermeras.show();
    }
    public void updateEnfermera() {
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        url1 = "http://192.168.0.15/BDSEP/UPDATEENFERMERASE.php?FOLIOGENERAL=1&ENFERMERA="+ SELENFERMERA;
        url1 = url1.replace(" ", "%20");
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                Toast.makeText(getApplicationContext(), "REGISTRO ACTUALIZADO", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "No se actualizo", Toast.LENGTH_SHORT).show();
            }
        }) {
        };
        MyRequestQueue.add(MyStringRequest);
    }



    public void consulEnfermera() {
        url1 = "http://192.168.0.15/BDSEP/CONSULTAEVENTO.php?evento";
        jsonrequest= new JsonObjectRequest(Request.Method.GET,url1,null,this,this);////////////////////////////////////////////////////////////json webservices/////////////////
        request1.add(jsonrequest);////////////////////////////////////////////////////////////json webservices/////////////////
    }
    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(getApplicationContext(), "CONSULTA LISTA"+response, Toast.LENGTH_SHORT).show();
        ConsultasActivity consultaUsuario = new ConsultasActivity();
        JSONArray consulta=response.optJSONArray("usuario");
        JSONObject jsonconsulta=null;

        try {
            jsonconsulta=consulta.getJSONObject(0);
            consultaUsuario.setFOLIOGENERAL(jsonconsulta.optString("FOLIOGENERAL"));
            consultaUsuario.setFECHA(jsonconsulta.optString("FECHA"));
            consultaUsuario.setHORA(jsonconsulta.optString("HORA"));
            consultaUsuario.setTURNO(jsonconsulta.optString("TURNO"));
            consultaUsuario.setHABITACION(jsonconsulta.optString("HABITACION"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ENFEASISTIR.setText(consultaUsuario.getFECHA());
    }
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), "ERROR CONSULTA"+error, Toast.LENGTH_SHORT).show();
    }
}