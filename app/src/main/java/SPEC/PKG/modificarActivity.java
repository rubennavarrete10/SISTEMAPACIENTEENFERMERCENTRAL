package SPEC.PKG;
import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import androidx.appcompat.app.AppCompatActivity;


public class modificarActivity extends AppCompatActivity {
    TextView fecha;
    TextView hora;
    TextView recovoz;
    SimpleDateFormat horaFormat = new SimpleDateFormat("HH:mm:ss");
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    Date horaD, horaD1, horaD0;
    Date date;
    String D0, D1;
    String horafinal;
    String turno;
    String fechafinal;
    String habitacion = "101";
    String TiempoRES="SIN RESPUESTA";
    String enfermera = "LAURA MARTINEZ ESPINOSA";
    long numEvento = 1;
    long idEorA,difh, difm, difs = 0;
    private String outputFile = null;
    MediaRecorder miGrabacion = null;
    private MediaPlayer player;
    private boolean presionado = false;
    private Object VolleySingleton;


    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);////////////////marcaba error solo por dar gusto al usuario en la orientacion no problem
        setContentView(R.layout.activity_modificar);

    }
}
