package mine.liu.com.myshopcart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mine.liu.com.Adapter.MyAdapter;
import mine.liu.com.Model.Constant;
import mine.liu.com.Model.Constant2;
import mine.liu.com.bean.ShopCart;
import mine.liu.com.interfaces.ShopCartChangeListener;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, ShopCartChangeListener {

    @Bind(R.id.shop_cart_list)
    ExpandableListView mListView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.btnSettle)
    TextView mComputerView;
    @Bind(R.id.tvCountMoney)
    TextView mTotalMoneyView;
    @Bind(R.id.ivSelectAll)
    CheckBox mCheckBox;

    private MyAdapter myAdapter;
    private List<ShopCart> mGroupList;
    private List<List<ShopCart.Cart>> mChildList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
        initData();
        initExpanList();

       mCheckBox.setOnCheckedChangeListener(this);
    }

    private void initData() {
        mGroupList = Constant.getGroup();
        mChildList = Constant.getChild();

    }

    private void initExpanList() {

        mListView.setGroupIndicator(null);
        myAdapter = new MyAdapter(mGroupList, mChildList, this,mCheckBox);
        mListView.setAdapter(myAdapter);
        myAdapter.setShopCartChangeListener(this);
        for (int i = 0; i < myAdapter.getGroupCount(); i++) {
            mListView.expandGroup(i);
        }
    }

    private void initView() {
        setSupportActionBar(toolbar);
        toolbar.setTitle("我的购物车");
    }


    @OnClick({R.id.btnSettle})
    public void onClick(View v) {

        switch (v.getId()) {
            /**结算**/
            case R.id.btnSettle:
                Toast.makeText(this,"未开发",Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        if (Constant2.isGroupCheckAll(mGroupList)){
            if (!b){
                Constant2.ActivitySelectAll(mGroupList,mChildList,b);
            }
        }else {
            if (b){
                Constant2.ActivitySelectAll(mGroupList,mChildList,b);
            }else {
                Constant2.ActivityChangeFalse(mGroupList,mChildList);

            }
        }
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDataChange(String Selectcount, String selectMoney) {
        String countMoney = String.format(getResources().getString(R.string.count_money), selectMoney);
        String countGoods = String.format(getResources().getString(R.string.count_goods), Selectcount);
        mTotalMoneyView.setText(countMoney);
        mComputerView.setText(countGoods);
    }
}
