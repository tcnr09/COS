package tw.tcnr.cos.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import top.defaults.view.PickerView;
import top.defaults.view.PickerViewDialog;
import tw.tcnr.cos.R;

public class SimplePickerDialog extends BaseDialogFragment {

    private static int pos;
    private PickerView pickerView;

    public static SimplePickerDialog newInstance(int type, ActionListener actionListener, int position) {
        pos = position;
        return BaseDialogFragment.newInstance(SimplePickerDialog.class, type, actionListener);
    }

    @Override
    public Dialog createDialog(Bundle savedInstanceState) {
        PickerViewDialog dialog = new PickerViewDialog(getActivity());
        dialog.setContentView(R.layout.dlg_layout);

        pickerView = dialog.findViewById(R.id.pickerView);
        pickerView.setItems(Item.sampleItems(pos), null);

        attachActions(dialog.findViewById(R.id.done), dialog.findViewById(R.id.cancel));
        return dialog;
    }

    @Nullable
    @Override
    public View createView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dlg_layout, container, false);

        pickerView = view.findViewById(R.id.pickerView);
        pickerView.setItems(Item.sampleItems(pos), null);

        attachActions(view.findViewById(R.id.done), view.findViewById(R.id.cancel));
        return view;
    }

    public Item getSelectedItem() {
        return pickerView.getSelectedItem(Item.class);
    }
}
