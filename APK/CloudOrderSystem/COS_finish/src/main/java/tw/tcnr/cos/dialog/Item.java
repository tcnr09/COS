package tw.tcnr.cos.dialog;

import java.util.ArrayList;
import java.util.List;

import top.defaults.view.PickerView;

public class Item implements PickerView.PickerItem {

    private static ArrayList<Item> items;
    private String text;

    public Item(String s) {
        text = s;
    }

    @Override
    public String getText() {
        return text;
    }

    public static List<Item> sampleItems(int pos) {
        switch (pos){
            case 0:
                items = new ArrayList<>();
                items.add(new Item("外帶"));
                items.add(new Item("內用"));
                items.add(new Item("訂餐"));

                break;
            case 2:
                items = new ArrayList<>();
                items.add(new Item("某某店1"));
                items.add(new Item("某某店2"));
                break;
        }
        return items;
    }
}
