package grant.touristapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by Grant on 18/03/2017.
 */

public class AddEntryDialog extends DialogFragment {

    ImageView edtImage;
    Uri image;
    DBHandler db;


    interface AddEntryDialogListener{

        void onSaveButtonClick(DialogFragment dialog);
        void onPhotoButtonClick(DialogFragment dialog);
    }

    AddEntryDialogListener addEntryListener;
    Context context;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        try{
            addEntryListener = (AddEntryDialogListener) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString()
                    + "must implement AddEntryDialogListener");
        }
    }

    public Dialog onCreateDialog(Bundle savedInstanceState){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View addEntryView = inflater.inflate(R.layout.entry_form, null);

        edtImage = (ImageView) addEntryView.findViewById(R.id.edtImage);
        edtImage.setImageURI(image);


        builder.setView(addEntryView)



                .setPositiveButton("ADD", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id){
                        addEntryListener.onSaveButtonClick(AddEntryDialog.this);
                    }
                })

                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        AddEntryDialog.this.getDialog().cancel();
                    }
                })
                .setNeutralButton("ADD PHOTO", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        addEntryListener.onPhotoButtonClick(AddEntryDialog.this);
                    }
                });


        return builder.create();
    }


    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }
}
