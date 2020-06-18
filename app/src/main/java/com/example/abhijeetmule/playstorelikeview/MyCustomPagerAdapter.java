package com.example.abhijeetmule.playstorelikeview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyCustomPagerAdapter extends PagerAdapter {

    Context context;
    String[] images;
    LayoutInflater inflater;

    public MyCustomPagerAdapter(Context viewerPager, String[] images) {
        this.context = viewerPager  ;
        this.images = images;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == ((LinearLayout)o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull View container, int position) {

        View itemView = inflater.inflate(R.layout.single_imageviewer, (ViewGroup) container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
      //  imageView.setImageResource(Integer.parseInt("https://s3-ap-southeast-1.amazonaws.com/awsaquabucket/images/"+images[position]));



        for (int k = 0; k < images.length; k++)
        {
            String imgName = images[k];
            Log.d("K", String.valueOf(k));
            Log.d("NAME2", imgName);
            Picasso.with(context).load("https://s3-ap-southeast-1.amazonaws.com/awsaquabucket/images/"+ imgName).into(imageView);

            ((ViewGroup) container).addView(itemView,k);
        }







        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
