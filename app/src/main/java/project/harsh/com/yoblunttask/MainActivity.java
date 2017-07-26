package project.harsh.com.yoblunttask;

import android.app.Activity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    public List<CardData> cardDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String string=loadJSONFromAsset();
        Log.v("Json",string);
        try {
            JSONArray jsonArray= new JSONArray(string);
            cardDataList=jsonToCardDataList(jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        CardScrollerFragment cardScrollerFragment= new CardScrollerFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        ft.replace(R.id.fragment_container, cardScrollerFragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getResources().openRawResource(R.raw.data);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public List<CardData> jsonToCardDataList(JSONArray jsonArray){
        List<CardData> cardDataList= new ArrayList<>();

        for(int i=0;i<jsonArray.length();i++){

            try {
                JSONObject object= jsonArray.getJSONObject(i);
                String imgUrl = object.getString("thumbnail");
                String title= object.getString("title");
                String tag= object.getString("tag");
                JSONObject coordinateObject= object.getJSONObject("location");
                String lan= coordinateObject.getString("lan");
                String lng= coordinateObject.getString("lng");
                LatLng latLng= new LatLng(Double.parseDouble(lan),Double.parseDouble(lng));

                CardData cardData= new CardData(title,tag,imgUrl,latLng,0.0);

                cardDataList.add(cardData);

            }
            catch (JSONException e){
                Log.e("Exception",e.getMessage().toString());
            }
        }







        return  cardDataList;
    }


}
