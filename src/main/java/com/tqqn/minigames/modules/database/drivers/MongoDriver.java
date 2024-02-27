package com.tqqn.minigames.modules.database.drivers;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.tqqn.minigames.framework.database.drivers.IDatabaseDriver;
import com.tqqn.minigames.framework.database.models.PlayerModel;
import com.tqqn.minigames.framework.database.models.PlayerStats;
import com.tqqn.minigames.modules.database.DatabaseModule;
import lombok.Getter;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.UUID;

/**
 * MongoDriver class implements the IDatabaseDriver interface and provides functionality to interact with MongoDB database.
 */
public class MongoDriver implements IDatabaseDriver {

    @Getter private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private MongoCollection<Document> playerCollection;

    private final DatabaseModule databaseModule;

    /**
     * Constructs a new MongoDriver instance with the given DatabaseModule.
     *
     * @param databaseModule The DatabaseModule instance.
     */
    public MongoDriver(DatabaseModule databaseModule) {
        this.databaseModule = databaseModule;
    }

    /**
     * Connects to the database server.
     *
     * @param database The name of the database to connect to.
     * @param host     The host address of the database server.
     * @param port     The port number of the database server.
     */
    @Override
    public void connect(String database, String host, String port) {
        this.mongoClient = new MongoClient(new MongoClientURI("mongodb://" + host + ":" + port));
        this.mongoDatabase = mongoClient.getDatabase(database);
        initPlayerCollection();
    }

    /**
     * Initializes the player collection in the MongoDB database.
     */
    public void initPlayerCollection() {
        try {
            mongoDatabase.createCollection("players");
        } catch (MongoCommandException ignored){

        }
        playerCollection = mongoDatabase.getCollection("players");
    }

    /**
     * Creates a player template in the database with the given UUID and name.
     *
     * @param uuid The UUID of the player.
     * @param name The name of the player.
     */
    @Override
    public void createPlayerTemplate(UUID uuid, String name) {

        Document statsDocument = new Document();
        statsDocument.put("human_kills", 0);
        statsDocument.put("human_wins", 0);
        statsDocument.put("human_losses", 0);
        statsDocument.put("human_deaths", 0);
        statsDocument.put("vampire_kills", 0);
        statsDocument.put("vampire_wins", 0);
        statsDocument.put("vampire_losses", 0);
        statsDocument.put("vampire_deaths", 0);

        Document playerDocument = new Document();
        playerDocument.put("_id", uuid.toString());
        playerDocument.put("uuid", uuid.toString());
        playerDocument.put("name", name);
        playerDocument.append("stats", statsDocument);

        playerCollection.insertOne(playerDocument);
    }

    /**
     * Retrieves player statistics from the database based on the given UUID.
     *
     * @param uuid The UUID of the player.
     * @return The PlayerStats object containing the player's statistics.
     */
    @Override
    public PlayerStats getStats(UUID uuid) {
        Document found = playerCollection.find(Filters.eq("_id", uuid.toString())).first();
        if (found == null) return null;
        Document playerStats = (Document) found.get("stats");
        return new PlayerStats(playerStats.getInteger("human_kills"),
                playerStats.getInteger("human_wins"),
                playerStats.getInteger("human_losses"),
                playerStats.getInteger("human_deaths"),
                playerStats.getInteger("vampire_kills"),
                playerStats.getInteger("vampire_wins"),
                playerStats.getInteger("vampire_losses"),
                playerStats.getInteger("vampire_deaths"));
    }

    /**
     * Saves the player model by updating the player document in the database.
     *
     * @param playerModel The PlayerModel object to save.
     */
    @Override
    public void savePlayer(PlayerModel playerModel) {
        PlayerStats playerStats = playerModel.getStats();
        Bson playerUpdates = Updates.set("name", playerModel.getName());
        Bson statsUpdates = Updates.combine(
                Updates.set("stats.human_kills", playerStats.getStat(PlayerStats.StatType.HUMAN_KILLS)),
                Updates.set("stats.human_wins", playerStats.getStat(PlayerStats.StatType.HUMAN_WINS)),
                Updates.set("stats.human_losses", playerStats.getStat(PlayerStats.StatType.HUMAN_LOSSES)),
                Updates.set("stats.human_deaths", playerStats.getStat(PlayerStats.StatType.HUMAN_DEATHS)),
                Updates.set("stats.vampire_kills", playerStats.getStat(PlayerStats.StatType.VAMPIRE_KILLS)),
                Updates.set("stats.vampire_wins", playerStats.getStat(PlayerStats.StatType.VAMPIRE_WINS)),
                Updates.set("stats.vampire_losses", playerStats.getStat(PlayerStats.StatType.VAMPIRE_LOSSES)),
                Updates.set("stats.vampire_deaths", playerStats.getStat(PlayerStats.StatType.VAMPIRE_DEATHS)));
        playerCollection.updateOne(Filters.eq("_id", playerModel.getUuid().toString()), playerUpdates);
        playerCollection.updateOne(Filters.eq("_id", playerModel.getUuid().toString()), statsUpdates);
    }
}
