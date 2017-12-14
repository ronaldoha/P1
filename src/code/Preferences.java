package code;

import java.io.File;
import java.io.FileOutputStream;

public final class Preferences {

    Preferences preferences = new Preferences();
    File configuration = new File("UserSettings/config");
    FileOutputStream fos;



    public Boolean getBoolean(final String key, final Boolean def) {
        return preferences.getBoolean(key, def);
    }


    public Boolean getBoolean(final Class clazz, final String key, final Boolean def) {
        return preferences.getBoolean(clazz, key, def);
    }


    public void putBoolean(final String key, final Boolean value) {
        preferences.putBoolean(key, value);
    }


    public void putBoolean(final Class clazz, final String key, final Boolean value) {
        preferences.putBoolean(clazz, key, value);
    }


    public String get(final String key, final String def) {
        return preferences.get(key, def);
    }


    public String get(final Class clazz, final String key, final String def) {
        return preferences.get(clazz, key, def);
    }


    public void put(final String key, final String value) {
        preferences.put(key, value);
    }


    public void put(final Class clazz, final String key, final String value) {
        preferences.put(clazz, key, value);
    }


    public Double getDouble(final String key, final Double def) {
        return preferences.getDouble(key, def);
    }


    public Double getDouble(final Class clazz, final String key, final Double def) {
        return preferences.getDouble(clazz, key, def);
    }


    public void putDouble(final String key, final Double value) {
        preferences.putDouble(key, value);
    }


    public void putDouble(final Class clazz, final String key, final Double value) {
        preferences.putDouble(clazz, key, value);
    }


    public Integer getInt(final String key, final Integer def) {
        return preferences.getInt(key, def);
    }


    public Integer getInt(final Class clazz, final String key, final Integer def) {
        return preferences.getInt(clazz, key, def);
    }


    public void putInt(final String key, final Integer value) {
        preferences.putInt(key, value);
    }


    public void putInt(final Class clazz, final String key, final Integer value) {
        preferences.putInt(clazz, key, value);
    }


    public Long getLong(final String key, final Long def) {
        return preferences.getLong(key, def);
    }


    public Long getLong(final Class clazz, final String key, final Long def) {
        return preferences.getLong(clazz, key, def);
    }


    public void putLong(final String key, final Long value) {
        preferences.putLong(key, value);
    }


    public void putLong(final Class clazz, final String key, final Long value) {
        preferences.putLong(clazz, key, value);
    }


    public void init(final boolean drop) {
        preferences.init(drop);
    }


    public Preferences forApplication() {
        return preferences.forApplication();
    }


    public Preferences forModule(Class clazz) {
        return preferences.forModule(clazz);
    }

}