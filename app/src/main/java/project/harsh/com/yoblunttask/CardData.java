package project.harsh.com.yoblunttask;


import com.google.android.gms.maps.model.LatLng;

/**
 * Created by moon on 26/7/17.
 */

public class CardData {

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



}
