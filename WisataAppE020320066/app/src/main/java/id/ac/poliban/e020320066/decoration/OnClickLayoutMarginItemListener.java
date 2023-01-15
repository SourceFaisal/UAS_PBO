package id.ac.poliban.e020320066.decoration;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public interface OnClickLayoutMarginItemListener {

    void onClick(Context context, View v, int position, int spanIndex, RecyclerView.State state);

}
