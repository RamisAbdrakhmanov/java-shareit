package ru.practicum.shareit.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.Map;

@Service
public class ItemRequestClient extends BaseClient {

    private static final String API_PREFIX = "/requests";

    @Autowired
    public ItemRequestClient
            (@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> getItemRequestsAll(Integer from, Integer size, long requesterId) {
        Map<String, Object> parameters = Map.of(
                "from", from,
                "size", size
        );
        return get("/all", requesterId, parameters);
    }

    public ResponseEntity<Object> getItemRequestsForRequester(long requesterId) {
        return get("", requesterId);
    }

    public ResponseEntity<Object> getItemRequest(long itemRequestId, long requesterId) {
        return get("/" + itemRequestId, requesterId);
    }

    public ResponseEntity<Object> addItemRequest(ItemRequestDto itemRequestDto, long requesterId) {
        return post("", requesterId, itemRequestDto);
    }
}
