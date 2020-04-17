package com.crud.tasks.trello.client;

import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TrelloClient {

    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;

    @Value("${trello.app.key}")
    private String trelloAppKey;

    @Value("${trello.app.token}")
    private String trelloToken;

    @Value("${trello.app.username}")
    private String userName;

    @Autowired
    private RestTemplate restTemplate;

    public List<TrelloBoardDto> getTrelloBoards() {

        System.out.println("Trello test 0");

        URI url = UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/members/" + userName + "/boards")

                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloToken)
                .queryParam("userName", userName)
                .queryParam("fields", "name,id")
                .queryParam("lists", "all").build().encode().toUri();

        System.out.println("Trello test after query");

        TrelloBoardDto[] boardsResponse = restTemplate.getForObject(url, TrelloBoardDto[].class);

        if (boardsResponse != null) {
            System.out.println("Trello test after if");
            return Arrays.stream(boardsResponse)
                    .filter(e -> e.equals(e.getId()) & e.equals(e.getName()))
                    .filter(a -> a.equals(a.getDescription()))
                    .collect(Collectors.toList());

        }
        System.out.println("Trello test END");
        return new ArrayList<>();

    }

    public CreatedTrelloCard createNewCard(TrelloCardDto trelloCardDto) {
        return new CreatedTrelloCard();
    }

}
