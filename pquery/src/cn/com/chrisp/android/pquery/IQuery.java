package cn.com.chrisp.android.pquery;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

public interface IQuery{
	
	/**
	 * 获取包含的第一个View
	 * @return
	 */
	public <T extends View> T view();
	
	/**
	 * 获取包含的第一个View的id
	 * @return
	 */
	public int getId();
	
	/**
	 * 设置包含的第一个View的id
	 * @param id
	 * @return
	 */
	public IQuery setId(int id);
	
	/**
	 * 根据id查找包含的View
	 * @param id
	 * @return
	 */
	public <T extends View> T id(int id);
	
	/**
	 * 根据id过滤View
	 * @param id
	 * @return
	 */
	public IQuery filterById(int id);
	
	/**
	 * 根据tag过滤View
	 * @param tag	tag值
	 * @param generations	遍历View层级的最大层数，0为只遍历第一层，-1表示不限制层数
	 * @return
	 */
	public IQuery filterByTag(Object tag, int generations);
	
	/**
	 * 根据class过滤View
	 * @param clazz
	 * @param generations	遍历View层级的最大层数，0为只遍历第一层，-1表示不限制层数
	 * @return
	 */
	public IQuery filterByClass(Class<? extends View> clazz, int generations);
	
	/**
	 * 根据property过滤View，需要该property支持无参get方法，如属性"visibility（对应方法getVisibility）"
	 * @param property	属性名
	 * @param value		属性值
	 * @param generations	遍历View层级的最大层数，0为只遍历第一层，-1表示不限制层数
	 * @return
	 */
	public IQuery filterByProperty(String property, Object value, int generations);
	
	/**
	 * 自定义过滤器过滤View
	 * @param filter
	 * @param generations	遍历View层级的最大层数，0为只遍历第一层，-1表示不限制层数
	 * @return
	 */
	public IQuery filter(IFilter filter, int generations);

	/**
	 * 设置包含的所有View的tag
	 * @param o
	 * @return
	 */
	public IQuery tag(Object o);
	
	/**
	 * 设置包含的所有View的tag
	 * @param key
	 * @param o
	 * @return
	 */
	public IQuery tag(int key, Object o);

	/**
	 * 获取包含的第一个View的tag值
	 * @return
	 */
	public <T> T tag();

	/**
	 * 获取包含的第一个View的tag值
	 *  @param key
	 * @return
	 */
	public <T> T tag(int key);

	/**
	 * 设置包含的所有View的text
	 * @param text
	 * @return
	 */
	public IQuery text(CharSequence text);

	/**
	 * 设置包含的所有View的text
	 * @param resId
	 * @return
	 */
	public IQuery text(int resId);

	/**
	 * 设置包含的所有View的text
	 * @param text
	 * @return
	 */
	public IQuery html(String text);

	/**
	 * 设置包含的所有View的text
	 * @param resId
	 * @return
	 */
	public IQuery html(int resId);

	/**
	 * 获取包含的第一个View的text
	 * @return
	 */
	public CharSequence text();

	/**
	 * 设置包含的所有View的image
	 * @param resId
	 * @return
	 */
	public IQuery image(int resId);

	/**
	 * 设置包含的所有View的image
	 * @param drawable
	 * @return
	 */
	public IQuery image(Drawable drawable);

	/**
	 * 设置包含的所有View的image
	 * @param bitmap
	 * @return
	 */
	public IQuery image(Bitmap bitmap);

	/**
	 * 设置包含的所有View的image
	 * @param uri
	 * @return
	 */
	public IQuery image(Uri uri);

	/**
	 * 设置包含的所有View的image
	 * @param url
	 * @return
	 */
	public IQuery image(String url);

	/**
	 * 获取包含的第一个View的image
	 * @return
	 */
	public Drawable image();

	/**
	 * 设置包含的所有View为visible状态
	 * @return
	 */
	public IQuery visible();

	/**
	 * 设置包含的所有View为invisible状态
	 * @return
	 */
	public IQuery invisible();

	/**
	 * 设置包含的所有View为gone状态
	 * @return
	 */
	public IQuery gone();

	/**
	 * 获取包含的第一个View的visibility
	 * @return
	 */
	public int visibility();

	/**
	 * 设置包含的所有View的background
	 * @param color		如“#FFFFFF”
	 * @return
	 */
	public IQuery background(String color);

	/**
	 * 设置包含的所有View的background
	 * @param resId
	 * @return
	 */
	public IQuery background(int resId);

	/**
	 * 设置包含的所有View的background
	 * @param drawable
	 * @return
	 */
	public IQuery background(Drawable drawable);

	/**
	 * 获取包含的第一个View的background
	 * @return
	 */
	public Drawable background();

	/**
	 * 设置包含的所有View的width
	 * @param widthPx	单位px
	 * @return
	 */
	public IQuery width(int widthPx);

	/**
	 * 设置包含的所有View的height
	 * @param heightPx	单位px
	 * @return
	 */
	public IQuery height(int heightPx);

	/**
	 * 设置包含的所有View的width
	 * @param widthDp	单位dp
	 * @return
	 */
	public IQuery widthDp(float widthDp);

	/**
	 * 设置包含的所有View的height
	 * @param heightDp	单位dp
	 * @return
	 */
	public IQuery heightDp(float heightDp);

	/**
	 * 获取包含的第一个View的width
	 * @return	单位px
	 */
	public int width();

	/**
	 * 获取包含的第一个View的height
	 * @return	单位px
	 */
	public int height();

	/**
	 * 获取包含的第一个View的width
	 * @return	单位dp
	 */
	public int widthDp();

	/**
	 * 获取包含的第一个View的height
	 * @return	单位dp
	 */
	public int heightDp();
	
	/**
	 * 获取包含的所有View的直接子View
	 * @return
	 */
	public IQuery children();

	/**
	 * 获取包含的第一个View的指定index的子View
	 * @param index
	 * @return
	 */
	public IQuery child(int index);

	/**
	 * 为包含的第一个View添加一个子View
	 * @param v
	 * @return
	 */
	public IQuery addChild(View v);

	/**
	 * 为包含的第一个View添加一个子View
	 * @param v
	 * @param width
	 * @param height
	 * @return
	 */
	public IQuery addChild(View v, int width, int height);

	/**
	 * 为包含的第一个View添加一个子View
	 * @param v
	 * @param lp
	 * @return
	 */
	public IQuery addChild(View v, LayoutParams lp);

	/**
	 * 为包含的第一个View添加一个子View
	 * @param v
	 * @param index
	 * @param lp
	 * @return
	 */
	public IQuery addChild(View v, int index, LayoutParams lp);

	/**
	 * 为包含的第一个View添加一个子View至指定位置
	 * @param v
	 * @param index
	 * @return
	 */
	public IQuery addChild(View v, int index);

	/**
	 * 获取包含的第一个View的直接父容器
	 * @return
	 */
	public IQuery parent();

	/**
	 * 获取包含的第一个View的所有祖先容器中id为指定值的View
	 * @param id
	 * @return
	 */
	public IQuery parent(int id);

	/**
	 * 设置包含的第一个View（必须是RadioGroup）的选中子button
	 * @param id
	 * @return
	 */
	public IQuery check(int id);

	/**
	 * 设置包含的所有View（必须是CompoundButton或其子类）为选中状态
	 * @return
	 */
	public IQuery check();
	
	/**
	 * 设置包含的所有View（必须是CompoundButton或其子类）为未选中状态
	 * @return
	 */
	public IQuery uncheck();

	/**
	 * 检查包含的第一个View（必须是RadioGroup）的某子View的check状态
	 * @param id
	 * @return
	 */
	public boolean isChecked(int id);

	/**
	 * 检查包含的第一个View（必须是CompoundButton或其子类）的check状态
	 * @return
	 */
	public boolean isChecked();

	/**
	 * 设置包含的所有View的点击事件处理方法，处理方法实现必须是以下形式：methodName(View v)
	 * 
	 * @author chris
	 *
	 * @param o
	 *            处理方法所在对象
	 * @param methodName
	 *            处理方法名字
	 * @return
	 * @since 2015年7月13日
	 */
	public IQuery click(Object o, String methodName);
	
	/**
	 * 设置包含的所有View的点击事件处理方法，处理方法实现必须View所在Context中以下形式的方法：methodName(View v)
	 * @author chris
	 *
	 * @param methodName
	 * @return
	 * @since 2015年10月12日
	 */
	public IQuery click(String methodName);
	
	/**
	 * 设置包含的所有View的点击事件处理方法
	 * @param onClickListener
	 * @return
	 */
	public IQuery click(View.OnClickListener onClickListener);
	
	/**
	 * 移除包含的所有View所有点击事件处理方法
	 * @return
	 */
	public IQuery removeClick();
}
