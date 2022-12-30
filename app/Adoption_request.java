package ncu.im3069.demo.app;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

import org.json.*;

public class Adoption_request {

    /** id，訂單編號 */
    private int adoption_request_id;

    /** first_name，會員姓名 */
    private String real_name;

    /** email，會員電子郵件信箱 */
    private String email;

    /** address，會員地址 */
    private String address;

    /** address，會員地址 */
    private String status;

    /** list，訂單列表 */
    private ArrayList<Adoption_detail> list = new ArrayList<Adoption_detail>();


    /** oph，OrderItemHelper 之物件與 Order 相關之資料庫方法（Sigleton） */
    private Adoption_detailHelper adh = Adoption_detailHelper.getHelper();

    /**
     * 實例化（Instantiates）一個新的（new）Order 物件<br>
     * 採用多載（overload）方法進行，此建構子用於建立訂單資料時，產生一個新的訂單
     *
     * @param first_name 會員名
     * @param last_name 會員姓
     * @param email 會員電子信箱
     * @param address 會員地址
     * @param phone 會員姓名
     */
    public Adoption_request(String real_name, String email, String address, String status) {
        this.real_name = real_name;
        this.email = email;
        this.address = address;
        this.status = status;
    }

    /**
     * 實例化（Instantiates）一個新的（new）Order 物件<br>
     * 採用多載（overload）方法進行，此建構子用於修改訂單資料時，新改資料庫已存在的訂單
     *
     * @param first_name 會員名
     * @param last_name 會員姓
     * @param email 會員電子信箱
     * @param address 會員地址
     * @param phone 會員姓名
     * @param create 訂單創建時間
     * @param modify 訂單修改時間
     */
    public Adoption_request(int adoption_request_id, String real_name, String email, String address, String status) {
        this.adoption_request_id = adoption_request_id;
        this.real_name = real_name;
        this.email = email;
        this.address = address;
        this.status = status;
        getAdoption_detailFromDB();
    }

    /**
     * 新增一個訂單產品及其數量
     */
    public void addAdoption_detail(For_adoption fa) {
        this.list.add(new Adoption_detail(fa));
    }

    /**
     * 新增一個訂單產品
     */
    public void addAdoption_detail(Adoption_detail ad) {
        this.list.add(ad);
    }

    /**
     * 設定訂單編號
     */
    public void setId(int adoption_request_id) {
        this.adoption_request_id = adoption_request_id;
    }

    /**
     * 取得訂單編號
     *
     * @return int 回傳訂單編號
     */
    public int getAdoption_request_id() {
        return this.adoption_request_id;
    }

    /**
     * 取得訂單會員的名
     *
     * @return String 回傳訂單會員的名
     */
    public String getReal_name() {
        return this.real_name;
    }

    /**
     * 取得訂單信箱
     *
     * @return String 回傳訂單信箱
     */
    public String getEmail() {
        return this.email;
    }


    /**
     * 取得訂單地址
     *
     * @return String 回傳訂單地址
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * 取得訂單電話
     *
     * @return String 回傳訂單電話
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * 取得該名會員所有資料
     *
     * @return the data 取得該名會員之所有資料並封裝於JSONObject物件內
     */
    public ArrayList<Adoption_detail> getAdoption_detail() {
        return this.list;
    }

    /**
     * 從 DB 中取得訂單產品
     */
    private void getAdoption_detailFromDB() {
        ArrayList<adoption_detail> data = adh.getAdoption_detailByAdoption_request_id(this.adoption_request_id);
        this.list = data;
    }

    /**
     * 取得訂單基本資料
     *
     * @return JSONObject 取得訂單基本資料
     */
    public JSONObject getAdoption_requestData() {
        JSONObject jso = new JSONObject();
        jso.put("adoption_request_id", getAdoption_request_id());
        jso.put("real_name", getReal_name());
        jso.put("email", getEmail());
        jso.put("address", getAddress());
        jso.put("status", getStatus());

        return jso;
    }

    /**
     * 取得訂單產品資料
     *
     * @return JSONArray 取得訂單產品資料
     */
    public JSONArray getAdoption_detailData() {
        JSONArray result = new JSONArray();

        for(int i=0 ; i < this.list.size() ; i++) {
            result.put(this.list.get(i).getData());
        }

        return result;
    }

    /**
     * 取得訂單所有資訊
     *
     * @return JSONObject 取得訂單所有資訊
     */
    public JSONObject getAdoption_requestAllInfo() {
        JSONObject jso = new JSONObject();
        jso.put("adoption_request_info", getAdoption_requestData());
        jso.put("for_adoption_info", getAdoption_detailData());

        return jso;
    }

    /**
     * 設定訂單產品編號
     */
    public void setAdoption_request_id(JSONArray data) {
        for(int i=0 ; i < this.list.size() ; i++) {
            this.list.get(i).setId((int) data.getLong(i));
        }
    }

}
