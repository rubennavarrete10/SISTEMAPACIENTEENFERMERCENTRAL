package SPEC.PKG;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;


public class modificarActivity extends AppCompatActivity {

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);////////////////marcaba error solo por dar gusto al usuario en la orientacion no problem
        setContentView(R.layout.activity_modificar);

        Button REGRESAR = (Button) findViewById(R.id.REGRESAR);

        REGRESAR.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent2 = new Intent(v.getContext(), MainActivity.class);
                Bundle extras = getIntent().getExtras();
                startActivityForResult(intent2, 0);
            }
        });
    }
}
