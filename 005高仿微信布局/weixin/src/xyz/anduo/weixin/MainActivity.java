package xyz.anduo.weixin;

import java.util.ArrayList;
import java.util.List;

import com.jauker.widget.BadgeView;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

	private ViewPager mViewPager;
	private FragmentPagerAdapter mFragmentPagerAdapter;
	private List<Fragment> mFragments;

	private TextView mTextView1;
	private TextView mTextView2;
	private TextView mTextView3;
	
	private BadgeView mBadgeView1;
	
	private LinearLayout  mLinearLayoutTab1;

	private ImageView mTabLine;
	private int mScreen;
	private int mTabIndex;

	// Fragment
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initTabLine();
		initView();
	}

	private void initTabLine() {
		mTabLine = (ImageView) findViewById(R.id.id_iv_tabline);
		Display display = getWindow().getWindowManager().getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();
		display.getMetrics(outMetrics);
		mScreen = outMetrics.widthPixels;
		LayoutParams layoutParams = mTabLine.getLayoutParams();
		layoutParams.width = mScreen / 3;
		mTabLine.setLayoutParams(layoutParams);
	}

	private void initView() {
		mViewPager = (ViewPager) findViewById(R.id.id_viewpager);

		mTextView1 = (TextView) findViewById(R.id.id_tv_tab1);
		mTextView2 = (TextView) findViewById(R.id.id_tv_tab2);
		mTextView3 = (TextView) findViewById(R.id.id_tv_tab3);
		
		
		mLinearLayoutTab1 = (LinearLayout) findViewById(R.id.id_ll_tab1);

		mFragments = new ArrayList<Fragment>();

		Tab1Fragment tab1Fragment = new Tab1Fragment();
		Tab2Fragment tab2Fragment = new Tab2Fragment();
		Tab3Fragment tab3Fragment = new Tab3Fragment();

		mFragments.add(tab1Fragment);
		mFragments.add(tab2Fragment);
		mFragments.add(tab3Fragment);

		mFragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
			@Override
			public int getCount() {
				return mFragments.size();
			}

			@Override
			public Fragment getItem(int location) {
				return mFragments.get(location);
			}

		};

		mViewPager.setAdapter(mFragmentPagerAdapter);

		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				resetTextView();
				switch (position) {
				case 0:
					if(mBadgeView1!=null){
						mLinearLayoutTab1.removeView(mBadgeView1);
					}
					mBadgeView1 = new BadgeView(MainActivity.this);
					mBadgeView1.setBadgeCount(7);
					mLinearLayoutTab1.addView(mBadgeView1);
					mTextView1.setTextColor(Color.parseColor("#008000"));
					break;
				case 1:
					mTextView2.setTextColor(Color.parseColor("#008000"));
					break;
				case 2:
					mTextView3.setTextColor(Color.parseColor("#008000"));
					break;
				}
				mTabIndex = position;
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				Log.i("TAG", "position:" + position + " positionOffset:" + positionOffset + " positionOffsetPixels:" + positionOffsetPixels);

				LinearLayout.LayoutParams layoutParams = (android.widget.LinearLayout.LayoutParams) mTabLine.getLayoutParams();

				int screen1_3 = mScreen / 3;
				if (mTabIndex == 0 && position == 0) {// 0 -> 1
					layoutParams.leftMargin = (int) (positionOffset * screen1_3 + mTabIndex * screen1_3);
				} else if (mTabIndex == 1 && position == 0) { // 1--> 0
					layoutParams.leftMargin = (int) ((positionOffset - 1) * screen1_3 + mTabIndex * screen1_3);
				} else if (mTabIndex == 1 && position == 1) {// 1 --> 2
					layoutParams.leftMargin = (int) (positionOffset * screen1_3 + mTabIndex * screen1_3);
				} else if (mTabIndex == 2 && position == 1) {// 2 --> 1
					layoutParams.leftMargin = (int) ((positionOffset - 1) * screen1_3 + mTabIndex * screen1_3);
				}

				mTabLine.setLayoutParams(layoutParams);

			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}

			private void resetTextView() {
				mTextView1.setTextColor(Color.BLACK);
				mTextView2.setTextColor(Color.BLACK);
				mTextView3.setTextColor(Color.BLACK);
			}

		});

	}

}
