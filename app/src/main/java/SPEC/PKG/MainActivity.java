package SPEC.PKG;
import android.Manifest;
import android.app.Application;
import android.content.ActivityNotFoundException;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.widget.Button;
import android.widget.TextView;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Date;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private static final int RECOGNIZE_SPEECH_ACTIVITY = 1;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

}
