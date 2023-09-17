package org.zaksen.fancyskills.database;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zaksen.fancyskills.FancySkills;
import org.zaksen.fancyskills.data.PlayerData;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DatabaseManager {
    public static final DatabaseManager instance = new DatabaseManager();
    private final Logger logger;
    private Connection connection;
    private Statement statement;

    private DatabaseManager() {
        logger = LoggerFactory.getLogger(FancySkills.class);
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:player-skills.sqlite");
            statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS players(uuid TEXT, combatXp INTEGER, miningXp INTEGER, tokens INTEGER);");
        } catch (SQLException e) {
            logger.error("unstable database connection: ", e);
        }
    }

    public void addNewPlayer(Player player) {
        try {
            PreparedStatement newSt = connection.prepareStatement("INSERT INTO players(uuid, combatXp, miningXp, tokens) VALUES(?, ?, ?, ?);");
            newSt.setString(1, player.getUniqueId().toString());
            newSt.setInt(2, 0);
            newSt.setInt(3, 0);
            newSt.setInt(4, 0);
            newSt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Failed to add player to database: ", e);
        }
    }

    public void setPlayerData(Player player, String columnName, int data) {
        try {
            PreparedStatement newSt = connection.prepareStatement("UPDATE players SET " + columnName + " = ? WHERE uuid = ?;");
            newSt.setInt(1, data);
            newSt.setString(2, player.getUniqueId().toString());
            newSt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Failed to change player data in database: ", e);
        }
    }

    @Nullable
    public PlayerData getPlayerData(Player player) {
        PlayerData result = null;
        ResultSet messageSet;
        try {
            messageSet = statement.executeQuery("SELECT * FROM players;");
            while (messageSet.next()) {
                String uuid = messageSet.getString("uuid");
                if(uuid.equals(player.getUniqueId().toString())) {
                    int combatX = messageSet.getInt("combatXp");
                    int miningX = messageSet.getInt("miningXp");
                    int tokens = messageSet.getInt("tokens");
                    result = new PlayerData(player, combatX, miningX, tokens);
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to get player from database: ", e);
        }
        return result;
    }

    public List<PlayerData> getAllData() {
        List<PlayerData> data = new ArrayList<>();
        ResultSet messageSet;
        try {
            messageSet = statement.executeQuery("SELECT * FROM players;");
            while (messageSet.next()) {
                String uuid = messageSet.getString("uuid");
                Player player = Bukkit.getPlayer(UUID.fromString(uuid));
                int combatX = messageSet.getInt("combatXp");
                int miningX = messageSet.getInt("miningXp");
                int tokens = messageSet.getInt("tokens");
                data.add(new PlayerData(player, combatX, miningX, tokens));
            }
        } catch (SQLException e) {
            logger.error("Failed to get players from database: ", e);
        }
        return data;
    }
}