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
 * Created by Grant on 20/03/2017.
 */

public class EditJournalEntry extends DialogFragment{

    interface EditJournalEntryDialogListener{

        void onEditButtonClick(DialogFragment dialog);
    }

    EditJournalEntryDialogListener editJournalEntryDialogListener;
    Context context;

    public void onAttach(Activity activity){
        super.onAttach(activity);

        try{
            editJournalEntryDialogListener = (EditJournalEntryDialogListener) activity;
        }catch(ClassCastException e){
            throw new ClassCastException(activity.toString()
                +"must implement EditJournalEntryDialogListener");
        }
    }

    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.edit_journal_info, null))
                .setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editJournalEntryDialogListener.onEditButtonClick(EditJournalEntry.this);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditJournalEntry.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }
}
