package project.harsh.com.yoblunttask;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;


public class CardScrollerFragment extends Fragment {
    LinearLayoutManager layoutManager;
    RecyclerView cardDataRecycler;

    private final float zoomLevel=10f;// zoom level

    public  Marker marker;

    public List<CardData> cardDataList;
  //  Context context;
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        try {

            cardDataList = ((MainActivity) activity).cardDataList;//initialize card data

        }
        catch (ClassCastException e){
            Log.e("CLassCast","Sorry");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        cardDataRecycler = (RecyclerView) inflater.inflate(R.layout.fragment_card_scroller,container,false);

        layoutManager = new LinearLayoutManager(inflater.getContext(), LinearLayoutManager.HORIZONTAL, false);
        CaptionedImagesAdapter adapter= new CaptionedImagesAdapter(cardDataList);
        cardDataRecycler.setAdapter(adapter);
        cardDataRecycler.setLayoutManager(layoutManager);



        return cardDataRecycler;
    }

    public void setFocusListener(final GoogleMap googleMap){

        /*----------Default value first element*/
        marker = googleMap.addMarker(new MarkerOptions().position(cardDataList.get(0).latLng).title(cardDataList.get(0).description));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cardDataList.get(0).latLng,zoomLevel));

        cardDataRecycler.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch(motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        return true;
                    case MotionEvent.ACTION_UP:// On touch up event the current first item visilble is considered and the location is changed according to that
                        int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

                            marker.setPosition(cardDataList.get(firstVisibleItem).latLng);
                            marker.setTitle(cardDataList.get(firstVisibleItem).description);
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cardDataList.get(firstVisibleItem).latLng,zoomLevel));

                        return true;
                }
                return false;
            }
        });

    }


}
