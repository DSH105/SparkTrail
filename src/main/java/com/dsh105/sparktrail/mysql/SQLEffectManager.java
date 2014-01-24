package com.dsh105.sparktrail.mysql;

import com.dsh105.sparktrail.SparkTrailPlugin;
import com.dsh105.sparktrail.config.ConfigOptions;
import com.dsh105.sparktrail.particle.EffectHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class SQLEffectManager {

    public static SQLEffectManager instance;

    public SQLEffectManager() {
        instance = this;
    }

    public void save(EffectHolder eh) {
        if (ConfigOptions.instance.useSql()) {
            Connection con = null;
            PreparedStatement statement = null;

            if (SparkTrailPlugin.getInstance().dbPool != null) {
                try {
                    con = SparkTrailPlugin.getInstance().dbPool.getConnection();

                    if (eh.getEffectType().equals(EffectHolder.EffectType.PLAYER)) {

                    } else if (eh.getEffectType().equals(EffectHolder.EffectType.LOCATION)) {

                    } else if (eh.getEffectType().equals(EffectHolder.EffectType.MOB)) {

                    }

                } catch (SQLException e) {
                    //Log the issue
                } finally {
                    try {
                        if (statement != null)
                            statement.close();
                        if (con != null)
                            con.close();
                    } catch (SQLException ignored) {
                    }
                }
            }
        }
    }

    public void clear(EffectHolder eh) {
        if (ConfigOptions.instance.useSql()) {
            Connection con = null;
            PreparedStatement statement = null;

            if (SparkTrailPlugin.getInstance().dbPool != null) {
                try {
                    con = SparkTrailPlugin.getInstance().dbPool.getConnection();

                    if (eh.getEffectType().equals(EffectHolder.EffectType.PLAYER)) {

                    } else if (eh.getEffectType().equals(EffectHolder.EffectType.LOCATION)) {

                    } else if (eh.getEffectType().equals(EffectHolder.EffectType.MOB)) {

                    }

                } catch (SQLException e) {
                    //Log the issue
                } finally {
                    try {
                        if (statement != null)
                            statement.close();
                        if (con != null)
                            con.close();
                    } catch (SQLException ignored) {
                    }
                }
            }
        }
    }

    public void update(EffectHolder eh) {
        if (ConfigOptions.instance.useSql()) {
            Connection con = null;
            PreparedStatement statement = null;

            if (SparkTrailPlugin.getInstance().dbPool != null) {
                try {
                    con = SparkTrailPlugin.getInstance().dbPool.getConnection();

                    if (eh.getEffectType().equals(EffectHolder.EffectType.PLAYER)) {

                    } else if (eh.getEffectType().equals(EffectHolder.EffectType.LOCATION)) {

                    } else if (eh.getEffectType().equals(EffectHolder.EffectType.MOB)) {

                    }

                } catch (SQLException e) {
                    //Log the issue
                } finally {
                    try {
                        if (statement != null)
                            statement.close();
                        if (con != null)
                            con.close();
                    } catch (SQLException ignored) {
                    }
                }
            }
        }
    }

    public void create(EffectHolder eh) {
        if (ConfigOptions.instance.useSql()) {
            Connection con = null;
            PreparedStatement statement = null;

            if (SparkTrailPlugin.getInstance().dbPool != null) {
                try {
                    con = SparkTrailPlugin.getInstance().dbPool.getConnection();

                    if (eh.getEffectType().equals(EffectHolder.EffectType.PLAYER)) {

                    } else if (eh.getEffectType().equals(EffectHolder.EffectType.LOCATION)) {

                    } else if (eh.getEffectType().equals(EffectHolder.EffectType.MOB)) {

                    }

                } catch (SQLException e) {
                    //Log the issue
                } finally {
                    try {
                        if (statement != null)
                            statement.close();
                        if (con != null)
                            con.close();
                    } catch (SQLException ignored) {
                    }
                }
            }
        }
    }
}