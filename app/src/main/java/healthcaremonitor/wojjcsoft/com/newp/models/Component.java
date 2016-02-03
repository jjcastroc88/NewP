package healthcaremonitor.wojjcsoft.com.newp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by josejaimecastro on 28/01/16.
 */
public class Component{

@SerializedName("feed")
@Expose
private Feed feed;

    public Feed getFeed() {
        return feed;
    }
}
