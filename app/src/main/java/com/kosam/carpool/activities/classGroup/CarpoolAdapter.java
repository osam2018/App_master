package com.kosam.carpool.activities.classGroup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kosam.carpool.R;

import java.util.ArrayList;

public  class CarpoolAdapter extends BaseAdapter {
    private ArrayList<CarpoolListItem> ItemList=new ArrayList<CarpoolListItem>();
    public int getCount(){
        return ItemList.size();
    }
    public View getView(int position, View convertView, ViewGroup parent){
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.carpool, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView StartTime = (TextView) convertView.findViewById(R.id.carpool_start_time) ;
        TextView Start = (TextView) convertView.findViewById(R.id.carpool_start) ;
        TextView End = (TextView) convertView.findViewById(R.id.carpool_end) ;
        TextView Poster = (TextView) convertView.findViewById(R.id.carpool_poster) ;
        TextView MaxPerson = (TextView) convertView.findViewById(R.id.carpool_max) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        CarpoolListItem listViewItem = ItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        StartTime.setText(listViewItem.getStartTime());
        Start.setText(listViewItem.getStart());
        End.setText(listViewItem.getEnd());
        Poster.setText(listViewItem.getPoster());
        MaxPerson.setText(listViewItem.getMax());

        return convertView;
    }
    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return ItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(CarpoolListItem item) {
        ItemList.add(item);
    }
}
