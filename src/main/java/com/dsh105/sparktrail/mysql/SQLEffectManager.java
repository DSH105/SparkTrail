package com.dsh105.sparktrail.mysql;

import com.dsh105.sparktrail.SparkTrailPlugin;
import com.dsh105.sparktrail.config.ConfigOptions;
import com.dsh105.sparktrail.data.DataFactory;
import com.dsh105.sparktrail.data.EffectCreator;
import com.dsh105.sparktrail.data.EffectManager;
import com.dsh105.sparktrail.trail.EffectHolder;
import org.bukkit.Location;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SQLEffectManager {

    public static SQLEffectManager instance;

    public SQLEffectManager() {
        instance = this;
    }

    public void save(EffectHolder eh) {
        if (eh.isPersistent()) {
            if (ConfigOptions.instance.useSql()) {
                Connection con = null;
                PreparedStatement statement = null;

                if (SparkTrailPlugin.getInstance().dbPool != null) {
                    try {
                        con = SparkTrailPlugin.getInstance().dbPool.getConnection();
                        String data = DataFactory.serialiseEffects(eh.getEffects(), false, false, true);

                        if (eh.getEffectType().equals(EffectHolder.EffectType.PLAYER)) {
                            statement = con.prepareStatement("INSERT INTO PlayerEffects (?, Effects) VALUES (?, ?);");
                            statement.setString(1, "PlayerName");
                            statement.setString(2, eh.getDetails().playerName);
                        } else if (eh.getEffectType().equals(EffectHolder.EffectType.LOCATION)) {
                            statement = con.prepareStatement("INSERT INTO PlayerEffects (?, Effects) VALUES (?, ?);");
                            statement.setString(1, "Location");
                            statement.setString(2, DataFactory.serialiseLocation(eh.getLocation()));
                        } else if (eh.getEffectType().equals(EffectHolder.EffectType.MOB)) {
                            statement = con.prepareStatement("INSERT INTO MobEffects (?, Effects) VALUES (?, ?);");
                            statement.setString(1, "MobUUID");
                            statement.setString(2, eh.getDetails().mobUuid.toString());
                        }

                        if (statement != null) {
                            statement.setString(3, data);
                            statement.executeUpdate();
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
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

    public void clear(EffectHolder eh) {
        if (ConfigOptions.instance.useSql()) {
            Connection con = null;
            PreparedStatement statement = null;

            if (SparkTrailPlugin.getInstance().dbPool != null) {
                try {
                    con = SparkTrailPlugin.getInstance().dbPool.getConnection();

                    if (eh.getEffectType().equals(EffectHolder.EffectType.PLAYER)) {
                        statement = con.prepareStatement("DELETE FROM PlayerEffects WHERE PlayerName = ?;");
                        statement.setString(1, eh.getDetails().playerName);
                    } else if (eh.getEffectType().equals(EffectHolder.EffectType.LOCATION)) {
                        statement = con.prepareStatement("DELETE FROM LocationEffects WHERE Location = ?;");
                        statement.setString(1, DataFactory.serialiseLocation(eh.getLocation()));
                    } else if (eh.getEffectType().equals(EffectHolder.EffectType.MOB)) {
                        statement = con.prepareStatement("DELETE FROM MobEffects WHERE MobUUID = ?;");
                        statement.setString(1, eh.getDetails().mobUuid.toString());
                    }

                    if (statement != null) {
                        statement.executeUpdate();
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
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
                    String data = DataFactory.serialiseEffects(eh.getEffects(), false, false, true);

                    if (eh.getEffectType().equals(EffectHolder.EffectType.PLAYER)) {
                        statement = con.prepareStatement("UPDATE PlayerEffects SET Effects = ? WHERE ? = ?");
                        statement.setString(2, "PlayerName");
                        statement.setString(3, eh.getDetails().playerName);
                    } else if (eh.getEffectType().equals(EffectHolder.EffectType.LOCATION)) {
                        statement = con.prepareStatement("UPDATE LocationEffects SET Effects = ? WHERE ? = ?");
                        statement.setString(2, "Location");
                        statement.setString(3, DataFactory.serialiseLocation(eh.getLocation()));
                    } else if (eh.getEffectType().equals(EffectHolder.EffectType.MOB)) {
                        statement = con.prepareStatement("UPDATE MobEffects SET Effects = ? WHERE ? = ?");
                        statement.setString(2, "MobUUID");
                        statement.setString(3, eh.getDetails().mobUuid.toString());
                    }

                    if (statement != null) {
                        statement.setString(1, data);
                        statement.executeUpdate();
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
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

    public EffectHolder create(String playerName) {
        if (ConfigOptions.instance.useSql()) {
            Connection con = null;
            PreparedStatement statement = null;

            if (SparkTrailPlugin.getInstance().dbPool != null) {
                try {
                    con = SparkTrailPlugin.getInstance().dbPool.getConnection();
                    statement = con.prepareStatement("SELECT * FROM PlayerEffects WHERE PlayerName = ?;");
                    statement.setString(1, playerName);
                    ResultSet rs = statement.executeQuery();
                    while (rs.next()) {
                        EffectManager.getInstance().clearFromMemory(EffectManager.getInstance().getEffect(playerName));
                        EffectHolder eh = EffectCreator.createPlayerHolder(playerName);
                        String effects = rs.getString("Effects");
                        DataFactory.addEffectsFrom(effects, eh);
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
        return null;
    }

    public EffectHolder create(Location location) {
        if (ConfigOptions.instance.useSql()) {
            Connection con = null;
            PreparedStatement statement = null;

            if (SparkTrailPlugin.getInstance().dbPool != null) {
                try {
                    con = SparkTrailPlugin.getInstance().dbPool.getConnection();
                    statement = con.prepareStatement("SELECT * FROM LocationEffects WHERE Location = ?;");
                    statement.setString(1, DataFactory.serialiseLocation(location));
                    ResultSet rs = statement.executeQuery();
                    while (rs.next()) {
                        EffectManager.getInstance().clearFromMemory(EffectManager.getInstance().getEffect(location));
                        EffectHolder eh = EffectCreator.createLocHolder(location);
                        String effects = rs.getString("Effects");
                        DataFactory.addEffectsFrom(effects, eh);
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
        return null;
    }
}