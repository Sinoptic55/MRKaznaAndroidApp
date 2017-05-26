package pro.umaks.mrkaznaandroidapp.Services;

import android.os.AsyncTask;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import pro.umaks.mrkaznaandroidapp.Constants;
import pro.umaks.mrkaznaandroidapp.models.RequestData;
import pro.umaks.mrkaznaandroidapp.models.RequestModel;

/**
 * Created by ivtla on 14.05.2017.
 */

public class MRRestService extends AsyncTask<String, Void, ArrayList<RequestModel>>
{
    private static String getResponseText(InputStream inStream) {
        // very nice trick from
        // http://weblogs.java.net/blog/pat/archive/2004/10/stupid_scanner_1.html
        return new Scanner(inStream).useDelimiter("\\A").next();
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }
    @Override
    protected ArrayList<RequestModel> doInBackground(String... params)
    {
        String jsonString = GetRequestsJSON(params[0], params[1]);
        JSONObject jObject = null;
        JSONObject jRequestArray = null;
        try {
            jObject = new JSONObject(jsonString);
            jRequestArray = jObject.getJSONObject("RequestData");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        RequestData rl;
        rl = new Gson().fromJson(jRequestArray.toString(), RequestData.class);
        return rl.Requests;
    }
    @Override
    protected void onPostExecute(ArrayList<RequestModel> result)
    {
        super.onPostExecute(result);
    }

    private String GetRequestsJSON(String login, String pin)
    {
        HttpURLConnection urlConnection = null;

        String result = "";
        try {
            URL svcUrl = new URL(Constants.SVC_PATH + "requests/" + login);// + "/" + pin); TODO Добавить работу с пином
            urlConnection = (HttpURLConnection) svcUrl.openConnection();
            urlConnection.setDoOutput(false);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            //urlConnection.setConnectTimeout(100000);

            //int statusCode = urlConnection.getResponseCode();
            //if (statusCode != HttpURLConnection.HTTP_OK)
            //{
            //Обработки ошибки соединения
            //  return null;
            //}
            InputStream inStream = new BufferedInputStream(urlConnection.getInputStream());
            result = getResponseText(inStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return result;
    }
}



