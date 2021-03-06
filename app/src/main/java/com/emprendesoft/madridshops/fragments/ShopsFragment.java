package com.emprendesoft.madridshops.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.emprendesoft.madridshops.R;
import com.emprendesoft.madridshops.adapters.ShopsAdapter;
import com.emprendesoft.madridshops.domain.shops.model.Shop;
import com.emprendesoft.madridshops.domain.shops.model.Shops;
import com.emprendesoft.madridshops.views.OnElementClick;

public class ShopsFragment extends Fragment
{

    DividerItemDecoration mDividerItemDecoration;
    private OnElementClick<Shop> listener;
    private RecyclerView shopRecyclerView;
    private ShopsAdapter adapter;

    public ShopsFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_shops, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar();

        shopRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_shops__recycler_view);
        shopRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mDividerItemDecoration = new DividerItemDecoration(shopRecyclerView.getContext(), DividerItemDecoration.VERTICAL);
        shopRecyclerView.addItemDecoration(mDividerItemDecoration);

        return view;
    }

    public void setShops(Shops shops)
    {
        adapter = new ShopsAdapter(shops, getActivity());
        shopRecyclerView.setAdapter(adapter);

        adapter.setONClickListener(new OnElementClick<Shop>()
        {
            @Override
            public void clickedOn(@NonNull Shop shop, int position)
            {
                if (listener != null)
                {
                    ShopsFragment.this.listener.clickedOn(shop, position);
                }
            }
        });
    }

    public void setOnElementClickListener(OnElementClick<Shop> listener)
    {
        this.listener = listener;
    }
}






























