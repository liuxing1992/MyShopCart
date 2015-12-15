package mine.liu.com.Model;

import java.util.ArrayList;
import java.util.List;

import mine.liu.com.bean.ShopCart;

/**
 * Created by liuyao on 2015/12/14.
 */
public class Constant {

    private static List<ShopCart> mGroupList=new ArrayList<>();
    private static List<List<ShopCart.Cart>> mChildList= new ArrayList<>();

    public  static  List<ShopCart> getGroup() {
        for (int i = 0; i < 6; i++) {
            ShopCart shopCar = new ShopCart();
            shopCar.setIsCheck(false);
            shopCar.setTitle("Apple" + i);
            mGroupList.add(shopCar);
        }
        return mGroupList;
    }
    public static List<List<ShopCart.Cart>> getChild(){

        for (int j=0;j<mGroupList.size();j++){
            ArrayList<ShopCart.Cart> list=new ArrayList<>();
            for (int i=0;i<2;i++){
                ShopCart.Cart cart= new ShopCart().new Cart();
                cart.setIsCheck(false);
                cart.setCount(i * 2+1);
                cart.setPrice(23 * i + 12);
                list.add(cart);
            }
            mChildList.add(list);
        }
        return  mChildList;
    }
}
