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


public class MyAdpter extends RecyclerView.Adapter<MyAdpter.MyViewholder> {
    private Context mContext;
    private ArrayList<MyItem> mMyItemList;
    private OnItemClickListener mListener;

    public MyAdpter(Context context, ArrayList<MyItem> myItemList){
        mContext = context;
        mMyItemList = myItemList;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    @Override
    public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.c0202_listitem, parent, false);
        return  new MyViewholder(v, mListener);
    }

    @Override
    public void onBindViewHolder(MyViewholder holder, int position) {
        MyItem currentItem = mMyItemList.get(position);

        String imageUrl = currentItem.getImageUrl();
        String title = currentItem.getTitle();
        int arrow = currentItem.getmArrow();

        holder.mTextvTitle.setText(title);
        holder.mArrow.setImageResource(arrow);
        Picasso.with(mContext).load(imageUrl).fit().centerInside().into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mMyItemList.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTextvTitle;
        public ImageView mArrow;

        public MyViewholder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.c0202_img_products);
            mTextvTitle = itemView.findViewById(R.id.c0202_txt_products);
            mArrow = itemView.findViewById(R.id.c0202_arrowR);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position =getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
