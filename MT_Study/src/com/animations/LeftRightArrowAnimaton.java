package com.animations;

import com.example.mt_study.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;

public class LeftRightArrowAnimaton extends Animation implements Animation.AnimationListener {

	private View view;
	private Context context;
	private boolean originalPosition;
	private int originalX;
	private int newX;
	
	public LeftRightArrowAnimaton(View view, Context context) {
		this.view = view;
		this.context = context;
		setDuration(500);
		setRepeatCount(10);
		setInterpolator(new AccelerateInterpolator());
		
		setAnimationListener(this);
	}
	
	
	@Override
	public void onAnimationEnd(Animation animation) {
		AlertDialog.Builder disclaimerBox = new AlertDialog.Builder(context);
		//disclaimerBox.setView(R.layout.custom_disclaimer_box);
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		if (originalPosition) {
			originalPosition = false;
			view.scrollTo(newX, 0);
		}
		else {
			originalPosition = true;
			view.scrollTo(originalX, 0);
		}
	}

	@Override
	public void onAnimationStart(Animation animation) {
		originalPosition = false;
		originalX = -10;
		newX = -20;
	}

}
