package pro.umaks.mrkaznaandroidapp;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pro.umaks.mrkaznaandroidapp.models.RequestModel;
import pro.umaks.mrkaznaandroidapp.viewmodels.RequestListViewModel;

public class RequestListActivity extends AppCompatActivity {
    private RequestListViewModel mRequestListViewModel;
    boolean mTwoPane = false;

    private String mLogin;
    private String mPin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_list);

        Intent intent = getIntent();
        mLogin = intent.getStringExtra("login");
        mPin = intent.getStringExtra("pin");

       mRequestListViewModel =  ViewModelProviders.of(this).get(RequestListViewModel.class);
       if (mRequestListViewModel.Requests == null)
       {
           mRequestListViewModel.FillRequestsList(mLogin, mPin);
       }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        toolbar.setSubtitle(mRequestListViewModel.Subtitle);

        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

//        if (findViewById(R.id.item_detail_container) != null) {
//            // The detail container view will be present only in the
//            // large-screen layouts (res/values-w900dp).
//            // If this view is present, then the
//            // activity should be in two-pane mode.
//            mTwoPane = true;
//        }

    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(mRequestListViewModel.Requests));
    }
    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<RequestModel> mValues;

        public SimpleItemRecyclerViewAdapter(List<RequestModel> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.request_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mCompanyView.setText(mValues.get(position).Organization);
            holder.mContragentView.setText(mValues.get(position).Contragent);
            holder.mContractView.setText(mValues.get(position).Contract);
            holder.mOrganizationAccountView.setText(mValues.get(position).OrganizationAccount);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(RequestDetailFragment.ARG_ITEM_ID, holder.mItem.Id);
                        RequestDetailFragment fragment = new RequestDetailFragment();
                        fragment.setArguments(arguments);
//                        getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.item_detail_container, fragment)
//                                .commit();
                    } 
                    else {
//                        Context context = v.getContext();
//                        Intent intent = new Intent(context, ItemDetailActivity.class);
//                        intent.putExtra(RequestDetailFragment.ARG_ITEM_ID, holder.mItem.id);
//
//                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mCompanyView;
            public final TextView mOrganizationAccountView;
            public final TextView mContragentView;
            public final TextView mContractView;
            public RequestModel mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mCompanyView = (TextView) view.findViewById(R.id.company);
                mContragentView = (TextView) view.findViewById(R.id.contragent);
                mOrganizationAccountView = (TextView) view.findViewById(R.id.OrganizationAccount);
                mContractView = (TextView) view.findViewById(R.id.contract);

            }

            @Override
            public String toString() {
                return super.toString() + " '" + mCompanyView.getText() + "'";
            }
        }
    }
}
