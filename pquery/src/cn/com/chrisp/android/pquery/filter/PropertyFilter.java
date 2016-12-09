package cn.com.chrisp.android.pquery.filter;

import java.lang.reflect.InvocationTargetException;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import cn.com.chrisp.android.pquery.IFilter;

public class PropertyFilter implements IFilter{
	
	private String property;
	private Object value;
	private static final Class<?>[] cs = null;
	
	public PropertyFilter(String property, Object value){
		if(TextUtils.isEmpty(property)){
			throw new RuntimeException("property不能为空");
		}
		this.property = property;
		this.value = value;
	}

	@SuppressLint("DefaultLocale")
	@Override
	public boolean filter(View v) {
		if(v == null){
			return false;
		}
		String vMethodName = "get" + (property.charAt(0) + "").toUpperCase() + property.substring(1);
		try {
			Object vValue = v.getClass().getDeclaredMethod(vMethodName, cs).invoke(v);
			if(vValue == null){
				if(value == null){
					return true;
				}
			}else{
				return vValue.equals(value);
			}
		} catch (IllegalAccessException e) {
			Log.wtf(PropertyFilter.class.getCanonicalName(), e);
		} catch (IllegalArgumentException e) {
			Log.wtf(PropertyFilter.class.getCanonicalName(), e);
		} catch (InvocationTargetException e) {
			Log.wtf(PropertyFilter.class.getCanonicalName(), e);
		} catch (NoSuchMethodException e) {
			Log.wtf(PropertyFilter.class.getCanonicalName(), e);
		}
		return false;
	}
}
