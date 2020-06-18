package com.example.abhijeetmule.playstorelikeview;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.M)
public class MainActivity extends AppCompatActivity implements RecyclerView.OnScrollChangeListener{

    private ArrayList<SectionDataModel> allSampleData;
    private Context mContext;
    private Activity mActivity;

    RecyclerView recyclerView;
    String[] imagenameArray;
    private int requestCount = 1;
    RecyclerViewDataAdapter adapter;

    private RequestQueue requestQueue;
    String strStatus, ISMORE;
    List<SingleItemModel> listSuperHeroes;
    String imgNames,strEvent_Title, strDate, strPROFILEPHOTO;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        mContext = getApplicationContext();
        mActivity = MainActivity.this;

        allSampleData = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(this);
        getData();
        recyclerView.setOnScrollChangeListener(this);

        adapter = new RecyclerViewDataAdapter(allSampleData, this);
        recyclerView.setAdapter(adapter);

    }



  private StringRequest getAllPurchaseList(final int requestCount) {
      //    String GetPurchaseDetails = "https://api.stackexchange.com/2.2/answers?page="+requestCount+"&pagesize=50&site=stackoverflow";

      String GetPurchaseDetails = "http://106.201.232.22:85/RCTP/RCTP.asmx/GetGalleryData";
      final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar1);

      //Displaying Progressbar
      progressBar.setVisibility(View.VISIBLE);
      setProgressBarIndeterminateVisibility(true);

      StringRequest request = new StringRequest(Request.Method.POST, GetPurchaseDetails, new Response.Listener<String>() {
          @Override
          public void onResponse(String response) {
              try {
                  JSONObject jsonObj = new JSONObject(response);
                  Log.d("response", response);

                  strStatus = jsonObj.getString("Status");
                  Log.d("strStatus", strStatus);

                  ISMORE = jsonObj.getString("ISMORE");
                  Log.d("ISMORE", ISMORE);

                  if (strStatus.equalsIgnoreCase("1") && ISMORE.equalsIgnoreCase("true")) {
                      select(response);
                  } else {
                      Toast.makeText(mContext, "all data loaded", Toast.LENGTH_SHORT).show();
                  }

                  progressBar.setVisibility(View.GONE);
              } catch (JSONException e) {
                  e.printStackTrace();
              }
          }
      }, new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
              progressBar.setVisibility(View.GONE);
              Toast.makeText(MainActivity.this, "Please Check internet Connection", Toast.LENGTH_SHORT).show();
          }
      }) {
          protected Map<String, String> getParams() {
              Map<String, String> params = new HashMap<>();
              params.put("RCTP_APIkey", "RCTP_sb24_6012*");
              params.put("PageIndex", "" + requestCount + "");
              params.put("PageSize", "4");
              return params;
          }
      };
      return request;
  }

    private void getData() {
        //Adding the method to the queue by calling the method getDataFromServer


        requestQueue.add(getAllPurchaseList(requestCount));
        //Incrementing the request counter
        requestCount++;
    }

    private void select(String jsonresponse) throws JSONException {

        JSONObject jsonObj = new JSONObject(jsonresponse);
        JSONArray items = jsonObj.getJSONArray("Gallery");

        for (int i = 0; i < items.length(); i++) {
            JSONObject c = items.getJSONObject(i);

            SectionDataModel dm = new SectionDataModel();
            SingleItemModel singleItemModel;
            ArrayList<SingleItemModel> arrayItemModels = new ArrayList<>();

            imgNames = c.getString("Event_Images");
            Log.d("imgNames", imgNames);

            imgNames = imgNames.replaceAll("\\s+", "");
            Log.d("pdfname2", imgNames);

            imagenameArray = imgNames.split(",");
            Log.d("pdfArray", Arrays.toString(imagenameArray));

            strEvent_Title = c.getString("Event_Title");
            Log.d("event_title", strEvent_Title);

            strDate = c.getString("Event_Date");
            Log.d("Event_Date", strDate);

            int index = strDate.indexOf(" ");
            strDate = strDate.substring(0, index);

            strPROFILEPHOTO = c.getString("PROFILEPHOTO");
            Log.d("profile", strPROFILEPHOTO);


            dm.setHeaderTitle(strEvent_Title);
            dm.setStrdate(strDate);
            dm.setStrImageName(strPROFILEPHOTO);

            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("gallery", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("imagenameArray", imgNames);
            editor.apply();

            for (int k = 0; k < imagenameArray.length; k++) {
                Log.d("imagenameArraylength", String.valueOf(imagenameArray.length));

                String imgName = imagenameArray[k];
                Log.d("K", String.valueOf(k));
                Log.d("NAME2", imgName);

                singleItemModel = new SingleItemModel();

                singleItemModel.setName(imgName);
                singleItemModel.setNameArray(imgNames);
                arrayItemModels.add(singleItemModel);
                Log.d("GetImageName", String.valueOf(singleItemModel.getName()));
            }




            dm.setAllItemInSection(arrayItemModels);
            allSampleData.add(dm);
            adapter.notifyDataSetChanged();

        }


    }


    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1)
                return true;
        }
        return false;
    }


    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (isLastItemDisplaying(recyclerView)) {
            //Calling the method getdata again

            if (strStatus.equalsIgnoreCase("1") && ISMORE.equalsIgnoreCase("true")) {
                getData();
            } else {
                Toast.makeText(mContext, "all data loaded", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
