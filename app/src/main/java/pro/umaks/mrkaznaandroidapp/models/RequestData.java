package pro.umaks.mrkaznaandroidapp.models;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
/**
 * Created by ivtla on 25.05.2017.
 */

public class RequestData {
    @SerializedName("Requests")
    public ArrayList<RequestModel> Requests;
    public RequestData() {
    }

    public void setRequests(ArrayList<RequestModel> Requests) {
        this.Requests = Requests;
    }
}
