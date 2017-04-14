package grant.touristapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

/**
 * Created by Grant on 21/03/2017.
 */

public class DeleteJournal extends DialogFragment {

    interface DeleteJournalDialogListener{

        void onDeleteButtonClick(DialogFragment dialog);

        }

        DeleteJournalDialogListener deleteJournalListener;
        Context context;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        try{
            deleteJournalListener = (DeleteJournalDialogListener) activity;

        }catch(ClassCastException e){
            throw new ClassCastException(activity.toString()
                +" must implement DeleteStudentDialogListener");
        }
    }

    public Dialog onCreateDialog(Bundle savedIntsanceState){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.delete_journal, null))

                .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteJournalListener.onDeleteButtonClick(DeleteJournal.this);
                    }
                })

                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DeleteJournal.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }

}

