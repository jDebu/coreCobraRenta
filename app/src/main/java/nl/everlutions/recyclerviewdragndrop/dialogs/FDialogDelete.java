package nl.everlutions.recyclerviewdragndrop.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import nl.everlutions.recyclerviewdragndrop.R;

/**
 * Created by jose on 13/09/15.
 */
public class FDialogDelete extends DialogFragment implements View.OnClickListener {
    private static  final String TAG = FDialogDelete.class.getSimpleName();
    private Button accept,cancel;
    DragEvent dragEvent;
    ViewGroup viewGroup;
    public FDialogDelete(DragEvent event) {
        this.dragEvent = event;
        //View view = (View) event.getLocalState();
        //ViewGroup owner = (ViewGroup) view.getParent();
        //owner.removeView(view);
        //owner.removeAllViews();
    }

    public FDialogDelete() {
    }

    public FDialogDelete(ViewGroup owner) {
        this.viewGroup = owner;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstance){
        return createDialog();
    }

    private AlertDialog createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_delete,null);
        builder.setView(v);
        accept = (Button)v.findViewById(R.id.aceptar);
        cancel = (Button)v.findViewById(R.id.cancelar);
        accept.setOnClickListener(this);
        cancel.setOnClickListener(this);
        return builder.create();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.aceptar:
                viewGroup.removeAllViews();
                Toast.makeText(getActivity(), "Aceptar", Toast.LENGTH_SHORT);
                dismiss();
                break;
            case R.id.cancelar:
                //retroceso
                Toast.makeText(getActivity(),"Cancelar",Toast.LENGTH_SHORT);
                dismiss();
                break;
        }
    }
}
