package mine.liu.com.Model;

import android.widget.CheckBox;

import java.text.DecimalFormat;
import java.util.List;

import mine.liu.com.bean.ShopCart;

/**
 * Created by liuyao on 2015/12/14.
 */
public class Constant2 {

    /**
     * activity下面的所有点击
     *
     * @param group
     * @param child
     * @param b
     */
    public static void ActivitySelectAll(List<ShopCart> group, List<List<ShopCart.Cart>> child,
                                         boolean b) {
        for (int i = 0; i < group.size(); i++) {
            group.get(i).setIsCheck(b);
            for (int j = 0; j < child.get(i).size(); j++) {
                child.get(i).get(j).setIsCheck(b);
            }
        }
    }


    public static  void  ActivityChangeFalse(List<ShopCart> group, List<List<ShopCart.Cart>> child){

        for (int i = 0; i < group.size(); i++) {
            group.get(i).setIsCheck(isGroupSelect(group,i));
//            for (int j = 0; j < child.get(i).size(); j++) {
//                child.get(i).get(j).setIsCheck(b);
//            }
        }

    }
    /**
     * 某一个group的box点击  需要判断是否是全部选择了 已完成判断
     *
     * @param group
     * @param child
     * @param b
     * @param position
     */
    public static void GroupBoxCheck(List<ShopCart> group, List<List<ShopCart.Cart>> child, boolean b
            , int position,CheckBox box) {

        group.get(position).setIsCheck(b);
        for (int i = 0; i < child.get(position).size(); i++) {
            child.get(position).get(i).setIsCheck(b);
        }
        if (isGroupCheckAll(group)){
            /**下面的checkBox选中**/
            box.setChecked(true);
        }else {
            box.setChecked(false);
        }
    }

    /**
     * 某一个 child点击 判断是否全部选中
     *
     * @param group
     * @param child
     * @param gpo
     * @param cpo
     * @param b
     */
    public static void oneChildBoxCheck(List<ShopCart> group, List<List<ShopCart.Cart>> child,
                                        int gpo, int cpo, boolean b) {

        child.get(gpo).get(cpo).setIsCheck(b);

        if (isChildCheckAll(child, gpo)){
            group.get(gpo).setIsCheck(true);
        }else {
            group.get(gpo).setIsCheck(false);
        }
    }


    public static  void  oneGroupCheckFalse(List<ShopCart> group, List<List<ShopCart.Cart>> child,
                                               int gpo,CheckBox box){
        for (int i=0;i<child.get(gpo).size();i++){
            child.get(gpo).get(i).setIsCheck(isChildSelect(child,gpo,i));
        }
        box.setChecked(false);
    }

    /**
     * 判断group是否全部选中
     * @param group
     *
     * @return
     */
    public static boolean isGroupCheckAll(List<ShopCart> group) {

        for (int i = 0; i < group.size(); i++) {
            boolean b = group.get(i).isCheck();
            if (!b) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断每一个group下的child是否全部选中
     * @param child
     * @param gpo
     * @return
     */
    public  static  boolean isChildCheckAll(  List<List<ShopCart.Cart>> child,int gpo){

        for (int i=0;i<child.get(gpo).size();i++){
            boolean b=child.get(gpo).get(i).isCheck();
            if (!b){
                return  false;
            }
        }
        return  true;
    }
    /**
     * 判断某一个group是否选择
     * @param group
     * @param position
     * @return
     */
    public  static  boolean isGroupSelect(List<ShopCart> group,int position){
        return  group.get(position).isCheck();
    }

    /**
     * 判断某一个group下的某一个child是否选中
     * @param child
     * @param gpo
     * @param position
     * @return
     */
    public  static  boolean isChildSelect(List<List<ShopCart.Cart>> child,int gpo, int position){
        return  child.get(gpo).get(position).isCheck();
    }

    /**=====================上面是界面改动部分，下面是数据变化部分=========================*/

    public static String[] getShopCartCountAndMoney(List<ShopCart> group,List<List<ShopCart.Cart>> child){
        String[] infos = new String[2];
        int count=0;
        double money=0;

        for (int i=0;i<group.size();i++){
            if (group.get(i).isCheck()){
                count+=child.get(i).size();
                for (int k=0;k<child.get(i).size();k++){
                    money+=calculateMoney(child.get(i).get(k).getCount(),child.get(i).get(k).getPrice());
                }
                continue;
            }else {
                for (int j=0;j<child.get(i).size();j++){
                    if (child.get(i).get(j).isCheck()){
                        count++;
                        money+=calculateMoney(child.get(i).get(j).getCount(),child.get(i).get(j).getPrice());
                    }
                }
            }
        }
        infos[0]=count+"";
        infos[1]=money+"";
        return  infos;
    }

    /**
     * 数量加
     * @param child
     * @param gpo
     * @param cpo
     */
    public static  void  add( List<List<ShopCart.Cart>> child,
                             int gpo, int cpo){
        ShopCart.Cart cart=child.get(gpo).get(cpo);
        int count = cart.getCount();
        cart.setCount(count+1);
    }

    /**
     * 数量减
     * @param child
     * @param gpo
     * @param cpo
     */
    public static  void  sub( List<List<ShopCart.Cart>> child,
                              int gpo, int cpo){
        ShopCart.Cart cart=child.get(gpo).get(cpo);
        int count = cart.getCount();
        if (count!=1)
            cart.setCount(count-1);
    }


    public static   double calculateMoney(int count,double price){
        DecimalFormat dformat=new DecimalFormat("######0.00");
        return Double.parseDouble(dformat.format(count*price));
    }
}
