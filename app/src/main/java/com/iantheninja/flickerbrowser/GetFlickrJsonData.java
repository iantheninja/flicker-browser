package com.iantheninja.flickerbrowser;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ian on 01/03/16.
 */
public class GetFlickrJsonData extends GetRawData{
    private String LOG_TAG = GetFlickrJsonData.class.getSimpleName();
    private List<Photo> mPhotos;
    private Uri mDestinationUri;

    public GetFlickrJsonData(String searchCriteria, Boolean matchAll){
        super(null);
        createAndUpdate(searchCriteria, matchAll);
        mPhotos = new ArrayList<Photo>();
    }

    //to enable the code below to execute
    public void execute(){
        super.setRawUrl(mDestinationUri.toString());
        DownloadJsonData downloadJsonData = new DownloadJsonData();
        Log.v(LOG_TAG, "Built uri = " +mDestinationUri.toString());

        //for it to execute
        downloadJsonData.execute(mDestinationUri.toString());
    }

    public boolean createAndUpdate(String searchCriteria, Boolean matchAll){
        final String FLICKR_API_BASE_URL = "https://api.flickr.com/services/feeds/photos_public.gne";
        final String TAGS_PARAM = "tags";
        final String TAGMODE_PARAM = "tagmode";
        final String FORMAT_PARAM = "format";
        final String NO_JSON_CALLBACK_PARAM = "nojsoncallback";

        mDestinationUri = Uri.parse(FLICKR_API_BASE_URL).buildUpon()
                .appendQueryParameter(TAGS_PARAM,searchCriteria)
                .appendQueryParameter(TAGMODE_PARAM, matchAll ? "ALL" : "ANY")
                .appendQueryParameter(FORMAT_PARAM, "json")
                .appendQueryParameter(NO_JSON_CALLBACK_PARAM, "1")
                .build();

        return mDestinationUri != null;
    }

    //processes the json data being received
    public void processResult(){
        if(getDownloadStatus() != DownloadStatus.OK ){
            Log.e(LOG_TAG, "Error downloading raw file");
            return;
        }

        //identifiers in the json data
        final String FLICKR_ITEMS = "items";
        final String FLICKR_TITLE = "title";
        final String FLICKR_MEDIA = "media";
        final String FLICKR_PHOTO_URL = "m";
        final String FLICKR_AUTHOR = "author";
        final String FLICKR_AUTHOR_ID = "author_id";
        final String FLICKR_LINK = "link";
        final String FLICKR_TAGS = "tags";

        try {
            // object for the json data we are receiving
            JSONObject jsonData = new JSONObject(getData());
            //finding the items array inside the json data
            JSONArray itemsArray = jsonData.getJSONArray(FLICKR_ITEMS);

            for (int i = 0; i < itemsArray.length(); i++){
                //gets each item in the array
                JSONObject jsonPhoto = itemsArray.getJSONObject(i);
                String title = jsonPhoto.getString(FLICKR_TITLE);
                String author = jsonPhoto.getString(FLICKR_AUTHOR);
                String authorId = jsonPhoto.getString(FLICKR_AUTHOR_ID);
                String link = jsonPhoto.getString(FLICKR_LINK);
                String tags = jsonPhoto.getString(FLICKR_TAGS);

                JSONObject jsonMedia = jsonPhoto.getJSONObject(FLICKR_MEDIA);
                String photoUrl = jsonMedia.getString(FLICKR_PHOTO_URL);

                //creating an object of Photo class which we will instantiate with values
                Photo photoObject = new Photo(title, author, authorId, link, tags, photoUrl);
                //add the object to the mPhotos List data structure
                this.mPhotos.add(photoObject);

            }
            for (Photo singlePhoto: mPhotos){
                Log.v(LOG_TAG, singlePhoto.toString());//calls the overriden tostring method
            }

        }catch (JSONException jsone){
            jsone.printStackTrace();
            Log.e(LOG_TAG, "Error processing json data");
        }

    }

    public class DownloadJsonData extends DownloadRawData{
        protected void onPostExecute(String webData){
            super.onPostExecute(webData);
            processResult();
        }

        protected String doInBackground(String... params){
            return super.doInBackground(params);
        }
    }
}
