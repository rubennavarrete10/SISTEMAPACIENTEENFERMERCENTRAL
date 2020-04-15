package SPEC.PKG;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class modificarActivity extends AppCompatActivity {

    int LAYOUTCONFIG=0;
    ImageButton REGRESAR, ESTABLECER;
    RadioButton s1,s2,h4,h5,h6,si,no;
    EditText hs,noc,em;
    String alarm="",LY="",hss,nocs,ems;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    public static final String TEXT2 = "text2";
    public static final String TEXT3 = "text3";
    public static final String TEXT4 = "text4";
    public static final String TEXT5 = "text5";

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);////////////////marcaba error solo por dar gusto al usuario en la orientacion no problem
        setContentView(R.layout.activity_modificar);

        REGRESAR = (ImageButton) findViewById(R.id.imageButton5);
        ESTABLECER = (ImageButton) findViewById(R.id.imageButton4);

        hs = (EditText) findViewById(R.id.host);
        noc =(EditText)findViewById(R.id.nocentral);
        em = (EditText) findViewById(R.id.email);

        s1 = (RadioButton) findViewById(R.id.seccionc1);
        s2 = (RadioButton) findViewById(R.id.secccionc2);
        h4 = (RadioButton)findViewById(R.id.h4);
        h5 = (RadioButton)findViewById(R.id.h5);
        h6 = (RadioButton)findViewById(R.id.h6);
        si = (RadioButton)findViewById(R.id.si);
        no = (RadioButton)findViewById(R.id.no);




        REGRESAR.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                intent2.putExtra("hs", hss);
                intent2.putExtra("noc", nocs);
                intent2.putExtra("em", ems);
                intent2.putExtra("alarmasonido", alarm);
                intent2.putExtra("ly", LY);
                startActivityForResult(intent2, 0);
            }
        });
        ESTABLECER.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (s1.isChecked() == true){
                    if (h4.isChecked() == true){
                        LAYOUTCONFIG=14;
                    }
                    if (h5.isChecked() == true){
                        LAYOUTCONFIG=15;
                    }
                    if (h6.isChecked() == true){
                        LAYOUTCONFIG=16;
                    }
                }
                if (s2.isChecked() == true){
                    if (h4.isChecked() == true){
                        LAYOUTCONFIG=24;
                    }
                    if (h5.isChecked() == true){
                        LAYOUTCONFIG=25;
                    }
                    if (h6.isChecked() == true){
                        LAYOUTCONFIG=26;
                    }
                }
                if (si.isChecked() == true){
                    alarm="si";
                }
                if (no.isChecked() == true){
                    alarm="no";
                }
                LY= String.valueOf(LAYOUTCONFIG);
                hss=hs.getText().toString();
                nocs=noc.getText().toString();
                ems=em.getText().toString();
                saveData();
                Toast.makeText(getApplicationContext(), ""+hss+"\n"+nocs+"\n"+ems+"\n"+alarm+"\n"+LY, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void alert() {
        AlertDialog.Builder alertmodificarenfermeras = new AlertDialog.Builder(this);
        alertmodificarenfermeras.setTitle("CONFIGURACION INCOMPLETA");
        alertmodificarenfermeras.setMessage("RELLENE LOS DATOS FALTANTES");
        alertmodificarenfermeras.show();
    }
    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TEXT, hs.getText().toString());
        editor.putString(TEXT2, noc.getText().toString());
        editor.putString(TEXT3, em.getText().toString());
        editor.putString(TEXT4, alarm);
        editor.putString(TEXT5, LY);
        editor.apply();
        Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
    }

}
