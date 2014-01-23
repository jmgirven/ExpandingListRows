package com.example.expandinglistrows;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

public class ScaleBitmapAsyncTask extends AsyncTask<Bitmap, Void, Bitmap> {
	
	private static final String TAG = ScaleBitmapAsyncTask.class.getSimpleName();
	
	private Context context;
    
	
	public interface ScaleBitmapAsyncTaskListener {
		public void returnBitmap(Bitmap bitmap);
	}
	
	private ScaleBitmapAsyncTaskListener mCallback;
	
	public void setScaleBitmapAsyncTaskListener(ScaleBitmapAsyncTaskListener listener) {
		mCallback = listener;
	}
	

    public ScaleBitmapAsyncTask(Context context) {
    	this.context = context;
    }

	@Override
	protected Bitmap doInBackground(Bitmap... bitmaps) {
		Bitmap bitmap = bitmaps[0];
		
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

    @Override
    protected void onPostExecute(Bitmap scaledBitmap) {
        if (mCallback!=null) mCallback.returnBitmap(scaledBitmap);
    }

}
