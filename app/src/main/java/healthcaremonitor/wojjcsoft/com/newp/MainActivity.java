package healthcaremonitor.wojjcsoft.com.newp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import healthcaremonitor.wojjcsoft.com.newp.Views.ProductDetailstActivity;
import healthcaremonitor.wojjcsoft.com.newp.Views.ProductListActivity;
import healthcaremonitor.wojjcsoft.com.newp.controller.MainController;
import healthcaremonitor.wojjcsoft.com.newp.controller.PicassoCache;
import healthcaremonitor.wojjcsoft.com.newp.models.Category;
import healthcaremonitor.wojjcsoft.com.newp.models.Entry;
import healthcaremonitor.wojjcsoft.com.newp.models.GlobalVar;


public class MainActivity extends AppCompatActivity {


    List<Category> categoryList;
    MyArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        super.onCreate(savedInstanceState);

        MainController controller = new MainController(this);
        GlobalVar.getInstace().setMainController(controller);
        if(checkConecction()){
            controller.createConnection();
        }else{
            controller.getFeed();
        }


        List<Entry> entriList = controller.getEntryList();

        categoryList = controller.getCategoryList();

        PicassoCache cache = new PicassoCache(this);
        cache.cacheImage(entriList);

        setContentView(R.layout.activity_main);

        AbsListView absListView =(AbsListView) findViewById(R.id.listView1);
        if(adapter!=null){
            adapter.clear();
            adapter.notifyDataSetChanged();
        }
        adapter = new MyArrayAdapter(this, R.layout.row, categoryList);

        absListView.setAdapter(adapter);


        absListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String category = ((TextView)view.findViewById(R.id.textView1)).getText().toString();
                List<Entry> entries = GlobalVar.getInstace().getMainController().getEntryByCategory(category);
                GlobalVar.getInstace().setEntryList(entries);
                Intent i = new Intent(getBaseContext(), ProductListActivity.class);
                startActivity(i);
            }
        });

    }

    private boolean checkConecction() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }

    private class MyArrayAdapter extends ArrayAdapter<Category>{

        public MyArrayAdapter(Context context, int resource,
                              List<Category> values) {
            super(context, resource, values);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.row, parent, false);
            TextView textView = (TextView) view.findViewById(R.id.textView1);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView1);
            Category categ = categoryList.get(position);
            textView.setText(categ.getAttributes().getLabel());

                Picasso
                        .with(getBaseContext())
                        .load(categ.getAttributes().getUrlImageCategory())
                        .into(imageView);


            Animation am = AnimationUtils.loadAnimation(getBaseContext(), R.anim.blink);
            imageView.startAnimation(am);

            return view;


        }





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();

    }
}
