package SPEC.PKG;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

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

    String url1, arraylugar, SELENFERMERA, SELPACIENTE, horafinal, turno, turnoON, fechafinal;
    String FECHA, HORA, TURNO, HABITACION, FOLIODISPOSITIVO,TR, EVENTOGEN, idENFERMERA, NOMBRE, PRIMERAPEIDO, SEGUNDOAPEIDO, ENFERMEGEN,FOLENFE;
    int arreglo1 = 1;
    int arrayLugar = 1;
    private TextView PACIENTEASISTIR, ENFEASISTIR, titulo, CUADRITO, tituloupdate;
    private ListView LISTAEVENTOS, LISTAENFERMERAS;
    SimpleDateFormat horaFormat = new SimpleDateFormat("HH:mm:ss");
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    Date horaD, date;
    Timer tiempo = new Timer();
    String[] array = {};
    ArrayList<String> EVENTOSDATOS = new ArrayList<String>(Arrays.asList(array));
    ArrayList<String> ENFERMERASDATOS = new ArrayList<String>(Arrays.asList(array));
    ArrayAdapter<String> adapterConsulta1, adapterConsulta2;
    RequestQueue request1;////////////////////////////////////////////////////////////json webservices/////////////////
    JsonObjectRequest jsonrequest;////////////////////////////////////////////////////////////json webservices/////////////////

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);////////////////marcaba error solo por dar gusto al usuario en la orientacion no problem
        setContentView(R.layout.activity_main);

        Button actualizar = (Button) findViewById(R.id.button);
        Button MODENFERMERAS = (Button) findViewById(R.id.MODENFERMERAS);
        Button ASISTIR = (Button) findViewById(R.id.ASISTIRPACIENTE);
        request1 = Volley.newRequestQueue(this);
        PACIENTEASISTIR = (TextView) findViewById(R.id.pacieAsisir);
        ENFEASISTIR = (TextView) findViewById(R.id.enfeAsistir);
        LISTAEVENTOS = (ListView) findViewById(R.id.eventosList);
        LISTAENFERMERAS = (ListView) findViewById(R.id.enfermerasList);
        titulo = (TextView) findViewById(R.id.textView);
        CUADRITO = (TextView) findViewById(R.id.textView2);
        tituloupdate = (TextView) findViewById(R.id.textView3);

        adapterConsulta1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, EVENTOSDATOS);
        adapterConsulta2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ENFERMERASDATOS);

///////////////////////////////////////////////////////////////////////////logica principal//////////////////////////////////////////////////////////
        date = new Date();
        fechafinal = dateFormat.format(date);
        turno();
        consulEvento();

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnoON = "CONSULTAEVENTO";
                consulEvento();
            }
        });
        ASISTIR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnoON = "UPDATEENFERMERA";
                consulEvento();
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

    ///////////////////////////////////////////////////////////////////////////logica principal//////////////////////////////////////////////////////////
    public void alert2() {
        AlertDialog.Builder alertmodificarenfermeras = new AlertDialog.Builder(this);
        alertmodificarenfermeras.setTitle("REGISTRO DE ENFERMERAS");
        alertmodificarenfermeras.setMessage(FOLIODISPOSITIVO);
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
                url1 = "http://192.168.0.15/BDSEP/CONSULTARENFERMERASMANANA.php";
            }
            if (turno == "TARDE") {
                url1 = "http://192.168.0.15/BDSEP/CONSULTARENFERMERASTARDE.php?";
            }
            if (turno == "NOCHE") {
                url1 = "http://192.168.0.15/BDSEP/CONSULTARENFERMERASNOCHE.php?";
            }

        }
        if (turnoON == "CONSULTAEVENTO") {
            if (arreglo1 % 2 == 0) {
                url1 = "http://192.168.0.15/BDSEP/CONSULTAEVENTO.php";
            }
            if (arreglo1 % 2 == 1) {
                url1 = "http://192.168.0.15/BDSEP/CONSULTAEVENTOE.php";
            }
            arreglo1 = arreglo1 + 1;
        }
        if (turnoON == "UPDATEENFERMERA") {
            if (arreglo1 % 2 == 0) {
                url1 = "http://192.168.0.15/BDSEP/UPDATEENFERMERASE.php?FOLIODISPOSITIVO=" + FOLENFE + "&FECHA=" + FECHA + "&HORA=" + HORA + "&HABITACION=" + HABITACION + "&ENFERMERA="+ SELENFERMERA;
            }
            if (arreglo1 % 2 == 1) {
                url1 = "http://192.168.0.15/BDSEP/UPDATEENFERMERAS.php?FOLIODISPOSITIVO=" + FOLENFE + "&FECHA=" + FECHA + "&HORA=" + HORA + "&HABITACION=" + HABITACION + "&ENFERMERA=" + SELENFERMERA;
            }
            url1 = url1.replace(" ", "%20");
            titulo.setText(url1);
        }
        jsonrequest = new JsonObjectRequest(Request.Method.POST, url1, null, this, this);////////////////////////////////////////////////////////////json webservices/////////////////
        request1.add(jsonrequest);////////////////////////////////////////////////////////////json webservices/////////////////
    }

    @Override
    public void onResponse(JSONObject response) {
        JSONArray consulta = response.optJSONArray("usuario");
        Usuarios consultaUsuario;

        if (turnoON == "CONSULTAEVENTO") {
            turnoON = "x";
            try {
                int i = 0;
                for (i = 0; i < consulta.length(); i++) {
                    consultaUsuario = new Usuarios();
                    JSONObject jsonconsulta = null;
                    jsonconsulta = consulta.getJSONObject(i);
                    consultaUsuario.setFOLIODISPOSITIVO(jsonconsulta.optString("FOLIODISPOSITIVO"));
                    consultaUsuario.setFECHA(jsonconsulta.optString("FECHA"));
                    consultaUsuario.setHORA(jsonconsulta.optString("HORA"));
                    consultaUsuario.setTURNO(jsonconsulta.optString("TURNO"));
                    consultaUsuario.setHABITACION(jsonconsulta.optString("HABITACION"));
                    consultaUsuario.setTR(jsonconsulta.optString("TIEMPORESPUESTA"));
                    FOLIODISPOSITIVO = consultaUsuario.getFOLIODISPOSITIVO();
                    FECHA = consultaUsuario.getFECHA();
                    HORA = consultaUsuario.getHORA();
                    TURNO = consultaUsuario.getTURNO();
                    HABITACION = consultaUsuario.getHABITACION();
                    TR=consultaUsuario.getTR();
                    EVENTOGEN = "FOLIODISPOSITIVO="+FOLIODISPOSITIVO + "\nFECHA: " + FECHA + "\nHORA: " + HORA + "\nTURNO: " + TURNO + "\nHABITACION: " + HABITACION;

                    if(TR.compareTo("SIN RESPUESTA") == 0) {
                        EVENTOSDATOS.add(EVENTOGEN);
                        FOLENFE=FOLIODISPOSITIVO;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            tituloupdate.setText(String.valueOf(arrayLugar));
            LISTAEVENTOS.setAdapter(adapterConsulta1);
            LISTAEVENTOS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                    PACIENTEASISTIR.setText(""+ LISTAEVENTOS.getItemAtPosition(i));
                }
            });
            Toast.makeText(getApplicationContext(), "CONSULTA EVENTOS LISTA", Toast.LENGTH_SHORT).show();
        }
        if (turnoON == "CONSULTAENFERMERA") {
                turnoON = "x";
            try {
                int i = 0;
                for (i = 0; i < consulta.length(); i++) {
                    consultaUsuario = new Usuarios();
                    JSONObject jsonconsulta = null;
                    jsonconsulta = consulta.getJSONObject(i);
                    consultaUsuario.setIdENFERMERA(jsonconsulta.optString("idENFERMERA"));
                    consultaUsuario.setNOMBRE(jsonconsulta.optString("NOMBRE"));
                    consultaUsuario.setPRIMERAPEIDO(jsonconsulta.optString("PRIMERAPEIDO"));
                    consultaUsuario.setSEGUNDOAPEIDO(jsonconsulta.optString("SEGUNDOAPEIDO"));
                    idENFERMERA = consultaUsuario.getIdENFERMERA();
                    NOMBRE= consultaUsuario.getNOMBRE();
                    PRIMERAPEIDO = consultaUsuario.getPRIMERAPEIDO();
                    SEGUNDOAPEIDO=consultaUsuario.getSEGUNDOAPEIDO();
                    ENFERMEGEN=NOMBRE+" "+PRIMERAPEIDO+" "+SEGUNDOAPEIDO;
                    ENFERMERASDATOS.add(ENFERMEGEN);
                }
            } catch (JSONException e) {
                    e.printStackTrace();
            }
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
        if (turnoON == "UPDATEENFERMERA") {
            turnoON = "x";
            Toast.makeText(getApplicationContext(), "REGISTRO ACTUALIZADO", Toast.LENGTH_LONG).show();
        }
        }


        @Override
        public void onErrorResponse (VolleyError error){
            if (turnoON == "CONSULTAEVENTO") {
                Toast.makeText(getApplicationContext(), "ERROR CONSULTA EVENTO" + error, Toast.LENGTH_SHORT).show();
            }
            if (turnoON == "UPDATEENFERMERA") {
                Toast.makeText(getApplicationContext(), "NO SE ACTUALIZO ENFERMERA", Toast.LENGTH_SHORT).show();
            }
            if (turnoON == "CONSULTAENFERMERA") {
                Toast.makeText(getApplicationContext(), "ERROR CONSULTA ENFERMERAS" + error, Toast.LENGTH_SHORT).show();
            }
            }
}

