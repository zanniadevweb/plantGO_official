package com.example.plantGO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.Objects;

class ViewPagerAdapter extends PagerAdapter {

    //Context object
    Context context;

    //Array of images
    ArrayList<Integer> images;

    //Layout Inflater
    LayoutInflater mLayoutInflater;


    //Viewpager Constructor
    public ViewPagerAdapter(Context context, ArrayList<Integer> images) {
        this.context = context;
        this.images = images;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        //return the number of images
        return images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((LinearLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        //inflating the item.xml
        View itemView = mLayoutInflater.inflate(R.layout.item, container, false);

        //referencing the image view from the item.xml file
        ImageView imageViewHat = (ImageView) itemView.findViewById(R.id.imageViewChapeau);

        //referencing the image view from the item.xml file
        ImageView imageViewTorso = (ImageView) itemView.findViewById(R.id.imageViewHaut);

        //referencing the image view from the item.xml file
        ImageView imageViewPants = (ImageView) itemView.findViewById(R.id.imageViewBas);

        //setting the image in the imageView
        imageViewTorso.setImageResource(images.get(position));

        //setting the image in the imageView
        imageViewHat.setImageResource(images.get(position));

        //setting the image in the imageView
        imageViewPants.setImageResource(images.get(position));

        //Adding the View
        Objects.requireNonNull(container).addView(itemView);



        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((LinearLayout) object);
    }
}
