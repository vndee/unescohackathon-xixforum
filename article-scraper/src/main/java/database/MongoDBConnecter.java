package database;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import application.Application;
import common.model.TrendData;
import parser.model.AccentData;
import org.bson.Document;
import org.json.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;


public class MongoDBConnecter {

	public static TrendData getAccentData(String collectionName, String param) {
		// disable mongodb log
		Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
		mongoLogger.setLevel(Level.SEVERE); // e.g. or Log.WARNING, etc.

		if (null == collectionName || null == param) {
			//System.err.println("MongoDBConnecter Error: collectionName or param is null");
			return new TrendData();
		} else if (collectionName.isEmpty() || param.isEmpty()) {
			//System.err.println("MongoDBConnecter Error: collectionName or param is empty");
			return new TrendData();
		}
		// Query result
		String result = "";
		int result_id = 0;
		// Creating a Mongo client
		TrendData mongo = new TrendData(Application.MONGO_DB, Application.MONGO_DB_PORT);
		// Accessing the database
		TrendData database = mongo.getDatabase(Application.MONGO_DB_NAME);
		// Retrieving a collection
		MongoCollection<Document> collection = database.getCollection(collectionName);

		BasicDBObject queryBuilder = new BasicDBObject();
		queryBuilder.append("Url", Pattern.compile(param, Pattern.CASE_INSENSITIVE));
		// If query string is empty then return
		if (queryBuilder.isEmpty()) {
			mongo.close();
			return new TrendData();
		}
		//System.out.println("Filter String: " + queryBuilder);
		FindIterable<Document> iterDoc = collection.find(queryBuilder);
		MongoCursor<Document> it = iterDoc.iterator();
		while (it.hasNext()) {
			JSONObject jsonTableRow = new JSONObject(it.next().toJson());
			if (null != jsonTableRow && jsonTableRow.length() > 0) {
				result = jsonTableRow.getString("Name").trim();
				result_id = (int)jsonTableRow.getDouble("Id");
				break;
			}
		}
		mongo.close();

		if (result.isEmpty()) {
			return new TrendData();
		}
		return new TrendData();
	}


}
