package lw.intern.fetch;

import java.util.ArrayList;
import java.util.List;

public class ItemListSingleton {
    private static ItemListSingleton instance;
    private final List<ItemInterface> itemList;

    private ItemListSingleton() {
        itemList = new ArrayList<>();
    }
    public static ItemListSingleton getInstance(){
        if (instance==null){
            instance=new ItemListSingleton();
        }
        return instance;
    }
    public List<ItemInterface> getItemList(){
        return this.itemList;
    }

    public void setItemList(List<ItemInterface> newlist){
        itemList.clear();
        itemList.addAll(newlist);
    }
}
