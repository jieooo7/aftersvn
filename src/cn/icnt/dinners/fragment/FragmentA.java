package cn.icnt.dinners.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentA extends Fragment {
	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		TextView textView = new TextView(getActivity());
		textView.setText("FragmentA");
		textView.setTextSize(20);
		textView.setTextColor(Color.BLACK);
		return textView;
	}
}
