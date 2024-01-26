package lw.intern.fetch;

import android.util.JsonReader;
import android.util.JsonToken;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * The DataFetcher class is responsible for fetching data from a specified URL using OkHttp
 * and parsing it into a list of ItemInterface objects.
 */
public class DataFetcher {
    private OkHttpClient client;

    /**
     * This interface is used as callback method to handle data fetch responses and errors.
     */
    public interface DataResponseListener {
        void onDataFetched(List<ItemInterface> data);

        void onError(Exception error);
    }

    public DataFetcher() {
        this.client = new OkHttpClient();
    }

    /**
     * Fetches data from the specified URL and notifies the listener of the result.
     *
     * @param url      The URL from which to fetch data.
     * @param listener The listener to be notified of the fetch result.
     */
    public void fetchData(String url, final DataResponseListener listener) {
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                listener.onError(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    listener.onError(new Exception(response.toString()));
                    return;
                }
                try (JsonReader reader = new JsonReader(new InputStreamReader(response.body().byteStream()))) {
                    List<ItemInterface> items = parseResponse(reader);
                    listener.onDataFetched(items);
                } catch (Exception e) {
                    listener.onError(e);
                }
            }
        });
    }

    /**
     * Parses the JSON response received from the server into a list of ItemInterface objects.
     *
     * @param reader The JsonReader for reading the JSON response.
     * @return A list of ItemInterface objects containing parsed data.
     * @throws IOException If an error occurs during parsing, or id, listId or name is missing
     */
    private List<ItemInterface> parseResponse(JsonReader reader) throws IOException {
        List<ItemInterface> items = new ArrayList<>();
        int id = -1, listId = -1;
        String name = null;
        reader.beginArray();
        while (reader.hasNext()) {
            reader.beginObject();
            while (reader.hasNext()) {
                String propertyName = reader.nextName();
                switch (propertyName) {
                    case "id":
                        id = reader.nextInt();
                        break;
                    case "listId":
                        listId = reader.nextInt();
                        break;
                    case "name":
                        if (reader.peek() == JsonToken.NULL) reader.nextNull();
                        else name = reader.nextString();
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }
            reader.endObject();
            if (name != null && !name.trim().isEmpty()) {
                items.add(new Item(id, listId, name));
            }
            if (id == -1 || listId == -1)
                throw new IOException("id or listId is missing in json file");
        }
        reader.endArray();
        Collections.sort(items);
        return items;
    }
}