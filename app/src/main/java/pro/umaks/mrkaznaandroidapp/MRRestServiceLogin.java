package pro.umaks.mrkaznaandroidapp;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by ivtla on 14.05.2017.
 */

public class MRRestServiceLogin  extends AsyncTask<String, Void, String>
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
    protected String doInBackground(String... login)
    {
        HttpURLConnection urlConnection = null;
        String result = "";
        try
        {
            URL svcUrl = new URL(Constants.SVC_PATH + "reg/" + login );
            urlConnection = (HttpURLConnection) svcUrl.openConnection();
            urlConnection.setConnectTimeout(5000);

            int statusCode = urlConnection.getResponseCode();
            if (statusCode != HttpURLConnection.HTTP_OK)
            {
                //Обработки ошибки соединения
                return null;
            }
            InputStream inStream = new BufferedInputStream(urlConnection.getInputStream());
            result = getResponseText(inStream);
        }
        catch (MalformedURLException e)
        {

        }
        catch (IOException e)
        {
            // could not read response body
            // (could not create input stream)
        }

        finally {

        }
        return result;
    }
    @Override
    protected void onPostExecute(String result)
    {
        super.onPostExecute(result);
    }

}



