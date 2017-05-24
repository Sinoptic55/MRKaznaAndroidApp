package pro.umaks.mrkaznaandroidapp.Services;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import pro.umaks.mrkaznaandroidapp.Constants;

/**
 * Created by ivtla on 14.05.2017.
 */

public class MRRestService extends AsyncTask<String, Void, String>
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
    protected String doInBackground(String... params)
    {
        String jsonString = GetRequestsJSON(params[0], params[1]);
        JSONObject jObject = null;
        JSONArray jRequestArray = null;
        try {
            jObject = new JSONObject(jsonString);
            jRequestArray = jObject.getJSONArray("RequestData");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        String result = "";
        return result;
    }
    @Override
    protected void onPostExecute(String result)
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



