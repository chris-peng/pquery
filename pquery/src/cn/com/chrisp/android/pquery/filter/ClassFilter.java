package cn.com.chrisp.android.pquery.filter;

import android.view.View;
import cn.com.chrisp.android.pquery.IFilter;

public class ClassFilter implements IFilter{
	
	private Class<?> clazz;
	
	public ClassFilter(Class<?> clazz){
		this.clazz = clazz;
	}

	@Override
	public boolean filter(View v) {
		if(v != null && v.getClass().equals(clazz)){
			return true;
		}
		return false;
	}
}
