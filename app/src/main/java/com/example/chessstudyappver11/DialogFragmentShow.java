package com.example.chessstudyappver11;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;



public class DialogFragmentShow extends DialogFragment  {
    @NonNull
    @Override

    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String button1String = "В меню";
        String button2String = "На доску";

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title_one);  // заголовок
        builder.setMessage(message_one); // сообщение
        builder.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                startActivity(new Intent(getContext(), MainActivity.class));
                close_activity.finish();

            }
        });
        builder.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getActivity(), "Вы вернулись", Toast.LENGTH_LONG)
                        .show();
            }
        });
        builder.setCancelable(true);

        return builder.create();
    }
    private String title_one;
    private String message_one;
    private Activity close_activity;

    public DialogFragmentShow(String title_one, String message_one, Activity close_activity) {
        this.title_one = title_one;
        this.message_one = message_one;
        this.close_activity = close_activity;
    }


}
