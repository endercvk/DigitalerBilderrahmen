package haw_landshtu.de.digitalerbilderrahmen;

/**
 * Created by x403f on 05.03.2018.
 */
public class ImageObject {

    private String id;
    private String date;
    private String uri;
    private String foldername;




    public ImageObject(String id,String foldername, String date, String uri){
        setId(id);
        setFoldername(foldername);
        setDate(date);
        setUri(uri);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getFoldername() {return foldername;}

    public void setFoldername(String foldername) {this.foldername = foldername;}














}
