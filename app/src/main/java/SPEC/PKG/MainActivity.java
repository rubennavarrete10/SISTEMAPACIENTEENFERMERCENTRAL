package SPEC.PKG;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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

public class MainActivity<HORA1> extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject> {

    String url1,SELENFERMERA,SELPACIENTE;
    int arreglo1=3;
    String arreglo2="1";
    private TextView CHECARDATOS,PACIENTEASISTIR,ENFEASISTIR,titulo;
    private ListView LISTAEVENTOS,LISTAENFERMERAS;
    SimpleDateFormat horaFormat = new SimpleDateFormat("HH:mm:ss");
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    Date horaD;
    Date date;
    String horafinal;
    String turno,turnoON;
    String fechafinal;

    String[] array = {};
    ArrayList<String> EVENTOSDATOS = new ArrayList<String>(Arrays.asList(array));
    ArrayList<String> ENFERMERASDATOS = new ArrayList<String>(Arrays.asList(array));
    ArrayAdapter<String> adapterConsulta1,adapterConsulta2;
    RequestQueue request1;////////////////////////////////////////////////////////////json webservices/////////////////
    JsonObjectRequest jsonrequest;////////////////////////////////////////////////////////////json webservices/////////////////
    String FECHA,HORA,TURNO,HABITACION,FOLIODISPOSITIVO,EVENTOGEN,idENFERMERA,NOMBRE,PRIMERAPEIDO,SEGUNDOAPEIDO,ENFERMEGEN;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);////////////////marcaba error solo por dar gusto al usuario en la orientacion no problem
        setContentView(R.layout.activity_main);

        Button actualizar=(Button)findViewById(R.id.button);
        Button MODENFERMERAS = (Button) findViewById(R.id.MODENFERMERAS);
        Button ASISTIR = (Button)findViewById(R.id.ASISTIRPACIENTE);
        request1=Volley.newRequestQueue(getApplicationContext());////////////////////////////////////////////////////////////json webservices/////////////////
        CHECARDATOS=(TextView)findViewById(R.id.textView);
        PACIENTEASISTIR=(TextView)findViewById(R.id.pacieAsisir);
        ENFEASISTIR = (TextView)findViewById(R.id.enfeAsistir);
        LISTAEVENTOS =(ListView)findViewById(R.id.eventosList);
        LISTAENFERMERAS=(ListView)findViewById(R.id.enfermerasList);
        titulo=(TextView)findViewById(R.id.textView);


        adapterConsulta1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, EVENTOSDATOS);
        adapterConsulta2 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, ENFERMERASDATOS);

        date = new Date();
        fechafinal = dateFormat.format(date);
        turno();
        consulEvento();
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnoON="CONSULTAEVENTO";
                consulEvento();
                ENFEASISTIR.setText(arreglo1+" "+url1);
            }
        });
        ASISTIR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnoON="UPDATEENFERMERA";
                arreglo2=FOLIODISPOSITIVO;
                consulEvento();
                ENFEASISTIR.setText(arreglo1+" "+url1);

            }
        });
        MODENFERMERAS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), modificarActivity.class);
                startActivityForResult(intent, 0);
            }
        });
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
    public boolean turno() {
        turnoON = "CONSULTAENFERMERA";
        try {
            horaD = new Date();
            horafinal = horaFormat.format(horaD);
            String hora1 = "00:00:00";
            String hora2 = "07:59:59";
            String hora3 = "08:00:00";
            String hora4 = "15:59:59";
            String hora5 = "16:00:00";
            String hora6 = "23:59:59";
            Date date1, date2, date3, date4, date5, date6;
            date1 = horaFormat.parse(hora1);
            date2 = horaFormat.parse(hora2);
            date3 = horaFormat.parse(hora3);
            date4 = horaFormat.parse(hora4);
            date5 = horaFormat.parse(hora5);
            date6 = horaFormat.parse(hora6);
            horaD = horaFormat.parse(horafinal);
            if ((date1.compareTo(horaD) <= 0) && (date2.compareTo(horaD) >= 0)) {
                turno = "NOCHE";
            }
            if ((date3.compareTo(horaD) <= 0) && (date4.compareTo(horaD) >= 0)) {
                turno = "MANANA";
            }
            if ((date5.compareTo(horaD) <= 0) && (date6.compareTo(horaD) >= 0)) {
                turno = "TARDE";
            }
        } catch (ParseException e) {
            turno = "ERROR";
        }
        return false;
    }
    public void consulEvento() {
        if (turnoON == "CONSULTAENFERMERA") {

            if (turno == "MANANA") {
                url1 = "http://192.168.0.15/BDSEP/CONSULTARENFERMERASMANANA.php?evento=1&idENFERMERA=1";
            }
            if (turno == "TARDE") {
                url1 = "http://192.168.0.15/BDSEP/CONSULTARENFERMERASTARDE.php?evento=1&idENFERMERA=1";
            }
            if (turno == "NOCHE") {
                url1 = "http://192.168.0.15/BDSEP/CONSULTARENFERMERASNOCHE.php?evento=1&idENFERMERA=1";
            }

            }
        if (turnoON == "CONSULTAEVENTO") {
            if (arreglo1%2==0){
                url1 = "http://192.168.0.15/BDSEP/CONSULTAEVENTO.php?evento=1+&FOLIODISPOSITIVO=" + arreglo1;
            }
            if (arreglo1%2==1){
                url1 = "http://192.168.0.15/BDSEP/CONSULTAEVENTOE.php?evento=1+&FOLIODISPOSITIVO=" + arreglo1;
            }
            arreglo1 = arreglo1 + 1;
            }
        if (turnoON == "UPDATEENFERMERA") {
            url1= "http://192.168.0.15/BDSEP/UPDATEENFERMERASE.php";
            if (arreglo1%2==0){
                url1 = "http://192.168.0.15/BDSEP/UPDATEENFERMERAS.php?FOLIODISPOSITIVO="+FOLIODISPOSITIVO+"&FECHA="+FECHA+"&HORA="+HORA+"&HABITACION="+HABITACION+"&ENFERMERA="+ENFERMEGEN;
            }
            if (arreglo1%2==1){
                url1 = "http://192.168.0.15/BDSEP/UPDATEENFERMERASE.php?FOLIODISPOSITIVO="+FOLIODISPOSITIVO+"&FECHA="+FECHA+"&HORA="+HORA+"&HABITACION="+HABITACION+"&ENFERMERA="+ENFERMEGEN;
            }
            //url1= "http://192.168.0.15/BDSEP/UPDATEENFERMERASE.php?FOLIODISPOSITIVO=3&FECHA=06/03/2020&HORA=10:34:19&HABITACION=101&ENFERMERA=HOLA";
            url1 = url1.replace(" ", "%20");
            }

        jsonrequest = new JsonObjectRequest(Request.Method.POST, url1, null, this, this);////////////////////////////////////////////////////////////json webservices/////////////////
        request1.add(jsonrequest);////////////////////////////////////////////////////////////json webservices/////////////////
    }
    @Override
    public void onResponse(JSONObject response) {
        ConsultasActivity consultaUsuario = new ConsultasActivity();
        JSONArray consulta=response.optJSONArray("usuario");
        JSONObject jsonconsulta;

        if (turnoON == "CONSULTAEVENTO") {
                    turnoON="x";
                    try {
                        jsonconsulta = consulta.getJSONObject(0);
                        consultaUsuario.setFOLIODISPOSITIVO(jsonconsulta.optString("FOLIODISPOSITIVO"));
                        consultaUsuario.setFECHA(jsonconsulta.optString("FECHA"));
                        consultaUsuario.setHORA(jsonconsulta.optString("HORA"));
                        consultaUsuario.setTURNO(jsonconsulta.optString("TURNO"));
                        consultaUsuario.setHABITACION(jsonconsulta.optString("HABITACION"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    FOLIODISPOSITIVO = consultaUsuario.getFOLIODISPOSITIVO();
                    FECHA = consultaUsuario.getFECHA();
                    HORA = consultaUsuario.getHORA();
                    TURNO = consultaUsuario.getTURNO();
                    HABITACION = consultaUsuario.getHABITACION();
                    EVENTOGEN = "FOLIODISPOSITIVO: " + FOLIODISPOSITIVO + "\nFECHA: " + FECHA + "\nHORA: " + HORA + "\nTURNO: " + TURNO + "\nHABITACION: " + HABITACION;

                        EVENTOSDATOS.add(EVENTOGEN);
                        
                        LISTAEVENTOS.setAdapter(adapterConsulta1);
                        LISTAEVENTOS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                            PACIENTEASISTIR.setText("" + LISTAEVENTOS.getItemAtPosition(i));
                            SELPACIENTE=EVENTOGEN;
                            EVENTOGEN="FOLIODISPOSITIVO="+FOLIODISPOSITIVO+"&FECHA="+FECHA+"&HORA="+HORA+"&HABITACION="+HABITACION+"&ENFERMERA="+SELENFERMERA;
                        }
                    });
                    Toast.makeText(getApplicationContext(), "CONSULTA EVENTOS LISTA", Toast.LENGTH_SHORT).show();
                    }
        if (turnoON == "CONSULTAENFERMERA"){
                    turnoON="x";
                    try {
                        jsonconsulta = consulta.getJSONObject(0);
                        consultaUsuario.setIdENFERMERA(jsonconsulta.optString("idENFERMERA"));
                        consultaUsuario.setNOMBRE(jsonconsulta.optString("NOMBRE"));
                        consultaUsuario.setPRIMERAPEIDO(jsonconsulta.optString("PRIMERAPEIDO"));
                        consultaUsuario.setSEGUNDOAPEIDO(jsonconsulta.optString("SEGUNDOAPEIDO"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    idENFERMERA = consultaUsuario.getIdENFERMERA();
                    NOMBRE= consultaUsuario.getNOMBRE();
                    PRIMERAPEIDO = consultaUsuario.getPRIMERAPEIDO();
                    SEGUNDOAPEIDO=consultaUsuario.getSEGUNDOAPEIDO();
                    ENFERMEGEN=NOMBRE+" "+PRIMERAPEIDO+" "+SEGUNDOAPEIDO;
                    ENFERMERASDATOS.add(ENFERMEGEN);
                    LISTAENFERMERAS.setAdapter(adapterConsulta2);
                    LISTAENFERMERAS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                            ENFEASISTIR.setText(""+ LISTAENFERMERAS.getItemAtPosition(i));
                            SELENFERMERA=ENFERMEGEN;
                        }
                    });
                    Toast.makeText(getApplicationContext(), "CONSULTA ENFERMERAS LISTA", Toast.LENGTH_SHORT).show();
                    }
        if(turnoON=="UPDATEENFERMERA"){
                    turnoON="x";
                    Toast.makeText(getApplicationContext(), "REGISTRO ACTUALIZADO", Toast.LENGTH_LONG).show();
                    }
    }
    @Override
    public void onErrorResponse(VolleyError error) {
        if (turnoON == "CONSULTAEVENTO") {
            Toast.makeText(getApplicationContext(), "ERROR CONSULTA EVENTO"+error, Toast.LENGTH_SHORT).show();
        }
        if(turnoON=="UPDATEENFERMERA"){
            Toast.makeText(getApplicationContext(), "NO SE ACTUALIZO ENFERMERA", Toast.LENGTH_SHORT).show();
        }
        if (turnoON == "CONSULTAENFERMERA") {
            Toast.makeText(getApplicationContext(), "ERROR CONSULTA ENFERMERAS"+error, Toast.LENGTH_SHORT).show();
        }

    }
}
