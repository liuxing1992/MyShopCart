package mine.liu.com.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import mine.liu.com.Model.Constant2;
import mine.liu.com.bean.ShopCart;
import mine.liu.com.dialog.UIAlertView;
import mine.liu.com.interfaces.ShopCartChangeListener;
import mine.liu.com.myshopcart.R;

/**
 * Created by liuyao on 2015/12/14.
 */
public class MyAdapter extends BaseExpandableListAdapter implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private List<ShopCart> mGroup;

    private List<List<ShopCart.Cart>> mChild;
    private Context context;
    private CheckBox activiBox;
    private ShopCartChangeListener shopCartChangeListener;
    public MyAdapter(List<ShopCart> mGroup, List<List<ShopCart.Cart>> mChild, Context context, CheckBox activiBox) {
        this.mGroup = mGroup;
        this.mChild = mChild;
        this.context = context;
        this.activiBox = activiBox;
    }

    public void setShopCartChangeListener(ShopCartChangeListener shopCartChangeListener) {
        this.shopCartChangeListener = shopCartChangeListener;
    }

    @Override
    public int getGroupCount() {
        return mGroup.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return mChild.get(i).size();
    }

    @Override
    public Object getGroup(int i) {
        return mGroup.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return mChild.get(i).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View convertView, ViewGroup parent) {

        GroupHolder holder=null;

        if (convertView==null) {

            holder=new GroupHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.parent, parent, false);
            holder.mCheckBox= (CheckBox) convertView.findViewById(R.id.dot1);
            holder.mTitleView= (TextView) convertView.findViewById(R.id.store_name);

            convertView.setTag(holder);
        }else {
            holder=(GroupHolder) convertView.getTag();
        }
        holder.mTitleView.setText(mGroup.get(i).getTitle());
        holder.mCheckBox.setTag(i);
        holder.mCheckBox.setOnCheckedChangeListener(this);
        holder.mCheckBox.setChecked(mGroup.get(i).isCheck());
        return convertView;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View convertView, ViewGroup parent) {
        ChildHolder holder=null;

        if (convertView==null) {

            holder = new ChildHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.child, parent, false);

            holder.addButton=(Button) convertView.findViewById(R.id.add);
            holder.subButton=(Button) convertView.findViewById(R.id.subdele);
            holder.delBtn=(Button) convertView.findViewById(R.id.delete2);
            holder.mCheckBox=(CheckBox) convertView.findViewById(R.id.dot2);
            holder.numText=(TextView) convertView.findViewById(R.id.num_edit);
            holder.priceview=(TextView) convertView.findViewById(R.id.price);

            convertView.setTag(holder);
        }else {
            holder=(ChildHolder) convertView.getTag();
        }
        holder.mCheckBox.setTag(R.id.group,i);
        holder.mCheckBox.setTag(R.id.child,i1);
        holder.mCheckBox.setOnCheckedChangeListener(this);
        holder.mCheckBox.setChecked(mChild.get(i).get(i1).isCheck());

        holder.numText.setText(mChild.get(i).get(i1).getCount() + "");
        holder.priceview.setText(mChild.get(i).get(i1).getPrice() + "");
        /**加**/
        holder.addButton.setTag(R.id.group, i);
        holder.addButton.setTag(R.id.child, i1);
        holder.addButton.setOnClickListener(this);
        /**减**/
        holder.subButton.setTag(R.id.group,i);
        holder.subButton.setTag(R.id.child,i1);
        holder.subButton.setOnClickListener(this);
        /**删除**/
        holder.delBtn.setTag(R.id.group,i);
        holder.delBtn.setTag(R.id.child,i1);
        holder.delBtn.setOnClickListener(this);
        return  convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        switch (compoundButton.getId()){
            case R.id.dot1:

                CheckBox box= (CheckBox) compoundButton;
                int po= (int) box.getTag();

                if (Constant2.isChildCheckAll(mChild,po)){
//                    if (!b){
                        Constant2.GroupBoxCheck(mGroup, mChild, b, po,activiBox);
//                    }
                }else {
                    if (b){
                        Constant2.GroupBoxCheck(mGroup, mChild, b, po,activiBox);
                    }else {
                        Constant2.oneGroupCheckFalse(mGroup,mChild,po,activiBox);
                    }
                }

                setSettleInfo();
                notifyDataSetChanged();
                break;
            case R.id.dot2:
                CheckBox box1= (CheckBox) compoundButton;
                int group= (int) box1.getTag(R.id.group);
                int child= (int) box1.getTag(R.id.child);
                Constant2.oneChildBoxCheck(mGroup, mChild, group, child, b);
                setSettleInfo();
                notifyDataSetChanged();
                break;

        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.add:

                int group= (int) view.getTag(R.id.group);
                int child= (int) view.getTag(R.id.child);
                Constant2.add(mChild,group,child);
                setSettleInfo();
                notifyDataSetChanged();
                break;
            case R.id.subdele:
                int group1= (int) view.getTag(R.id.group);
                int child1= (int) view.getTag(R.id.child);
                Constant2.sub(mChild, group1, child1);
                setSettleInfo();
                notifyDataSetChanged();
                break;
            case R.id.delete2:
                int group2= (int) view.getTag(R.id.group);
                int child2= (int) view.getTag(R.id.child);
                showDialog(group2, child2);

                break;
        }
    }




    class GroupHolder {
        private CheckBox mCheckBox;
        private TextView mTitleView;
    }

    class ChildHolder {
        private CheckBox mCheckBox;
        private Button subButton;
        private Button addButton;
        private TextView numText;
        private TextView priceview;
        private Button delBtn;
    }

    private void setSettleInfo() {
        String[] infos = Constant2.getShopCartCountAndMoney(mGroup,mChild);
        //删除或者选择商品之后，需要通知结算按钮，更新自己的数据；
        if (shopCartChangeListener != null && infos != null) {
            shopCartChangeListener.onDataChange(infos[0], infos[1]);
        }
    }


    private void showDialog(final int group, final int child) {

        System.out.println("----"+group+"---"+child);
        final UIAlertView delDialog = new UIAlertView(context, "温馨提示", "确认删除该商品吗?",
                "取消", "确定");
        delDialog.show();
        delDialog.setClicklistener(new UIAlertView.ClickListenerInterface() {
            @Override
            public void doLeft() {
                delDialog.dismiss();
            }

            @Override
            public void doRight() {
                delGoods(group,child);
                setSettleInfo();
                notifyDataSetChanged();
                delDialog.dismiss();
            }
        });

    }

    private void delGoods(int groupPosition, int childPosition) {
        mChild.get(groupPosition).remove(childPosition);
        if (mChild.get(groupPosition).size() == 0) {
            mChild.remove(groupPosition);
            mGroup.remove(groupPosition);

        }
        notifyDataSetChanged();
    }
}
