package tw.tcnr.cos.recyclerAdpter;

public class CartItem {
    private String mTitle;
    private int mAmount;
    private int mCost;
    private int mCostAll;
    private String mImgUrl;
    private String mDetail;
    private int mImgDel;
    private int mImgEdit;

    public CartItem(String title, int amount, int cost, int costAll, String imgUrl, String detail, int imgdel, int imgedit){
        mTitle = title;
        mAmount = amount;
        mCost = cost;
        mCostAll = costAll;
        mImgUrl = imgUrl;
        mDetail = detail;
        mImgDel = imgdel;
        mImgEdit = imgedit;
    }

    public String getTitle(){
        return mTitle;
    }

    public int getAmount(){
        return mAmount;
    }

    public int getCost(){
        return mCost;
    }

    public int getCostAll(){
        return mCostAll;
    }

    public String getImgUrl(){
        return mImgUrl;
    }

    public String getDetail(){
        return mDetail;
    }

    public int getImgDel(){
        return mImgDel;
    }

    public int getImgEdit(){
        return mImgEdit;
    }
}
