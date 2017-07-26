package project.harsh.com.yoblunttask;


import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by moon on 26/7/17.
 */

public class CardData implements Comparable<CardData>{

    public String tag,description,imgUrl;
    public LatLng latLng;
    public double distanceToOrigin;
    public CardData(){

    }
    public CardData(String description,String tag,String imgUrl,LatLng latLng,double distanceToOrigin){
        this.tag=tag;
        this.description=description;
        this.imgUrl=imgUrl;
        this.distanceToOrigin=distanceToOrigin;
        this.latLng=latLng;

    }


    @Override
    public int compareTo(@NonNull CardData cardData) {
        if(this.distanceToOrigin== cardData.distanceToOrigin)
        return  0;
        else if(this.distanceToOrigin>cardData.distanceToOrigin)
            return 1;
        else
            return -1;


    }
}
