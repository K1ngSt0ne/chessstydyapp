package com.example.chessstudyappver11;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class TaskSolveDialog extends DialogFragment {
    @NonNull
    @Override

    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String button1String = button_one_str;//"В меню";
        String button2String = button_two_str;//"Попробовать еще раз/к следующей задаче";

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title_one);  // заголовок
        builder.setMessage(message_one); // сообщение
        builder.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                return_to_screen.IsReturn(false, button1String);
            }
        });
        builder.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                return_to_screen.IsReturn(true, button2String);
            }
        });
        builder.setCancelable(true);

        return builder.create();
    }
    private String title_one;
    private String message_one;
    private String button_one_str;
    private String button_two_str;
    private Activity close_activity;
    private CheckReturnToScreen return_to_screen;


    public TaskSolveDialog(String title_one, String message_one, Activity close_activity, String button_one_str, String button_two_str, CheckReturnToScreen return_to_screen) {
        this.title_one = title_one;
        this.message_one = message_one;
        this.close_activity = close_activity;
        this.button_one_str = button_one_str;
        this.button_two_str = button_two_str;
        this.return_to_screen = return_to_screen;
    }
}
