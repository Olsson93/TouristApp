package grant.touristapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Grant on 19/03/2017.
 */

public class MarkerDialog extends DialogFragment{



    String markerClicked;
    String message;




    interface MarkerDialogListener{

        void onMarkerDialogClick(DialogFragment dialog);
        void onRouteClick(DialogFragment dialog);
    }

    MarkerDialogListener markerDialogListener;
    Context context;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        try{
            markerDialogListener = (MarkerDialogListener) activity;
        }catch(ClassCastException e){
            throw new ClassCastException(activity.toString()
                        + "must implement MarkerDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.marker_form,null))
                .setPositiveButton("ADD TO ROUTE", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        markerDialogListener.onRouteClick(MarkerDialog.this);
                    }
                 })
                .setNegativeButton("CLOSE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        MarkerDialog.this.getDialog().cancel();
                    }
                });

            builder.setTitle(getMarkerClicked());
            builder.setMessage(getMessage());
            builder.setIcon(R.drawable.common_google_signin_btn_icon_dark);



        return builder.create();


    }

    public String getMarkerClicked() {
        return markerClicked;
    }

    public void setMarkerClicked(String markerClicked) {
        this.markerClicked = markerClicked;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
