package grant.touristapp;

import android.app.DialogFragment;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ArrowKeyMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class JournalEntryActivity extends AppCompatActivity implements EditJournalEntry.EditJournalEntryDialogListener, DeleteJournal.DeleteJournalDialogListener {

    Button btn_editEntry, btn_delete;
    TextView journalList;
    SQLiteDatabase dtb;
    DBHandler db;
    private String TAG = "EditEntry";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_entry);

        db = new DBHandler(this);

        btn_editEntry = (Button)findViewById(R.id.btn_editEntry);
        btn_delete = (Button)findViewById(R.id.btn_delete);

        journalList = (TextView)findViewById(R.id.entriesList);


        btn_editEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditJournalEntry editDialog = new EditJournalEntry();
                editDialog.show(getFragmentManager(), TAG);
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteJournal deleteDialog = new DeleteJournal();
                deleteDialog.show(getFragmentManager(), TAG);
            }
        });

        journalList.setMovementMethod(ArrowKeyMovementMethod.getInstance());
        journalList.setText("");
        journalList.setPadding(5,2,5,2);

        List<JournalEntry> journalsList = db.getAllEntries();

        for(JournalEntry journal : journalsList){

            String journalDetail = "\n\nID: "+ journal.getId()+"\n\tTitle: " + journal.getTitle() + "\n\tNotes: "+ journal.getNotes()
                    + "\n'tLatitude" + journal.getLAT() +"\n\tLongtitude" + journal.getLNG();
            journalList.append("\n"+journalDetail);
        }
    }

    @Override
    public void onEditButtonClick(DialogFragment dialog) {

        EditText entID = (EditText) dialog.getDialog().findViewById(R.id.edt_updateID);
        String idNo = entID.getText().toString();
        int int_idNo = Integer.parseInt(entID.getText().toString());

        EditText entTitle = (EditText) dialog.getDialog().findViewById(R.id.edt_updateTitle);
        String title = entTitle.getText().toString();

        EditText entNotes = (EditText) dialog.getDialog().findViewById(R.id.edt_updateNotes);
        String notes = entNotes.getText().toString();

        boolean check_ID = checkID(idNo);

        boolean check_title = checkTitle(title);

        boolean check_notes = checkNotes(notes);

        if(check_ID == false || check_title == false || check_notes == false){
            Toast.makeText(getApplicationContext(), "Enter Data Again..", Toast.LENGTH_LONG).show();
        }else{

            boolean updateCheck = db.updateJournalInfo(int_idNo, title, notes);

            if(updateCheck == true) {
                Toast.makeText(getApplicationContext(), "Journal entry updated..", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(getApplicationContext(), "Update failed..", Toast.LENGTH_LONG).show();
            }
        }


    }

    public boolean checkID(String ID_NO){
        if(ID_NO == ""){
            return false;
        }else{
            return true;
        }
    }

    public boolean checkTitle(String jrnlTitle){
        if(jrnlTitle == ""){
            return false;
        }else{
            return true;
        }
    }

    public boolean checkNotes(String jrnlNotes){
        if(jrnlNotes == ""){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void onDeleteButtonClick(DialogFragment dialog) {

        EditText entID = (EditText) dialog.getDialog().findViewById(R.id.edt_deleteID);
        String idNo = entID.getText().toString();
        int int_idNo = Integer.parseInt(entID.getText().toString());

        boolean check_idNo = checkID(idNo);

        if(check_idNo == false){

            Toast.makeText(getApplicationContext(), "Enter valid ID", Toast.LENGTH_SHORT).show();
        }else{

            boolean deleteCheck = db.deleteJournal(int_idNo);

            if(deleteCheck == true){
                Toast.makeText(getApplicationContext(), "Journal deleted succesfully", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(), "Journal deletion failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
