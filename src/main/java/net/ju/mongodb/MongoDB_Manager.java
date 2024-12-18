package net.ju.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import net.dv8tion.jda.api.entities.Guild;
import net.ju.Lighty;
import org.bson.Document;

import java.util.ArrayList;

public class MongoDB_Manager {

    private String uri = "mongodb://" + Lighty.getDotenv().get("username") + ":" + Lighty.getDotenv().get("password") + "@" + Lighty.getDotenv().get("address");
    private MongoClient client = MongoClients.create(uri);
    private MongoDatabase database = client.getDatabase("lighty_bot");


    public MongoDatabase getDatabase() {
        return database;
    }

    public void init(){
        database.createCollection("servers");
        MongoCollection<Document> collection = database.getCollection("servers");
        for(Guild guild : Lighty.getJDA().getGuilds()){
            if(getQuery(guild.getId()) == null) {
                collection.insertOne(new Document()
                        .append("serverid", guild.getId())
                        .append("ignored_categories", new ArrayList<String>())
                        .append("ignored_channels", new ArrayList<String>())
                        .append("panelchannel", "")
                        .append("message_title", "Change Slotlimit")
                        .append("message_description", "Press the button to change the slotlimit!")
                        .append("message_color", "#fffff")
                        .append("message_footer", "Lighty Bot | Made with cookies by Ju ♡"));
            }
        }
    }

    public Document getQuery(String serverid){
        BasicDBObject query = new BasicDBObject("serverid", serverid);
        var cursor = database.getCollection("servers").find(query);

        while(cursor.cursor().hasNext()) {
            return cursor.cursor().next();
        }
        return null;
    }

    public void updateValue(String serverid, String key, Object value){
        BasicDBObject query = new BasicDBObject();
        query.put("serverid", serverid); // (1)

        BasicDBObject newDocument = new BasicDBObject();
        newDocument.put(key, value); // (2)

        BasicDBObject updateObject = new BasicDBObject();
        updateObject.put("$set", newDocument); // (3)

        database.getCollection("servers").updateOne(query, updateObject); // (4)
    }
}
