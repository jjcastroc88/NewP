package healthcaremonitor.wojjcsoft.com.newp.Services;

import healthcaremonitor.wojjcsoft.com.newp.models.Component;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Jose Jaime on 30/01/2016.
 */
public interface Service {

    @GET("limit=20/json")
    Call<Component> getDataFeed();

}
