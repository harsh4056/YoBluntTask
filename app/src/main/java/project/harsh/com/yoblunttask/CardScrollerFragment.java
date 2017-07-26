package project.harsh.com.yoblunttask;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


public class CardScrollerFragment extends Fragment {


    public List<CardData> cardDataList;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        try {
            cardDataList = ((MainActivity) activity).cardDataList;
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
        RecyclerView cardDataRecycler= (RecyclerView) inflater.inflate(R.layout.fragment_card_scroller,container,false);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(inflater.getContext(), LinearLayoutManager.HORIZONTAL, false);
        CaptionedImagesAdapter adapter= new CaptionedImagesAdapter(cardDataList);
        cardDataRecycler.setAdapter(adapter);
        cardDataRecycler.setLayoutManager(layoutManager);



        return cardDataRecycler;
    }


}
