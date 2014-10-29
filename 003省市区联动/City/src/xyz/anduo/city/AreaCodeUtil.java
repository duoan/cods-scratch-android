package xyz.anduo.city;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * 城市代码
 * 
 * @author zd
 * 
 */
public class AreaCodeUtil {

	private List<String> provinceList = new ArrayList<String>();
	private List<String> cityList = new ArrayList<String>();
	private List<String> counyList = new ArrayList<String>();
	public List<String> provinceListCode = new ArrayList<String>();
	public List<String> cityListCode = new ArrayList<String>();
	public List<String> counyListCode = new ArrayList<String>();
	/** 单例 */
	public static AreaCodeUtil instance;

	private AreaCodeUtil() {
	}

	public List<String> getProvinceListCode() {
		return provinceListCode;
	}

	public List<String> getCityListCode() {
		return cityListCode;
	}

	public void setCityListCode(List<String> cityListCode) {
		this.cityListCode = cityListCode;
	}

	public List<String> getCounyListCode() {
		return counyListCode;
	}

	public void setCounyListCode(List<String> counyListCode) {
		this.counyListCode = counyListCode;
	}

	public void setProvinceListCode(ArrayList<String> provinceListCode) {

		this.provinceListCode = provinceListCode;
	}

	/**
	 * 获取单例
	 * 
	 * @return
	 */
	public static AreaCodeUtil getSingleton() {
		if (null == instance) {
			instance = new AreaCodeUtil();
		}
		return instance;
	}

	public List<String> getProvince(List<AreaEntity> provice) {
		if (provinceListCode.size() > 0) {
			provinceListCode.clear();
		}
		if (provinceList.size() > 0) {
			provinceList.clear();
		}
		for (int i = 0; i < provice.size(); i++) {
			provinceList.add(provice.get(i).getName());
			provinceListCode.add(provice.get(i).getId());
		}
		return provinceList;

	}

	public List<String> getCity(Map<String, List<AreaEntity>> cityHashMap, String provicecode) {
		if (cityListCode.size() > 0) {
			cityListCode.clear();
		}
		if (cityList.size() > 0) {
			cityList.clear();
		}
		List<AreaEntity> city = new ArrayList<AreaEntity>();
		city = cityHashMap.get(provicecode);
		
		//System.out.println("city--->" + city.toString());
		
		for (int i = 0; i < city.size(); i++) {
			cityList.add(city.get(i).getName());
			cityListCode.add(city.get(i).getId());
		}
		return cityList;

	}

	public List<String> getCouny(Map<String, List<AreaEntity>> cityHashMap, String citycode) {
		//System.out.println("citycode" + citycode);
		
		List<AreaEntity> couny = null;
		if (counyListCode.size() > 0) {
			counyListCode.clear();

		}
		if (counyList.size() > 0) {
			counyList.clear();
		}
		couny = new ArrayList<AreaEntity>();

		couny = cityHashMap.get(citycode);
		//System.out.println("couny--->" + couny.toString());
		
		for (int i = 0; i < couny.size(); i++) {
			counyList.add(couny.get(i).getName());
			counyListCode.add(couny.get(i).getId());
		}
		return counyList;

	}
}
