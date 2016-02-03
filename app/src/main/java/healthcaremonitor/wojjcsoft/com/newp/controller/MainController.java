package healthcaremonitor.wojjcsoft.com.newp.controller;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import healthcaremonitor.wojjcsoft.com.newp.Services.Service;
import healthcaremonitor.wojjcsoft.com.newp.models.Category;
import healthcaremonitor.wojjcsoft.com.newp.models.Component;
import healthcaremonitor.wojjcsoft.com.newp.models.Entry;
import healthcaremonitor.wojjcsoft.com.newp.models.Feed;
import healthcaremonitor.wojjcsoft.com.newp.models.ImImage;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Jose Jaime on 30/01/2016.
 */
public class MainController {

    public boolean connection = false;
    public Retrofit retrofit;
    public Service service;
    public Call<Component> call;
    public Response<Component> response;
    public Feed feed;
    public Context context;
    public Gson gson;
    public List<Category> categoryList;

    public MainController(Context context){
        this.context = context;
        gson = new Gson();
        categoryList = new ArrayList<Category>();
    }


    public void createConnection(){
        try{

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://itunes.apple.com/us/rss/topfreeapplications/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            service = retrofit.create(Service.class);
            call =  service.getDataFeed();
            response = call.execute();
            connection = true;
            getFeed();
        }catch (Exception e){
            Log.i("Error conecction.", e.getMessage());
            e.printStackTrace();
        }

    }

    public void getFeed(){
        if(connection==true){
            feed = (response.body()).getFeed();
            createDataJson();
        }else{
            feed = getFeedFromJson();
        }
    }


    public List<Entry> getEntryList(){
        List<Entry> entriesList = feed.getEntry();
        List<String> nameCategory = new ArrayList<String>();
        for (Entry entry:entriesList) {
            String imageUrl = entry.getImImage().get(entry.getImImage().size() - 1).getLabel();
            entry.setUrlImageEntry(imageUrl);
            if(nameCategory.indexOf(entry.getCategory().getAttributes().getLabel())==-1){
                entry.getCategory().getAttributes().setUrlImageCategory(imageUrl);
                categoryList.add(entry.getCategory());
                nameCategory.add(entry.getCategory().getAttributes().getLabel());
            }

        }

        return  entriesList;
    }

    public List<Category> getCategoryList(){
        return categoryList;
    }

    public  Feed getFeedFromJson(){
        Feed feed = null;
        String json = getDataJson();
        Component comp = gson.fromJson(json,Component.class);
        feed = comp.getFeed();
        return feed;
    }

    public void createDataJson(){

        String json = gson.toJson(response.body());
        FileOutputStream outputStream;

        try {

            outputStream =  new FileOutputStream(new File("/storage/sdcard0/off_line_data.json"));
            outputStream.write(json.getBytes());
            outputStream.close();
        } catch (Exception e) {
            Log.i("Error json file.", e.getMessage());
            e.printStackTrace();
        }
    }

    public String getDataJson(){

        String json="";
        FileInputStream inputStream;

        try {
            File file = new File("/storage/sdcard0/off_line_data.json");
            inputStream = new FileInputStream(file);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer);

        } catch (Exception e) {
            //Log.i("Error reading json file.", e.getMessage());
            e.printStackTrace();
        }

        return json;
    }

    public List<Entry> getEntryByCategory(String category){
        List<Entry> entriesCategory = new ArrayList<Entry>();

        for (Entry entri:getEntryList()) {
            if(entri.getCategory().getAttributes().getLabel().equals(category)){
                entriesCategory.add(entri);
            }
        }
        return entriesCategory;
    }

    public Entry getEntryByName(String name){
        Entry entry = new Entry() ;

            for (Entry ent:getEntryList()) {
            if(ent.getImName().getLabel().equals(name)){
                entry = ent;
                break;
            }
        }
        return entry;
    }

}
