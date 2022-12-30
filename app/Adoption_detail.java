package ncu.im3069.demo.app;

import org.json.JSONObject;
import ncu.im3069.demo.util.Arith;

public class Adoption_detail {

    /** id，產品細項編號 */
    private int adoption_detail_id;

    /** pd，產品 */
    private For_adoption fa;

    /** ph，ProductHelper 之物件與 OrderItem 相關之資料庫方法（Sigleton） */
    private For_adoptionHelper fah =  For_adoptionHelper.getHelper();

    /**
     * 實例化（Instantiates）一個新的（new）OrderItem 物件<br>
     * 採用多載（overload）方法進行，此建構子用於建立訂單細項時
     *
     * @param pd 會員電子信箱
     * @param quantity 會員密碼
     */
    public Adoption_detail(For_adoption fa) {
        this.fa = fa;
    }

    /**
     * 實例化（Instantiates）一個新的（new）OrderItem 物件<br>
     * 採用多載（overload）方法進行，此建構子用於修改訂單細項時
     *
     * @param order_product_id 訂單產品編號
     * @param order_id 會員密碼
     * @param product_id 產品編號
     * @param price 產品價格
     * @param quantity 產品數量
     * @param subtotal 小計
     */
    public Adoption_detail(int adoption_detail_id, int adoption_request_id, int for_adoption_id) {
        this.adoption_detail_id = adoption_detail_id;

        getFor_adoptionFromDB(for_adoption_id);
    }

    /**
     * 從 DB 中取得產品
     */
    private void getFor_adoptionFromDB(int for_adoption_id) {
        String id = String.valueOf(for_adoption_id);
        this.fa = fah.getById(id);
    }

    /**
     * 取得產品
     *
     * @return Product 回傳產品
     */
    public For_adoption getFor_adoption() {
        return this.fa;
    }

    /**
     * 設定訂單細項編號
     */
    public void setId(int adoption_detail_id) {
        this.adoption_detail_id = adoption_detail_id;
    }

    /**
     * 取得訂單細項編號
     *
     * @return int 回傳訂單細項編號
     */
    public int getId() {
        return this.adoption_detail_id;
    }

    
    /**
     * 取得產品細項資料
     *
     * @return JSONObject 回傳產品細項資料
     */
    public JSONObject getData() {
        JSONObject data = new JSONObject();
        data.put("id", getId());
        data.put("for_adoption", getFor_adoption().getData());

        return data;
    }
}
