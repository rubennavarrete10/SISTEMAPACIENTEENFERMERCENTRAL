package SPEC.PKG;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
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

public class MainActivity extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject>, login.Datoslogin, loginservicio.Datoslogin, logout.Datoslogout {

    ////////////////////////////////////////////////////////////variables/////////////////
    int i,a=0,b1=0,b2=0,b3=0,b4=0,c=0;
    int layout=0;
    String ip = "192.168.0.16";
    String url1;
    String ESTATUS = "VACANTE",FECHAI= "N/A", FECHA="N/A", HORA="N/A", TURNO="N/A", HABITACION="N/A",TIPODELLAMADO="N/A", FOLIODISPOSITIVO="N/A",ENFERMERA="N/A",TR="N/A",ESTACION="N/A",SECCION="N/A",AUDIO="N/A",PACIENTE= "N/A" ,MEDICO="N/A",PAGO="N/A";
    String CONTRASENA="N/A", NOMBREENFERMERA="N/A",CONTRASENAINSERTADA="N/A",SECCIONINS="N/A",SECCIONOUT="N/A",USUARIOINS="N/A",USUARIOUT="N/A",AVISO="";
    String SEC1S="SIN ENFERMERA", SEC2S="SIN ENFERMERA", SEC3S="SIN ENFERMERA", SEC4S="SIN ENFERMERA";
    String COLORVAC = "#555454";
    String COLORASIS="#FFC107";
    String COLOREMER="#B71C1C";
    String COLOROCUPADO="#99CC01";
    ////////////////////////////////////////////////////////////botones layout/////////////////
    private TextView SEC1;
    private TextView SEC1H1,SEC1H2,SEC1H3,SEC1H4,SEC1H5,SEC1H6;
    private TextView SEC1H101,SEC1H102,SEC1H103,SEC1H104,SEC1H105,SEC1H106;
    ImageButton ibsec1h1,ib1sec1h1,ib2sec1h1;
    ImageButton ibsec1h2,ib1sec1h2,ib2sec1h2;
    ImageButton ibsec1h3,ib1sec1h3,ib2sec1h3;
    ImageButton ibsec1h4,ib1sec1h4,ib2sec1h4;
    ImageButton ibsec1h5,ib1sec1h5,ib2sec1h5;
    ImageButton ibsec1h6,ib1sec1h6,ib2sec1h6;
    private TextView SEC2;
    private TextView SEC2H1,SEC2H2,SEC2H3,SEC2H4,SEC2H5,SEC2H6;
    private TextView SEC2H101,SEC2H102,SEC2H103,SEC2H104,SEC2H105,SEC2H106;
    ImageButton ibsec2h1,ib1sec2h1,ib2sec2h1;
    ImageButton ibsec2h2,ib1sec2h2,ib2sec2h2;
    ImageButton ibsec2h3,ib1sec2h3,ib2sec2h3;
    ImageButton ibsec2h4,ib1sec2h4,ib2sec2h4;
    ImageButton ibsec2h5,ib1sec2h5,ib2sec2h5;
    ImageButton ibsec2h6,ib1sec2h6,ib2sec2h6;
    private TextView SEC3,SEC4;
    private TextView SEC3H1,SEC3H2,SEC3H3,SEC3H4,SEC3H5,SEC3H6;
    private TextView SEC4H1,SEC4H2,SEC4H3,SEC4H4,SEC4H5,SEC4H6;
    ////////////////////////////////////////////////////////////arreglos guardar datos/////////////////
    String []sesiones={"N/A","N/A","N/A","N/A"};
    String [][]enfermeras= new String[24][10];
    ////////////////////////////////////////////////////////////fechas/////////////////
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    Date date = new Date();
    String D0, D1,HD,H1;
    ////////////////////////////////////////////////////////////json webservices/////////////////
    RequestQueue MyRequestQueue;
    RequestQueue request1;
    Usuarios consultaUsuario;
    JSONArray consulta;
    JsonObjectRequest jsonrequest;
    ////////////////////////////////////////////////////////////reproducir audio/////////////////////////////////////////////////
    private MediaPlayer player;
    private String outputFile =  Environment.getExternalStorageDirectory().getAbsolutePath() + "/Fonts/" + FOLIODISPOSITIVO + "Grabacion.mp3";
    ////////////////////////////////////////////////////////DELAY////////////////////////////////
    final Handler handler = new Handler();
    //////////////////////////////////////////////////////////ciclo////////////////////////////////
    Timer tiempo=new Timer();
    Bundle infobundle = new Bundle();

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.inicializador);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                layout=14;
                setContentView(R.layout.unoacuatro);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1000);
        }

        ImageButton SERVICIO = (ImageButton)findViewById(R.id.ser);
        final ImageButton login = (ImageButton) findViewById(R.id.ini);
        final ImageButton logOut =(ImageButton) findViewById(R.id.out);

        referenciasobjetos();
///////////////////////////////////////////////////////////////////////////logica principal//////////////////////////////////////////////////////////

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    login();
                }
            });
            logOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    logout();
                }
            });
            SERVICIO.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loginSer();
                }
            });

            TimerTask ciclo = new TimerTask() {
                @Override
                public void run() {
                    SEC1S=SEC1.getText().toString();
                    //SEC2S=SEC2.getText().toString();
                    //SEC3S=SEC3.getText().toString();
                    //SEC4S=SEC4.getText().toString();
                    consulEvento();
                }
            };tiempo.schedule(ciclo,500,1000);
            }
        }, 2000);
     }
    ///////////////////////////////////////////////////////////////////////////fin logica principal//////////////////////////////////////////////////////////
    private void referenciasobjetos() {
        request1 = Volley.newRequestQueue(this);
        MyRequestQueue = Volley.newRequestQueue(this);

        if(layout==0) {
            SEC1 = (TextView) findViewById(R.id.SEC1SINENFE);
            SEC2 = (TextView) findViewById(R.id.SEC2SINENFE);
            SEC3 = (TextView) findViewById(R.id.SEC3SINENFE);
            SEC4 = (TextView) findViewById(R.id.SEC4SINENFE);

            SEC1H1 = (TextView) findViewById(R.id.sec1h1);
            SEC1H2 = (TextView) findViewById(R.id.sec1h2);
            SEC1H3 = (TextView) findViewById(R.id.sec1h3);
            SEC1H4 = (TextView) findViewById(R.id.sec1h4);
            SEC1H5 = (TextView) findViewById(R.id.sec1h5);
            SEC1H6 = (TextView) findViewById(R.id.sec1h6);

            SEC2H1 = (TextView) findViewById(R.id.sec2h1);
            SEC2H2 = (TextView) findViewById(R.id.sec2h2);
            SEC2H3 = (TextView) findViewById(R.id.sec2h3);
            SEC2H4 = (TextView) findViewById(R.id.sec2h4);
            SEC2H5 = (TextView) findViewById(R.id.sec2h5);
            SEC2H6 = (TextView) findViewById(R.id.sec2h6);

            SEC3H1 = (TextView) findViewById(R.id.sec3h1);
            SEC3H2 = (TextView) findViewById(R.id.sec3h2);
            SEC3H3 = (TextView) findViewById(R.id.sec3h3);
            SEC3H4 = (TextView) findViewById(R.id.sec3h4);
            SEC3H5 = (TextView) findViewById(R.id.sec3h5);
            SEC3H6 = (TextView) findViewById(R.id.sec3h6);

            SEC4H1 = (TextView) findViewById(R.id.sec4h1);
            SEC4H2 = (TextView) findViewById(R.id.sec4h2);
            SEC4H3 = (TextView) findViewById(R.id.sec4h3);
            SEC4H4 = (TextView) findViewById(R.id.sec4h4);
            SEC4H5 = (TextView) findViewById(R.id.sec4h5);
            SEC4H6 = (TextView) findViewById(R.id.sec4h6);

            SEC1H1.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC1H2.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC1H3.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC1H4.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC1H5.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC1H6.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");

            SEC2H1.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC2H2.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC2H3.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC2H4.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC2H5.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC2H6.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");

            SEC3H1.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC3H2.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC3H3.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC3H4.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC3H5.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC3H6.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");

            SEC4H1.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC4H2.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC4H3.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC4H4.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC4H5.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC4H6.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");

            SEC1H1.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC1H2.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC1H3.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC1H4.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC1H5.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC1H6.setBackgroundColor(Color.parseColor("#8BC34A"));

            SEC2H1.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC2H2.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC2H3.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC2H4.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC2H5.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC2H6.setBackgroundColor(Color.parseColor("#8BC34A"));

            SEC3H1.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC3H2.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC3H3.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC3H4.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC3H5.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC3H6.setBackgroundColor(Color.parseColor("#8BC34A"));

            SEC4H1.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC4H2.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC4H3.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC4H4.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC4H5.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC4H6.setBackgroundColor(Color.parseColor("#8BC34A"));

            SEC1H1.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (SEC1S.equals("SIN ENFERMERA") == false) {
                        //play();
                        Toast.makeText(getApplicationContext(), "1-1", Toast.LENGTH_SHORT).show();
                        HABITACION = enfermeras[0][0];
                        FECHA = enfermeras[0][1];
                        HORA = enfermeras[0][2];
                        PACIENTE = enfermeras[0][3];
                        TIPODELLAMADO = enfermeras[0][4];
                        NOMBREENFERMERA = SEC1.getText().toString();
                        insetenfermera();
                    }
                }
            });
            SEC1H2.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (SEC1S.equals("SIN ENFERMERA") == false) {
                        //play();
                        Toast.makeText(getApplicationContext(), "1-2", Toast.LENGTH_SHORT).show();
                        HABITACION = enfermeras[1][0];
                        FECHA = enfermeras[1][1];
                        HORA = enfermeras[1][2];
                        PACIENTE = enfermeras[1][3];
                        TIPODELLAMADO = enfermeras[1][4];
                        NOMBREENFERMERA = SEC1.getText().toString();
                        insetenfermera();
                    }
                }
            });
            SEC1H3.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (SEC1S.equals("SIN ENFERMERA") == false) {
                        //play();
                        Toast.makeText(getApplicationContext(), "1-3", Toast.LENGTH_SHORT).show();
                        HABITACION = enfermeras[2][0];
                        FECHA = enfermeras[2][1];
                        HORA = enfermeras[2][2];
                        PACIENTE = enfermeras[2][3];
                        TIPODELLAMADO = enfermeras[2][4];
                        NOMBREENFERMERA = SEC1.getText().toString();
                        insetenfermera();
                    }
                }
            });
            SEC1H4.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (SEC1S.equals("SIN ENFERMERA") == false) {
                        //play();
                        Toast.makeText(getApplicationContext(), "1-4", Toast.LENGTH_SHORT).show();
                        HABITACION = enfermeras[3][0];
                        FECHA = enfermeras[3][1];
                        HORA = enfermeras[3][2];
                        PACIENTE = enfermeras[3][3];
                        TIPODELLAMADO = enfermeras[3][4];
                        NOMBREENFERMERA = SEC1.getText().toString();
                        insetenfermera();
                    }
                }
            });
            SEC1H5.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (SEC1S.equals("SIN ENFERMERA") == false) {
                        //play();
                        Toast.makeText(getApplicationContext(), "" + enfermeras[4][4] + " ASIGANDA", Toast.LENGTH_SHORT).show();
                        HABITACION = enfermeras[4][0];
                        FECHA = enfermeras[4][1];
                        HORA = enfermeras[4][2];
                        PACIENTE = enfermeras[4][3];
                        TIPODELLAMADO = enfermeras[4][4];
                        NOMBREENFERMERA = SEC1.getText().toString();
                        insetenfermera();
                    }
                }
            });
            SEC1H6.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (SEC1S.equals("SIN ENFERMERA") == false) {
                        //play();
                        Toast.makeText(getApplicationContext(), "1-6", Toast.LENGTH_SHORT).show();
                        HABITACION = enfermeras[5][0];
                        FECHA = enfermeras[5][1];
                        HORA = enfermeras[5][2];
                        PACIENTE = enfermeras[5][3];
                        TIPODELLAMADO = enfermeras[5][4];
                        NOMBREENFERMERA = SEC1.getText().toString();
                        insetenfermera();
                    }
                }
            });
            SEC2H1.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (SEC2S.equals("SIN ENFERMERA") == false) {
                        //play();
                        Toast.makeText(getApplicationContext(), "2-1", Toast.LENGTH_SHORT).show();
                        HABITACION = enfermeras[6][0];
                        FECHA = enfermeras[6][1];
                        HORA = enfermeras[6][2];
                        PACIENTE = enfermeras[6][3];
                        TIPODELLAMADO = enfermeras[6][4];
                        NOMBREENFERMERA = SEC2.getText().toString();
                        insetenfermera();
                    }
                }
            });
            SEC2H2.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (SEC2S.equals("SIN ENFERMERA") == false) {
                        //play();
                        Toast.makeText(getApplicationContext(), "2-2", Toast.LENGTH_SHORT).show();
                        HABITACION = enfermeras[7][0];
                        FECHA = enfermeras[7][1];
                        HORA = enfermeras[7][2];
                        PACIENTE = enfermeras[7][3];
                        TIPODELLAMADO = enfermeras[7][4];
                        NOMBREENFERMERA = SEC2.getText().toString();
                        insetenfermera();
                    }
                }
            });
            SEC2H3.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (SEC2S.equals("SIN ENFERMERA") == false) {
                        //play();
                        Toast.makeText(getApplicationContext(), "2-3", Toast.LENGTH_SHORT).show();
                        HABITACION = enfermeras[8][0];
                        FECHA = enfermeras[8][1];
                        HORA = enfermeras[8][2];
                        PACIENTE = enfermeras[8][3];
                        TIPODELLAMADO = enfermeras[8][4];
                        NOMBREENFERMERA = SEC2.getText().toString();
                        insetenfermera();
                    }
                }
            });
            SEC2H4.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (SEC2S.equals("SIN ENFERMERA") == false) {
                        //play();
                        HABITACION = enfermeras[9][0];
                        FECHA = enfermeras[9][1];
                        HORA = enfermeras[9][2];
                        PACIENTE = enfermeras[9][3];
                        TIPODELLAMADO = enfermeras[9][4];
                        NOMBREENFERMERA = SEC2.getText().toString();
                        insetenfermera();
                        Toast.makeText(getApplicationContext(), "2-4" + HABITACION + FECHA + HORA + PACIENTE + TIPODELLAMADO, Toast.LENGTH_SHORT).show();
                    }
                }
            });
            SEC2H5.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (SEC2S.equals("SIN ENFERMERA") == false) {
                        //play();
                        Toast.makeText(getApplicationContext(), "2-5", Toast.LENGTH_SHORT).show();
                        HABITACION = enfermeras[10][0];
                        FECHA = enfermeras[10][1];
                        HORA = enfermeras[10][2];
                        PACIENTE = enfermeras[10][3];
                        TIPODELLAMADO = enfermeras[10][4];
                        NOMBREENFERMERA = SEC2.getText().toString();
                        insetenfermera();
                    }
                }
            });
            SEC2H6.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (SEC2S.equals("SIN ENFERMERA") == false) {
                        //play();
                        Toast.makeText(getApplicationContext(), "2-6", Toast.LENGTH_SHORT).show();
                        HABITACION = enfermeras[11][0];
                        FECHA = enfermeras[11][1];
                        HORA = enfermeras[11][2];
                        PACIENTE = enfermeras[11][3];
                        TIPODELLAMADO = enfermeras[11][4];
                        NOMBREENFERMERA = SEC2.getText().toString();
                        insetenfermera();
                    }
                }
            });
            SEC3H1.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (SEC3S.equals("SIN ENFERMERA") == false) {
                        //play();
                        Toast.makeText(getApplicationContext(), "3-1", Toast.LENGTH_SHORT).show();
                        HABITACION = enfermeras[12][0];
                        FECHA = enfermeras[12][1];
                        HORA = enfermeras[12][2];
                        PACIENTE = enfermeras[12][3];
                        TIPODELLAMADO = enfermeras[12][4];
                        NOMBREENFERMERA = SEC3.getText().toString();
                        insetenfermera();
                    }
                }
            });
            SEC3H2.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (SEC3S.equals("SIN ENFERMERA") == false) {
                        //play();
                        Toast.makeText(getApplicationContext(), "3-2", Toast.LENGTH_SHORT).show();
                        HABITACION = enfermeras[13][0];
                        FECHA = enfermeras[13][1];
                        HORA = enfermeras[13][2];
                        PACIENTE = enfermeras[13][3];
                        TIPODELLAMADO = enfermeras[13][4];
                        NOMBREENFERMERA = SEC3.getText().toString();
                        insetenfermera();
                    }
                }
            });
            SEC3H3.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (SEC3S.equals("SIN ENFERMERA") == false) {
                        //play();
                        Toast.makeText(getApplicationContext(), "3-3", Toast.LENGTH_SHORT).show();
                        HABITACION = enfermeras[14][0];
                        FECHA = enfermeras[14][1];
                        HORA = enfermeras[14][2];
                        PACIENTE = enfermeras[14][3];
                        TIPODELLAMADO = enfermeras[14][4];
                        NOMBREENFERMERA = SEC3.getText().toString();
                        insetenfermera();
                    }
                }
            });
            SEC3H4.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (SEC3S.equals("SIN ENFERMERA") == false) {
                        //play();
                        Toast.makeText(getApplicationContext(), "3-4", Toast.LENGTH_SHORT).show();
                        HABITACION = enfermeras[15][0];
                        FECHA = enfermeras[15][1];
                        HORA = enfermeras[15][2];
                        PACIENTE = enfermeras[15][3];
                        TIPODELLAMADO = enfermeras[15][4];
                        NOMBREENFERMERA = SEC3.getText().toString();
                        insetenfermera();
                    }
                }
            });
            SEC3H5.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (SEC3S.equals("SIN ENFERMERA") == false) {
                        //play();
                        Toast.makeText(getApplicationContext(), "3-5", Toast.LENGTH_SHORT).show();
                        HABITACION = enfermeras[16][0];
                        FECHA = enfermeras[16][1];
                        HORA = enfermeras[16][2];
                        PACIENTE = enfermeras[16][3];
                        TIPODELLAMADO = enfermeras[16][4];
                        NOMBREENFERMERA = SEC3.getText().toString();
                        insetenfermera();
                    }
                }
            });
            SEC3H6.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (SEC3S.equals("SIN ENFERMERA") == false) {
                        //play();
                        Toast.makeText(getApplicationContext(), "3-6", Toast.LENGTH_SHORT).show();
                        HABITACION = enfermeras[17][0];
                        FECHA = enfermeras[17][1];
                        HORA = enfermeras[17][2];
                        PACIENTE = enfermeras[17][3];
                        TIPODELLAMADO = enfermeras[17][4];
                        NOMBREENFERMERA = SEC3.getText().toString();
                        insetenfermera();
                    }
                }
            });
            SEC4H1.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (SEC4S.equals("SIN ENFERMERA") == false) {
                        //play();
                        Toast.makeText(getApplicationContext(), "4-1", Toast.LENGTH_SHORT).show();
                        HABITACION = enfermeras[18][0];
                        FECHA = enfermeras[18][1];
                        HORA = enfermeras[18][2];
                        PACIENTE = enfermeras[18][3];
                        TIPODELLAMADO = enfermeras[18][4];
                        NOMBREENFERMERA = SEC4.getText().toString();
                        insetenfermera();
                    }
                }
            });
            SEC4H2.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (SEC4S.equals("SIN ENFERMERA") == false) {
                        //play();
                        Toast.makeText(getApplicationContext(), "4-2", Toast.LENGTH_SHORT).show();
                        HABITACION = enfermeras[19][0];
                        FECHA = enfermeras[19][1];
                        HORA = enfermeras[19][2];
                        PACIENTE = enfermeras[19][3];
                        TIPODELLAMADO = enfermeras[19][4];
                        NOMBREENFERMERA = SEC4.getText().toString();
                        insetenfermera();
                    }
                }
            });
            SEC4H3.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (SEC4S.equals("SIN ENFERMERA") == false) {
                        //play();
                        Toast.makeText(getApplicationContext(), "4-3", Toast.LENGTH_SHORT).show();
                        HABITACION = enfermeras[20][0];
                        FECHA = enfermeras[20][1];
                        HORA = enfermeras[20][2];
                        PACIENTE = enfermeras[20][3];
                        TIPODELLAMADO = enfermeras[20][4];
                        NOMBREENFERMERA = SEC4.getText().toString();
                        insetenfermera();
                    }
                }
            });
            SEC4H4.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (SEC4S.equals("SIN ENFERMERA") == false) {
                        //play();
                        Toast.makeText(getApplicationContext(), "4-4", Toast.LENGTH_SHORT).show();
                        HABITACION = enfermeras[21][0];
                        FECHA = enfermeras[21][1];
                        HORA = enfermeras[21][2];
                        PACIENTE = enfermeras[21][3];
                        TIPODELLAMADO = enfermeras[21][4];
                        NOMBREENFERMERA = SEC4.getText().toString();
                        insetenfermera();
                    }
                }
            });
            SEC4H5.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (SEC4S.equals("SIN ENFERMERA") == false) {
                        //play();
                        Toast.makeText(getApplicationContext(), "4-5", Toast.LENGTH_SHORT).show();
                        HABITACION = enfermeras[22][0];
                        FECHA = enfermeras[22][1];
                        HORA = enfermeras[22][2];
                        PACIENTE = enfermeras[22][3];
                        TIPODELLAMADO = enfermeras[22][4];
                        NOMBREENFERMERA = SEC4.getText().toString();
                        insetenfermera();
                    }
                }
            });
            SEC4H6.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (SEC4S.equals("SIN ENFERMERA") == false) {
                        //play();
                        Toast.makeText(getApplicationContext(), "4-6", Toast.LENGTH_SHORT).show();
                        HABITACION = enfermeras[23][0];
                        FECHA = enfermeras[23][1];
                        HORA = enfermeras[23][2];
                        PACIENTE = enfermeras[23][3];
                        TIPODELLAMADO = enfermeras[23][4];
                        NOMBREENFERMERA = SEC4.getText().toString();
                        insetenfermera();
                    }
                }
            });
        }
        if(layout==14) {
            SEC1 = (TextView) findViewById(R.id.SEC1SINENFE);

            SEC1H1 = (TextView) findViewById(R.id.sec1h1);
            SEC1H101 = (TextView) findViewById(R.id.sec1h101);
            ibsec1h1 = (ImageButton) findViewById(R.id.ibsec1h1);
            ib1sec1h1 = (ImageButton) findViewById(R.id.ib1sec1h1);
            ib2sec1h1 = (ImageButton) findViewById(R.id.ib2sec1h1);

            SEC1H2 = (TextView) findViewById(R.id.sec1h2);
            SEC1H102 = (TextView) findViewById(R.id.sec1h102);
            ibsec1h2 = (ImageButton) findViewById(R.id.ibsec1h2);
            ib1sec1h2 = (ImageButton) findViewById(R.id.ib1sec1h2);
            ib2sec1h2 = (ImageButton) findViewById(R.id.ib2sec1h2);

            SEC1H3 = (TextView) findViewById(R.id.sec1h3);
            SEC1H103 = (TextView) findViewById(R.id.sec1h103);
            ibsec1h3 = (ImageButton) findViewById(R.id.ibsec1h3);
            ib1sec1h3 = (ImageButton) findViewById(R.id.ib1sec1h3);
            ib2sec1h3 = (ImageButton) findViewById(R.id.ib2sec1h3);

            SEC1H4 = (TextView) findViewById(R.id.sec1h4);
            SEC1H104 = (TextView) findViewById(R.id.sec1h104);
            ibsec1h4 = (ImageButton) findViewById(R.id.ibsec1h4);
            ib1sec1h4 = (ImageButton) findViewById(R.id.ib1sec1h4);
            ib2sec1h4 = (ImageButton) findViewById(R.id.ib2sec1h4);

            SEC1H1.setBackgroundColor(Color.parseColor(COLORVAC));
            SEC1H2.setBackgroundColor(Color.parseColor(COLORVAC));
            SEC1H3.setBackgroundColor(Color.parseColor(COLORVAC));
            SEC1H4.setBackgroundColor(Color.parseColor(COLORVAC));

            if(layout==14) {
                SEC1H1.setText("");
                SEC1H101.setText("N/A");
                ibsec1h1.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "bitacora", Toast.LENGTH_SHORT).show();
                    }
                });
                ib1sec1h1.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "peticiones", Toast.LENGTH_SHORT).show();
                    }
                });
                ib2sec1h1.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        infobundle.putString("hb",enfermeras[0][0]);
                        infobundle.putString("pac",enfermeras[0][3]);
                        infobundle.putString("fei",enfermeras[0][6]);
                        infobundle.putString("med",enfermeras[0][4]);
                        infobundle.putString("pag",enfermeras[0][5]);
                        info();
                    }
                });
                SEC1H2.setText("");
                SEC1H102.setText("N/A");
                ibsec1h2.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "bitacora2", Toast.LENGTH_SHORT).show();
                    }
                });
                ib1sec1h2.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "peticiones2", Toast.LENGTH_SHORT).show();
                    }
                });
                ib2sec1h2.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        infobundle.putString("hb",enfermeras[1][0]);
                        infobundle.putString("pac",enfermeras[1][3]);
                        infobundle.putString("fei",enfermeras[1][6]);
                        infobundle.putString("med",enfermeras[1][4]);
                        infobundle.putString("pag",enfermeras[1][5]);
                        info();
                    }
                });
                SEC1H3.setText("");
                SEC1H103.setText("N/A");
                ibsec1h3.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "bitacora3", Toast.LENGTH_SHORT).show();
                    }
                });
                ib1sec1h3.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "peticiones3", Toast.LENGTH_SHORT).show();
                    }
                });
                ib2sec1h3.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        infobundle.putString("hb",enfermeras[2][0]);
                        infobundle.putString("pac",enfermeras[2][3]);
                        infobundle.putString("fei",enfermeras[2][6]);
                        infobundle.putString("med",enfermeras[2][4]);
                        infobundle.putString("pag",enfermeras[2][5]);
                        info();
                    }
                });
                SEC1H4.setText("");
                SEC1H104.setText("N/A");
                ibsec1h4.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "bitacora4", Toast.LENGTH_SHORT).show();
                    }
                });
                ib1sec1h4.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "peticiones4", Toast.LENGTH_SHORT).show();
                    }
                });
                ib2sec1h4.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        infobundle.putString("hb",enfermeras[3][0]);
                        infobundle.putString("pac",enfermeras[3][3]);
                        infobundle.putString("fei",enfermeras[3][6]);
                        infobundle.putString("med",enfermeras[3][4]);
                        infobundle.putString("pag",enfermeras[3][5]);
                        info();
                    }
                });
            }
        }
        if(layout==15) {
            SEC1 = (TextView) findViewById(R.id.SEC1SINENFE);

            SEC1H1 = (TextView) findViewById(R.id.sec1h1);
            SEC1H2 = (TextView) findViewById(R.id.sec1h2);
            SEC1H3 = (TextView) findViewById(R.id.sec1h3);
            SEC1H4 = (TextView) findViewById(R.id.sec1h4);
            SEC1H5 = (TextView) findViewById(R.id.sec1h5);

            SEC1H1.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC1H2.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC1H3.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC1H4.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC1H5.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");

            SEC1H1.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC1H2.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC1H3.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC1H4.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC1H5.setBackgroundColor(Color.parseColor("#8BC34A"));

            if(layout==15) {
                SEC1H1.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (SEC1S.equals("SIN ENFERMERA") == false) {
                            //play();
                            Toast.makeText(getApplicationContext(), "1-1", Toast.LENGTH_SHORT).show();
                            HABITACION = enfermeras[0][0];
                            FECHA = enfermeras[0][1];
                            HORA = enfermeras[0][2];
                            PACIENTE = enfermeras[0][3];
                            TIPODELLAMADO = enfermeras[0][4];
                            NOMBREENFERMERA = SEC1.getText().toString();
                            insetenfermera();
                        }
                    }
                });
                SEC1H2.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (SEC1S.equals("SIN ENFERMERA") == false) {
                            //play();
                            Toast.makeText(getApplicationContext(), "1-2", Toast.LENGTH_SHORT).show();
                            HABITACION = enfermeras[1][0];
                            FECHA = enfermeras[1][1];
                            HORA = enfermeras[1][2];
                            PACIENTE = enfermeras[1][3];
                            TIPODELLAMADO = enfermeras[1][4];
                            NOMBREENFERMERA = SEC1.getText().toString();
                            insetenfermera();
                        }
                    }
                });
                SEC1H3.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (SEC1S.equals("SIN ENFERMERA") == false) {
                            //play();
                            Toast.makeText(getApplicationContext(), "1-3", Toast.LENGTH_SHORT).show();
                            HABITACION = enfermeras[2][0];
                            FECHA = enfermeras[2][1];
                            HORA = enfermeras[2][2];
                            PACIENTE = enfermeras[2][3];
                            TIPODELLAMADO = enfermeras[2][4];
                            NOMBREENFERMERA = SEC1.getText().toString();
                            insetenfermera();
                        }
                    }
                });
                SEC1H4.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (SEC1S.equals("SIN ENFERMERA") == false) {
                            //play();
                            Toast.makeText(getApplicationContext(), "1-4", Toast.LENGTH_SHORT).show();
                            HABITACION = enfermeras[3][0];
                            FECHA = enfermeras[3][1];
                            HORA = enfermeras[3][2];
                            PACIENTE = enfermeras[3][3];
                            TIPODELLAMADO = enfermeras[3][4];
                            NOMBREENFERMERA = SEC1.getText().toString();
                            insetenfermera();
                        }
                    }
                });
                SEC1H5.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (SEC1S.equals("SIN ENFERMERA") == false) {
                            //play();
                            Toast.makeText(getApplicationContext(), "" + enfermeras[4][4] + " ASIGANDA", Toast.LENGTH_SHORT).show();
                            HABITACION = enfermeras[4][0];
                            FECHA = enfermeras[4][1];
                            HORA = enfermeras[4][2];
                            PACIENTE = enfermeras[4][3];
                            TIPODELLAMADO = enfermeras[4][4];
                            NOMBREENFERMERA = SEC1.getText().toString();
                            insetenfermera();
                        }
                    }
                });
            }
        }
        if(layout==16) {
            SEC1 = (TextView) findViewById(R.id.SEC1SINENFE);

            SEC1H1 = (TextView) findViewById(R.id.sec1h1);
            SEC1H2 = (TextView) findViewById(R.id.sec1h2);
            SEC1H3 = (TextView) findViewById(R.id.sec1h3);
            SEC1H4 = (TextView) findViewById(R.id.sec1h4);
            SEC1H5 = (TextView) findViewById(R.id.sec1h5);
            SEC1H6 = (TextView) findViewById(R.id.sec1h6);

            SEC1H1.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC1H2.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC1H3.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC1H4.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC1H5.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC1H6.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");


            SEC1H1.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC1H2.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC1H3.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC1H4.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC1H5.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC1H6.setBackgroundColor(Color.parseColor("#8BC34A"));

            if(layout==16) {
                SEC1H1.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (SEC1S.equals("SIN ENFERMERA") == false) {
                            //play();
                            Toast.makeText(getApplicationContext(), "1-1", Toast.LENGTH_SHORT).show();
                            HABITACION = enfermeras[0][0];
                            FECHA = enfermeras[0][1];
                            HORA = enfermeras[0][2];
                            PACIENTE = enfermeras[0][3];
                            TIPODELLAMADO = enfermeras[0][4];
                            NOMBREENFERMERA = SEC1.getText().toString();
                            insetenfermera();
                        }
                    }
                });
                SEC1H2.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (SEC1S.equals("SIN ENFERMERA") == false) {
                            //play();
                            Toast.makeText(getApplicationContext(), "1-2", Toast.LENGTH_SHORT).show();
                            HABITACION = enfermeras[1][0];
                            FECHA = enfermeras[1][1];
                            HORA = enfermeras[1][2];
                            PACIENTE = enfermeras[1][3];
                            TIPODELLAMADO = enfermeras[1][4];
                            NOMBREENFERMERA = SEC1.getText().toString();
                            insetenfermera();
                        }
                    }
                });
                SEC1H3.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (SEC1S.equals("SIN ENFERMERA") == false) {
                            //play();
                            Toast.makeText(getApplicationContext(), "1-3", Toast.LENGTH_SHORT).show();
                            HABITACION = enfermeras[2][0];
                            FECHA = enfermeras[2][1];
                            HORA = enfermeras[2][2];
                            PACIENTE = enfermeras[2][3];
                            TIPODELLAMADO = enfermeras[2][4];
                            NOMBREENFERMERA = SEC1.getText().toString();
                            insetenfermera();
                        }
                    }
                });
                SEC1H4.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (SEC1S.equals("SIN ENFERMERA") == false) {
                            //play();
                            Toast.makeText(getApplicationContext(), "1-4", Toast.LENGTH_SHORT).show();
                            HABITACION = enfermeras[3][0];
                            FECHA = enfermeras[3][1];
                            HORA = enfermeras[3][2];
                            PACIENTE = enfermeras[3][3];
                            TIPODELLAMADO = enfermeras[3][4];
                            NOMBREENFERMERA = SEC1.getText().toString();
                            insetenfermera();
                        }
                    }
                });
                SEC1H5.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (SEC1S.equals("SIN ENFERMERA") == false) {
                            //play();
                            Toast.makeText(getApplicationContext(), "" + enfermeras[4][4] + " ASIGANDA", Toast.LENGTH_SHORT).show();
                            HABITACION = enfermeras[4][0];
                            FECHA = enfermeras[4][1];
                            HORA = enfermeras[4][2];
                            PACIENTE = enfermeras[4][3];
                            TIPODELLAMADO = enfermeras[4][4];
                            NOMBREENFERMERA = SEC1.getText().toString();
                            insetenfermera();
                        }
                    }
                });
                SEC1H6.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (SEC1S.equals("SIN ENFERMERA") == false) {
                            //play();
                            Toast.makeText(getApplicationContext(), "1-6", Toast.LENGTH_SHORT).show();
                            HABITACION = enfermeras[5][0];
                            FECHA = enfermeras[5][1];
                            HORA = enfermeras[5][2];
                            PACIENTE = enfermeras[5][3];
                            TIPODELLAMADO = enfermeras[5][4];
                            NOMBREENFERMERA = SEC1.getText().toString();
                            insetenfermera();
                        }
                    }
                });
            }
        }
        if(layout==24) {
            SEC1 = (TextView) findViewById(R.id.SEC1SINENFE);
            SEC2 = (TextView) findViewById(R.id.SEC2SINENFE);

            SEC1H1 = (TextView) findViewById(R.id.sec1h1);
            SEC1H2 = (TextView) findViewById(R.id.sec1h2);
            SEC1H3 = (TextView) findViewById(R.id.sec1h3);
            SEC1H4 = (TextView) findViewById(R.id.sec1h4);

            SEC2H1 = (TextView) findViewById(R.id.sec2h1);
            SEC2H2 = (TextView) findViewById(R.id.sec2h2);
            SEC2H3 = (TextView) findViewById(R.id.sec2h3);
            SEC2H4 = (TextView) findViewById(R.id.sec2h4);

            SEC1H1.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC1H2.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC1H3.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC1H4.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");

            SEC2H1.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC2H2.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC2H3.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC2H4.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");

            SEC1H1.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC1H2.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC1H3.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC1H4.setBackgroundColor(Color.parseColor("#8BC34A"));

            SEC2H1.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC2H2.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC2H3.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC2H4.setBackgroundColor(Color.parseColor("#8BC34A"));

            if(layout==24) {
                SEC1H1.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (SEC1S.equals("SIN ENFERMERA") == false) {
                            //play();
                            Toast.makeText(getApplicationContext(), "1-1", Toast.LENGTH_SHORT).show();
                            HABITACION = enfermeras[0][0];
                            FECHA = enfermeras[0][1];
                            HORA = enfermeras[0][2];
                            PACIENTE = enfermeras[0][3];
                            TIPODELLAMADO = enfermeras[0][4];
                            NOMBREENFERMERA = SEC1.getText().toString();
                            insetenfermera();
                        }
                    }
                });
                SEC1H2.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (SEC1S.equals("SIN ENFERMERA") == false) {
                            //play();
                            Toast.makeText(getApplicationContext(), "1-2", Toast.LENGTH_SHORT).show();
                            HABITACION = enfermeras[1][0];
                            FECHA = enfermeras[1][1];
                            HORA = enfermeras[1][2];
                            PACIENTE = enfermeras[1][3];
                            TIPODELLAMADO = enfermeras[1][4];
                            NOMBREENFERMERA = SEC1.getText().toString();
                            insetenfermera();
                        }
                    }
                });
                SEC1H3.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (SEC1S.equals("SIN ENFERMERA") == false) {
                            //play();
                            Toast.makeText(getApplicationContext(), "1-3", Toast.LENGTH_SHORT).show();
                            HABITACION = enfermeras[2][0];
                            FECHA = enfermeras[2][1];
                            HORA = enfermeras[2][2];
                            PACIENTE = enfermeras[2][3];
                            TIPODELLAMADO = enfermeras[2][4];
                            NOMBREENFERMERA = SEC1.getText().toString();
                            insetenfermera();
                        }
                    }
                });
                SEC1H4.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (SEC1S.equals("SIN ENFERMERA") == false) {
                            //play();
                            Toast.makeText(getApplicationContext(), "1-4", Toast.LENGTH_SHORT).show();
                            HABITACION = enfermeras[3][0];
                            FECHA = enfermeras[3][1];
                            HORA = enfermeras[3][2];
                            PACIENTE = enfermeras[3][3];
                            TIPODELLAMADO = enfermeras[3][4];
                            NOMBREENFERMERA = SEC1.getText().toString();
                            insetenfermera();
                        }
                    }
                });
                SEC2H1.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (SEC2S.equals("SIN ENFERMERA") == false) {
                            //play();
                            Toast.makeText(getApplicationContext(), "2-1", Toast.LENGTH_SHORT).show();
                            HABITACION = enfermeras[6][0];
                            FECHA = enfermeras[6][1];
                            HORA = enfermeras[6][2];
                            PACIENTE = enfermeras[6][3];
                            TIPODELLAMADO = enfermeras[6][4];
                            NOMBREENFERMERA = SEC2.getText().toString();
                            insetenfermera();
                        }
                    }
                });
                SEC2H2.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (SEC2S.equals("SIN ENFERMERA") == false) {
                            //play();
                            Toast.makeText(getApplicationContext(), "2-2", Toast.LENGTH_SHORT).show();
                            HABITACION = enfermeras[7][0];
                            FECHA = enfermeras[7][1];
                            HORA = enfermeras[7][2];
                            PACIENTE = enfermeras[7][3];
                            TIPODELLAMADO = enfermeras[7][4];
                            NOMBREENFERMERA = SEC2.getText().toString();
                            insetenfermera();
                        }
                    }
                });
                SEC2H3.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (SEC2S.equals("SIN ENFERMERA") == false) {
                            //play();
                            Toast.makeText(getApplicationContext(), "2-3", Toast.LENGTH_SHORT).show();
                            HABITACION = enfermeras[8][0];
                            FECHA = enfermeras[8][1];
                            HORA = enfermeras[8][2];
                            PACIENTE = enfermeras[8][3];
                            TIPODELLAMADO = enfermeras[8][4];
                            NOMBREENFERMERA = SEC2.getText().toString();
                            insetenfermera();
                        }
                    }
                });
                SEC2H4.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (SEC2S.equals("SIN ENFERMERA") == false) {
                            //play();
                            HABITACION = enfermeras[9][0];
                            FECHA = enfermeras[9][1];
                            HORA = enfermeras[9][2];
                            PACIENTE = enfermeras[9][3];
                            TIPODELLAMADO = enfermeras[9][4];
                            NOMBREENFERMERA = SEC2.getText().toString();
                            insetenfermera();
                            Toast.makeText(getApplicationContext(), "2-4" + HABITACION + FECHA + HORA + PACIENTE + TIPODELLAMADO, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
        if(layout==25) {
            SEC1 = (TextView) findViewById(R.id.SEC1SINENFE);
            SEC2 = (TextView) findViewById(R.id.SEC2SINENFE);

            SEC1H1 = (TextView) findViewById(R.id.sec1h1);
            SEC1H2 = (TextView) findViewById(R.id.sec1h2);
            SEC1H3 = (TextView) findViewById(R.id.sec1h3);
            SEC1H4 = (TextView) findViewById(R.id.sec1h4);
            SEC1H5 = (TextView) findViewById(R.id.sec1h5);

            SEC2H1 = (TextView) findViewById(R.id.sec2h1);
            SEC2H2 = (TextView) findViewById(R.id.sec2h2);
            SEC2H3 = (TextView) findViewById(R.id.sec2h3);
            SEC2H4 = (TextView) findViewById(R.id.sec2h4);
            SEC2H5 = (TextView) findViewById(R.id.sec2h5);

            SEC1H1.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC1H2.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC1H3.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC1H4.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC1H5.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");

            SEC2H1.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC2H2.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC2H3.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC2H4.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC2H5.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");


            SEC1H1.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC1H2.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC1H3.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC1H4.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC1H5.setBackgroundColor(Color.parseColor("#8BC34A"));

            SEC2H1.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC2H2.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC2H3.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC2H4.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC2H5.setBackgroundColor(Color.parseColor("#8BC34A"));

            if(layout==25) {
                SEC1H1.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (SEC1S.equals("SIN ENFERMERA") == false) {
                            //play();
                            Toast.makeText(getApplicationContext(), "1-1", Toast.LENGTH_SHORT).show();
                            HABITACION = enfermeras[0][0];
                            FECHA = enfermeras[0][1];
                            HORA = enfermeras[0][2];
                            PACIENTE = enfermeras[0][3];
                            TIPODELLAMADO = enfermeras[0][4];
                            NOMBREENFERMERA = SEC1.getText().toString();
                            insetenfermera();
                        }
                    }
                });
                SEC1H2.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (SEC1S.equals("SIN ENFERMERA") == false) {
                            //play();
                            Toast.makeText(getApplicationContext(), "1-2", Toast.LENGTH_SHORT).show();
                            HABITACION = enfermeras[1][0];
                            FECHA = enfermeras[1][1];
                            HORA = enfermeras[1][2];
                            PACIENTE = enfermeras[1][3];
                            TIPODELLAMADO = enfermeras[1][4];
                            NOMBREENFERMERA = SEC1.getText().toString();
                            insetenfermera();
                        }
                    }
                });
                SEC1H3.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (SEC1S.equals("SIN ENFERMERA") == false) {
                            //play();
                            Toast.makeText(getApplicationContext(), "1-3", Toast.LENGTH_SHORT).show();
                            HABITACION = enfermeras[2][0];
                            FECHA = enfermeras[2][1];
                            HORA = enfermeras[2][2];
                            PACIENTE = enfermeras[2][3];
                            TIPODELLAMADO = enfermeras[2][4];
                            NOMBREENFERMERA = SEC1.getText().toString();
                            insetenfermera();
                        }
                    }
                });
                SEC1H4.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (SEC1S.equals("SIN ENFERMERA") == false) {
                            //play();
                            Toast.makeText(getApplicationContext(), "1-4", Toast.LENGTH_SHORT).show();
                            HABITACION = enfermeras[3][0];
                            FECHA = enfermeras[3][1];
                            HORA = enfermeras[3][2];
                            PACIENTE = enfermeras[3][3];
                            TIPODELLAMADO = enfermeras[3][4];
                            NOMBREENFERMERA = SEC1.getText().toString();
                            insetenfermera();
                        }
                    }
                });
                SEC1H5.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (SEC1S.equals("SIN ENFERMERA") == false) {
                            //play();
                            Toast.makeText(getApplicationContext(), "" + enfermeras[4][4] + " ASIGANDA", Toast.LENGTH_SHORT).show();
                            HABITACION = enfermeras[4][0];
                            FECHA = enfermeras[4][1];
                            HORA = enfermeras[4][2];
                            PACIENTE = enfermeras[4][3];
                            TIPODELLAMADO = enfermeras[4][4];
                            NOMBREENFERMERA = SEC1.getText().toString();
                            insetenfermera();
                        }
                    }
                });

                SEC2H1.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (SEC2S.equals("SIN ENFERMERA") == false) {
                            //play();
                            Toast.makeText(getApplicationContext(), "2-1", Toast.LENGTH_SHORT).show();
                            HABITACION = enfermeras[6][0];
                            FECHA = enfermeras[6][1];
                            HORA = enfermeras[6][2];
                            PACIENTE = enfermeras[6][3];
                            TIPODELLAMADO = enfermeras[6][4];
                            NOMBREENFERMERA = SEC2.getText().toString();
                            insetenfermera();
                        }
                    }
                });
                SEC2H2.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (SEC2S.equals("SIN ENFERMERA") == false) {
                            //play();
                            Toast.makeText(getApplicationContext(), "2-2", Toast.LENGTH_SHORT).show();
                            HABITACION = enfermeras[7][0];
                            FECHA = enfermeras[7][1];
                            HORA = enfermeras[7][2];
                            PACIENTE = enfermeras[7][3];
                            TIPODELLAMADO = enfermeras[7][4];
                            NOMBREENFERMERA = SEC2.getText().toString();
                            insetenfermera();
                        }
                    }
                });
                SEC2H3.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (SEC2S.equals("SIN ENFERMERA") == false) {
                            //play();
                            Toast.makeText(getApplicationContext(), "2-3", Toast.LENGTH_SHORT).show();
                            HABITACION = enfermeras[8][0];
                            FECHA = enfermeras[8][1];
                            HORA = enfermeras[8][2];
                            PACIENTE = enfermeras[8][3];
                            TIPODELLAMADO = enfermeras[8][4];
                            NOMBREENFERMERA = SEC2.getText().toString();
                            insetenfermera();
                        }
                    }
                });
                SEC2H4.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (SEC2S.equals("SIN ENFERMERA") == false) {
                            //play();
                            HABITACION = enfermeras[9][0];
                            FECHA = enfermeras[9][1];
                            HORA = enfermeras[9][2];
                            PACIENTE = enfermeras[9][3];
                            TIPODELLAMADO = enfermeras[9][4];
                            NOMBREENFERMERA = SEC2.getText().toString();
                            insetenfermera();
                            Toast.makeText(getApplicationContext(), "2-4" + HABITACION + FECHA + HORA + PACIENTE + TIPODELLAMADO, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                SEC2H5.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (SEC2S.equals("SIN ENFERMERA") == false) {
                            //play();
                            Toast.makeText(getApplicationContext(), "2-5", Toast.LENGTH_SHORT).show();
                            HABITACION = enfermeras[10][0];
                            FECHA = enfermeras[10][1];
                            HORA = enfermeras[10][2];
                            PACIENTE = enfermeras[10][3];
                            TIPODELLAMADO = enfermeras[10][4];
                            NOMBREENFERMERA = SEC2.getText().toString();
                            insetenfermera();
                        }
                    }
                });
            }
        }
        if(layout==26) {
            SEC1 = (TextView) findViewById(R.id.SEC1SINENFE);
            SEC2 = (TextView) findViewById(R.id.SEC2SINENFE);
            SEC1H1 = (TextView) findViewById(R.id.sec1h1);
            SEC1H2 = (TextView) findViewById(R.id.sec1h2);
            SEC1H3 = (TextView) findViewById(R.id.sec1h3);
            SEC1H4 = (TextView) findViewById(R.id.sec1h4);
            SEC1H5 = (TextView) findViewById(R.id.sec1h5);
            SEC1H6 = (TextView) findViewById(R.id.sec1h6);

            SEC2H1 = (TextView) findViewById(R.id.sec2h1);
            SEC2H2 = (TextView) findViewById(R.id.sec2h2);
            SEC2H3 = (TextView) findViewById(R.id.sec2h3);
            SEC2H4 = (TextView) findViewById(R.id.sec2h4);
            SEC2H5 = (TextView) findViewById(R.id.sec2h5);
            SEC2H6 = (TextView) findViewById(R.id.sec2h6);

            SEC1H1.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC1H2.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC1H3.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC1H4.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC1H5.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC1H6.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");

            SEC2H1.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC2H2.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC2H3.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC2H4.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC2H5.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
            SEC2H6.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");


            SEC1H1.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC1H2.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC1H3.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC1H4.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC1H5.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC1H6.setBackgroundColor(Color.parseColor("#8BC34A"));

            SEC2H1.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC2H2.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC2H3.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC2H4.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC2H5.setBackgroundColor(Color.parseColor("#8BC34A"));
            SEC2H6.setBackgroundColor(Color.parseColor("#8BC34A"));

            if(layout==26) {
                SEC1H1.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (SEC1S.equals("SIN ENFERMERA") == false) {
                            //play();
                            Toast.makeText(getApplicationContext(), "1-1", Toast.LENGTH_SHORT).show();
                            HABITACION = enfermeras[0][0];
                            FECHA = enfermeras[0][1];
                            HORA = enfermeras[0][2];
                            PACIENTE = enfermeras[0][3];
                            TIPODELLAMADO = enfermeras[0][4];
                            NOMBREENFERMERA = SEC1.getText().toString();
                            insetenfermera();
                        }
                    }
                });
                SEC1H2.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (SEC1S.equals("SIN ENFERMERA") == false) {
                            //play();
                            Toast.makeText(getApplicationContext(), "1-2", Toast.LENGTH_SHORT).show();
                            HABITACION = enfermeras[1][0];
                            FECHA = enfermeras[1][1];
                            HORA = enfermeras[1][2];
                            PACIENTE = enfermeras[1][3];
                            TIPODELLAMADO = enfermeras[1][4];
                            NOMBREENFERMERA = SEC1.getText().toString();
                            insetenfermera();
                        }
                    }
                });
                SEC1H3.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (SEC1S.equals("SIN ENFERMERA") == false) {
                            //play();
                            Toast.makeText(getApplicationContext(), "1-3", Toast.LENGTH_SHORT).show();
                            HABITACION = enfermeras[2][0];
                            FECHA = enfermeras[2][1];
                            HORA = enfermeras[2][2];
                            PACIENTE = enfermeras[2][3];
                            TIPODELLAMADO = enfermeras[2][4];
                            NOMBREENFERMERA = SEC1.getText().toString();
                            insetenfermera();
                        }
                    }
                });
                SEC1H4.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (SEC1S.equals("SIN ENFERMERA") == false) {
                            //play();
                            Toast.makeText(getApplicationContext(), "1-4", Toast.LENGTH_SHORT).show();
                            HABITACION = enfermeras[3][0];
                            FECHA = enfermeras[3][1];
                            HORA = enfermeras[3][2];
                            PACIENTE = enfermeras[3][3];
                            TIPODELLAMADO = enfermeras[3][4];
                            NOMBREENFERMERA = SEC1.getText().toString();
                            insetenfermera();
                        }
                    }
                });
                SEC1H5.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (SEC1S.equals("SIN ENFERMERA") == false) {
                            //play();
                            Toast.makeText(getApplicationContext(), "" + enfermeras[4][4] + " ASIGANDA", Toast.LENGTH_SHORT).show();
                            HABITACION = enfermeras[4][0];
                            FECHA = enfermeras[4][1];
                            HORA = enfermeras[4][2];
                            PACIENTE = enfermeras[4][3];
                            TIPODELLAMADO = enfermeras[4][4];
                            NOMBREENFERMERA = SEC1.getText().toString();
                            insetenfermera();
                        }
                    }
                });
                SEC1H6.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (SEC1S.equals("SIN ENFERMERA") == false) {
                            //play();
                            Toast.makeText(getApplicationContext(), "1-6", Toast.LENGTH_SHORT).show();
                            HABITACION = enfermeras[5][0];
                            FECHA = enfermeras[5][1];
                            HORA = enfermeras[5][2];
                            PACIENTE = enfermeras[5][3];
                            TIPODELLAMADO = enfermeras[5][4];
                            NOMBREENFERMERA = SEC1.getText().toString();
                            insetenfermera();
                        }
                    }
                });
                SEC2H1.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (SEC2S.equals("SIN ENFERMERA") == false) {
                            //play();
                            Toast.makeText(getApplicationContext(), "2-1", Toast.LENGTH_SHORT).show();
                            HABITACION = enfermeras[6][0];
                            FECHA = enfermeras[6][1];
                            HORA = enfermeras[6][2];
                            PACIENTE = enfermeras[6][3];
                            TIPODELLAMADO = enfermeras[6][4];
                            NOMBREENFERMERA = SEC2.getText().toString();
                            insetenfermera();
                        }
                    }
                });
                SEC2H2.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (SEC2S.equals("SIN ENFERMERA") == false) {
                            //play();
                            Toast.makeText(getApplicationContext(), "2-2", Toast.LENGTH_SHORT).show();
                            HABITACION = enfermeras[7][0];
                            FECHA = enfermeras[7][1];
                            HORA = enfermeras[7][2];
                            PACIENTE = enfermeras[7][3];
                            TIPODELLAMADO = enfermeras[7][4];
                            NOMBREENFERMERA = SEC2.getText().toString();
                            insetenfermera();
                        }
                    }
                });
                SEC2H3.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (SEC2S.equals("SIN ENFERMERA") == false) {
                            //play();
                            Toast.makeText(getApplicationContext(), "2-3", Toast.LENGTH_SHORT).show();
                            HABITACION = enfermeras[8][0];
                            FECHA = enfermeras[8][1];
                            HORA = enfermeras[8][2];
                            PACIENTE = enfermeras[8][3];
                            TIPODELLAMADO = enfermeras[8][4];
                            NOMBREENFERMERA = SEC2.getText().toString();
                            insetenfermera();
                        }
                    }
                });
                SEC2H4.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (SEC2S.equals("SIN ENFERMERA") == false) {
                            //play();
                            HABITACION = enfermeras[9][0];
                            FECHA = enfermeras[9][1];
                            HORA = enfermeras[9][2];
                            PACIENTE = enfermeras[9][3];
                            TIPODELLAMADO = enfermeras[9][4];
                            NOMBREENFERMERA = SEC2.getText().toString();
                            insetenfermera();
                            Toast.makeText(getApplicationContext(), "2-4" + HABITACION + FECHA + HORA + PACIENTE + TIPODELLAMADO, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                SEC2H5.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (SEC2S.equals("SIN ENFERMERA") == false) {
                            //play();
                            Toast.makeText(getApplicationContext(), "2-5", Toast.LENGTH_SHORT).show();
                            HABITACION = enfermeras[10][0];
                            FECHA = enfermeras[10][1];
                            HORA = enfermeras[10][2];
                            PACIENTE = enfermeras[10][3];
                            TIPODELLAMADO = enfermeras[10][4];
                            NOMBREENFERMERA = SEC2.getText().toString();
                            insetenfermera();
                        }
                    }
                });
                SEC2H6.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (SEC2S.equals("SIN ENFERMERA") == false) {
                            //play();
                            Toast.makeText(getApplicationContext(), "2-6", Toast.LENGTH_SHORT).show();
                            HABITACION = enfermeras[11][0];
                            FECHA = enfermeras[11][1];
                            HORA = enfermeras[11][2];
                            PACIENTE = enfermeras[11][3];
                            TIPODELLAMADO = enfermeras[11][4];
                            NOMBREENFERMERA = SEC2.getText().toString();
                            insetenfermera();
                        }
                    }
                });
            }
        }
    }
    public void consulEvento() {
        a=1;
        D0 = dateFormat.format(date);
        D1 = dateFormat.format(date);

        HD = D0.substring(0,2);
        H1 = D0.substring(2);
        int HDI =Integer.parseInt(HD);
        HDI=HDI-1;///////// 1 = 2 DIAS EN LA COMPARACION///
        HD = String.valueOf(HDI);
        if(HDI < 10){
            HD="0"+HD;
        }
        D0 = HD+H1;
        url1 = "http://" + ip + "/BDEJEMPLOS/CONSULTAEVENTO.php?H1="+D0+"&H2="+D1;
        url1 = url1.replace(" ", "%20");
        jsonrequest = new JsonObjectRequest(Request.Method.POST, url1, null, this, this);
        request1.add(jsonrequest);
    }
    public void onResponse(JSONObject response) {
        if(a==1) {
            consulta = response.optJSONArray("usuario");
            try {
                for (i = 0; i < consulta.length(); i++) {
                    obtenerdatos();
                    if (SECCION.equals("1") == true) {
                        if (TR.equals("SIN RESPUESTA") == false && ESTATUS == "VACANTE") {
                            if (HABITACION.equals("101") == true) {
                                SEC1H1.setBackgroundColor(Color.parseColor(COLORVAC));
                                SEC1H1.setText("");
                                SEC1H101.setText("HABITACION "+HABITACION);
                            }
                            if (HABITACION.equals("102") == true) {
                                SEC1H2.setBackgroundColor(Color.parseColor(COLORVAC));
                                SEC1H2.setText("");
                                SEC1H102.setText("HABITACION "+HABITACION);
                            }
                            if (HABITACION.equals("103") == true) {
                                SEC1H3.setBackgroundColor(Color.parseColor(COLORVAC));
                                SEC1H3.setText("");
                                SEC1H103.setText("HABITACION "+HABITACION);
                            }
                            if (HABITACION.equals("104") == true) {
                                SEC1H4.setBackgroundColor(Color.parseColor(COLORVAC));
                                SEC1H4.setText("");
                                SEC1H104.setText("HABITACION "+HABITACION);
                            }
                            if (HABITACION.equals("105") == true) {
                                SEC1H5.setBackgroundColor(Color.parseColor(COLORVAC));
                                SEC1H5.setText("");
                                SEC1H105.setText("HABITACION "+HABITACION);
                            }
                            if (HABITACION.equals("106") == true) {
                                SEC1H6.setBackgroundColor(Color.parseColor(COLORVAC));
                                SEC1H6.setText("");
                                SEC1H106.setText("HABITACION "+HABITACION);
                            }
                        }
                        if (TR.equals("SIN RESPUESTA") == false && ESTATUS == "OCUPADO") {
                            if (HABITACION.equals("101") == true) {
                                SEC1H1.setBackgroundColor(Color.parseColor(COLOROCUPADO));
                                SEC1H1.setText("");
                                SEC1H101.setText("HABITACION "+HABITACION);
                            }
                            if (HABITACION.equals("102") == true) {
                                SEC1H2.setBackgroundColor(Color.parseColor(COLOROCUPADO));
                                SEC1H2.setText("");
                                SEC1H102.setText("HABITACION "+HABITACION);
                            }
                            if (HABITACION.equals("103") == true) {
                                SEC1H3.setBackgroundColor(Color.parseColor(COLOROCUPADO));
                                SEC1H3.setText("");
                                SEC1H103.setText("HABITACION "+HABITACION);
                            }
                            if (HABITACION.equals("104") == true) {
                                SEC1H4.setBackgroundColor(Color.parseColor(COLOROCUPADO));
                                SEC1H4.setText("");
                                SEC1H104.setText("HABITACION "+HABITACION);
                            }
                            if (HABITACION.equals("105") == true) {
                                SEC1H5.setBackgroundColor(Color.parseColor(COLOROCUPADO));
                                SEC1H5.setText("");
                                SEC1H105.setText("HABITACION "+HABITACION);
                            }
                            if (HABITACION.equals("106") == true) {
                                SEC1H6.setBackgroundColor(Color.parseColor(COLOROCUPADO));
                                SEC1H6.setText("");
                                SEC1H106.setText("HABITACION "+HABITACION);
                            }
                        }
                        if (TR.equals("SIN RESPUESTA") == true) {
                            if (HABITACION.equals("101") == true) {
                                SEC1H1.setText(FECHA + "\n" + HORA);
                                SEC1H101.setText("HABITACION "+HABITACION);
                                enfermeras[0][0]=HABITACION;
                                enfermeras[0][1]=FECHA;
                                enfermeras[0][2]=HORA;
                                enfermeras[0][3]=PACIENTE;
                                enfermeras[0][4]=MEDICO;
                                enfermeras[0][5]=PAGO;
                                enfermeras[0][6]=FECHAI;
                                enfermeras[0][7]=TIPODELLAMADO;
                                if (TIPODELLAMADO.equals("ASISTENCIA") == true) {
                                    SEC1H1.setBackgroundColor(Color.parseColor(COLORASIS));
                                }
                                if (TIPODELLAMADO.equals("EMERGENCIA") || TIPODELLAMADO.equals("EMERGENCIA BANO")  == true) {
                                    SEC1H1.setBackgroundColor(Color.parseColor(COLOREMER));
                                }
                                //HACER ARRAY PARA ALMACENAR LOS AUDIOS Y REPRODUCIRLOS
                                //PLAYER=PLAYER+AUDIO;
                            }
                            if (HABITACION.equals("102") == true) {
                                SEC1H2.setText(FECHA + "\n" + HORA);
                                SEC1H102.setText("HABITACION "+HABITACION);
                                enfermeras[1][0]=HABITACION;
                                enfermeras[1][1]=FECHA;
                                enfermeras[1][2]=HORA;
                                enfermeras[1][3]=PACIENTE;
                                enfermeras[1][4]=MEDICO;
                                enfermeras[1][5]=PAGO;
                                enfermeras[1][6]=FECHAI;
                                enfermeras[1][7]=TIPODELLAMADO;
                                if (TIPODELLAMADO.equals("ASISTENCIA") == true) {
                                    SEC1H2.setBackgroundColor(Color.parseColor(COLORASIS));
                                }
                                if (TIPODELLAMADO.equals("EMERGENCIA") || TIPODELLAMADO.equals("EMERGENCIA BANO")  == true) {
                                    SEC1H2.setBackgroundColor(Color.parseColor(COLOREMER));
                                }
                            }
                            if (HABITACION.equals("103") == true) {
                                SEC1H3.setText(FECHA + "\n" + HORA);
                                SEC1H103.setText("HABITACION "+HABITACION);
                                enfermeras[2][0]=HABITACION;
                                enfermeras[2][1]=FECHA;
                                enfermeras[2][2]=HORA;
                                enfermeras[2][3]=PACIENTE;
                                enfermeras[2][4]=MEDICO;
                                enfermeras[2][5]=PAGO;
                                enfermeras[2][6]=FECHAI;
                                enfermeras[2][7]=TIPODELLAMADO;
                                if (TIPODELLAMADO.equals("ASISTENCIA") == true) {
                                    SEC1H3.setBackgroundColor(Color.parseColor(COLORASIS));
                                }
                                if (TIPODELLAMADO.equals("EMERGENCIA") || TIPODELLAMADO.equals("EMERGENCIA BANO")  == true) {
                                    SEC1H3.setBackgroundColor(Color.parseColor(COLOREMER));
                                }
                            }
                            if (HABITACION.equals("104") == true) {
                                SEC1H4.setText(FECHA + "\n" + HORA);
                                SEC1H104.setText("HABITACION "+HABITACION);
                                enfermeras[3][0]=HABITACION;
                                enfermeras[3][1]=FECHA;
                                enfermeras[3][2]=HORA;
                                enfermeras[3][3]=PACIENTE;
                                enfermeras[3][4]=MEDICO;
                                enfermeras[3][5]=PAGO;
                                enfermeras[3][6]=FECHAI;
                                enfermeras[3][7]=TIPODELLAMADO;
                                if (TIPODELLAMADO.equals("ASISTENCIA") == true) {
                                    SEC1H4.setBackgroundColor(Color.parseColor(COLORASIS));
                                }
                                if (TIPODELLAMADO.equals("EMERGENCIA") || TIPODELLAMADO.equals("EMERGENCIA BANO")  == true) {
                                    SEC1H4.setBackgroundColor(Color.parseColor(COLOREMER));
                                }
                            }
                            if (HABITACION.equals("105") == true) {
                                SEC1H5.setText(FECHA + "\n" + HORA);
                                SEC1H105.setText("HABITACION "+HABITACION);
                                enfermeras[4][0]=HABITACION;
                                enfermeras[4][1]=FECHA;
                                enfermeras[4][2]=HORA;
                                enfermeras[4][3]=PACIENTE;
                                enfermeras[4][4]=MEDICO;
                                enfermeras[4][5]=PAGO;
                                enfermeras[4][6]=FECHAI;
                                enfermeras[4][7]=TIPODELLAMADO;
                                if (TIPODELLAMADO.equals("ASISTENCIA") == true) {
                                    SEC1H5.setBackgroundColor(Color.parseColor(COLORASIS));
                                }
                                if (TIPODELLAMADO.equals("EMERGENCIA") || TIPODELLAMADO.equals("EMERGENCIA BANO")  == true) {
                                    SEC1H5.setBackgroundColor(Color.parseColor(COLOREMER));
                                }
                            }
                            if (HABITACION.equals("106") == true) {
                                SEC1H6.setText(FECHA + "\n" + HORA);
                                SEC1H106.setText("HABITACION "+HABITACION);
                                enfermeras[5][0]=HABITACION;
                                enfermeras[5][1]=FECHA;
                                enfermeras[5][2]=HORA;
                                enfermeras[5][3]=PACIENTE;
                                enfermeras[5][4]=MEDICO;
                                enfermeras[5][5]=PAGO;
                                enfermeras[5][6]=FECHAI;
                                enfermeras[5][7]=TIPODELLAMADO;
                                if (TIPODELLAMADO.equals("ASISTENCIA") == true) {
                                    SEC1H6.setBackgroundColor(Color.parseColor(COLORASIS));
                                }
                                if (TIPODELLAMADO.equals("EMERGENCIA") || TIPODELLAMADO.equals("EMERGENCIA BANO")  == true) {
                                    SEC1H6.setBackgroundColor(Color.parseColor(COLOREMER));
                                }
                            }
                        }
                    }
                    if (SECCION.equals("2") == true) {
                        if (TR.equals("SIN RESPUESTA") == false) {
                            if (HABITACION.equals("107") == true) {
                                SEC2H1.setBackgroundColor(Color.parseColor("#8BC34A"));
                                SEC2H1.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
                            }
                            if (HABITACION.equals("108") == true) {
                                SEC2H2.setBackgroundColor(Color.parseColor("#8BC34A"));
                                SEC2H2.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
                            }
                            if (HABITACION.equals("109") == true) {
                                SEC2H3.setBackgroundColor(Color.parseColor("#8BC34A"));
                                SEC2H3.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
                            }
                            if (HABITACION.equals("110") == true) {
                                SEC2H4.setBackgroundColor(Color.parseColor("#8BC34A"));
                                SEC2H4.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
                            }
                            if (HABITACION.equals("111") == true) {
                                SEC2H5.setBackgroundColor(Color.parseColor("#8BC34A"));
                                SEC2H5.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
                            }
                            if (HABITACION.equals("112") == true) {
                                SEC2H6.setBackgroundColor(Color.parseColor("#8BC34A"));
                                SEC2H6.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
                            }
                        }
                        if (TR.equals("SIN RESPUESTA") == true) {
                            if (HABITACION.equals("107") == true) {
                                //REQUEST TABLA HABITACION
                                enfermeras[6][0]=HABITACION;
                                enfermeras[6][1]=FECHA;
                                enfermeras[6][2]=HORA;
                                enfermeras[6][3]=PACIENTE;
                                enfermeras[6][4]=TIPODELLAMADO;
                                SEC2H1.setText("HABITACION: " + HABITACION + "\nPACIENTE: " + PACIENTE + "\nFECHA: " + FECHA + "\nHORA: " + HORA + "\nMEDICO: " + MEDICO + "\n" + PAGO);
                                if (TIPODELLAMADO.equals("ASISTENCIA") == true) {
                                    SEC2H1.setBackgroundColor(Color.parseColor("#FFC107"));
                                }
                                if (TIPODELLAMADO.equals("EMERGENCIA") || TIPODELLAMADO.equals("EMERGENCIA BANO")  == true) {
                                    SEC2H1.setBackgroundColor(Color.parseColor("#B71C1C"));
                                }
                            }
                            if (HABITACION.equals("108") == true) {
                                //REQUEST TABLA HABITACION
                                enfermeras[7][0]=HABITACION;
                                enfermeras[7][1]=FECHA;
                                enfermeras[7][2]=HORA;
                                enfermeras[7][3]=PACIENTE;
                                enfermeras[7][4]=TIPODELLAMADO;
                                SEC2H2.setText("HABITACION: " + HABITACION + "\nPACIENTE: " + PACIENTE + "\nFECHA: " + FECHA + "\nHORA: " + HORA + "\nMEDICO: " + MEDICO + "\n" + PAGO);
                                if (TIPODELLAMADO.equals("ASISTENCIA") == true) {
                                    SEC2H2.setBackgroundColor(Color.parseColor("#FFC107"));
                                }
                                if (TIPODELLAMADO.equals("EMERGENCIA") || TIPODELLAMADO.equals("EMERGENCIA BANO")  == true) {
                                    SEC2H2.setBackgroundColor(Color.parseColor("#B71C1C"));
                                }
                            }
                            if (HABITACION.equals("109") == true) {
                                //REQUEST TABLA HABITACION
                                enfermeras[8][0]=HABITACION;
                                enfermeras[8][1]=FECHA;
                                enfermeras[8][2]=HORA;
                                enfermeras[8][3]=PACIENTE;
                                enfermeras[8][4]=TIPODELLAMADO;
                                SEC2H3.setText("HABITACION: " + HABITACION + "\nPACIENTE: " + PACIENTE + "\nFECHA: " + FECHA + "\nHORA: " + HORA + "\nMEDICO: " + MEDICO + "\n" + PAGO);
                                if (TIPODELLAMADO.equals("ASISTENCIA") == true) {
                                    SEC2H3.setBackgroundColor(Color.parseColor("#FFC107"));
                                }
                                if (TIPODELLAMADO.equals("EMERGENCIA") || TIPODELLAMADO.equals("EMERGENCIA BANO")  == true) {
                                    SEC2H3.setBackgroundColor(Color.parseColor("#B71C1C"));
                                }
                            }
                            if (HABITACION.equals("110") == true) {
                                //REQUEST TABLA HABITACION
                                enfermeras[9][0]=HABITACION;
                                enfermeras[9][1]=FECHA;
                                enfermeras[9][2]=HORA;
                                enfermeras[9][3]=PACIENTE;
                                enfermeras[9][4]=TIPODELLAMADO;
                                SEC2H4.setText("HABITACION: " + HABITACION + "\nPACIENTE: " + PACIENTE + "\nFECHA: " + FECHA + "\nHORA: " + HORA + "\nMEDICO: " + MEDICO + "\n" + PAGO);
                                if (TIPODELLAMADO.equals("ASISTENCIA") == true) {
                                    SEC2H4.setBackgroundColor(Color.parseColor("#FFC107"));
                                }
                                if (TIPODELLAMADO.equals("EMERGENCIA") || TIPODELLAMADO.equals("EMERGENCIA BANO")  == true) {
                                    SEC2H4.setBackgroundColor(Color.parseColor("#B71C1C"));
                                }
                            }
                            if (HABITACION.equals("111") == true) {
                                //REQUEST TABLA HABITACION
                                enfermeras[10][0]=HABITACION;
                                enfermeras[10][1]=FECHA;
                                enfermeras[10][2]=HORA;
                                enfermeras[10][3]=PACIENTE;
                                enfermeras[10][4]=TIPODELLAMADO;
                                SEC2H5.setText("HABITACION: " + HABITACION + "\nPACIENTE: " + PACIENTE + "\nFECHA: " + FECHA + "\nHORA: " + HORA + "\nMEDICO: " + MEDICO + "\n" + PAGO);
                                if (TIPODELLAMADO.equals("ASISTENCIA") == true) {
                                    SEC2H5.setBackgroundColor(Color.parseColor("#FFC107"));
                                }
                                if (TIPODELLAMADO.equals("EMERGENCIA") || TIPODELLAMADO.equals("EMERGENCIA BANO")  == true) {
                                    SEC2H5.setBackgroundColor(Color.parseColor("#B71C1C"));
                                }
                            }
                            if (HABITACION.equals("112") == true) {
                                //REQUEST TABLA HABITACION
                                enfermeras[11][0]=HABITACION;
                                enfermeras[11][1]=FECHA;
                                enfermeras[11][2]=HORA;
                                enfermeras[11][3]=PACIENTE;
                                enfermeras[11][4]=TIPODELLAMADO;
                                SEC2H6.setText("HABITACION: " + HABITACION + "\nPACIENTE: " + PACIENTE + "\nFECHA: " + FECHA + "\nHORA: " + HORA + "\nMEDICO: " + MEDICO + "\n" + PAGO);
                                if (TIPODELLAMADO.equals("ASISTENCIA") == true) {
                                    SEC2H6.setBackgroundColor(Color.parseColor("#FFC107"));
                                }
                                if (TIPODELLAMADO.equals("EMERGENCIA") || TIPODELLAMADO.equals("EMERGENCIA BANO")  == true) {
                                    SEC2H6.setBackgroundColor(Color.parseColor("#B71C1C"));
                                }
                            }

                        }
                    }
                    if (SECCION.equals("3") == true) {
                        if (TR.equals("SIN RESPUESTA") == false) {
                            if (HABITACION.equals("113") == true) {
                                SEC3H1.setBackgroundColor(Color.parseColor("#8BC34A"));
                                SEC3H1.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
                            }
                            if (HABITACION.equals("114") == true) {
                                SEC3H2.setBackgroundColor(Color.parseColor("#8BC34A"));
                                SEC3H2.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
                            }
                            if (HABITACION.equals("115") == true) {
                                SEC3H3.setBackgroundColor(Color.parseColor("#8BC34A"));
                                SEC3H3.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
                            }
                            if (HABITACION.equals("116") == true) {
                                SEC3H4.setBackgroundColor(Color.parseColor("#8BC34A"));
                                SEC3H4.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
                            }
                            if (HABITACION.equals("117") == true) {
                                SEC3H5.setBackgroundColor(Color.parseColor("#8BC34A"));
                                SEC3H5.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
                            }
                            if (HABITACION.equals("118") == true) {
                                SEC3H6.setBackgroundColor(Color.parseColor("#8BC34A"));
                                SEC3H6.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
                            }
                        }
                        if (TR.equals("SIN RESPUESTA") == true) {
                            if (HABITACION.equals("113") == true) {
                                //REQUEST TABLA HABITACION
                                enfermeras[12][0]=HABITACION;
                                enfermeras[12][1]=FECHA;
                                enfermeras[12][2]=HORA;
                                enfermeras[12][3]=PACIENTE;
                                enfermeras[12][4]=TIPODELLAMADO;
                                SEC3H1.setText("HABITACION: " + HABITACION + "\nPACIENTE: " + PACIENTE + "\nFECHA: " + FECHA + "\nHORA: " + HORA + "\nMEDICO: " + MEDICO + "\n" + PAGO);
                                if (TIPODELLAMADO.equals("ASISTENCIA") == true) {
                                    SEC3H1.setBackgroundColor(Color.parseColor("#FFC107"));
                                }
                                if (TIPODELLAMADO.equals("EMERGENCIA") || TIPODELLAMADO.equals("EMERGENCIA BANO")  == true) {
                                    SEC3H1.setBackgroundColor(Color.parseColor("#B71C1C"));
                                }
                            }
                            if (HABITACION.equals("114") == true) {
                                //REQUEST TABLA HABITACION
                                enfermeras[13][0]=HABITACION;
                                enfermeras[13][1]=FECHA;
                                enfermeras[13][2]=HORA;
                                enfermeras[13][3]=PACIENTE;
                                enfermeras[13][4]=TIPODELLAMADO;
                                SEC3H2.setText("HABITACION: " + HABITACION + "\nPACIENTE: " + PACIENTE + "\nFECHA: " + FECHA + "\nHORA: " + HORA + "\nMEDICO: " + MEDICO + "\n" + PAGO);
                                if (TIPODELLAMADO.equals("ASISTENCIA") == true) {
                                    SEC3H2.setBackgroundColor(Color.parseColor("#FFC107"));
                                }
                                if (TIPODELLAMADO.equals("EMERGENCIA") || TIPODELLAMADO.equals("EMERGENCIA BANO")  == true) {
                                    SEC3H2.setBackgroundColor(Color.parseColor("#B71C1C"));
                                }
                            }
                            if (HABITACION.equals("115") == true) {
                                //REQUEST TABLA HABITACION
                                enfermeras[14][0]=HABITACION;
                                enfermeras[14][1]=FECHA;
                                enfermeras[14][2]=HORA;
                                enfermeras[14][3]=PACIENTE;
                                enfermeras[14][4]=TIPODELLAMADO;
                                SEC3H3.setText("HABITACION: " + HABITACION + "\nPACIENTE: " + PACIENTE + "\nFECHA: " + FECHA + "\nHORA: " + HORA + "\nMEDICO: " + MEDICO + "\n" + PAGO);
                                if (TIPODELLAMADO.equals("ASISTENCIA") == true) {
                                    SEC3H3.setBackgroundColor(Color.parseColor("#FFC107"));
                                }
                                if (TIPODELLAMADO.equals("EMERGENCIA") || TIPODELLAMADO.equals("EMERGENCIA BANO")  == true) {
                                    SEC3H3.setBackgroundColor(Color.parseColor("#B71C1C"));
                                }
                            }
                            if (HABITACION.equals("116") == true) {
                                //REQUEST TABLA HABITACION
                                enfermeras[15][0]=HABITACION;
                                enfermeras[15][1]=FECHA;
                                enfermeras[15][2]=HORA;
                                enfermeras[15][3]=PACIENTE;
                                enfermeras[15][4]=TIPODELLAMADO;
                                SEC3H4.setText("HABITACION: " + HABITACION + "\nPACIENTE: " + PACIENTE + "\nFECHA: " + FECHA + "\nHORA: " + HORA + "\nMEDICO: " + MEDICO + "\n" + PAGO);
                                if (TIPODELLAMADO.equals("ASISTENCIA") == true) {
                                    SEC3H4.setBackgroundColor(Color.parseColor("#FFC107"));
                                }
                                if (TIPODELLAMADO.equals("EMERGENCIA") || TIPODELLAMADO.equals("EMERGENCIA BANO")  == true) {
                                    SEC3H4.setBackgroundColor(Color.parseColor("#B71C1C"));
                                }
                            }
                            if (HABITACION.equals("117") == true) {
                                //REQUEST TABLA HABITACION
                                enfermeras[16][0]=HABITACION;
                                enfermeras[16][1]=FECHA;
                                enfermeras[16][2]=HORA;
                                enfermeras[16][3]=PACIENTE;
                                enfermeras[16][4]=TIPODELLAMADO;
                                SEC3H5.setText("HABITACION: " + HABITACION + "\nPACIENTE: " + PACIENTE + "\nFECHA: " + FECHA + "\nHORA: " + HORA + "\nMEDICO: " + MEDICO + "\n" + PAGO);
                                if (TIPODELLAMADO.equals("ASISTENCIA") == true) {
                                    SEC3H5.setBackgroundColor(Color.parseColor("#FFC107"));
                                }
                                if (TIPODELLAMADO.equals("EMERGENCIA") || TIPODELLAMADO.equals("EMERGENCIA BANO")  == true) {
                                    SEC3H5.setBackgroundColor(Color.parseColor("#B71C1C"));
                                }
                            }
                            if (HABITACION.equals("118") == true) {
                                //REQUEST TABLA HABITACION
                                enfermeras[17][0]=HABITACION;
                                enfermeras[17][1]=FECHA;
                                enfermeras[17][2]=HORA;
                                enfermeras[17][3]=PACIENTE;
                                enfermeras[17][4]=TIPODELLAMADO;
                                SEC3H6.setText("HABITACION: " + HABITACION + "\nPACIENTE: " + PACIENTE + "\nFECHA: " + FECHA + "\nHORA: " + HORA + "\nMEDICO: " + MEDICO + "\n" + PAGO);
                                if (TIPODELLAMADO.equals("ASISTENCIA") == true) {
                                    SEC3H6.setBackgroundColor(Color.parseColor("#FFC107"));
                                }
                                if (TIPODELLAMADO.equals("EMERGENCIA") || TIPODELLAMADO.equals("EMERGENCIA BANO")  == true) {
                                    SEC3H6.setBackgroundColor(Color.parseColor("#B71C1C"));
                                }
                            }
                        }
                    }
                    if (SECCION.equals("4") == true) {
                        if (TR.equals("SIN RESPUESTA") == false) {
                            if (HABITACION.equals("119") == true) {
                                SEC4H1.setBackgroundColor(Color.parseColor("#8BC34A"));
                                SEC4H1.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
                            }
                            if (HABITACION.equals("120") == true) {
                                SEC4H2.setBackgroundColor(Color.parseColor("#8BC34A"));
                                SEC4H2.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
                            }
                            if (HABITACION.equals("121") == true) {
                                SEC4H3.setBackgroundColor(Color.parseColor("#8BC34A"));
                                SEC4H3.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
                            }
                            if (HABITACION.equals("122") == true) {
                                SEC4H4.setBackgroundColor(Color.parseColor("#8BC34A"));
                                SEC4H4.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
                            }
                            if (HABITACION.equals("123") == true) {
                                SEC4H5.setBackgroundColor(Color.parseColor("#8BC34A"));
                                SEC4H5.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
                            }
                            if (HABITACION.equals("124") == true) {
                                SEC4H6.setBackgroundColor(Color.parseColor("#8BC34A"));
                                SEC4H6.setText("HABITACION:N/A\nPACIENTE:N/A\nFECHA:N/A\nHORA:N/A\nMEDICO:N/A\n");
                            }
                        }
                        if (TR.equals("SIN RESPUESTA") == true) {
                            if (HABITACION.equals("119") == true) {
                                //REQUEST TABLA HABITACION
                                enfermeras[18][0]=HABITACION;
                                enfermeras[18][1]=FECHA;
                                enfermeras[18][2]=HORA;
                                enfermeras[18][3]=PACIENTE;
                                enfermeras[18][4]=TIPODELLAMADO;
                                SEC4H1.setText("HABITACION: " + HABITACION + "\nPACIENTE: " + PACIENTE + "\nFECHA: " + FECHA + "\nHORA: " + HORA + "\nMEDICO: " + MEDICO + "\n" + PAGO);
                                if (TIPODELLAMADO.equals("ASISTENCIA") == true) {
                                    SEC4H1.setBackgroundColor(Color.parseColor("#FFC107"));
                                }
                                if (TIPODELLAMADO.equals("EMERGENCIA") || TIPODELLAMADO.equals("EMERGENCIA BANO")  == true) {
                                    SEC4H1.setBackgroundColor(Color.parseColor("#B71C1C"));
                                }
                            }
                            if (HABITACION.equals("120") == true) {
                                //REQUEST TABLA HABITACION
                                enfermeras[19][0]=HABITACION;
                                enfermeras[19][1]=FECHA;
                                enfermeras[19][2]=HORA;
                                enfermeras[19][3]=PACIENTE;
                                enfermeras[19][4]=TIPODELLAMADO;
                                SEC4H2.setText("HABITACION: " + HABITACION + "\nPACIENTE: " + PACIENTE + "\nFECHA: " + FECHA + "\nHORA: " + HORA + "\nMEDICO: " + MEDICO + "\n" + PAGO);
                                if (TIPODELLAMADO.equals("ASISTENCIA") == true) {
                                    SEC4H2.setBackgroundColor(Color.parseColor("#FFC107"));
                                }
                                if (TIPODELLAMADO.equals("EMERGENCIA") || TIPODELLAMADO.equals("EMERGENCIA BANO")  == true) {
                                    SEC4H2.setBackgroundColor(Color.parseColor("#B71C1C"));
                                }
                            }
                            if (HABITACION.equals("121") == true) {
                                //REQUEST TABLA HABITACION
                                enfermeras[20][0]=HABITACION;
                                enfermeras[20][1]=FECHA;
                                enfermeras[20][2]=HORA;
                                enfermeras[20][3]=PACIENTE;
                                enfermeras[20][4]=TIPODELLAMADO;
                                SEC4H3.setText("HABITACION: " + HABITACION + "\nPACIENTE: " + PACIENTE + "\nFECHA: " + FECHA + "\nHORA: " + HORA + "\nMEDICO: " + MEDICO + "\n" + PAGO);
                                if (TIPODELLAMADO.equals("ASISTENCIA") == true) {
                                    SEC4H3.setBackgroundColor(Color.parseColor("#FFC107"));
                                }
                                if (TIPODELLAMADO.equals("EMERGENCIA") || TIPODELLAMADO.equals("EMERGENCIA BANO")  == true) {
                                    SEC4H3.setBackgroundColor(Color.parseColor("#B71C1C"));
                                }
                            }
                            if (HABITACION.equals("122") == true) {
                                //REQUEST TABLA HABITACION
                                enfermeras[21][0]=HABITACION;
                                enfermeras[21][1]=FECHA;
                                enfermeras[21][2]=HORA;
                                enfermeras[21][3]=PACIENTE;
                                enfermeras[21][4]=TIPODELLAMADO;
                                SEC4H4.setText("HABITACION: " + HABITACION + "\nPACIENTE: " + PACIENTE + "\nFECHA: " + FECHA + "\nHORA: " + HORA + "\nMEDICO: " + MEDICO + "\n" + PAGO);
                                if (TIPODELLAMADO.equals("ASISTENCIA") == true) {
                                    SEC4H4.setBackgroundColor(Color.parseColor("#FFC107"));
                                }
                                if (TIPODELLAMADO.equals("EMERGENCIA") || TIPODELLAMADO.equals("EMERGENCIA BANO")  == true) {
                                    SEC4H4.setBackgroundColor(Color.parseColor("#B71C1C"));
                                }
                            }
                            if (HABITACION.equals("123") == true) {
                                //REQUEST TABLA HABITACION
                                enfermeras[22][0]=HABITACION;
                                enfermeras[22][1]=FECHA;
                                enfermeras[22][2]=HORA;
                                enfermeras[22][3]=PACIENTE;
                                enfermeras[22][4]=TIPODELLAMADO;
                                SEC4H5.setText("HABITACION: " + HABITACION + "\nPACIENTE: " + PACIENTE + "\nFECHA: " + FECHA + "\nHORA: " + HORA + "\nMEDICO: " + MEDICO + "\n" + PAGO);
                                if (TIPODELLAMADO.equals("ASISTENCIA") == true) {
                                    SEC4H5.setBackgroundColor(Color.parseColor("#FFC107"));
                                }
                                if (TIPODELLAMADO.equals("EMERGENCIA") || TIPODELLAMADO.equals("EMERGENCIA BANO")  == true) {
                                    SEC4H5.setBackgroundColor(Color.parseColor("#B71C1C"));
                                }
                            }
                            if (HABITACION.equals("124") == true) {
                                //REQUEST TABLA HABITACION
                                enfermeras[23][0]=HABITACION;
                                enfermeras[23][1]=FECHA;
                                enfermeras[23][2]=HORA;
                                enfermeras[23][3]=PACIENTE;
                                enfermeras[23][4]=TIPODELLAMADO;
                                SEC4H6.setText("HABITACION: " + HABITACION + "\nPACIENTE: " + PACIENTE + "\nFECHA: " + FECHA + "\nHORA: " + HORA + "\nMEDICO: " + MEDICO + "\n" + PAGO);
                                if (TIPODELLAMADO.equals("ASISTENCIA") == true) {
                                    SEC4H6.setBackgroundColor(Color.parseColor("#FFC107"));
                                }
                                if (TIPODELLAMADO.equals("EMERGENCIA") || TIPODELLAMADO.equals("EMERGENCIA BANO")  == true) {
                                    SEC4H6.setBackgroundColor(Color.parseColor("#B71C1C"));
                                }
                            }

                        }
                    }
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "SIN CONSULTAS PENDIENTES", Toast.LENGTH_SHORT).show();
            }
            a=0;
        }
        if(a==2) {

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    url1 = "http://" + ip + "/BDEJEMPLOS/INSERTNUEVASECCION.php?SECCION="+SECCIONINS+"&NOMBREENFERMERA="+NOMBREENFERMERA;
                    url1 = url1.replace(" ", "%20");
                    StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }) {
                    };
                    MyRequestQueue.add(MyStringRequest);
                }
            }, 200);


            consulta = response.optJSONArray("usuario");
            try {
                for (i = 0; i < consulta.length(); i++) {
                    obtenerdatos();
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "ERROR CONSILTA", Toast.LENGTH_SHORT).show();
            }
            if (CONTRASENA.equals(CONTRASENAINSERTADA) == true ) {
                AVISO="SECCION INCORRECTA";
                if (SECCIONINS.equals("1")==true && b1 == 0 && sesiones[0].equals("N/A")==true) {
                    SEC1.setText(NOMBREENFERMERA);
                    b1=1;
                    sesiones[0]=USUARIOINS;
                    AVISO="SESION ENFERMERA INICIADA";
                }
                if (SECCIONINS.equals("2")==true && b2 == 0 && sesiones[1].equals("N/A")==true) {
                    SEC2.setText(NOMBREENFERMERA);
                    b2=1;
                    sesiones[1]=USUARIOINS;
                    AVISO="SESION ENFERMERA INICIADA";
                }
                if (SECCIONINS.equals("3")==true && b3 == 0 && sesiones[2].equals("N/A")==true) {
                    SEC3.setText(NOMBREENFERMERA);
                    b3=1;
                    sesiones[2]=USUARIOINS;
                    AVISO="SESION ENFERMERA INICIADA";
                }
                if (SECCIONINS.equals("4")==true && b4 == 0 && sesiones[3].equals("N/A")==true) {
                    SEC4.setText(NOMBREENFERMERA);
                    b4=1;
                    sesiones[3]=USUARIOINS;
                    AVISO="SESION ENFERMERA INICIADA";
                }
            } else {
                AVISO="USUARIO O CONTRASEÑA INCORRECTA ";
            }
            Toast.makeText(getApplicationContext(), ""+AVISO, Toast.LENGTH_SHORT).show();
            a = 0;

        }
        if(a==3){
            consulta = response.optJSONArray("usuario");
            try {
                for (i = 0; i < consulta.length(); i++) {
                    obtenerdatos();
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "ERROR CONSILTA", Toast.LENGTH_SHORT).show();
            }
            if (CONTRASENA.equals(CONTRASENAINSERTADA) == true) {
                AVISO="SECCION INCORRECTA";
                if (SECCIONINS.equals("1")==true && b1 == 1 && sesiones[0].equals(USUARIOUT)==true ) {
                    SEC1.setText("SIN ENFERMERA");
                    b1=0;
                    AVISO="SESION ENFERMERA CERRADA";
                    sesiones[0]="N/A";
                }
                if (SECCIONINS.equals("2")==true && b2 == 1 && sesiones[1].equals(USUARIOUT)==true) {
                    SEC2.setText("SIN ENFERMERA");
                    b2=0;
                    AVISO="SESION ENFERMERA CERRADA";
                    sesiones[1]="N/A";
                }
                if (SECCIONINS.equals("3")==true && b3 == 1 && sesiones[2].equals(USUARIOUT)==true) {
                    SEC3.setText("SIN ENFERMERA");
                    b3=0;
                    AVISO="SESION ENFERMERA CERRADA";
                    sesiones[2]="N/A";
                }
                if (SECCIONINS.equals("4")==true && b4 == 1 && sesiones[3].equals(USUARIOUT)==true) {
                    SEC4.setText("SIN ENFERMERA");
                    b4=0;
                    AVISO="SESION ENFERMERA CERRADA";
                    sesiones[3]="N/A";
                }
            } else {
                AVISO="USUARIO  CONTRASEÑA INCORRECTA";

            }
            Toast.makeText(getApplicationContext(), ""+AVISO, Toast.LENGTH_SHORT).show();
            a = 0;
        }
    }
    public void onErrorResponse (VolleyError error){
         alertaBasededatos();
    }
    public void obtenerdatos() throws JSONException {
        consultaUsuario = new Usuarios();
        JSONObject jsonconsulta = null;
        jsonconsulta = consulta.getJSONObject(i);

        // ORDEN DEL EVENTO FOLIOGENERAL,FOLIODISPOSITIVO,TIPODELLAMDO,FECHA,HORA,TURNO,HABITACION,ENFERMERA,TR,PACIENTE,MEDICO,PAGO,ESTACION,SECCION,AUDIO;
        consultaUsuario.setFOLIODISPOSITIVO(jsonconsulta.optString("FOLIOGENERAL"));
        consultaUsuario.setTIPODELLAMDO(jsonconsulta.optString("TIPODELLAMADO"));
        consultaUsuario.setFECHA(jsonconsulta.optString("FECHA"));
        consultaUsuario.setHORA(jsonconsulta.optString("HORA"));
        consultaUsuario.setTURNO(jsonconsulta.optString("TURNO"));
        consultaUsuario.setHABITACION(jsonconsulta.optString("HABITACION"));
        consultaUsuario.setENFERMERA(jsonconsulta.optString("ENFERMERA"));
        consultaUsuario.setTR(jsonconsulta.optString("TIEMPORESPUESTA"));
        consultaUsuario.setPACIENTE(jsonconsulta.optString("PACIENTE"));
        consultaUsuario.setMEDICO(jsonconsulta.optString("MEDICO"));
        consultaUsuario.setPAGO(jsonconsulta.optString("PAGO"));
        consultaUsuario.setESTACION(jsonconsulta.optString("ESTACION"));
        consultaUsuario.setSECCION(jsonconsulta.optString("SECCION"));
        consultaUsuario.setAUDIO(jsonconsulta.optString("AUDIO"));
        consultaUsuario.setCONTRASENA(jsonconsulta.optString("CONTRASENA"));
        consultaUsuario.setNOMBREENEFERMERA(jsonconsulta.optString("NOMBREENFERMERA"));
        //consultaUsuario.setFECHAINGRESO(jsonconsulta.optString("FECHAINGRESO"));
        //consultaUsuario.setESTATUS(jsonconsulta.optString("ESTATUS"));



        FOLIODISPOSITIVO = consultaUsuario.getFOLIODISPOSITIVO();
        TIPODELLAMADO=consultaUsuario.getTIPODELLAMDO();
        FECHA = consultaUsuario.getFECHA();
        HORA = consultaUsuario.getHORA();
        TURNO = consultaUsuario.getTURNO();
        HABITACION = consultaUsuario.getHABITACION();
        ENFERMERA = consultaUsuario.getENFERMERA();
        TR=consultaUsuario.getTR();
        PACIENTE = consultaUsuario.getPACIENTE();
        MEDICO = consultaUsuario.getMEDICO();
        PAGO = consultaUsuario.getPAGO();
        ESTACION = consultaUsuario.getESTACION();
        SECCION = consultaUsuario.getSECCION();
        AUDIO = consultaUsuario.getAUDIO();
        CONTRASENA = consultaUsuario.getCONTRASENA();
        NOMBREENFERMERA = consultaUsuario.getNOMBREENEFERMERA();
        //FECHAI = consultaUsuario.getFECHAINGRESO();
        //ESTATUS = consultaUsuario.getESTATUS();
    }
    /////////////////////////////////////////////////////////////////////////////
    public void login() {
        login login = new login();
        login.show(getSupportFragmentManager(), "INICIO SESION");
    }
    public void loginSer() {
        tiempo.cancel();
        tiempo.purge();
        loginservicio loginservicio = new loginservicio();
        loginservicio.show(getSupportFragmentManager(), "INICIO SESION");
    }
    public void logout() {
        logout logout = new logout();
        logout.show(getSupportFragmentManager(), "CERRAR SESION");
    }
    public void info(){
        infoextens info = new infoextens();
        info.setArguments(infobundle);
        info.show(getSupportFragmentManager(), "info extens");
    }
    public void applyTexts(String usuario, String contraseña, String seccion) {
        a=2;
        USUARIOINS=usuario;
        CONTRASENAINSERTADA=contraseña;
        SECCIONINS=seccion;
        url1 = "http://" + ip + "/BDEJEMPLOS/CONSULTAENFERMERAS.php?USER="+usuario;
        url1 = url1.replace(" ", "%20");
        jsonrequest = new JsonObjectRequest(Request.Method.POST, url1, null, this, this);
        request1.add(jsonrequest);
    }
    public void applyTexts2(String usuario, String contraseña, String seccion) {
        a=3;
        USUARIOUT=usuario;
        CONTRASENAINSERTADA=contraseña;
        SECCIONINS=seccion;
        url1 = "http://" + ip + "/BDEJEMPLOS/CONSULTAENFERMERAS.php?USER="+usuario;
        url1 = url1.replace(" ", "%20");
        jsonrequest = new JsonObjectRequest(Request.Method.POST, url1, null, this, this);
        request1.add(jsonrequest);



    }
    public void applyTexts1(String usuario, String contraseña) {

        if (usuario.equals("user") == true && contraseña.equals("1234") == true) {
            Intent intent = new Intent(getApplicationContext(), modificarActivity.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "SESION SERVICIO INICIADA", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "USUARIO O CONTRASEÑA INCORRECTA", Toast.LENGTH_SHORT).show();
        }
    }
    ////////////////////////////////////////////////////////////////////////
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
    public void alertaBasededatos(){
        AlertDialog.Builder noeventos = new AlertDialog.Builder(this);
        noeventos.setTitle("ERROR!");
        noeventos.setMessage("INFORMACION DE BASE DE DATOS NO COINCIDE");
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
        handler.postDelayed(runnable, 2000);
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
        handler.postDelayed(runnable, 1500);
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
        handler.postDelayed(runnable, 1500);
    }
    public void insetenfermera(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                url1 = "http://" + ip + "/BDEJEMPLOS/INSERTENFERMERA.php?HABITACION="+HABITACION+"&PACIENTE="+PACIENTE+"&FECHA="+FECHA+"&HORA="+HORA+"&TIPODELLAMADO="+TIPODELLAMADO+"&NOMBREENFERMERA="+NOMBREENFERMERA;
                url1 = url1.replace(" ", "%20");
                StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
                };
                MyRequestQueue.add(MyStringRequest);
            }
        }, 200);
    }

}

/* handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                }
            }, 200);
 */

