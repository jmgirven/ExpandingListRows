package com.example.expandinglistrows;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;


public class ResizeAnimation extends Animation {
	int targetHeight;
	int originalHeight;
	int extraHeight;
	View mView;

	protected ResizeAnimation(View view, int targetHeight) {
		this.mView = view;
		this.targetHeight = targetHeight;
		originalHeight = view.getHeight();
		extraHeight = this.targetHeight - originalHeight;
	}

	@Override
	protected void applyTransformation(float interpolatedTime,
			Transformation t) {

		int newHeight;
		newHeight = (int) (targetHeight - extraHeight
				* (1 - interpolatedTime));
		mView.getLayoutParams().height = newHeight;
		mView.requestLayout();
	}

	@Override
	public void initialize(int width, int height, int parentWidth,
			int parentHeight) {
		super.initialize(width, height, parentWidth, parentHeight);
	}

}
