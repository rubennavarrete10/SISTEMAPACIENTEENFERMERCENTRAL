package SPEC.PKG;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class logout extends AppCompatDialogFragment {
    private EditText user,pass,sec;
    private Datoslogout listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.login, null);
        builder.setView(view).setTitle("INGRESE USUARIO, CONTRASEÑA Y SECCION ").setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setPositiveButton("CERRAR SESION", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String usuario = user.getText().toString();
                String contraseña = pass.getText().toString();
                String seccion = sec.getText().toString();
                listener.applyTexts2(usuario,contraseña,seccion);
            }
        });
        user=view.findViewById(R.id.usuario);
        pass=view.findViewById(R.id.contra);
        sec=view.findViewById(R.id.seccion);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (Datoslogout)context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() +"error");
        }

    }

    public interface Datoslogout{
        void applyTexts2(String usuario, String contraseña, String seccion);

    }
}
