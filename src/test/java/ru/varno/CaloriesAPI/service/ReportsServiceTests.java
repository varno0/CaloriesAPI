package ru.varno.CaloriesAPI.service;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.varno.CaloriesAPI.dto.DailyReportDTO;
import ru.varno.CaloriesAPI.exceptions.UserNotFoundException;
import ru.varno.CaloriesAPI.repositories.ClientRepositories;
import ru.varno.CaloriesAPI.repositories.DishRepositories;
import ru.varno.CaloriesAPI.repositories.EatRepositories;
import ru.varno.CaloriesAPI.until.DataUntil;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
public class ReportsServiceTests {

    @Mock
    private EatRepositories eatRepositories;
    @Mock
    private DishRepositories dishRepositories;
    @Mock
    private ClientRepositories clientRepositories;

    @InjectMocks
    private ReportsService reportsService;

    @Test
    @DisplayName("Test get daily report functionality")
    public void givenClientId_whenGetDailyReport_thenReturnDailyReport() {

        BDDMockito.given(clientRepositories.existsById(anyLong())).willReturn(true);
        BDDMockito.given(eatRepositories.findAllByTimestampBetweenAndClient_Id(any(), any(), anyLong()))
                .willReturn(List.of(DataUntil.getEatPersistent()));

        DailyReportDTO dailyReportDTO = reportsService.getDailyReport(Instant.now().toString(), 1L);

        assertThat(dailyReportDTO).isNotNull();
    }

    @Test
    @DisplayName("Test get daily report with not exist client functionality")
    public void givenClientNotExistId_whenGetDailyReport_thenThrowException() {

        BDDMockito.given(clientRepositories.existsById(anyLong())).willReturn(false);

        assertThrows(UserNotFoundException.class,
                () -> reportsService.getDailyReport(Instant.now().toString(), 1L));
    }

}

