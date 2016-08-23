package com.github.gkhere.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.gkhere.R;
import com.github.gkhere.bean.ScoreBean;

import java.util.List;

/**
 * Created by Meiji on 2016/8/23.
 */
public class ScoreAdapter extends BaseAdapter {

    private List<ScoreBean> scoreBeanList;
    private Context mContext;
    private LayoutInflater mInflater;

    public ScoreAdapter(List<ScoreBean> scoreBeanList, Context context) {
        this.scoreBeanList = scoreBeanList;
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return scoreBeanList.size();
    }

    @Override
    public Object getItem(int i) {
        return scoreBeanList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = mInflater.inflate(R.layout.item_score, null);
            holder.tvScoreName = (TextView) view.findViewById(R.id.tv_scoreName);
            holder.tvScore = (TextView) view.findViewById(R.id.tv_score);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tvScoreName.setText(scoreBeanList.get(i).getScoreName());
        holder.tvScore.setText(scoreBeanList.get(i).getScore());
        return view;
    }

    public class ViewHolder{
        TextView tvScoreName;
        TextView tvScore;
    }
}
