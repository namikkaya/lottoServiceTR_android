package com.example.namikkaya.lottoservicetr_android.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.namikkaya.lottoservicetr_android.Fragment.checkFragment;
import com.example.namikkaya.lottoservicetr_android.R;
import com.example.namikkaya.lottoservicetr_android.model.dateData;

public class simpleDialog extends AppCompatDialogFragment {
    private simpleDialogDelegate delegate;

    private static final String MSG = "msg";

    private String msg;

    public simpleDialog() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            msg = getArguments().getString(MSG);
        }
    }

    public static simpleDialog newInstance(String msg) {
        simpleDialog fragment = new simpleDialog();
        Bundle bundle = new Bundle();
        bundle.putString(MSG, msg);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_delete,null);
        TextView lucky = (TextView) view.findViewById(R.id.dialogLuckyNumbers);
        lucky.setText(msg);

        String dialogTitleWarn = getString(R.string.dialog_warn);
        String cancelButton = getString(R.string.dialog_no_btn);
        String yesButton = getString(R.string.dialog_yes_btn);
        builder.setView(view)
                .setTitle(dialogTitleWarn)
                .setNegativeButton(cancelButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delegate.negativeButtonEvent();
                    }
                })
                .setPositiveButton(yesButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delegate.positiveButtonEvent();
                    }
                });

        return builder.create();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            delegate = (simpleDialogDelegate) context;
        }catch (Exception e){
            throw new ClassCastException(context.toString() + " simpleDialogDelegate implement ");
        }

    }

    public interface simpleDialogDelegate{
        void positiveButtonEvent();
        void negativeButtonEvent();
    }
}
