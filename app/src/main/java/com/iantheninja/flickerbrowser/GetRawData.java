package com.iantheninja.flickerbrowser;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//https://api.flickr.com/services/feeds/photos_public.gne?tags=android,lollipop&format=json&nojsoncallback=1

enum DownloadStatus { IDLE, PROCESSING, NOT_INITIALIZED, FAILED_OR_EMPTY, OK }
 public class GetRawData {
     private String LOG_TAG = GetRawData.class.getSimpleName();
     private String rawUrl;
     private String data;
     private DownloadStatus downloadStatus;

     public GetRawData(String rawUrl) { //constructor
         this.rawUrl = rawUrl;
         this.downloadStatus = DownloadStatus.IDLE;
     }

     public void reset(){ // clears data, download status and removes url
         this.downloadStatus = DownloadStatus.IDLE;
         this.rawUrl = null;
         this.data = null;
     }

     public DownloadStatus getDownloadStatus() {
         return downloadStatus;
     }

     public String getData() {
         return data;
     }

     public void setRawUrl(String rawUrl) {
         this.rawUrl = rawUrl;
     }

     public void execute(){//executes the class code
         this.downloadStatus = DownloadStatus.PROCESSING;
         DownloadRawData downloadRawData = new DownloadRawData();
         downloadRawData.execute(rawUrl);
     }

     public class DownloadRawData extends AsyncTask<String, Void, String>{

         protected void onPostExecute(String webData){
             data = webData;
             Log.v(LOG_TAG, "Data returned was: "+data);
             if (data == null){ //if no data was decieved from url
                 if (rawUrl == null){ // and the reason was a raw url
                     downloadStatus = DownloadStatus.NOT_INITIALIZED; //show that url was not passed
                 }else { //else theres an error with the url
                     downloadStatus = DownloadStatus.FAILED_OR_EMPTY;
                 }
             }else {
                 //success
                 downloadStatus = DownloadStatus.OK;
             }
         }

         @Override
         protected String doInBackground(String... params) {
             HttpURLConnection urlConnection = null;
             BufferedReader bufferedReader = null;

             if (params == null){
                 return null;
             }

             try {
                 URL url = new URL(params[0]);
                 urlConnection = (HttpURLConnection) url.openConnection();
                 urlConnection.setRequestMethod("GET");
                 urlConnection.connect();

                 InputStream inputStream = urlConnection.getInputStream();
                 if (inputStream == null){
                     return null;
                 }
                 StringBuffer stringBuffer = new StringBuffer();

                 bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                 String line;
                 while ((line = bufferedReader.readLine()) != null){ //if not empty
                    stringBuffer.append(line+"\n");// insert the next line to buffer
                 }
                 return stringBuffer.toString();

             }catch (IOException e){
                 Log.e(LOG_TAG, "Error ", e);
                 return null;
             }finally {
                 if (urlConnection != null){
                     urlConnection.disconnect();
                 }
                 if (bufferedReader != null){
                     try {
                         bufferedReader.close();
                     } catch (final IOException e) {
                         Log.e(LOG_TAG, "Error closing stream ",e);
                     }
                 }
             }
         }
     }
 }

