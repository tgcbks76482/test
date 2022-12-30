package ncu.im3069.demo.app;

import java.sql.*;
import java.util.*;

import org.json.*;

import ncu.im3069.demo.util.DBMgr;

public class Adoption_requestHelper {
    
    private static Adoption_requestHelper arh;
    private Connection conn = null;
    private PreparedStatement pres = null;
    private Adoption_detailHelper oph =  Adoption_detailHelper.getHelper();  ////////
    
    private Adoption_requestHelper() {
    }
    
    public static Adoption_requestHelper getHelper() {
        if(arh == null) arh = new Adoption_requestHelper();
        
        return arh;
    }
    
    public JSONObject create(Adoption_request adoption_request) {
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        long id = -1;
        JSONArray rca = new JSONArray();
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "INSERT INTO `missa`.`adoption_request`(`real_name`, `address`, `email`, `status`)"
                    + " VALUES(?, ?, ?, ?)";
            
            /** 取得所需之參數 */
            String real_name = adoption_request.getRealName();
            String address = adoption_request.getAddress();
            String email = adoption_request.getEmail();
            String status = adoption_request.getStatus();
            
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pres.setString(1, real_name);
            pres.setString(2, address);
            pres.setString(3, email);
            pres.setString(4, status);

            /** 執行新增之SQL指令並記錄影響之行數 */
            pres.executeUpdate();
            
            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
            
            ResultSet rs = pres.getGeneratedKeys();

            if (rs.next()) {
                id = rs.getLong(1);
                ArrayList<Adoption_detail> rcd = adoption_request.getRequestCase();
                rca = rch.createByList(id, rcd);
            }
        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(pres, conn);
        }

        /** 將SQL指令、花費時間與影響行數，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("adoption_reqeust_id", id);
        response.put("request_case_id", rca);

        return response;
    }
    
    public JSONObject getAll() {
        Order o = null;
        JSONArray jsa = new JSONArray();
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 紀錄程式開始執行時間 */
        long start_time = System.nanoTime();
        /** 紀錄SQL總行數 */
        int row = 0;
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT * FROM `missa`.`adoption_request`";
            
            /** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
            pres = conn.prepareStatement(sql);
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
            
            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            while(rs.next()) {
                /** 每執行一次迴圈表示有一筆資料 */
                row += 1;
                
                /** 將 ResultSet 之資料取出 */
                int id = rs.getInt("adoption_request_id");
                String real_name = rs.getString("real_name");
                String address = rs.getString("address");
                String email = rs.getString("email");
                String status = rs.getString("status");
                
                /** 將每一筆商品資料產生一名新Product物件 */
                a = new Adoption_request(id, real_name, last_name, address, email, status);
                /** 取出該項商品之資料並封裝至 JSONsonArray 內 */
                jsa.put(a.getAdoption_requestAllInfo());
            }

        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(rs, pres, conn);
        }
        
        /** 紀錄程式結束執行時間 */
        long end_time = System.nanoTime();
        /** 紀錄程式執行時間 */
        long duration = (end_time - start_time);
        
        /** 將SQL指令、花費時間、影響行數與所有會員資料之JSONArray，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);
        response.put("data", jsa);

        return response;
    }
    
    public JSONObject getById(String adoption_request_id) {
        JSONObject data = new JSONObject();
        Order o = null;
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 紀錄程式開始執行時間 */
        long start_time = System.nanoTime();
        /** 紀錄SQL總行數 */
        int row = 0;
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT * FROM `missa`.`adoption_request` WHERE `adoption_request`.`id` = ?";
            
            /** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
            pres = conn.prepareStatement(sql);
            pres.setString(1, adoption_request_id);
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
            
            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            while(rs.next()) {
                /** 每執行一次迴圈表示有一筆資料 */
                row += 1;
                
                /** 將 ResultSet 之資料取出 */
                int id = rs.getInt("adoption_request_id");
                String real_name = rs.getString("real_name");
                String address = rs.getString("address");
                String email = rs.getString("email");
                String status = rs.getString("status");
                
                /** 將每一筆商品資料產生一名新Product物件 */
                a = new Adoption_request(id, real_name, last_name, address, email, status);
                /** 取出該項商品之資料並封裝至 JSONsonArray 內 */
                data = a.getAdoption_requestAllInfo();
            }

        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(rs, pres, conn);
        }
        
        /** 紀錄程式結束執行時間 */
        long end_time = System.nanoTime();
        /** 紀錄程式執行時間 */
        long duration = (end_time - start_time);
        
        /** 將SQL指令、花費時間、影響行數與所有會員資料之JSONArray，封裝成JSONObject回傳 */
        
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);
        response.put("data", data);

        return response;
    }
}
