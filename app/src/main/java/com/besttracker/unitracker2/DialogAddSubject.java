package com.besttracker.unitracker2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.besttracker.unitracker2.R;

/**
 * Created by psinc_000 on 28/02/2015.
 */
public class DialogAddSubject extends DialogFragment {

    EditText edtSubject;
    EditText edtTime;
    Button btnSave;
    Button btnCancel;

    public interface GetSubjectDataListener {
        void onSaveSubjectDialog(String subject, String time);
    }


    /**
     * The system calls this to get the DialogFragment's layout, regardless
     * of whether it's being displayed as a dialog or an embedded fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout to use as dialog or embedded fragment
        View view = inflater.inflate(R.layout.dialog_add_subject, container);
        edtSubject = (EditText) view.findViewById(R.id.dAddSubject_edt_subject);
        edtTime = (EditText) view.findViewById(R.id.dAddSubject_edt_time);
        btnSave = (Button) view.findViewById(R.id.dAddSubject_btn_save);
        btnCancel = (Button)view.findViewById(R.id.dAddSubject_btn_cancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GetSubjectDataListener activity = (GetSubjectDataListener) getActivity();
                activity.onSaveSubjectDialog(edtSubject.getText().toString(), edtTime.getText().toString());
                dismiss();
            }
        });

        btnCancel.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }

    /* The system calls this only when creating the layout in a dialog. */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.

        final Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }
}
