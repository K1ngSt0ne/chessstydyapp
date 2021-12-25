package com.example.chessstudyappver11;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.HashMap;

public class FigureTransformDialog extends DialogFragment {
    private final String TAG = "ChessGame";
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final String[] figureNamesArray = {"Ферзь", "Ладья", "Слон", "Конь"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Выберите, во что превратить пешку")
                // добавляем переключатели
                .setItems(figureNamesArray,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,int item) {
                                getData.getData(figureNamesArray[item]);

                            }
                        });


        return builder.create();
    }

    private GettingDataFromDialog getData;


    public FigureTransformDialog(GettingDataFromDialog getData) {
        this.getData = getData;
    }


}
