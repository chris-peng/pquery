package cn.com.chrisp.android.pquery.filter;

import android.view.View;
import cn.com.chrisp.android.pquery.IFilter;

public class TagFilter implements IFilter{
	
	private Object tagValue;
	
	public TagFilter(Object tagValue){
		this.tagValue = tagValue;
	}

	@Override
	public boolean filter(View v) {
		if(v == null){
			return false;
		}
		if(v.getTag() == null){
			if(tagValue == null){
				return true;
			}
		}else if(v.getTag().equals(tagValue)){
			return true;
		}
		return false;
	}
}
