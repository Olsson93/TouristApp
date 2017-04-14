package grant.touristapp;

/**
 * Created by Grant on 18/03/2017.
 */

public class JournalEntry {

    int id;
    String title;
    String notes;
    double LAT;
    double LNG;
    byte[] image;

    public JournalEntry(){

    }

    public JournalEntry(int id, String title, String notes, double LAT, double LNG, String image){

        this.id = id;
        this.title = title;
        this.notes = notes;
        this.LAT = LAT;
        this.LNG = LNG;
    }

    public JournalEntry(String title, String notes, double LAT, double LNG){

        this.title = title;
        this.notes = notes;
        this.LAT = LAT;
        this.LNG = LNG;
    }

    public JournalEntry(String title, String notes, byte[] image){

        this.title = title;
        this.notes = notes;
        this.image = image;
    }

    public JournalEntry(String title, String notes){
        this.title = title;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public double getLAT() {
        return LAT;
    }

    public void setLAT(double LAT) {
        this.LAT = LAT;
    }

    public double getLNG() {
        return LNG;
    }

    public void setLNG(double LNG) {
        this.LNG = LNG;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
