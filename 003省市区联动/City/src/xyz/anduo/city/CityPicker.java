package xyz.anduo.city;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import xyz.anduo.city.ScrollerNumberPicker.OnSelectListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * 城市Picker
 * 
 * @author zihao
 * 
 */
public class CityPicker extends LinearLayout {
	/** 滑动控件 */
	private ScrollerNumberPicker provinceScollerNumberPicker;
	private ScrollerNumberPicker cityScollerNumberPicker;
	private ScrollerNumberPicker counyScollerNumberPicker;
	/** 选择监听 */
	private OnSelectingListener onSelectingListener;
	/** 刷新界面 */
	private static final int REFRESH_VIEW = 0x001;
	/** 临时日期 */
	private int tempProvinceIndex = -1;
	private int temCityIndex = -1;
	private int tempCounyIndex = -1;
	private Context context;
	
	/** 存储省市区三级数据 */
	private List<AreaEntity> provinceList = new ArrayList<AreaEntity>();
	private Map<String, List<AreaEntity>> cityMap = new HashMap<String, List<AreaEntity>>();
	private Map<String, List<AreaEntity>> counyMap = new HashMap<String, List<AreaEntity>>();

	private AreaCodeUtil areaCodeUtil;
	
	private String cityCodeString;
	private String cityString;

	public CityPicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		getAddressInfo();
	}

	public CityPicker(Context context) {
		super(context);
		this.context = context;
		getAddressInfo();
	}

	// 获取城市信息
	private void getAddressInfo() {
		// 读取城市信息string
		JSONParser parser = new JSONParser();
		String jsonStr = FileUtil.readAssets(context, "area.json");
		provinceList = parser.getJSONParserResult(jsonStr, "area0");
		cityMap = parser.getJSONParserResultArray(jsonStr, "area1");
		counyMap = parser.getJSONParserResultArray(jsonStr, "area2");
	}

	public static class JSONParser {
		public List<String> provinceListCode = new ArrayList<String>();
		public List<String> cityListCode = new ArrayList<String>();

		public List<AreaEntity> getJSONParserResult(String JSONString, String key) {
			List<AreaEntity> list = new ArrayList<AreaEntity>();
			JsonObject result = new JsonParser().parse(JSONString).getAsJsonObject().getAsJsonObject(key);

			Iterator<?> iterator = result.entrySet().iterator();
			while (iterator.hasNext()) {
				@SuppressWarnings("unchecked")
				Map.Entry<String, JsonElement> entry = (Entry<String, JsonElement>) iterator.next();
				AreaEntity areaEntity = new AreaEntity();

				areaEntity.setName(entry.getValue().getAsString());
				areaEntity.setId(entry.getKey());
				provinceListCode.add(entry.getKey());
				list.add(areaEntity);
			}
			
			return list;
		}

		public HashMap<String, List<AreaEntity>> getJSONParserResultArray(String JSONString, String key) {
			HashMap<String, List<AreaEntity>> hashMap = new HashMap<String, List<AreaEntity>>();
			JsonObject result = new JsonParser().parse(JSONString).getAsJsonObject().getAsJsonObject(key);

			Iterator<?> iterator = result.entrySet().iterator();
			while (iterator.hasNext()) {
				@SuppressWarnings("unchecked")
				Map.Entry<String, JsonElement> entry = (Entry<String, JsonElement>) iterator.next();
				List<AreaEntity> list = new ArrayList<AreaEntity>();
				JsonArray array = entry.getValue().getAsJsonArray();
				for (int i = 0; i < array.size(); i++) {
					AreaEntity areaEntity = new AreaEntity();
					areaEntity.setName(array.get(i).getAsJsonArray().get(0).getAsString());
					areaEntity.setId(array.get(i).getAsJsonArray().get(1).getAsString());
					cityListCode.add(array.get(i).getAsJsonArray().get(1).getAsString());
					list.add(areaEntity);
				}
				
				hashMap.put(entry.getKey(), list);
			}
			return hashMap;
		}
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		LayoutInflater.from(getContext()).inflate(R.layout.city_picker, this);
		
		this.areaCodeUtil = AreaCodeUtil.getSingleton();
		// 获取控件引用
		this.provinceScollerNumberPicker = (ScrollerNumberPicker) findViewById(R.id.province);
		this.cityScollerNumberPicker = (ScrollerNumberPicker) findViewById(R.id.city);
		this.counyScollerNumberPicker = (ScrollerNumberPicker) findViewById(R.id.couny);
		
		//初始化控件数据
		this.provinceScollerNumberPicker.setData(areaCodeUtil.getProvince(provinceList));
		this.provinceScollerNumberPicker.setDefault(1);
		this.cityScollerNumberPicker.setData(areaCodeUtil.getCity(cityMap, areaCodeUtil.getProvinceListCode().get(1)));
		this.cityScollerNumberPicker.setDefault(1);
		this.counyScollerNumberPicker.setData(areaCodeUtil.getCouny(counyMap, areaCodeUtil.getCityListCode().get(1)));
		this.counyScollerNumberPicker.setDefault(1);
		
		//绑定监听事件
		this.provinceScollerNumberPicker.setOnSelectListener(new OnSelectListener() {

			@Override
			public void endSelect(int id, String text) {
				
				if (text.equals("") || text == null)
					return;
				if (tempProvinceIndex != id) {
					
					String selectCity = cityScollerNumberPicker.getSelectedText();
					if (selectCity == null || selectCity.equals("")){
						return;
					}
					
					String selectCouny = counyScollerNumberPicker.getSelectedText();
					
					if (selectCouny == null || selectCouny.equals("")){
						return;
					}
					// 城市数组
					cityScollerNumberPicker.setData(areaCodeUtil.getCity(cityMap, areaCodeUtil.getProvinceListCode().get(id)));
					cityScollerNumberPicker.setDefault(1);
					
					counyScollerNumberPicker.setData(areaCodeUtil.getCouny(counyMap, areaCodeUtil.getCityListCode().get(1)));
					counyScollerNumberPicker.setDefault(1);
					
					int lastProvince = Integer.valueOf(provinceScollerNumberPicker.getListSize());
					if (id > lastProvince) {
						provinceScollerNumberPicker.setDefault(lastProvince - 1);
					}
				}
				
				tempProvinceIndex = id;
				Message message = new Message();
				message.what = REFRESH_VIEW;
				handler.sendMessage(message);
			}

			@Override
			public void selecting(int id, String text) {
				
			}
			
		});
		
		
		this.cityScollerNumberPicker.setOnSelectListener(new OnSelectListener() {

			@Override
			public void endSelect(int id, String text) {
				if (text.equals("") || text == null)
					return;
				if (temCityIndex != id) {
					String selectProvince = provinceScollerNumberPicker.getSelectedText();
					if (selectProvince == null || selectProvince.equals(""))
						return;
					String selectCouny = counyScollerNumberPicker.getSelectedText();
					if (selectCouny == null || selectCouny.equals(""))
						return;
					
					counyScollerNumberPicker.setData(areaCodeUtil.getCouny(counyMap, areaCodeUtil.getCityListCode().get(id)));
					counyScollerNumberPicker.setDefault(1);
					
					int lastCity = Integer.valueOf(cityScollerNumberPicker.getListSize());
					if (id > lastCity) {
						cityScollerNumberPicker.setDefault(lastCity - 1);
					}
				}
				temCityIndex = id;
				Message message = new Message();
				message.what = REFRESH_VIEW;
				handler.sendMessage(message);
			}

			@Override
			public void selecting(int id, String text) {

			}
		});
		
		
		this.counyScollerNumberPicker.setOnSelectListener(new OnSelectListener() {

			@Override
			public void endSelect(int id, String text) {

				if (text.equals("") || text == null)
					return;
				if (tempCounyIndex != id) {
					String selectProvince = provinceScollerNumberPicker.getSelectedText();
					if (selectProvince == null || selectProvince.equals(""))
						return;
					String selectCity = cityScollerNumberPicker.getSelectedText();
					if (selectCity == null || selectCity.equals(""))
						return;
					// 城市数组
					cityCodeString = areaCodeUtil.getCounyListCode().get(id);
					int lastCouny = Integer.valueOf(counyScollerNumberPicker.getListSize());
					if (id > lastCouny) {
						counyScollerNumberPicker.setDefault(lastCouny - 1);
					}
					
				}
				
				tempCounyIndex = id;
				Message message = new Message();
				message.what = REFRESH_VIEW;
				handler.sendMessage(message);
			}

			@Override
			public void selecting(int id, String text) {
				
			}
		});
	}

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case REFRESH_VIEW:
				if (onSelectingListener != null)
					onSelectingListener.selected(true);
				break;
			default:
				break;
			}
		}

	};

	public void setOnSelectingListener(OnSelectingListener onSelectingListener) {
		this.onSelectingListener = onSelectingListener;
	}

	public String getCityCodeString() {
		return cityCodeString;
	}

	public String getCityString() {
		cityString = provinceScollerNumberPicker.getSelectedText() + cityScollerNumberPicker.getSelectedText() + counyScollerNumberPicker.getSelectedText();
		return cityString;
	}
	
	public interface OnSelectingListener {
		public void selected(boolean selected);
	}
	
}
