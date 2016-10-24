package com.github.gkhere.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.gkhere.R;
import com.github.gkhere.bean.CourseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meiji on 2016/8/11.
 */
public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter
        .CourseBeanViewHolder> {

    private Context mContext;
    private LayoutInflater inflater;
    private List<CourseBean> list = new ArrayList<CourseBean>();

    public CourseAdapter(Context context, List<CourseBean> list) {
        this.mContext = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public CourseBeanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_course, parent, false);
        return new CourseBeanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CourseBeanViewHolder holder, int position) {
        CourseBean bean = list.get(position);
        String week = setWeek(list.get(position).getWeek());
        holder.courseWeek.setText(week);
        holder.courseName.setText(bean.getCourse());
        holder.courseInfo.setText(bean.getLocation() + "," + bean.getTimeinfo()
                + bean.getExtra());

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * 获得当前是星期几
     *
     * @param time
     * @return
     */
    public String setWeek(String time) {
        switch (time) {
            case "1":
                time = "周一";
                break;
            case "2":
                time = "周二";
                break;
            case "3":
                time = "周三";
                break;
            case "4":
                time = "周四";
                break;
            case "5":
                time = "周五";
                break;
            case "6":
                time = "周六";
                break;
            case "7":
                time = "周日";
                break;
        }
        return time;
    }

    public class CourseBeanViewHolder extends RecyclerView.ViewHolder {
        private TextView courseWeek;
        private TextView courseName;
        private TextView courseInfo;

        public CourseBeanViewHolder(View itemView) {
            super(itemView);
            courseWeek = (TextView) itemView.findViewById(R.id.course_week);
            courseName = (TextView) itemView.findViewById(R.id.course_name);
            courseInfo = (TextView) itemView.findViewById(R.id.course_info);
        }
    }
}
