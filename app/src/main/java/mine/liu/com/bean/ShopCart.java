package mine.liu.com.bean;

/**
 * Created by liuyao on 2015/12/14.
 */
public class ShopCart {

    private String title;
    private boolean isCheck;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }



    public class Cart {

        private String title;
        private double price;
        private int count;
        private boolean isCheck;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public boolean isCheck() {
            return isCheck;
        }

        public void setIsCheck(boolean isCheck) {
            this.isCheck = isCheck;
        }
    }

}
