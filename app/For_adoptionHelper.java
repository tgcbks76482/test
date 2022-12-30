package ncu.im3069.demo.app;

import java.sql.*;

import org.json.*;

import ncu.im3069.demo.util.DBMgr;
import ncu.im3069.demo.app.For_adoption;

public class For_adoptionHelper {
    private For_adoptionHelper() {
        
    }
    
    private static For_adoptionHelper fah;
    private Connection conn = null;
    private PreparedStatement pres = null;
    
    public static AdoptionHelper getHelper() {
        /** Singleton檢查是否已經有For_apotionHelper物件，若無則new一個，若有則直接回傳 */
        if(fah == null) fah = new For_adoptionHelper();
        
        return fah;
    }


    public JSONObject getAll() {
        /** 新建一個 For_adoption 物件之 a 變數，用於紀錄每一位查詢回之商品資料 */
    	For_adoption fa = null;
        /** 用於儲存所有檢索回之商品，以JSONArray方式儲存 */
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
            String sql = "SELECT * FROM `missa`.`for_adoption`";
            
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
                int for_adoption_id = rs.getInt("for_adoption_id");          
                String name = rs.getString("name");
                String species = rs.getString("species");
                String breed = rs.getString("breed");
                int age = rs.getInt("age");
                String gender = rs.getString("gender");
                String size = rs.getString("size");
                String exterior = rs.getString("exterior");
                String ligation = rs.getString("ligation");
                String area = rs.getString("area");
                String description = rs.getString("description");
                String image = rs.getString("img_path");
                String status = rs.getString("status");
              
                
                /** 將每一筆商品資料產生一名新For_adoption物件 */
                fa = new For_adoption(for_adoption_id, name, species, breed, age, gender, size, exterior,
                                      ligation, area, description, image, status);         //還沒改
                /** 取出該項商品之資料並封裝至 JSONsonArray 內 */
                jsa.put(fa.getData());
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


    public JSONObject getByIdList(String data) {
        /** 新建一個 Product 物件之 m 變數，用於紀錄每一位查詢回之商品資料 */
        For_adoption fa = null;
        /** 用於儲存所有檢索回之商品，以JSONArray方式儲存 */
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
          String[] in_para = DBMgr.stringToArray(data, ",");
          /** SQL指令 */
          String sql = "SELECT * FROM `missa`.`for_adoption` WHERE `for_adoption`.`for_adoption_id`";
          for (int i=0 ; i < in_para.length ; i++) {
              sql += (i == 0) ? "in (?" : ", ?";
              sql += (i == in_para.length-1) ? ")" : "";
          }
          
          /** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
          pres = conn.prepareStatement(sql);
          for (int i=0 ; i < in_para.length ; i++) {
            pres.setString(i+1, in_para[i]);
          }
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
                int for_adoption_id = rs.getInt("for_adoption_id");          
                String name = rs.getString("name");
                String species = rs.getString("species");
                String breed = rs.getString("breed");
                int age = rs.getInt("age");
                String gender = rs.getString("gender");
                String size = rs.getString("size");
                String exterior = rs.getString("exterior");
                String ligation = rs.getString("ligation");
                String area = rs.getString("area");
                String description = rs.getString("description");
                String image = rs.getString("img_path");
                String status = rs.getString("status");
              
                
                /** 將每一筆商品資料產生一名新For_adoption物件 */
                fa = new For_adoption(for_adoption_id, name, species, breed, age, gender, size, exterior,
                                      ligation, area, description, image, status);         
                /** 取出該項商品之資料並封裝至 JSONsonArray 內 */
                jsa.put(fa.getData());
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


    public JSONObject getById(String id) {
        /** 新建一個 For_adoption 物件之 fa 變數，用於紀錄每一位查詢回之會員資料 */
        For_adoption fa = null;
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT * FROM `missa`.`for_adoption` WHERE `for_adoption_id` = ? LIMIT 1";
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setString(1, id);
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
            
            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            /** 正確來說資料庫只會有一筆該會員編號之資料，因此其實可以不用使用 while 迴圈 */
            while(rs.next()) {
                
                /** 將 ResultSet 之資料取出 */
                int for_adoption_id = rs.getInt("for_adoption_id");          
                String name = rs.getString("name");
                String species = rs.getString("species");
                String breed = rs.getString("breed");
                int age = rs.getInt("age");
                String gender = rs.getString("gender");
                String size = rs.getString("size");
                String exterior = rs.getString("exterior");
                String ligation = rs.getString("ligation");
                String area = rs.getString("area");
                String description = rs.getString("description");
                String image = rs.getString("img_path");
                String status = rs.getString("status");
              
                
                /** 將每一筆商品資料產生一名新For_adoption物件 */
                fa = new For_adoption(for_adoption_id, name, species, breed, age, gender, size, exterior,
                                      ligation, area, description, image, status);         
                
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
        

        return fa;
    }






    public JSONObject deleteByID(int id) {
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
            String sql = "DELETE FROM `missa`.`for_adoption` WHERE `for_adoption_id` = ? LIMIT 1";
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1, id);
            /** 執行刪除之SQL指令並記錄影響之行數 */
            row = pres.executeUpdate();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
            
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
        
        /** 將SQL指令、花費時間與影響行數，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);

        return response;
    }


    public JSONObject create(For_adoption fa) {
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 紀錄程式開始執行時間 */
        long start_time = System.nanoTime();
        /** 紀錄SQL總行數 */
        int row = 0;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "INSERT INTO `missa`.`for_adoptoin`(`name`, `species`, `breed`, `age`, `gender`, `size`, `exterior`, `ligation`,
							     `area`, `description`, `img_path`, `status`)"
                    + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            /** 取得所需之參數 */
            String name = fa.getName();
            String species = fa.getSpecies();
            String breed = fa.getBreed();
            int age = fa.getAge();
            String gender = fa.Gender();
            String size = fa.getSize();
            String exterior = fa.getExterior();
            String ligation = fa.getLigation();
            String area = fa.getArea();
            String description = fa.getDescription();
            String image = fa.getImage();
            String status = fa.getStatus();
            
            /** 將參數回填至SQL指令當中 */               
            pres = conn.prepareStatement(sql);
            pres.setString(1, name);
            pres.setString(2, species);
            pres.setString(3, breed);
            pres.setInt(4, age);
            pres.setString(5, gender);
            pres.setString(6, size);
            pres.setString(7, exterior);
            pres.setString(8, ligation);
            pres.setString(9, area);
            pres.setString(10, description);
            pres.setString(11, image);
            pres.setString(12, status);
            
            /** 執行新增之SQL指令並記錄影響之行數 */
            row = pres.executeUpdate();
            
            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);

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

        /** 紀錄程式結束執行時間 */
        long end_time = System.nanoTime();
        /** 紀錄程式執行時間 */
        long duration = (end_time - start_time);

        /** 將SQL指令、花費時間與影響行數，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("time", duration);
        response.put("row", row);

        return response;
    }


    public JSONObject update(For_adoption fa) {
        /** 紀錄回傳之資料 */
        JSONArray jsa = new JSONArray();
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 紀錄程式開始執行時間 */
        long start_time = System.nanoTime();
        /** 紀錄SQL總行數 */
        int row = 0;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "Update `missa`.`for_adoption` SET `name` = ? ,`species` = ? , `breed` = ?, `age` = ? ,`gender` = ? ,
                        `size` = ? ,`exterior` = ? ,`ligation` = ? ,`area` = ? ,`description` = ? ,`image` = ? WHERE `for_adoption_id` = ?";  //還沒改
            /** 取得所需之參數 */
            int for_adoption_id = fa.getInt();
            String name = fa.getName();
            String species = fa.getSpecies();
            String breed = fa.getBreed();
            int age = fa.getAge();
            String gender = fa.Gender();
            String size = fa.getSize();
            String exterior = fa.getExterior();
            String ligation = fa.getLigation();
            String area = fa.getArea();
            String description = fa.getDescription();
            String image = fa.getImage();

            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);     
            pres.setString(1, name);
            pres.setString(2, species);
            pres.setString(3, breed);
            pres.setInt(4, age);
            pres.setString(5, gender);
            pres.setString(6, size);
            pres.setString(7, exterior);
            pres.setString(8, ligation);
            pres.setString(9, area);
            pres.setString(10, description);
            pres.setString(11, image);
            pres.setString(12, status);
            pres.setInt(12, for_adoption_id);
            /** 執行更新之SQL指令並記錄影響之行數 */
            row = pres.executeUpdate();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);

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
        
        /** 紀錄程式結束執行時間 */
        long end_time = System.nanoTime();
        /** 紀錄程式執行時間 */
        long duration = (end_time - start_time);
        
        /** 將SQL指令、花費時間與影響行數，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);
        response.put("data", jsa);

        return response;
    }



    public void updateStatus(For_adoption fa, String status) {      
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "Update `missa`.`for_adoption` SET `status` = ? WHERE `id` = ?";
            /** 取得會員編號 */
            int id = m.getID();
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setString(1, status);
            pres.setInt(2, id);
            /** 執行更新之SQL指令 */
            pres.executeUpdate();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
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

}