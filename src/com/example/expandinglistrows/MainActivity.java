package com.example.expandinglistrows;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;
import android.app.Activity;
import android.content.pm.ActivityInfo;

public class MainActivity extends Activity {
	
	private static String TAG = MainActivity.class.getSimpleName();

	private ListView listView;
    private MyAdapter myAdapter;
    private List<ListItem> aList = new ArrayList<ListItem>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
				| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		setContentView(R.layout.activity_main);
		
		listView = (ListView) findViewById(R.id.list_view);

		listView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				Log.d(TAG, "firstVisibleItem: " + firstVisibleItem + ", visibleItemCount: "
						+ visibleItemCount + ", totalItemCount: " + totalItemCount);
				
				if (myAdapter!=null) myAdapter.setFirstVisibleItem(firstVisibleItem+1);
			}
		});

		myAdapter = new MyAdapter(this);
		listView.setAdapter(myAdapter);
		
		// Build list
		aList.clear();
		for (int iii=0; iii<20; iii++) {
			ListItem listItem = new ListItem(Integer.toString(iii));
			aList.add(listItem);
		}
		myAdapter.updateList(aList);
		
	}

}
