package de.hyper.worlds.domain.using.apis;

import de.hyper.worlds.domain.WorldManagement;
import lombok.Getter;
import net.coreprotect.CoreProtectAPI.ParseResult;
import net.coreprotect.config.ConfigHandler;
import net.coreprotect.database.Database;
import net.coreprotect.database.statement.UserStatement;
import net.coreprotect.utility.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CoreProtectAPI {

    private boolean existsCO;
    private net.coreprotect.CoreProtectAPI coreProtectAPI;

    public CoreProtectAPI() {
        this.existsCO = WorldManagement.get().getDependencyManager().getDependency("CoreProtect").isEnabled();
        if (!existsCO) {
            WorldManagement.get().getLogger().warning(
                    "Plugin CoreProtect wasn't found, make sure to install CoreProtect to enable Histories.");
        }
        if (existsCO) {
            coreProtectAPI = new net.coreprotect.CoreProtectAPI();
            coreProtectAPI.testAPI();
        }
    }

    public List<ParseResult> getBlockActionsFromUUIDInWorld(String uuid, String worldName) {
        List<ParseResult> list = new ArrayList<>();
        String sql = "SELECT * FROM co_block WHERE user=(SELECT rowid FROM co_user WHERE uuid='" + uuid + "') AND wid=(SELECT id FROM co_world WHERE world='" + worldName + "') AND (action=0 OR action=1) AND rolled_back=0 ORDER BY time DESC;";
        Connection connection = Database.getConnection(false, 1000);
        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                    String resultTime = resultSet.getString("time");
                    int resultUserId = resultSet.getInt("user");
                    String resultAction = resultSet.getString("action");
                    int resultType = resultSet.getInt("type");
                    String resultData = resultSet.getString("data");
                    byte[] resultBlockData = resultSet.getBytes("blockdata");
                    String resultRolledBack = resultSet.getString("rolled_back");
                    if (ConfigHandler.playerIdCacheReversed.get(resultUserId) == null) {
                        UserStatement.loadName(connection, resultUserId);
                    }
                    String resultUser = ConfigHandler.playerIdCacheReversed.get(resultUserId);
                    String blockData = Util.byteDataToString(resultBlockData, resultType);
                    int resultX = resultSet.getInt("x");
                    int resultY = resultSet.getInt("y");
                    int resultZ = resultSet.getInt("z");
                    int worldId = resultSet.getInt("wid");
                    String[] lookupData = new String[]{
                            resultTime,
                            resultUser,
                            String.valueOf(resultX),
                            String.valueOf(resultY),
                            String.valueOf(resultZ),
                            String.valueOf(resultType),
                            resultData, resultAction,
                            resultRolledBack,
                            String.valueOf(worldId), blockData};
                    String[] lineData = Util.toStringArray(lookupData);
                    list.add(coreProtectAPI.parseResult(lineData));
                }
                statement.close();
                resultSet.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public List<String> getUUIDsOfPlayersInWorld(String worldName) {
        List<String> list = new ArrayList<>();
        String sql = "SELECT uuid FROM co_user WHERE rowid IN (SELECT DISTINCT user FROM co_block WHERE wid=(SELECT id FROM co_world WHERE world='" + worldName + "'));";
        Connection connection = Database.getConnection(false, 1000);
        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                    String uuid = resultSet.getString("uuid");
                    list.add(uuid);
                }
                statement.close();
                resultSet.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}