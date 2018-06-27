package com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.model;

import android.arch.persistence.room.Entity;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public class OriginalDebt extends Debt {
    @Override
    public OriginalDebt clone() {
        return (OriginalDebt) super.clone();
    }

    @Override
    public OriginalDebt set(String fieldName, Object value) {
        return (OriginalDebt) super.set(fieldName, value);
    }
}
