package com.example.zaimi.oving_5;


import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

class Http {

    private String urlToServer;
    private HttpRequestType typeOfRequest;
    private final String TAG = "HTTPWRAPPER";
    final String ENCODING = "ISO-8859-1";//should be this as most web-servers expect this (with GET requests)

    public static enum HttpRequestType { //Used to decide which HTTP request method to use
        HTTP_GET, HTTP_POST, HTTP_GET_WITH_HEADER_IN_RESPOMSE
    }

    public Http(String urlToServer){
        this.urlToServer = urlToServer;
        CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
    }

    public void runHttpRequestInThread(HttpRequestType typeOfRequest, Map<String, String> requestValues, Callback callback){
        this.typeOfRequest = typeOfRequest;
        new RequestThread(callback).execute(requestValues);//start new thread for each request - can't restart finished thread
    }

    /*Thread class for executing one of the HTTP request methods (below) */
    private class RequestThread extends AsyncTask<Map<String, String>,String,String> {

        private Callback callback;

        public RequestThread(Callback callback) { this.callback = callback;}

        /* Thread method */
        protected String doInBackground(Map<String,String>... v){
            try{
                if (typeOfRequest == HttpRequestType.HTTP_GET) 			return httpGet(v[0]);
                else if (typeOfRequest == HttpRequestType.HTTP_POST) 	return httpPost(v[0]);
                else 													return httpGetWithHeader(v[0]);
            }catch(Exception e){
                Log.e(TAG, e.getMessage());
                return e.getMessage();//return errorMessage
            }
        }

        protected void onPostExecute(String response){
            callback.run(response);
        }
    }/*END THREAD CLASS */

    /* Sends HTTP GET request and returns body of response from server as String
     * This method should be done in its own thread.
     * */
    public String httpGet(Map<String,String> parameterList) throws IOException {
        Log.i(TAG, "**********************  START GET ************************'");
        String url = urlToServer + "?" + encodeParameters(parameterList);
        URLConnection connection = new URL(url).openConnection();
        connection.setRequestProperty("Accept-Charset", ENCODING);
        try (InputStream response = connection.getInputStream()){//connection auto close
            return this.readResponseBody(response,getCharSet(connection));
        }
    }

    /* Sends HTTP POST request and returns body of response from server as String
     * This method should be done in its own thread.
     * */
    public String httpPost(Map<String,String> parameterList) throws IOException {
        Log.i(TAG, "**********************  START POST ************************'");
        URLConnection connection = new URL(urlToServer).openConnection();
        connection.setDoOutput(true); // Triggers POST.
        connection.setRequestProperty("Accept-Charset", ENCODING);
        //tell server what type of data we're sending -> string below is standard
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + ENCODING);



        try (OutputStream output = connection.getOutputStream()) {//auto close
            String postString = encodeParameters(parameterList);
            output.write(postString.getBytes(ENCODING));//sending with enchoding in ISO-8859-1
        }

        try (InputStream is = connection.getInputStream()) { //auto close
            String charset = this.getCharSet(connection);//charset/encoding in response from server
            return readResponseBody(is, charset);
        }
    }

    /* Sends HTTP GET request and returns body of response from server as HTTPResponse object.
     * The response object contains HTTP headers and body.
     * Also notice that in this case the body is read line by line from a stream (in.readLine()).
     * In most cases one of the above methods should suffice.
     * */
    public String httpGetWithHeader(Map<String,String> parameterList) throws IOException {
        Log.i(TAG, "**********************  START GET WITH HEADER ************************'");
        String url = urlToServer + "?" + encodeParameters(parameterList);
        String responseStr = "";
        URLConnection connection = new URL(url).openConnection();
        connection.setRequestProperty("Accept-Charset", ENCODING);

        /* Get the HTTP-header and add all names/values to response **/
        for (Map.Entry<String, List<String>> header : connection.getHeaderFields().entrySet()) {
            responseStr += header.getKey() + "=" + header.getValue();
        }

        try (InputStream response = connection.getInputStream()){//connection auto close
            responseStr += readResponseBody(response,getCharSet(connection));
        }

        return responseStr;
    }

    private String encodeParameters(Map<String,String> parameterList){
        String s = "";
        for(String key : parameterList.keySet()){
            String value = parameterList.get(key);
            try {
                s += URLEncoder.encode(key, ENCODING);
                s += "=";
                s += URLEncoder.encode(value, ENCODING);
                s += "&";
            }catch(UnsupportedEncodingException e){
                Log.e(TAG, e.toString());
            }
        }

        return s;
    }

    private String readResponseBody(InputStream is, String charset) {
        String body = "";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, charset))) {
            for (String line; (line = reader.readLine()) != null; ) {
                body += line;
            }
        }catch(Exception e){
            Log.e(TAG, e.toString());
            body += "************** Problems reading from server *****************";
        }
        return body;
    }

    private String getCharSet(URLConnection connection){
        String contentType = connection.getHeaderField("Content-Type");
        String charset = null;

        for (String param : contentType.replace(" ", "").split(";")) {
            if (param.startsWith("charset=")) {
                charset = param.split("=", 2)[1];
                break;
            }
        }
        Log.i(TAG,"Contentcont-type from server: " + contentType + " ***************************");
        Log.i(TAG,"Encoding/charset = " + charset);
        return charset;
    }

    public interface Callback {
        public void run(String response);
    }
}