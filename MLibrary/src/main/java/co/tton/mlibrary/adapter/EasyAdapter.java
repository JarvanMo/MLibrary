package co.tton.mlibrary.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/10/7.
 *
 */
public abstract class EasyAdapter<E, H extends BaseViewHolder> extends BaseAdapter {

    private List<E> data;

    private LayoutInflater inflater;

    private Context context;

    private int layoutResId;

    private Class<H> clazz;

    public EasyAdapter(Context context, List<E> data, @LayoutRes int layoutResId, Class<H> clazz) {

        this.context = context;
        this.layoutResId = layoutResId;
        this.inflater = LayoutInflater.from(context);

        if (data == null) {
            data = new ArrayList<>();
        }

        this.data = data;
        this.clazz = clazz;
    }

    public Context getContext() {
        return context;
    }

    public List<E> getData() {
        return data;
    }


    /**
     * @return return the last one of  data
     * if the size of data is 0 or data is null
     * it will return null
     **/
    public E getLastItem() {

        E result = null;
        if (data != null) {
            int size = data.size();

            if (size > 0) {
                result = data.get(size - 1);
            }
        }

        return result;

    }


    /**
     * reset the data used by adapter
     */
    public void setData(List<E> data) {
        if (data == null) {
            data = new ArrayList<>();
        }

        this.data = data;
        notifyDataSetChanged();
    }

    /**
     * add a piece of data to the adapter
     *
     * @param item a piece of data
     */
    public void addItem(E item) {

        if (data != null) {
            data.add(item);
            notifyDataSetChanged();
        }
    }

    /**
     * add more add to adapter
     *
     * @param moreData the data need to show together
     */
    public void addAll(List<E> moreData) {

        if (data != null) {
            data.addAll(moreData);
            notifyDataSetChanged();
        }
    }

    /**
     * add a item to the data at index
     **/
    public void add(int position, E item) {

        if (data != null) {
            data.add(position, item);
            notifyDataSetChanged();
        }
    }

    public void add(E item) {
        if (data != null) {
            data.add(item);
            notifyDataSetChanged();
        }
    }


    /**
     * add a item to the data at the first position
     **/
    public void addToFirst(E item) {
        add(0, item);
    }

    public void set(int position, E item) {

        if (data != null) {
            data.set(position, item);
            notifyDataSetChanged();
        }
    }

    public void removeItem(int position) {

        int size = data.size();
        if (position < size) {
            data.remove(position);
            notifyDataSetChanged();
        } else {
            throw new IndexOutOfBoundsException("Invalid index " + position + ", size is " + size);
        }
    }

    public void removeItem(E item) {

        if (data.contains(item)) {
            data.remove(item);
        }
    }

    public void clear() {

        if (data != null) {
            data.clear();
            notifyDataSetChanged();
        }
    }


    @Override
    public int getCount() {

        if (data != null) {
            return data.size();
        } else {
            return 0;
        }
    }

    @Override
    public E getItem(int position) {

        if (position < data.size()) {
            return data.get(position);
        } else {
            return null;
        }
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressWarnings("unchecked")
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        H holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(layoutResId, null);

            try {
//                holder = clazz.newInstance();
                Constructor<H> constructor;
                constructor = clazz.getDeclaredConstructor(View.class);
                constructor.setAccessible(true);
                holder = constructor.newInstance(convertView);
                constructor.setAccessible(false);
//                holder.initButterKnife(convertView);

            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }

//            ViewHolderBuilder.with(context).load(convertView).into(holder);
            convertView.setTag(holder);
        } else {
            holder = (H) convertView.getTag();
        }


            onControlView(holder, position);


        return convertView;
    }

    public abstract void onControlView(H viewHolder, int position);

}
