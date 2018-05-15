package handlerclasses;

import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.immad.sabahat.buzzmovetest.MainActivity;
import com.immad.sabahat.buzzmovetest.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Pattern;

import beans.Place;

public class WebserviceHandler {

    Context ctx;
    private ProgressBar progressDialog;
    private JSONObject jsonObject;

    public WebserviceHandler(Context ctx) {
        this.ctx = ctx;
    }


    /**
     * performs volley string request
     * @param urlParams
     */
    public void getStringRequest(String urlParams){

        final AlertDialogHandler alertDialogHandler = new AlertDialogHandler();



        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(ctx);
        String url ="https://maps.googleapis.com/maps/api/place/nearbysearch/json?"
                .concat(urlParams);

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        JSONArray resultsArrJson = null;

                        try {
                             jsonObject = new JSONObject(response);
                             resultsArrJson= jsonObject.getJSONArray("results");
                             String status = jsonObject.getString("status");

                             if(status.equals("OK")) {

                                 parseJsonResponse(resultsArrJson.toString());



                             }
                             //handling errors in status
                             else if(status.equals("ZERO_RESULTS")){
                                     // Zero results found
                                 Toast.makeText(ctx, "Sorry no places found. Try to search another places",
                                         Toast.LENGTH_SHORT).show();
                                 }
                                 else if(status.equals("UNKNOWN_ERROR"))
                                 {
                                     Toast.makeText(ctx, "SSorry unknown error occured.",
                                             Toast.LENGTH_SHORT).show();

                                 }
                                 else if(status.equals("OVER_QUERY_LIMIT"))
                                 {
                                     Toast.makeText(ctx, "Sorry query limit to google places is reached",
                                             Toast.LENGTH_SHORT).show();

                                 }
                                 else if(status.equals("REQUEST_DENIED"))
                                 {
                                     Toast.makeText(ctx, "Sorry error occured. Request is denied",
                                             Toast.LENGTH_SHORT).show();

                                 }
                                 else if(status.equals("INVALID_REQUEST"))
                                 {
                                     Toast.makeText(ctx, "SSorry error occured. Invalid Request",
                                             Toast.LENGTH_SHORT).show();

                                 }
                                 else
                                 {
                                     Toast.makeText(ctx, "Sorry error occured.",
                                             Toast.LENGTH_SHORT).show();

                                 }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        ((MainActivity)ctx).showProgressBar(false);

                        Log.d("nearby api response", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ((MainActivity)ctx).showProgressBar(false);
                alertDialogHandler.showAlertDialog(ctx, "oops", error.getMessage());
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    /**parses the response json array
     *
     * @param response result array
     */
    public void parseJsonResponse(String response){

        SessionManagement sessionManager = new SessionManagement(ctx);
        sessionManager.saveResults(response);
        ArrayList<Place> placeArr = new ArrayList<>();
        JSONArray resultsArrJson = null;
        try {
            resultsArrJson = new JSONArray(response);



        for (int i = 0; i < resultsArrJson.length(); i++) {


            Place place = new Place();

            JSONObject resultObj = resultsArrJson.getJSONObject(i);
            JSONObject geoObj = resultObj.getJSONObject("geometry");
            JSONObject locObj = geoObj.getJSONObject("location");

            place.setLat(locObj.getString("lat"));
            place.setLng(locObj.getString("lng"));
            place.setId(resultObj.getString("id"));
            place.setName(resultObj.getString("name"));
            place.setIcon(resultObj.getString("icon"));
            place.setVicinity(resultObj.getString("vicinity"));
            place.setFormatted_address(resultObj.optString("formatted_address"));
            JSONArray typesArr = resultObj.getJSONArray("types");
            String types = "";
            if (typesArr != null) {
                for (int j = 0; j < typesArr.length(); j++) {

                    String text = typesArr.getString(j);
                    String type = text.replaceAll(Pattern.quote("_"), " ");
                    types = types.concat(type + ", ");
                }
            }
            place.setTypes(types.substring(0, types.length() - 2));

            placeArr.add(place);
        }
        } catch (JSONException e) {
                e.printStackTrace();
            }
        ((MainActivity)ctx).setListViewAdapter(placeArr);
        ((MainActivity)ctx).placeArr = placeArr;

    }
    /**
     * Concatinates all parameters in string
     *
     * @param location user's current location
     * @param type type of place (optional)
     * @param keyword place to be searched
     * @return urlParams
     */
    public String urlParams(String location,String type, String keyword){

        String urlParams = "location=".concat(location)
                .concat("&radius=1000")
        .concat("&keyword=").concat(keyword)
                .concat("&key=").concat(ctx.getResources().getString(R.string.google_nearby_webkey));
        if(!type.equals("")){

            urlParams = urlParams.concat("&type=")
                    .concat(type);
        }


        return urlParams;

    }
}
