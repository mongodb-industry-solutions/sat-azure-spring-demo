package sat.azure.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@SpringBootApplication
public class Application {

    @Autowired
    private Environment environment;

    public static void main(String[] args) {
        Dotenv env = Dotenv.load();
        String mongoUri = env.get("MONGODBATLAS_CLUSTER_CONNECTIONSTRING") != null && !env.get("MONGODBATLAS_CLUSTER_CONNECTIONSTRING").isEmpty()
                ? env.get("MONGODBATLAS_CLUSTER_CONNECTIONSTRING")
                : env.get("MONGO_URI");
        System.setProperty("MONGO_URI", mongoUri);
        System.setProperty("MONGO_DBNAME", env.get("MONGO_DBNAME"));

        SpringApplication.run(Application.class, args);
    }

    @Bean
    public MongoClient mongoClient() {
        String uri = environment.getProperty("MONGO_URI");
        ConnectionString connectionString = new ConnectionString(uri);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        return MongoClients.create(mongoClientSettings);
    }

    @SuppressWarnings("null")
    @Bean
    public SimpleMongoClientDatabaseFactory mongoDbFactory(MongoClient mongoClient) {
        String uri = environment.getProperty("MONGO_URI");
        ConnectionString connectionString = new ConnectionString(uri);
        String databaseName = connectionString.getDatabase();
        if (databaseName == null || databaseName.isEmpty()) {
            // Fallback - replace with your actual database name if the URI doesn't contain
            // it explicitly
            databaseName = environment.getProperty("MONGO_DBNAME");
        }
        return new SimpleMongoClientDatabaseFactory(mongoClient, databaseName);
    }

    @Bean
    public MongoTemplate mongoTemplate(SimpleMongoClientDatabaseFactory mongoDbFactory) {
        return new MongoTemplate(mongoDbFactory);
    }
}
