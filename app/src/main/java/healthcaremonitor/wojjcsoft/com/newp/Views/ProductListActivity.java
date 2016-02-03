package healthcaremonitor.wojjcsoft.com.newp.Views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import healthcaremonitor.wojjcsoft.com.newp.R;
import healthcaremonitor.wojjcsoft.com.newp.animate.Splash;
import healthcaremonitor.wojjcsoft.com.newp.controller.MainController;
import healthcaremonitor.wojjcsoft.com.newp.models.Entry;
import healthcaremonitor.wojjcsoft.com.newp.models.GlobalVar;

public class ProductListActivity extends AppCompatActivity {
    MainController controller = GlobalVar.getInstace().getMainController();
    AbsListView absListView;
    List<Entry> entries;
    MyArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_list_category);
        entries = GlobalVar.getInstace().getEntryList();

        absListView =(AbsListView) findViewById(R.id.listView1);
        adapter =new MyArrayAdapter(this, R.layout.row, entries);
        absListView.setAdapter(adapter);
        absListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nameEntry = ((TextView)view.findViewById(R.id.textView1)).getText().toString();
                Entry entry = controller.getEntryByName(nameEntry);
                GlobalVar.getInstace().setProduct(entry);
                Intent i = new Intent(getBaseContext(),ProductDetailstActivity.class);
                startActivity(i);
            }
        });

    }

    private class MyArrayAdapter extends ArrayAdapter<Entry> {

        public MyArrayAdapter(Context context, int resource,
                              List<Entry> values) {
            super(context, resource, values);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.row, parent, false);
            TextView textView = (TextView) view.findViewById(R.id.textView1);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView1);
            Entry entry = entries.get(position);
            textView.setText(entry.getImName().getLabel());

                Picasso
                        .with(getBaseContext())
                        .load(entry.getUrlImageEntry())
                        .into(imageView);

            Animation am = AnimationUtils.loadAnimation(getBaseContext(), R.anim.clockwise);
            imageView.startAnimation(am);
            return view;
        }





    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}
