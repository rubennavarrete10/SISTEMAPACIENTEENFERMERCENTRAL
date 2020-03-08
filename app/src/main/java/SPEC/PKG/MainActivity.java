package SPEC.PKG;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.io.IOException;
import java.lang.reflect.Array;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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

    String url1, SELENFERMERA, REPHABITACION, horafinal, turno, turnoON, fechafinal;
    String FECHA, HORA, TURNO, HABITACION,TIPODELLAMADO, FOLIODISPOSITIVO,TR="NADA", EVENTOGEN, idENFERMERA, NOMBRE, PRIMERAPEIDO, SEGUNDOAPEIDO, ENFERMEGEN,FOLENFE;
    int arreglo1 = 1;
    int i,a;
    int delete=0;
    private TextView PACIENTEASISTIR, ENFEASISTIR, titulo, CUADRITO, tituloupdate;
    private ListView LISTAEVENTOS, LISTAENFERMERAS;
    SimpleDateFormat horaFormat = new SimpleDateFormat("HH:mm:ss");
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    Date horaD, date;
    String[] array = {};
    ArrayList<String> EVENTOSDATOS = new ArrayList<String>(Arrays.asList(array));
    ArrayList<String> ENFERMERASDATOS = new ArrayList<String>(Arrays.asList(array));
    ArrayAdapter<String> adapterConsulta1, adapterConsulta2,adapterConsulta3;
    RequestQueue request1;////////////////////////////////////////////////////////////json webservices/////////////////
    Usuarios consultaUsuario;
    JSONArray consulta;
    JsonObjectRequest jsonrequest;////////////////////////////////////////////////////////////json webservices/////////////////
    private MediaPlayer player;
    private String outputFile =  Environment.getExternalStorageDirectory().getAbsolutePath() + "/Fonts/" + FOLIODISPOSITIVO + "Grabacion.mp3";

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);////////////////marcaba error solo por dar gusto al usuario en la orientacion no problem
        setContentView(R.layout.activity_main);
        tarjetaSd();

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1000);
        }

        Button actualizar = (Button) findViewById(R.id.button);
        Button MODENFERMERAS = (Button) findViewById(R.id.MODENFERMERAS);
        Button ASISTIR = (Button) findViewById(R.id.ASISTIRPACIENTE);
        referenciasobjetos();

///////////////////////////////////////////////////////////////////////////logica principal//////////////////////////////////////////////////////////
        date = new Date();
        fechafinal = dateFormat.format(date);
        turno();
        consulEvento();

        LISTAEVENTOS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                PACIENTEASISTIR.setText(""+ LISTAEVENTOS.getItemAtPosition(i));
                FOLENFE = PACIENTEASISTIR.getText().toString();
                FOLENFE = FOLENFE.toString().substring(106);
                TIPODELLAMADO = PACIENTEASISTIR.getText().toString();
                TIPODELLAMADO = TIPODELLAMADO.toString().substring(0,26);
                TIPODELLAMADO = TIPODELLAMADO.toString().substring(15);
                FECHA= PACIENTEASISTIR.getText().toString();
                FECHA= FECHA.toString().substring(0,43);
                FECHA= FECHA.toString().substring(33);
                HORA= PACIENTEASISTIR.getText().toString();
                HORA= HORA.toString().substring(0,58);
                HORA= HORA.toString().substring(50);
                HABITACION= PACIENTEASISTIR.getText().toString();
                HABITACION= HABITACION.toString().substring(0,87);
                HABITACION= HABITACION.toString().substring(84);
                delete=i;
               /* EVENTOSDATOS.remove(i);
                adapterConsulta1.notifyDataSetChanged();*/
            }
        });
        LISTAENFERMERAS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                ENFEASISTIR.setText(""+ LISTAENFERMERAS.getItemAtPosition(i));
                SELENFERMERA = ENFEASISTIR.getText().toString();
            }
        });
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnoON = "CONSULTAEVENTO";
                REPHABITACION="NADA";
                adapterConsulta1.clear();
                adapterConsulta1.notifyDataSetChanged();
                arreglo1=1;
                consulEvento();
            }
        });
        ASISTIR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EVENTOSDATOS.remove(delete);
                adapterConsulta1.notifyDataSetChanged();
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
    ///////////////////////////////////////////////////////////////////////////fin logica principal//////////////////////////////////////////////////////////
    public boolean tarjetaSd() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            Toast.makeText(getApplicationContext(), "SD LISTO", Toast.LENGTH_LONG).show();
            return true;
        }
        alertaSD();
        return false;
    }
    private void referenciasobjetos() {
        request1 = Volley.newRequestQueue(this);
        PACIENTEASISTIR = (TextView) findViewById(R.id.pacieAsisir);
        ENFEASISTIR = (TextView) findViewById(R.id.enfeAsistir);
        LISTAEVENTOS = (ListView) findViewById(R.id.eventosList);
        LISTAENFERMERAS = (ListView) findViewById(R.id.enfermerasList);
        titulo = (TextView) findViewById(R.id.textView);
        CUADRITO = (TextView) findViewById(R.id.textView2);
        tituloupdate = (TextView) findViewById(R.id.textView3);
        adapterConsulta1 = new ArrayAdapter<String>(this, R.layout.simple_list_adapter, EVENTOSDATOS){
           /*
           //Con este cambias como se ve el listview en codigo cuando usas simple_list_item_1
           @Override public View getView(int position, View convertView, ViewGroup parent)
            {
                View view =super.getView(position, convertView, parent);
                TextView textView=(TextView) view.findViewById(android.R.id.text1);
                textView.setTextSize(10);
                textView.setTextColor(Color.BLACK);
                return view;
            }*/
        };
        adapterConsulta3 = new ArrayAdapter<String>(this, R.layout.simple_list_adapter_2, EVENTOSDATOS);
        adapterConsulta2 = new ArrayAdapter<String>(this, R.layout.simple_list_adapt, ENFERMERASDATOS){
        };
    }
    public void turno() {
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
                url1 = "http://192.168.0.15/BDSEP/CONSULTAEVENTO.php";
        }
        if (turnoON == "UPDATEENFERMERA") {
            url1 = "http://192.168.0.15/BDSEP/UPDATEENFERMERAS.php?FOLIOGENERAL=" + FOLENFE +"&TIPODELLAMADO="+TIPODELLAMADO+ "&FECHA=" + FECHA + "&HORA=" + HORA + "&HABITACION=" + HABITACION + "&ENFERMERA=" + SELENFERMERA;
            url1 = url1.replace(" ", "%20");
        }
        jsonrequest = new JsonObjectRequest(Request.Method.POST, url1, null, this, this);////////////////////////////////////////////////////////////json webservices/////////////////
        request1.add(jsonrequest);////////////////////////////////////////////////////////////json webservices/////////////////
    }
    @Override
    public void onResponse(JSONObject response) {
        //JSONArray consulta = response.optJSONArray("usuario");
        consulta = response.optJSONArray("usuario");
       // Usuarios consultaUsuario;

        if (turnoON == "CONSULTAEVENTO") {
            turnoON = "x";
            try {
                a=0;
                for (i = 0; i < consulta.length(); i++) {
                    obtenerdatos();
                    String index = String.valueOf(i);
                    EVENTOGEN = "TIPODELLAMADO: "+TIPODELLAMADO+"\nFECHA: " + FECHA + "\nHORA: " + HORA + "\nTURNO: " + TURNO + "\nHABITACION: " + HABITACION+"\nFOLIODISPOSITIVO="+FOLIODISPOSITIVO;
                    if(TR.contains("SIN RESPUESTA")==true) {

                    if((HABITACION.contains(REPHABITACION)) == true) {
                        a--;
                        EVENTOSDATOS.remove(a);
                    }
                    //EVENTOSDATOS.add(EVENTOGEN+"/"+index);
                    EVENTOSDATOS.add(EVENTOGEN);
                    a++;
                    REPHABITACION = HABITACION;
                    arreglo1 =10;

                    }
                }

            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            if(arreglo1 !=10){
                alertNOEVENTOS();
            }
            LISTAEVENTOS.setAdapter(adapterConsulta1);
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
            Toast.makeText(getApplicationContext(), "CONSULTA ENFERMERAS LISTA", Toast.LENGTH_SHORT).show();
        }
        if (turnoON == "UPDATEENFERMERA") {
            turnoON = "x";
            PACIENTEASISTIR.setText("");
            ENFEASISTIR.setText("");
            Toast.makeText(getApplicationContext(), "REGISTRO ACTUALIZADO", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onErrorResponse (VolleyError error){
            if (turnoON == "CONSULTAEVENTO") {
                Toast.makeText(getApplicationContext(), "ERROR CONSULTA EVENTO BASE DE DATOS VACIA" + error, Toast.LENGTH_LONG).show();
                alertNOEVENTOS();
            }
            if (turnoON == "UPDATEENFERMERA") {
                Toast.makeText(getApplicationContext(), "NO SE ACTUALIZO ENFERMERA", Toast.LENGTH_LONG).show();
            }
            if (turnoON == "CONSULTAENFERMERA") {
                Toast.makeText(getApplicationContext(), "ERROR CONSULTA ENFERMERAS" + error, Toast.LENGTH_LONG).show();
            }
            }
    public void obtenerdatos() throws JSONException {
        consultaUsuario = new Usuarios();
        JSONObject jsonconsulta = null;
        jsonconsulta = consulta.getJSONObject(i);
        consultaUsuario.setFOLIODISPOSITIVO(jsonconsulta.optString("FOLIOGENERAL"));
        consultaUsuario.setFECHA(jsonconsulta.optString("FECHA"));
        consultaUsuario.setTIPODELLAMDO(jsonconsulta.optString("TIPODELLAMADO"));
        consultaUsuario.setHORA(jsonconsulta.optString("HORA"));
        consultaUsuario.setTURNO(jsonconsulta.optString("TURNO"));
        consultaUsuario.setHABITACION(jsonconsulta.optString("HABITACION"));
        consultaUsuario.setTR(jsonconsulta.optString("TIEMPORESPUESTA"));
        FOLIODISPOSITIVO = consultaUsuario.getFOLIODISPOSITIVO();
        FECHA = consultaUsuario.getFECHA();
        HORA = consultaUsuario.getHORA();
        TURNO = consultaUsuario.getTURNO();
        HABITACION = consultaUsuario.getHABITACION();
        TIPODELLAMADO=consultaUsuario.getTIPODELLAMDO();
        TR=consultaUsuario.getTR();
    }
    public void alertNOEVENTOS() {
        AlertDialog.Builder noeventos = new AlertDialog.Builder(this);
        noeventos.setTitle("NO AHI NUEVOS EVENTOS");
        noeventos.setMessage("Actualmente no tiene eventos de paciente");
        final AlertDialog noeventosB = noeventos.create();
        noeventosB.setCanceledOnTouchOutside(true);
        noeventosB.show();

        final Handler handler  = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (noeventosB.isShowing()) {
                    noeventosB.dismiss();
                }
            }
        };
        noeventosB.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                handler.removeCallbacks(runnable);
            }
        });
        handler.postDelayed(runnable, 3000);
    }
    public void play() {
        MediaPlayer m = new MediaPlayer();
        try {
            m.setDataSource(outputFile);
        } catch (IOException e) {
            alertaPLAY();
        }
        try {
            m.prepare();
        } catch (IOException e) {
            alertaPLAY2();
        }
        m.start();
        Toast.makeText(getApplicationContext(), "REPRODUCCION EVENTO", Toast.LENGTH_LONG).show();
    }
    public void alertaSD(){
        AlertDialog.Builder noeventos = new AlertDialog.Builder(this);
        noeventos.setTitle("ERROR!");
        noeventos.setMessage("SD DANADA, LLENA, NO DETECTADA");
        final AlertDialog noeventosB = noeventos.create();
        noeventosB.setCanceledOnTouchOutside(true);
        noeventosB.show();

        final Handler handler  = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (noeventosB.isShowing()) {
                    noeventosB.dismiss();
                }
            }
        };
        noeventosB.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                handler.removeCallbacks(runnable);
            }
        });
        handler.postDelayed(runnable, 3000);
    }
    public void alertaPLAY(){
        AlertDialog.Builder noeventos = new AlertDialog.Builder(this);
        noeventos.setTitle("ERROR!");
        noeventos.setMessage("ERROR AL OBTENER EL ARCHIVO DE AUDIO");
        final AlertDialog noeventosB = noeventos.create();
        noeventosB.setCanceledOnTouchOutside(true);
        noeventosB.show();

        final Handler handler  = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (noeventosB.isShowing()) {
                    noeventosB.dismiss();
                }
            }
        };
        noeventosB.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                handler.removeCallbacks(runnable);
            }
        });
        handler.postDelayed(runnable, 3000);
    }
    public void alertaPLAY2(){
        AlertDialog.Builder noeventos = new AlertDialog.Builder(this);
        noeventos.setTitle("ERROR!");
        noeventos.setMessage("ERROR AL PROCESAR ARCHIVO DE AUDIO");
        final AlertDialog noeventosB = noeventos.create();
        noeventosB.setCanceledOnTouchOutside(true);
        noeventosB.show();

        final Handler handler  = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (noeventosB.isShowing()) {
                    noeventosB.dismiss();
                }
            }
        };
        noeventosB.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                handler.removeCallbacks(runnable);
            }
        });
        handler.postDelayed(runnable, 3000);
    }
}

