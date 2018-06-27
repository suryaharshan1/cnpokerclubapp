package com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

import java.util.List;

public class Group extends GenericJson{
    @Key("id")
    private int id;

    @Key("name")
    private String name;

    @Key("updated_at")
    private String updatedAt;

    @Key("members")
    private List<Member> members;

    @Key("simplify_by_default")
    private boolean isSimplifiedByDefault;

    @Key("original_debts")
    private List<OriginalDebt> originalDebts;

    @Key("simplified_debts")
    private List<SimplifiedDebt> simplifiedDebts;

    @Key("whiteboard")
    private String whiteboard;

    @Key("group_type")
    private String groupType;

    @Key("invite_link")
    private String inviteLink;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public List<Member> getMembers() {
        return members;
    }

    public boolean isSimplifiedByDefault() {
        return isSimplifiedByDefault;
    }

    public List<OriginalDebt> getOriginalDebts() {
        return originalDebts;
    }

    public List<SimplifiedDebt> getSimplifiedDebts() {
        return simplifiedDebts;
    }

    public String getWhiteboard() {
        return whiteboard;
    }

    public String getGroupType() {
        return groupType;
    }

    public String getInviteLink() {
        return inviteLink;
    }

    @Override
    public Group clone() {
        return (Group) super.clone();
    }

    @Override
    public Group set(String fieldName, Object value) {
        return (Group) super.set(fieldName, value);
    }
}
