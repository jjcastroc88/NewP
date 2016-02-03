package healthcaremonitor.wojjcsoft.com.newp.Views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import healthcaremonitor.wojjcsoft.com.newp.R;
import healthcaremonitor.wojjcsoft.com.newp.models.Entry;
import healthcaremonitor.wojjcsoft.com.newp.models.GlobalVar;

/**
 * Created by Jose Jaime on 01/02/2016.
 */
public class ProductDetailstActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Entry entry = GlobalVar.getInstace().getProduct();
        setContentView(R.layout.product_details);

        ImageView image = (ImageView) findViewById(R.id.imageView2);
        String internetUrl = entry.getUrlImageEntry();

            Picasso
                    .with(this)
                    .load(internetUrl)
                    .into(image);
        Animation am = AnimationUtils.loadAnimation(getBaseContext(), R.anim.animation);
        image.startAnimation(am);

        TextView text = (TextView)findViewById(R.id.textView);
        text.setText(entry.getImName().getLabel());

        TextView text2 = (TextView)findViewById(R.id.textView3);

        text2.setText("Price: "+entry.getImPrice().getAttributes().getAmount()+"\r\n"
                +"Description: "+entry.getSummary().getLabel());

        text2.setMovementMethod(new ScrollingMovementMethod());
    }
}
