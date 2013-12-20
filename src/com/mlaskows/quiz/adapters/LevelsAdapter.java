/*
 * Copyright (c) 2013, Maciej Laskowski. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  
 * 
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact mlaskowsk@gmail.com if you need additional information
 * or have any questions.
 */

package com.mlaskows.quiz.adapters;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mlaskows.quiz.ExerciseActivity;
import com.mlaskows.quiz.R;
import com.mlaskows.quiz.model.entities.Level;

/**
 * Levels list adapter.
 * 
 * @author Maciej Laskowski
 * 
 */
public class LevelsAdapter extends ArrayAdapter<Level> {

	private Context context;
	private List<Level> levels;
	private int resource;

	/**
	 * {@link LevelsAdapter} constructor
	 * 
	 * @param context
	 *            app context
	 * @param resource
	 *            resource id
	 * @param levels
	 *            levels list
	 */
	public LevelsAdapter(Context context, int resource, List<Level> levels) {
		super(context, resource, levels);
		this.context = context;
		this.levels = levels;
		this.resource = resource;
	}

	/* (non-Javadoc)
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		LevelHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(resource, parent, false);
			holder = new LevelHolder();
			holder.setProgressBar((ProgressBar) row.findViewById(R.id.progressBarLevel));
			holder.setTxtTitle((TextView) row.findViewById(R.id.textLevel));
			row.setTag(holder);
			// Set custom background for row
			row.setBackgroundDrawable((getContext().getResources().getDrawable(R.drawable.button_main)));
			// Set listener
			row.setOnClickListener(new OnClickRow(position + 1));
		} else {
			holder = (LevelHolder) row.getTag();
		}
		
		// Set level info
		Level level = levels.get(position);
		holder.getTxtTitle().setText(level.getName());
		holder.getProgressBar().setProgress(level.getProgress());

		return row;
	}

	/**
	 * Holder for {@link Level} element
	 */
	private static class LevelHolder {
		private TextView txtTitle;
		private ProgressBar progressBar;

		public TextView getTxtTitle() {
			return txtTitle;
		}

		public void setTxtTitle(TextView txtTitle) {
			this.txtTitle = txtTitle;
		}

		public ProgressBar getProgressBar() {
			return progressBar;
		}

		public void setProgressBar(ProgressBar progressBar) {
			this.progressBar = progressBar;
		}

	}

	/**
	 * {@link OnClickListener} binded to level item on
	 * levels list. Should start {@link ExerciseActivity}.
	 */
	private class OnClickRow implements View.OnClickListener {

		/** Bundle to start new {@link ExerciseActivity}. */
		private Bundle b;

		/**
		 * OnClickRow constructor.
		 * 
		 * @param levelId
		 *            id of level which question must
		 *            displayed.
		 */
		public OnClickRow(int levelId) {
			b = new Bundle();
			b.putInt("level_id", levelId);
		}

		/* (non-Javadoc)
		 * @see android.view.View.OnClickListener#onClick(android.view.View)
		 */
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(getContext(), ExerciseActivity.class);
			intent.putExtras(b);
			getContext().startActivity(intent);

		}

	}

}
