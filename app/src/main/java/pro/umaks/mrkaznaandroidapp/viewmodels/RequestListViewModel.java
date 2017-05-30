package pro.umaks.mrkaznaandroidapp.viewmodels;

import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import pro.umaks.mrkaznaandroidapp.Services.MRRestService;
import pro.umaks.mrkaznaandroidapp.models.RequestModel;

/**
 * Created by kholkin on 29.05.2017.
 */
public class RequestListViewModel extends ViewModel
{
    public ArrayList<RequestModel> Requests;

    public RequestListViewModel() throws ExecutionException, InterruptedException {
        if (Requests == null)
        {
            MRRestService service = new MRRestService();
            service.execute("sapryshina", "1111"); //TODO login & Pin
            Requests = service.get();
        }
    }

}
