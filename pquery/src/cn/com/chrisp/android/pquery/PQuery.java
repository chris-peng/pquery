package cn.com.chrisp.android.pquery;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import cn.com.chrisp.android.pquery.filter.ClassFilter;
import cn.com.chrisp.android.pquery.filter.PropertyFilter;
import cn.com.chrisp.android.pquery.filter.TagFilter;

public class PQuery extends ArrayList<View> implements IQuery {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4985515714988058205L;

	private Context context;
	private Map<Integer, PQuery> idViewCache = new HashMap<Integer, PQuery>();
	private Map<View, ClickPair> clickMethods = new HashMap<View, ClickPair>();
	private static IImageLoader imageLoader;
	
	public PQuery(Context context){
		this.context = context;
	};
	
	public PQuery(Activity acitivty) {
		context = acitivty;
		this.add(acitivty.findViewById(android.R.id.content));
	}

	public PQuery(View view) {
		this.context = view.getContext();
		this.add(view);
	}

	public void setView(View view) {
		this.context = view.getContext();
		this.clear();
		this.add(view);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends View> T view() {
		if(this.size() > 0){
			return (T) this.get(0);
		}else{
			return null;
		}
	}

	@Override
	public PQuery setId(int id) {
		View v = view();
		if(v != null){
			v.setId(id);
		}
		return this;
	}
	
	@Override
	public int getId() {
		return view().getId();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends View> T id(int id){
		PQuery pq = idViewCache.get(id);
		if (pq == null) {
			for(View view : this){
				if(view.getId() == id){
					return (T) view;
				}
				View v = view.findViewById(id);
				if (v != null) {
					return (T) v;
				}
			}
		}else{
			return pq.view();
		}
		return null;
	}

	@Override
	public PQuery filterById(int id) {
		PQuery pq = idViewCache.get(id);
		if (pq == null) {
			View v = id(id);
			if (v != null) {
				pq = new PQuery(v);
				idViewCache.put(id, pq);
			}
		}
		return pq;
	}

	@Override
	public PQuery filterByTag(Object tagValue, int generations) {
		return filter(new TagFilter(tagValue), generations);
	}

	@Override
	public PQuery filterByClass(Class<? extends View> clazz, int generations) {
		return filter(new ClassFilter(clazz), generations);
	}

	@Override
	public PQuery filterByProperty(String property, Object value, int generations) {
		return filter(new PropertyFilter(property, value), generations);
	}

	@Override
	public PQuery filter(IFilter filter, int generations) {
		PQuery pq = new PQuery(context);
		for(View v : this){
			if(filter.filter(v)){
				pq.add(v);
			}
		}
		if(generations == -1){
			//不限制层数
			PQuery cpq = children();
			if(cpq != null && !cpq.isEmpty()){
				pq.addAll(children().filter(filter, -1));
			}
		}else if(generations > 0){
			pq.addAll(children().filter(filter, generations - 1));
		}
		return pq;
	}

	@Override
	public PQuery tag(Object o) {
		for(View v : this){
			v.setTag(o);
		}
		return this;
	}

	@Override
	public PQuery tag(int key, Object o) {
		for(View v : this){
			v.setTag(key, o);
		}
		return this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T tag() {
		return (T) view().getTag();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T tag(int key) {
		return (T) view().getTag(key);
	}

	@Override
	public PQuery text(CharSequence text) {
		for(View view : this){
			if (view instanceof TextView) {
				((TextView) view).setText(text);
			} else {
				if(size() == 1){
					throw new ViewTypeIllegalException();
				}
			}
		}
		return this;
	}

	@Override
	public PQuery text(int resId) {
		text(context.getString(resId));
		return this;
	}

	@Override
	public PQuery html(String text) {
		return this.text(Html.fromHtml(text));
	}

	@Override
	public PQuery html(int resId) {
		return this.text(Html.fromHtml(context.getString(resId)));
	}

	@Override
	public CharSequence text() {
		View view = view();
		if (view instanceof TextView) {
			return ((TextView) view).getText();
		} else {
			throw new ViewTypeIllegalException();
		}
	}

	@Override
	public PQuery image(int resId) {
		for(View view : this){
			if (view instanceof ImageView) {
				((ImageView) view).setImageResource(resId);
			} else {
				if(size() == 1){
					throw new ViewTypeIllegalException();
				}
			}
		}
		return this;
	}

	@Override
	public PQuery image(Drawable drawable) {
		for(View view : this){
			if (view instanceof ImageView) {
				((ImageView) view).setImageDrawable(drawable);
			} else {
				if(size() == 1){
					throw new ViewTypeIllegalException();
				}
			}
		}
		return this;
	}

	@Override
	public PQuery image(Bitmap bitmap) {
		for(View view : this){
			if (view instanceof ImageView) {
				((ImageView) view).setImageBitmap(bitmap);
			} else {
				if(size() == 1){
					throw new ViewTypeIllegalException();
				}
			}
		}
		return this;
	}

	@Override
	public PQuery image(Uri uri) {
		for(View view : this){
			if (view instanceof ImageView) {
				((ImageView) view).setImageURI(uri);
			} else {
				if(size() == 1){
					throw new ViewTypeIllegalException();
				}
			}
		}
		return this;
	}

	@Override
	public PQuery image(String url) {
		for(View view : this){
			if (view instanceof ImageView) {
				imageLoader.loadImg2View(((ImageView) view), url);
			} else {
				if(size() == 1){
					throw new ViewTypeIllegalException();
				}
			}
		}
		return this;
	}

	@Override
	public Drawable image() {
		View view = view();
		if (view instanceof ImageView) {
			return ((ImageView) view).getDrawable();
		} else {
			throw new ViewTypeIllegalException();
		}
	}

	@Override
	public PQuery visible() {
		for(View view : this){
			view.setVisibility(View.VISIBLE);
		}
		return this;
	}

	@Override
	public PQuery invisible() {
		for(View view : this){
			view.setVisibility(View.INVISIBLE);
		}
		return this;
	}

	@Override
	public PQuery gone() {
		for(View view : this){
			view.setVisibility(View.GONE);
		}
		return this;
	}

	@Override
	public int visibility() {
		return view().getVisibility();
	}

	@Override
	public PQuery background(String color) {
		for(View view : this){
			view.setBackgroundColor(Color.parseColor(color));
		}
		return this;
	}

	@Override
	public PQuery background(int resId) {
		for(View view : this){
			view.setBackgroundResource(resId);
		}
		return this;
	}

	@SuppressWarnings("deprecation")
	@Override
	public PQuery background(Drawable drawable) {
		for(View view : this){
			view.setBackgroundDrawable(drawable);
		}
		return this;
	}

	@Override
	public Drawable background() {
		return view().getBackground();
	}

	@Override
	public PQuery width(int widthPx) {
		for(View view : this){
			LayoutParams lp = view.getLayoutParams();
			lp.width = widthPx;
			view.setLayoutParams(lp);
		}
		return this;
	}

	@Override
	public PQuery height(int heightPx) {
		for(View view : this){
			LayoutParams lp = view.getLayoutParams();
			lp.height = heightPx;
			view.setLayoutParams(lp);
		}
		return this;
	}

	@Override
	public PQuery widthDp(float widthDp) {
		int widthPx = UnitUtil.dip2px(widthDp);
		return this.width(widthPx);
	}

	@Override
	public PQuery heightDp(float heightDp) {
		int heightPx = UnitUtil.dip2px(heightDp);
		return this.height(heightPx);
	}

	@Override
	public int width() {
		return view().getWidth();
	}

	@Override
	public int height() {
		return view().getHeight();
	}

	@Override
	public int widthDp() {
		return UnitUtil.px2dip(view().getWidth());
	}

	@Override
	public int heightDp() {
		return UnitUtil.px2dip(view().getHeight());
	}
	
	@Override
	public PQuery children(){
		PQuery pq = new PQuery(context);
		for(View v : this){
			if(v instanceof ViewGroup){
				ViewGroup vg = (ViewGroup) v;
				for(int i = 0; i < vg.getChildCount(); i++){
					pq.add(vg.getChildAt(i));
				}
			}
		}
		return pq;
	}

	@Override
	public PQuery child(int index) {
		View view = view();
		if (view instanceof ViewGroup) {
			return new PQuery(((ViewGroup) view).getChildAt(index));
		} else {
			throw new ViewTypeIllegalException();
		}
	}

	@Override
	public PQuery addChild(View v) {
		View view = view();
		if (view instanceof ViewGroup) {
			((ViewGroup) view).addView(v);
			return this;
		} else {
			throw new ViewTypeIllegalException();
		}
	}

	@Override
	public PQuery addChild(View v, int width, int height) {
		View view = view();
		if (view instanceof ViewGroup) {
			((ViewGroup) view).addView(v, width, height);
			return this;
		} else {
			throw new ViewTypeIllegalException();
		}
	}

	@Override
	public PQuery addChild(View v, LayoutParams lp) {
		View view = view();
		if (view instanceof ViewGroup) {
			((ViewGroup) view).addView(v, lp);
			return this;
		} else {
			throw new ViewTypeIllegalException();
		}
	}

	@Override
	public PQuery addChild(View v, int index, LayoutParams lp) {
		View view = view();
		if (view instanceof ViewGroup) {
			((ViewGroup) view).addView(v, index, lp);
			return this;
		} else {
			throw new ViewTypeIllegalException();
		}
	}

	@Override
	public PQuery addChild(View v, int index) {
		View view = view();
		if (view instanceof ViewGroup) {
			((ViewGroup) view).addView(v, index);
			return this;
		} else {
			throw new ViewTypeIllegalException();
		}
	}

	@Override
	public PQuery parent() {
		View view = view();
		ViewGroup vp = (ViewGroup) view.getParent();
		if (vp != null) {
			return new PQuery(vp);
		}
		return null;
	}

	@Override
	public PQuery parent(int id) {
		PQuery parent = this.parent();
		if (parent == null) {
			return null;
		}
		if (parent.getId() == id) {
			return this;
		}
		return parent.parent(id);
	}

	@Override
	public PQuery check(int id) {
		View view = view();
		if (view instanceof RadioGroup) {
			((RadioGroup) view).check(id);
			return this;
		} else {
			throw new ViewTypeIllegalException();
		}
	}

	@Override
	public PQuery check() {
		for(View view : this){
			if (view instanceof CompoundButton) {
				((CompoundButton) view).setChecked(true);
			} else {
				if(size() == 1){
					throw new ViewTypeIllegalException();
				}
			}
		}
		return this;
	}
	
	@Override
	public PQuery uncheck() {
		for(View view : this){
			if (view instanceof CompoundButton) {
				((CompoundButton) view).setChecked(false);
			} else {
				if(size() == 1){
					throw new ViewTypeIllegalException();
				}
			}
		}
		return this;
	}

	@Override
	public boolean isChecked(int id) {
		View view = view();
		if (view instanceof RadioGroup) {
			return ((RadioGroup) view).getCheckedRadioButtonId() == id;
		} else {
			throw new ViewTypeIllegalException();
		}
	}

	@Override
	public boolean isChecked() {
		View view = view();
		if (view instanceof CompoundButton) {
			return ((CompoundButton) view).isChecked();
		} else {
			throw new ViewTypeIllegalException();
		}
	}

	@Override
	public PQuery click(Object o, String methodName) {
		try {
			ClickPair cp = new ClickPair(o, getMethod(o, methodName));
			for(View view : this){
				clickMethods.put(view, cp);
				view.setOnClickListener(onClickListener);
			}
		} catch (NoSuchMethodException e) {
			throw new ClickMethodIllegalException(e);
		}
		return this;
	}

	@Override
	public PQuery click(String methodName) {
		return click(context, methodName);
	}

	@Override
	public PQuery click(OnClickListener onClickListener) {
		for(View view : this){
			view.setOnClickListener(onClickListener);
		}
		return this;
	}

	/**
	 * 集合操作
	 */
	@Override
	public PQuery removeClick() {
		for(View view : this){
			view.setOnClickListener(null);
			clickMethods.remove(view);
		}
		return this;
	}

	private class ClickPair {
		ClickPair(Object o, Method method) {
			this.o = o;
			this.method = method;
		}

		Object o;
		Method method;

	}

	private View.OnClickListener onClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View view) {
			ClickPair clickPair = clickMethods.get(view);
			if (clickPair != null) {
				clickPair.method.setAccessible(true);
				try {
					clickPair.method.invoke(clickPair.o, view);
				} catch (IllegalAccessException e) {
					throw new ClickMethodIllegalException(e);
				} catch (IllegalArgumentException e) {
					throw new ClickMethodIllegalException(e);
				} catch (InvocationTargetException e) {
					throw new ClickMethodIllegalException(e);
				}
			}
		}
	};

	public static class ViewTypeIllegalException extends RuntimeException {

		/**
		 * 
		 */
		private static final long serialVersionUID = 8388383374381130023L;

		public ViewTypeIllegalException() {
			super();
		}

		public ViewTypeIllegalException(String detailMessage,
				Throwable throwable) {
			super(detailMessage, throwable);
		}

		public ViewTypeIllegalException(String detailMessage) {
			super(detailMessage);
		}

		public ViewTypeIllegalException(Throwable throwable) {
			super(throwable);
		}
	}

	public static class ClickMethodIllegalException extends RuntimeException {

		/**
		 * 
		 */
		private static final long serialVersionUID = -8322290746414848221L;

		public ClickMethodIllegalException() {
			super();
		}

		public ClickMethodIllegalException(String detailMessage,
				Throwable throwable) {
			super(detailMessage, throwable);
		}

		public ClickMethodIllegalException(String detailMessage) {
			super(detailMessage);
		}

		public ClickMethodIllegalException(Throwable throwable) {
			super(throwable);
		}
	}

	public static void setImageLoader(IImageLoader imageLoader) {
		PQuery.imageLoader = imageLoader;
	}
	
	private Method getMethod(Object o, String methodName) throws NoSuchMethodException{
		try {
			return o.getClass().getDeclaredMethod(methodName, View.class);
		} catch (NoSuchMethodException e1) {
			return o.getClass().getMethod(methodName, View.class);
		}
	}
}
