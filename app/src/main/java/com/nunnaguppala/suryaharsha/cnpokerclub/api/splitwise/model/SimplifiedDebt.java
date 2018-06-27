package com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.model;

import android.arch.persistence.room.Entity;

import com.google.api.client.json.GenericJson;

public class SimplifiedDebt extends Debt {
    @Override
    public SimplifiedDebt clone() {
        return (SimplifiedDebt) super.clone();
    }

    @Override
    public SimplifiedDebt set(String fieldName, Object value) {
        return (SimplifiedDebt) super.set(fieldName, value);
    }
}
