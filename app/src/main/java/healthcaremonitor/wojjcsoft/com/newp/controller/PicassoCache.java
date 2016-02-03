package healthcaremonitor.wojjcsoft.com.newp.controller;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import healthcaremonitor.wojjcsoft.com.newp.models.Entry;

/**
 * Created by Jose Jaime on 01/02/2016.
 */
public class PicassoCache {

    Context context;


    public PicassoCache(Context context){

        this.context = context;
    }



    public void cacheImage(List<Entry> entryList){
        ImageView image = new ImageView(context);
        for (Entry entry: entryList) {
            Picasso
                    .with(context)
                    .load(entry.getUrlImageEntry())
                    .into(image);
        }
    }

}
