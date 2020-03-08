package SPEC.PKG;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
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

    String NOMBRE,PRIMERA,SEGUNDOA,TURNOS, url1;
    int TURNO=0;
    Button REGRESAR, AGREGAR,BORRAR;
    RadioButton MANANA,TARDE,NOCHE;
    EditText INSERTNOMBRE,INSERTPRIMERA,INSERTSEGUNDOA;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);////////////////marcaba error solo por dar gusto al usuario en la orientacion no problem
        setContentView(R.layout.activity_modificar);

        REGRESAR = (Button) findViewById(R.id.REGRESAR);
        AGREGAR = (Button) findViewById(R.id.AGREGAR);
        BORRAR = (Button) findViewById(R.id.BORRAR);
        MANANA =(RadioButton) findViewById(R.id.TURNOMANANA);
        TARDE =(RadioButton) findViewById(R.id.TURNOTARDE);
        NOCHE =(RadioButton) findViewById(R.id.TURNONOCHE);
        INSERTNOMBRE = (EditText) findViewById(R.id.NOMBRE);
        INSERTPRIMERA = (EditText) findViewById(R.id.PRIMERA);
        INSERTSEGUNDOA = (EditText) findViewById(R.id.SEGUNDOA);

        REGRESAR.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent2 = new Intent(v.getContext(), MainActivity.class);
                Bundle extras = getIntent().getExtras();
                startActivityForResult(intent2, 0);
            }
        });
        BORRAR.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (MANANA.isChecked() == true){
                    TURNO =1;
                }
                if (TARDE.isChecked() == true){
                    TURNO=2;
                }
                if (NOCHE.isChecked() == true){
                    TURNO=3;
                }
                NOMBRE = INSERTNOMBRE.getText().toString();
                PRIMERA = INSERTPRIMERA.getText().toString();
                SEGUNDOA = INSERTSEGUNDOA.getText().toString();
                Toast.makeText(getApplicationContext(), NOMBRE +" "+ PRIMERA+" "+ SEGUNDOA, Toast.LENGTH_SHORT).show();
                INSERTNOMBRE.setText("");
                INSERTPRIMERA.setText("");
                INSERTSEGUNDOA.setText("");
                alert2();
            }
        });

        AGREGAR.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (MANANA.isChecked() == true){
                    TURNO =1;
                }
                if (TARDE.isChecked() == true){
                    TURNO=2;
                }
                if (NOCHE.isChecked() == true){
                    TURNO=3;
                }
                NOMBRE = INSERTNOMBRE.getText().toString();
                PRIMERA = INSERTPRIMERA.getText().toString();
                SEGUNDOA = INSERTSEGUNDOA.getText().toString();
                TURNOS=String.valueOf(TURNO);
                INSERTNOMBRE.setText("");
                INSERTPRIMERA.setText("");
                INSERTSEGUNDOA.setText("");
                alert();
            }
        });
    }
    public void agregar() {
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        if (TURNO == 1) {
            url1 = "http://192.168.0.15/BDSEP/INSERTENFERMERAS.PHP?NOMBRE=" + NOMBRE + "&PRIMERA=" + PRIMERA + "&SEGUNDOA=" + SEGUNDOA;
        }
        if (TURNO == 2) {
             url1 = "http://192.168.0.15/BDSEP/INSERTENFERMERASTARDE.PHP?NOMBRE=" + NOMBRE + "&PRIMERA=" + PRIMERA + "&SEGUNDOA=" + SEGUNDOA;
        }
        if (TURNO == 3) {
             url1 = "http://192.168.0.15/BDSEP/INSERTENFERMERASNOCHE.PHP?NOMBRE=" + NOMBRE + "&PRIMERA=" + PRIMERA + "&SEGUNDOA=" + SEGUNDOA;
        }
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
    public void borrar() {
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        String url1 = "http://192.168.0.15/BDSEP/ELIMINARENFERMERAS.php?NOMBRE=" + NOMBRE + "&PRIMERA=" + PRIMERA + "&SEGUNDOA=" + SEGUNDOA+"&TURNO="+TURNO;
        url1 = url1.replace(" ", "%20");
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                Toast.makeText(getApplicationContext(), "ENFERMERA BORRADA", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "NO SE BORRO ENFERMERA-ERROR EN WEBSERVICE", Toast.LENGTH_SHORT).show();
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
                agregar();
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
                borrar();
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
}
