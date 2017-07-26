package project.harsh.com.yoblunttask;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by moon on 26/7/17.
 */

public class CaptionedImagesAdapter extends RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder>{

    List<CardData> cardDatas;
    public CaptionedImagesAdapter(List<CardData> cardDatas){//Constructor
     this.cardDatas=cardDatas;

    }

    @Override
    public CaptionedImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        CardView cardView =  (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_item,parent,false);
        return new ViewHolder(cardView);

    }
/*--------------Setting values to all views---------------------------*/
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CardView cardView=holder.cardView;
        ImageView imageView= cardView.findViewById((R.id.info_image));
        //Using glide library for thumbnails
        Glide.with(cardView.getContext()).load(cardDatas.get(position).imgUrl)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
        //Drawable drawable=cardView.getResources().getDrawable();
        //imageView.setImageDrawable(drawable);
        //imageView.setContentDescription(tags[position]);
        TextView textViewTAG = (cardView.findViewById(R.id.tag_Text));
        textViewTAG.setText(cardDatas.get(position).tag);

        TextView textViewDes = (cardView.findViewById(R.id.description_Text));
        textViewDes.setText(cardDatas.get(position).description);

    }
/*--------------------------the total number of elements----------*/
    @Override
    public int getItemCount() {
        return cardDatas.size();
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder{

        private CardView cardView;
        public ViewHolder(CardView v){
            super(v);
            cardView=v;
        }

    }
}