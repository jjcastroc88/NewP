package healthcaremonitor.wojjcsoft.com.newp.models;

import android.app.Application;

import java.util.List;

import healthcaremonitor.wojjcsoft.com.newp.controller.MainController;

/**
 * Created by Jose Jaime on 01/02/2016.
 */
public class GlobalVar {
    private static GlobalVar instace = new GlobalVar();
    private List<Entry> entryList;
    private Entry product;
    private MainController mainController;

    public List<Entry> getEntryList(){
        return this.entryList;
    }

    public void setEntryList(List<Entry> entryList){
        this.entryList = entryList;
    }

    public Entry getProduct() {
        return product;
    }

    public void setProduct(Entry product){
        this.product = product;
    }

    public static GlobalVar getInstace() { return instace;}

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public MainController getMainController() {
        return mainController;
    }
}
