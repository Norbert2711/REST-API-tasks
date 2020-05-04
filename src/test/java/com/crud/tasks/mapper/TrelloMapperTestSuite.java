package com.crud.tasks.mapper;


import com.crud.tasks.domain.*;
import com.crud.tasks.service.SimpleEmailService;
import lombok.ToString;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TrelloMapperTestSuite {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleEmailService.class);

    @InjectMocks
    private TrelloMapper trelloMapper;

    @Test
    public void mapToBoards() {
        LOGGER.info("Start test: map to Boards");
        //Given
        TrelloListDto trelloListDto = new TrelloListDto("10", "Test List", false);
        List<TrelloListDto> listDtos = Arrays.asList(trelloListDto);

        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("12", "Test Board", listDtos);
        List<TrelloBoardDto> boardDtos = Arrays.asList(trelloBoardDto);

        //When
        List<TrelloBoard> mappedBoard = trelloMapper.mapToBoards(boardDtos);

        //Then
        System.out.println(mappedBoard);

        Assert.assertEquals(boardDtos.get(0).getId(), mappedBoard.get(0).getId());
    }

    @Test
    public void mapToList() {
        LOGGER.info("Start test: map to List");
        //Given
        TrelloListDto trelloList = new TrelloListDto("20", "Test mapping to List", false);
        List<TrelloListDto> lists = Arrays.asList(trelloList);

        //When
        List<TrelloList> mappedList = trelloMapper.mapToList(lists);

        //Then
        System.out.println(mappedList);
        Assert.assertEquals(lists.get(0).getId(), mappedList.get(0).getId());

    }

    @Test
    public void mapToCard() {
        LOGGER.info("Start test: map to Card");
        //Given
        TrelloCardDto trelloCard = new TrelloCardDto("test", "Try mapping to Card", "1", "10");

        //When
        TrelloCard mappedCard = trelloMapper.mapToCard(trelloCard);

        //Then
        System.out.println(mappedCard);
        Assert.assertEquals(trelloCard.getName(), mappedCard.getName());
    }

    @Test
    public void mapToBoardsDto() {
        LOGGER.info("Start test: map to Boards DTO");
        //Given
        TrelloList trelloList = new TrelloList("10", "Test List", false);
        List<TrelloList> listDtos = Arrays.asList(trelloList);

        TrelloBoard trelloBoard = new TrelloBoard("12", "Test Board", listDtos);
        List<TrelloBoard> board = Arrays.asList(trelloBoard);

        //When
        List<TrelloBoardDto> trelloBoardDto = trelloMapper.mapToBoardsDto(board);

        //Given
        System.out.println(trelloBoardDto);
        Assert.assertEquals(board.get(0).getId(), trelloBoardDto.get(0).getId());
    }

    @Test
    public void mapToListDto() {
        LOGGER.info("Start test: map to List DTO");
        //Given
        TrelloList trelloList = new TrelloList("122", "Try mapping to ListDto", false);
        List<TrelloList> mappedListDto = Arrays.asList(trelloList);

        //When
        List<TrelloListDto> trelloListDtos = trelloMapper.mapToListDto(mappedListDto);

        //Then
        System.out.println(trelloListDtos);
        Assert.assertEquals(mappedListDto.get(0).getId(), trelloListDtos.get(0).getId());
    }

    @Test
    public void mapToCardDto(){
        LOGGER.info("Start test: map to Card DTO");
        //Given
        TrelloCard trelloCard = new TrelloCard("test1", "Try mapping to Card DTO", "2", "11");

        //When
        TrelloCardDto mappedCardDto = trelloMapper.mapToCardDto(trelloCard);

        //Then
        System.out.println(mappedCardDto);
        Assert.assertEquals(trelloCard.getName(), mappedCardDto.getName());

    }

}
