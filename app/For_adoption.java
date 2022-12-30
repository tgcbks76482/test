package ncu.im3069.demo.app;

import org.json.*;

public class For_adoption{

    /** id，編號 */
    private int for_adoption_id;

    /** id，姓名 */
    private String name;

    /** id，物種 */
    private String species;

    /** id，會員編號 */
    private String breed;

    /** id，會員編號 */
	private int age;

    private String gender;

    private String size;

    private String exterior;

    private String ligation;

    private String area;

    private String description;

    private String img_path;

    private String status;
    /**
     * 實例化（Instantiates）一個新的（new）Product 物件<br>
     * 採用多載（overload）方法進行，此建構子用於新增產品時
     *
     * @param id 產品編號
     */
	public For_adoption(int for_adoption_id) {
		this.for_adoption_id = for_adoption_id;
	}

    /**
     * 實例化（Instantiates）一個新的（new）Product 物件<br>
     * 採用多載（overload）方法進行，此建構子用於新增產品時
     *
     * @param name 產品名稱
     * @param price 產品價格
     * @param image 產品圖片
     */
	public For_adoption(String name, String species, String breed, int age, String gender, String size, String exterior, String ligation, String area, String description, String img_path) {
        this.name = name;
		this.species = species;
		this.breed = breed;
        this.age = age;
        this.gender = gender;
        this.size = size;
        this.exterior = exterior;
        this.ligation = ligation;
        this.area = area;
        this.description = description;
        this.img_path = img_path;
	}

    /**
     * 實例化（Instantiates）一個新的（new）Product 物件<br>
     * 採用多載（overload）方法進行，此建構子用於修改產品時
     *
     * @param for_adoption_id  產品編號
     * @param name 產品名稱
     * @param price 產品價格
     * @param image 產品圖片
     * @param describe 產品敘述
     */
	public For_adoption(int for_adoption_id, String name, String species, String breed, int age, String gender, String size, String exterior, String ligation, String area, String description, String img_path, String status) {
		this.for_adoption_id = for_adoption_id;
        this.name = name;
		this.species = species;
		this.breed = breed;
        this.age = age;
        this.gender = gender;
        this.size = size;
        this.exterior = exterior;
        this.ligation = ligation;
        this.area = area;
        this.description = description;
        this.img_path = img_path;
        this.status = status;
	}

    /**
     * 取得產品編號
     *
     * @return int 回傳產品編號
     */
	public int getFor_adoption_id() {
		return this.for_adoption_id;;
	}

    /**
     * 取得產品名稱
     *
     * @return String 回傳產品名稱
     */
	public String getName() {
		return this.name;
	}

    /**
     * 取得產品價格
     *
     * @return double 回傳產品價格
     */
	public String getSpecies() {
		return this.species;
	}

	public String getBreed() {
		return this.breed;
	}

	public int getAge() {
		return this.age;
	}

    public String getGender() {
		return this.gender;
	}

    public String getSize() {
		return this.size;
	}

    public String getExterior() {
		return this.exterior;
	}

    public String getLigation() {
		return this.ligation;
	}

    public String getArea() {
		return this.area;
	}

    public String getImg_path() {
		return this.img_path;
	}

    public String getStatus() {
		return this.status;
	}

    /**
     * 取得產品敘述
     *
     * @return String 回傳產品敘述
     */
	public String getDescription() {
		return this.description;
	}

    /**
     * 取得產品資訊
     *
     * @return JSONObject 回傳產品資訊
     */
	public JSONObject getData() {
        /** 透過JSONObject將該項產品所需之資料全部進行封裝*/
        JSONObject jso = new JSONObject();
        jso.put("for_adoption_id", getFor_adoption_id());
        jso.put("name", getName());
        jso.put("species", getSpecies());
        jso.put("breed", getBreed());
        jso.put("age", getAge());
        jso.put("gender", getGender());
        jso.put("size", getSize());
        jso.put("exterior", getExterior());
        jso.put("ligation", getLigation());
        jso.put("area", getArea());
        jso.put("description", getDescription());
        jso.put("img_path", getImg_path());
        jso.put("status", getStatus());

        return jso;
    }
}
