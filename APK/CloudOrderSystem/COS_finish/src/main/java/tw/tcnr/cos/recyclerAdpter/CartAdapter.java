package tw.tcnr.cos.recyclerAdpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import tw.tcnr.cos.R;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private Context mContext;
    private ArrayList<CartItem> mCartItemList;
    private OnItemClickListener mListener;

    public CartAdapter(Context context, ArrayList<CartItem> mItemList){
        mContext = context;
        mCartItemList = mItemList;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.c0204_cart_item, parent, false);
        return new CartViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        CartItem currentItem = mCartItemList.get(position);

        String imageUrl = currentItem.getImgUrl();
        String title = currentItem.getTitle();
        int amount = currentItem.getAmount();
        int cost = currentItem.getCost();
        int costAll = currentItem.getCostAll();
        String detail = currentItem.getDetail();
        int imgdel = currentItem.getImgDel();
        int imgedit = currentItem.getImgEdit();

        holder.mTitle.setText(title);
        holder.mAmount.setText(Integer.toString(amount));
        holder.mCost.setText(Integer.toString(cost));
        holder.mCostAll.setText(Integer.toString(costAll));
        holder.mdetail.setText(detail);
        holder.mImgDel.setImageResource(imgdel);
        holder.mImgEdit.setImageResource(imgedit);
        Picasso.with(mContext).load(imageUrl).fit().centerInside().into(holder.mImgproduct);
    }

    public class CartViewHolder extends RecyclerView.ViewHolder{
        public TextView mTitle;
        public TextView mAmount;
        public TextView mCost;
        public TextView mCostAll;
        public TextView mdetail;
        public ImageView mImgproduct;
        public ImageView mImgDel;
        public ImageView mImgEdit;


        public CartViewHolder(View itemView, final OnItemClickListener listener){
            super(itemView);

            mImgproduct= itemView.findViewById(R.id.c0204_imgProducts);
            mTitle = itemView.findViewById(R.id.c0204_txtTitle);
            mAmount = itemView.findViewById(R.id.c0204_txtAmount);
            mCost = itemView.findViewById(R.id.c0204_txtCost);
            mCostAll = itemView.findViewById(R.id.c0204_txtCostAll);
            mdetail = itemView.findViewById(R.id.c0204_txtDetail);
            mImgDel = itemView.findViewById(R.id.c0204_imgDel);
            mImgEdit = itemView.findViewById(R.id.c0204_imgEdit);

            mImgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position =getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onUpdateClick(position);
                        }
                    }
                }
            });
            mImgDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position =getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return mCartItemList.size();
    }

    public interface OnItemClickListener{
        void onUpdateClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }


}
