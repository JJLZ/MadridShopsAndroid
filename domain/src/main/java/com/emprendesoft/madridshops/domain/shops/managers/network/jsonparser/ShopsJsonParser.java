package com.emprendesoft.madridshops.domain.shops.managers.network.jsonparser;

import com.emprendesoft.madridshops.domain.shops.managers.network.entities.ShopEntity;
import com.emprendesoft.madridshops.domain.shops.managers.network.entities.ShopsResponseEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;

public class ShopsJsonParser {

    public List<ShopEntity> parse(String response) {

        List<ShopEntity> shopEntities = null;

        try {
            Gson gson = new GsonBuilder().create();

            Reader reader = new StringReader(response);
            ShopsResponseEntity shopsResponseEntity = gson.fromJson(reader, ShopsResponseEntity.class);
            shopEntities = shopsResponseEntity.getResult();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return shopEntities;
    }
}
