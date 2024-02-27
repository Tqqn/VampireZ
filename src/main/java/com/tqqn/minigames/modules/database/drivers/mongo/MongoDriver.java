package com.tqqn.minigames.modules.database.drivers.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.tqqn.minigames.framework.database.drivers.IDatabaseDriver;
import com.tqqn.minigames.framework.database.models.PlayerModel;
import com.tqqn.minigames.framework.database.models.PlayerStats;
import com.tqqn.minigames.modules.database.DatabaseModule;
import lombok.Getter;

import java.util.UUID;

public class MongoDriver implements IDatabaseDriver {

    @Getter private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;

    private final DatabaseModule databaseModule;

    public MongoDriver(DatabaseModule databaseModule) {
        this.databaseModule = databaseModule;
    }

    @Override
    public void connect(String database, String username, String password, String URI) {
        ConnectionString connectionString = new ConnectionString("mongodb+srv://" + username + ":" + password + "@" + URI + "/?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(connectionString).serverApi(ServerApi.builder().version(ServerApiVersion.V1).build()).retryWrites(true).retryReads(true).build();
        mongoClient = MongoClients.create(settings);
        mongoDatabase = mongoClient.getDatabase(database);
    }

    @Override
    public boolean doesPlayerExist(UUID uuid) {
        return false;
    }

    @Override
    public void createPlayerTemplate(UUID uuid, String name) {

    }

    @Override
    public PlayerStats getStats(UUID uuid) {
        return null;
    }

    @Override
    public void savePlayer(PlayerModel playerModel) {

    }
}
