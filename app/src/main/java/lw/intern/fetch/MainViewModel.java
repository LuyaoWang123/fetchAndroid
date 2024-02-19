package lw.intern.fetch;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

/**
 * This class use view model to manager data
 */
public class MainViewModel extends ViewModel {
    private final MutableLiveData<List<ItemInterface>> items;
    private final MutableLiveData<String> errorLiveData;
    public MainViewModel(){
        this.items=new MutableLiveData<>();
        this.errorLiveData=new MutableLiveData<>();
    }

    public MutableLiveData<List<ItemInterface>> getItems() {
        return items;
    }

    public MutableLiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public void fetchData(String url, DataFetcher dataFetcher){
        dataFetcher.fetchData(url, new DataFetcher.DataResponseListener(){

            @Override
            public void onDataFetched(List<ItemInterface> data) {
                items.postValue(data);
            }

            @Override
            public void onError(Exception error) {
                errorLiveData.postValue("Sorry, something wrong with the data");
            }
        });
    }
}
