package pro.umaks.mrkaznaandroidapp.models;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Created by ivtla on 25.05.2017.
 */

public class RequestData {
    @SerializedName("Requests")
    public ArrayList<RequestModel> Requests = new ArrayList<RequestModel>();
    public static final Map<String, RequestModel> ITEM_MAP = new HashMap<String, RequestModel>();
    public RequestData() {
    }

    public void setRequests(ArrayList<RequestModel> Requests) {
        this.Requests = Requests;
    }
}
