package SPEC.PKG;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;


public class infoextens extends AppCompatDialogFragment {
    String hb,pac,fecha,pagos,medicos;
    TextView hbt,pact,fechat,pagot,medicot;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.infoextens, null);
        builder.setView(view);
        if (getArguments() != null){
            hb=getArguments().getString("hb","N/A");
            pac=getArguments().getString("pac","N/A");
            fecha=getArguments().getString("fei","N/A");
            pagos=getArguments().getString("med","N/A");
            medicos=getArguments().getString("pag","N/A");
        }
        hbt = (TextView)view.findViewById(R.id.textView18);
        pact = (TextView)view.findViewById(R.id.textView5);
        fechat = (TextView)view.findViewById(R.id.textView10);
        pagot = (TextView)view.findViewById(R.id.textView15);
        medicot = (TextView)view.findViewById(R.id.textView12);
        hbt.setText(hb);
        pact.setText(pac);
        fechat.setText(fecha);
        pagot.setText(pagos);
        medicot.setText(medicos);
        return builder.create();


    }

}