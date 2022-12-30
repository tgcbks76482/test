package ncu.im3069.demo.app;

import java.sql.*;
import java.util.*;

import org.json.*;

import ncu.im3069.demo.util.DBMgr;

public class Adoption_detailHelper {
    
    private Adoption_detailHelper() {
        
    }
    
    private static Adoption_detailHelper oph;
    private Connection conn = null;
    private PreparedStatement pres = null;
    
    /**
     * 靜態方法<br>
     * 實作Singleton（單例模式），僅允許建立一個Adoption_detailHelper物件
     *
     * @return the helper 回傳Adoption_detailHelper物件
     */
    public static Adoption_detailHelper getHelper() {
        /** Singleton檢查是否已經有Adoption_detailHelper物件，若無則new一個，若有則直接回傳 */
        if(adh == null) adh = new Adoption_detailHelper();
        
        return adh;
    }
    
    public JSONArray createByList(long for_adoption_id, List<Adoption_detail> requestcase) {           //oderproduct-->requestcase
        JSONArray jsa = new JSONArray();
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        
        for(int i=0 ; i < requestcase.size() ; i++) {
            Adoption_detail rc = requestcase.get(i);
            
            /** 取得所需之參數 */
            int for_adoption_id = rc.getFor_adoption().getID();
            String name = rc.getName();
            
            
            try {
                /** 取得資料庫之連線 */
                conn = DBMgr.getConnection();
                /** SQL指令 */
                String sql = "INSERT INTO `missa`.`request_case`(`adoption_request_id`, `for_adoption_id`, `name`)"
                        + " VALUES(?, ?, ?)";
                
                /** 將參數回填至SQL指令當中 */
                pres = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pres.setLong(1, adoption_request_id);
                pres.setInt(2, for_adoption_id);
                pres.setString(3, name);
                
                
                /** 執行新增之SQL指令並記錄影響之行數 */
                pres.executeUpdate();
                
                /** 紀錄真實執行的SQL指令，並印出 **/
                exexcute_sql = pres.toString();
                System.out.println(exexcute_sql);
                
                ResultSet rs = pres.getGeneratedKeys();

                if (rs.next()) {                        //request_case的id
                    long id = rs.getLong(1);
                    jsa.put(id);
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
        }
        
        return jsa;
    }
    
    public ArrayList<Adoption_detail> getRequestCaseByAdoptionRequestId(int adoption_request_id) {
        ArrayList<Adoption_detail> result = new ArrayList<Adoption_detail>();
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        ResultSet rs = null;
        Adoption_detail rc;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT * FROM `missa`.`request_case` WHERE `request_case`.`adoption_request_id` = ?";
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1, adoption_request_id);
            
            /** 執行新增之SQL指令並記錄影響之行數 */
            rs = pres.executeQuery();
            
            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
            
            while(rs.next()) {
                /** 每執行一次迴圈表示有一筆資料 */
                
                /** 將 ResultSet 之資料取出 */
                int request_case_id = rs.getInt("id");
                int for_adoption_id = rs.getInt("for_adoption_id");
                String name = rs.getString("name");
                
                /** 將每一筆會員資料產生一名新Member物件 */
                rc = new Adoption_detail(request_case_id, adoption_request_id, for_adoption_id, name);
                /** 取出該名會員之資料並封裝至 JSONsonArray 內 */
                result.add(rc);
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
        
        return result;
    }
}
