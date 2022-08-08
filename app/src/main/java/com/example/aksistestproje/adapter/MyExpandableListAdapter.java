package com.example.aksistestproje.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aksistestproje.R;

import java.util.List;
import java.util.Map;

public class MyExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private Map<String, List<String>> myCollection;
    private List<String> groupList;

    public MyExpandableListAdapter(Context context, List<String> groupList,
                                   Map<String, List<String>> myCollection) {
        this.context = context;
        this.myCollection = myCollection;
        this.groupList = groupList;
    }

    @Override
    public int getGroupCount() {
        return myCollection.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return myCollection.get(groupList.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return groupList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return myCollection.get(groupList.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String name = getGroup(i).toString();
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_group, null);
        }
        TextView item = view.findViewById(R.id.listTitle);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(name);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        String name = getChild(i, i1).toString();
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item, null);
        }
        TextView item = view.findViewById(R.id.expandedListItem);
        item.setText(name);

        view.setOnClickListener(e -> {
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("label", item.getText());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(context, "Copied To Clipboard!", Toast.LENGTH_SHORT).show();
        });

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
