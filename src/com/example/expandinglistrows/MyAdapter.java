package com.example.expandinglistrows;

import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;


public class MyAdapter extends BaseAdapter {
	
	private static final String TAG = MyAdapter.class.getSimpleName();
	
	private final Context context;
	
	private List<ListItem> aList = Collections.emptyList();
	
	private int firstVisibleItem = 0;
	
	public void setFirstVisibleItem(int firstVisibleItem) {
		if (this.firstVisibleItem != firstVisibleItem) {
			this.firstVisibleItem = firstVisibleItem;
			notifyDataSetChanged();
		}
	}

    public MyAdapter(Context context) {
        this.context = context;
    }

    public void updateList(List<ListItem> aList) {
        this.aList = aList;
        notifyDataSetChanged();
    }

	@Override
	public int getCount() {
		return aList.size();
	}

	@Override
	public ListItem getItem(int position) {
		return aList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		final ListItem item = aList.get(position);
		
		LayoutInflater inflater = LayoutInflater.from(context);
		
	    if (convertView == null) {
	        convertView = inflater.inflate(R.layout.list_item, parent, false);
	    }
	    
	    RelativeLayout container = (RelativeLayout) convertView.findViewById(R.id.list_item_container);
	    final ImageView imageView = (ImageView) convertView.findViewById(R.id.list_item_image);
	    
	    // Image bitmap
	    if (item.getBitmap()!=null) {
	    	imageView.setImageBitmap(item.getBitmap());
	    } else {
	    	//
		    // Download image here
		    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.headerpic);
		    //
		    //

//		    Bitmap scaledBitmap = processImage(bitmap);
//		    item.setBitmap(scaledBitmap);
//			imageView.setImageBitmap(scaledBitmap);
		    ScaleBitmapAsyncTask scaleBitmapAsyncTask = new ScaleBitmapAsyncTask(context);
		    scaleBitmapAsyncTask.setScaleBitmapAsyncTaskListener(
		    		new ScaleBitmapAsyncTask.ScaleBitmapAsyncTaskListener() {
				@Override
				public void returnBitmap(Bitmap scaledBitmap) {
				    item.setBitmap(scaledBitmap);
					imageView.setImageBitmap(scaledBitmap);
				}
			});
		    scaleBitmapAsyncTask.execute(bitmap);
	    }

		// Set row heights
    	int height;
	    if (position == firstVisibleItem) {
	    	Log.d(TAG, "list_item_large_height");
	    	height = (int) context.getResources().getDimension(R.dimen.list_item_large_height);
	    } else {
	    	Log.d(TAG, "list_item_height");
	    	height = (int) context.getResources().getDimension(R.dimen.list_item_height);
	    }
	    
	    if (container.getHeight()!=height) {
	    	ResizeAnimation animation = new ResizeAnimation(
	    			container, height);
			animation.setDuration(500);
			container.startAnimation(animation);
	    }
		
		return convertView;
	}
	
	
	/**
	 * Scale an image to list_item_large_height whilst preserving the aspect ratio
	 * 
	 * @param bitmap Bitmap to process
	 */
	private Bitmap processImage(Bitmap bitmap) {
		if ((context!=null) && (bitmap!=null)) {
			int reqHeight = (int) context.getResources().getDimension(R.dimen.list_item_large_height);
			int curHeight = bitmap.getHeight();
			int curWidth = bitmap.getWidth();
			
			int reqWidth = curWidth * reqHeight / curHeight;
	    	Log.d(TAG, "processImage: curHeight: " + curHeight + ", curWidth: "
	    			+ curWidth + ", reqHeight: " + reqHeight + ", reqWidth: " + reqWidth);
			
	    	Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, reqWidth, reqHeight, false);
	    	
	    	return scaledBitmap;
		}
		return null;
	}
	
}
