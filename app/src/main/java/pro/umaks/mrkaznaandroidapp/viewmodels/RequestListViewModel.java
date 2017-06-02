package pro.umaks.mrkaznaandroidapp.viewmodels;

import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;

import pro.umaks.mrkaznaandroidapp.Services.MRRestService;
import pro.umaks.mrkaznaandroidapp.models.RequestModel;

/**
 * Created by kholkin on 29.05.2017.
 */
public class RequestListViewModel extends ViewModel
{
    public ArrayList<RequestModel> Requests;
    public String Subtitle;

    public RequestListViewModel() {
    }

    public void FillRequestsList(String login, String pin)
    {
        try {
            MRRestService service = new MRRestService();
            service.execute(login, pin);
            Requests = service.get();
        }
        catch (Exception e)
        {}
        Subtitle = "У меня на согласовании";
    }

}
