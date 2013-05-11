package com.animations;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;


//twas a cool class. Will keep just because it's a good example.
public class UpDownArrowAnimation extends Animation implements Animation.AnimationListener {

	private Context context;
	private View firstArrow;
	private View secondArrow;
	private TextView firstArrowText;
	private TextView secondArrowText;
	private boolean originalPosition;
	private int newArrowPositionY;
	private int originalArrowPositionY;
	private Animation secondArrowAnimation;
	
	//constructor for the animation
	public UpDownArrowAnimation(View firstArrow, View secondArrow, TextView firstArrowText,
			TextView secondArrowText, Context context) {
		this.firstArrow = firstArrow;
		this.secondArrow = secondArrow;
		this.context = context;
		this.firstArrowText = firstArrowText;
		this.secondArrowText = secondArrowText;
		setDuration(500);
		setRepeatCount(10);
		setInterpolator(new AccelerateInterpolator());
		
		setAnimationListener(this);
	}
	
	
	@Override
	public void onAnimationEnd(Animation animation) {
		Animation fadeOut = AnimationUtils.loadAnimation(context, android.R.anim.fade_out);
		firstArrow.startAnimation(fadeOut);
		firstArrow.setVisibility(View.INVISIBLE);
		firstArrowText.startAnimation(fadeOut);
		firstArrowText.setVisibility(View.INVISIBLE);
		secondArrowText.setText("This makes your own classes!");
		secondArrowAnimation = new secondUpDownArrowAnimation(secondArrow, context, secondArrowText);
		secondArrow.startAnimation(secondArrowAnimation);
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		//makes the image go up and down
		if (originalPosition) {
			firstArrow.scrollBy(0, -1);
			//firstArrow.scrollTo(0, newArrowPositionY);
			originalPosition = false;
		}
		else {
			firstArrow.scrollBy(0, 1);
			//firstArrow.scrollTo(0, originalArrowPositionY);
			originalPosition = true;
		}
	}

	@Override
	public void onAnimationStart(Animation animation) {
		//set the positions of the image
		originalPosition = false;
		originalArrowPositionY = -20;
		newArrowPositionY = -10;
	}

private class secondUpDownArrowAnimation extends Animation implements Animation.AnimationListener {

	private View secondArrow;
	private TextView secondArrowText;
	private Context context;
	private boolean originalPosition;
	private int newArrowPositionY;
	private int originalArrowPositionY;

	
	public secondUpDownArrowAnimation(View secondArrow, Context context, TextView secondArrowText) {
		this.secondArrow = secondArrow;
		this.secondArrowText = secondArrowText;
		this.context = context;
		setDuration(500);
		setRepeatCount(10);
		setInterpolator(new AccelerateInterpolator());
		
		setAnimationListener(this);
	}
	
	@Override
	public void onAnimationEnd(Animation animation) {
		Animation fadeOut = AnimationUtils.loadAnimation(context, android.R.anim.fade_out);
		secondArrowText.startAnimation(fadeOut);
		secondArrow.setVisibility(View.INVISIBLE);
		secondArrowText.setText("");
		Intent longPressTutorials = new Intent(context, theTUTORIALS.LongPressTutorial.class);
		((Activity) context).overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
		longPressTutorials.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(longPressTutorials);
		((Activity) context).finish();
		
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		//makes the image go up and down
				if (originalPosition) {
					secondArrow.scrollTo(0, newArrowPositionY);
					originalPosition = false;
				}
				else {
					secondArrow.scrollTo(0, originalArrowPositionY);
					originalPosition = true;
				}
	}

	@Override
	public void onAnimationStart(Animation animation) {
		//set the positions of the image
				originalPosition = false;
				originalArrowPositionY = -20;
				newArrowPositionY = -10;
	}
	
}
}