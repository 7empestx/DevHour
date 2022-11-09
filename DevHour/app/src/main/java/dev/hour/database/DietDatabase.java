package dev.hour.database;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import dev.hour.contracts.MealContract;
import dev.hour.diet.Diet;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.http.SdkHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;

public class DietDatabase implements MealContract.Diet.Database {

    /// ---------------
    /// Private Members

    private DynamoDbClient client      ;
    private String                      tableName   ;
    private String                      region      ;
    private SdkHttpClient httpClient  ;
    private Map<String, AttributeValue> response    ;

    /// ------------
    /// Constructing

    public DietDatabase(final String region, final String tableName,
                              final SdkHttpClient httpClient){

        this.client     = null          ;
        this.tableName  = tableName     ;
        this.region     = region        ;
        this.httpClient = httpClient    ;

    }

    /// ---------------
    /// Private Methods

    /**
     * Creates a GetItemRequest to get the item  with the specified key, value pair.
     * @param key The value of the key
     * @param value The specific value to match
     * @return Map containing the requested data, if any
     */
    private Map<String, AttributeValue> getItem(final String key, final String value) {

        final Map<String, AttributeValue> keyMap = new HashMap<>();
        Map<String, AttributeValue> result;

        if(client != null) {

            keyMap.put(key, AttributeValue.builder().s(value).build());

            final GetItemRequest request = GetItemRequest.builder()
                    .key(keyMap)
                    .tableName(tableName)
                    .build();

            final DietDatabase database = this;

            final Thread thread = new Thread(() ->
                    database.response = database.client.getItem(request).item());

            try {

                thread.start();
                thread.join();

                result = response;

            } catch (final Exception exception) {

                Log.e("DietDatabase", exception.getMessage());
                result = new HashMap<>();

            }

        } else result = new HashMap<>();

        return result;

    }

    /**
     * Sets the credentials required to build the DynamoDB client
     * @param credentials The credentials to set.
     */
    @Override
    public void setCredentials(final Map<String, String> credentials) {

        client = DynamoDbClient
                .builder()
                .region(Region.of(this.region))
                .httpClient(this.httpClient)
                .credentialsProvider(() -> AwsSessionCredentials.create(
                        credentials.get("ACCESS_KEY"),
                        credentials.get("SECRET_KEY"),
                        credentials.get("SESSION_TOKEN")))
                .build();

    }

    @Override
    public MealContract.Diet getDiet(String id) {
        final MealContract.Diet diet;

        if(client != null) {
            final Map<String, AttributeValue> dietBlob = getItem("id", id);

            final List<AttributeValue> allergensAttributeValues = Objects.requireNonNull(dietBlob.get("allergens")).l();
            final List<AttributeValue> dietsAttributeValues = Objects.requireNonNull(dietBlob.get("diets")).l();

            List<String> allergens = new ArrayList<String>();
            List<String> diets = new ArrayList<String>();
            for (AttributeValue index : dietsAttributeValues) {
                diets.add(index.s());
            }
            diet = new Diet(allergens, diets);
        } else diet = null;

        return diet;
    }
}
