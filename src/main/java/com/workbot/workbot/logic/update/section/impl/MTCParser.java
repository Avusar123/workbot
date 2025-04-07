package com.workbot.workbot.logic.update.section.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workbot.workbot.data.model.Area;
import com.workbot.workbot.data.model.Company;
import com.workbot.workbot.data.model.dto.VacancyDto;
import com.workbot.workbot.logic.update.section.SectionParser;
import com.workbot.workbot.logic.update.section.util.HttpConstants;
import com.workbot.workbot.logic.update.section.util.ParserException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
public class MTCParser implements SectionParser {
    @Autowired
    private OkHttpClient okHttpClient;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String VACANCIES_URL = "https://job.mts.ru/v1/vacancies/filtered";

    private static final String VACANCY_URL_BASE = "https://job.mts.ru/v1/vacancy/%s";

    private static final String VACANCY_CLIENT_URL_BASE = "https://job.mts.ru/vacancy/%s";

    private static final String REQUEST_BODY = "{\"filters\":{\"main_filter\":{\"id\":\"searchString\",\"label\":\"Поисковая строка\",\"value\":\"\"},\"quick_filters\":[],\"extra_filters\":[{\"filterValues\":[{\"id\":\"60376693377859705\",\"label\":\"Работа в ИТ\",\"selected\":true,\"subfilterValues\":[{\"id\":\"427822716028977248\",\"label\":\"Информационная безопасность\",\"selected\":true},{\"id\":\"60377958136676435\",\"label\":\"Менеджемент\",\"selected\":true},{\"id\":\"60377929682518099\",\"label\":\"Поддержка и администрирование\",\"selected\":true},{\"id\":\"60377876309999688\",\"label\":\"Разработка\",\"selected\":true},{\"id\":\"60377900922175580\",\"label\":\"Тестирование\",\"selected\":true}]}],\"id\":\"category\",\"label\":\"Направление работы\",\"type\":\"dropdown\"}]},\"limit\":99999,\"offset\":0}";

    private static final String XAPIKey = "eyJhbGciOiJFRDI1NTE5IiwidHlwIjoiSldUIn0.eyJDbGFpbXMiOm51bGwsInVzZXJJRCI6NDQyMjQxODE2NTE1ODM5NDQ2LCJ0b2tlbklEIjo0NTEzMjYxNTc3Mjg1MTU1MzB9.wRU3yfdP4sP3_gNaIoJOmwq2L_GFoIdqSoDzxfL-x272p4EMNzoKWB5jSzjVnlwejmO9-yec1Ud8R1MhYAPBAQ";

    @Override
    public Set<VacancyDto> parse() {
        var vacancies = new HashSet<VacancyDto>();

        Request request = new Request.Builder()
                .url(VACANCIES_URL)
                .addHeader("User-Agent", HttpConstants.USER_AGENT)
                .addHeader("Accept", HttpConstants.ACCEPT)
                .addHeader("Content-Type", HttpConstants.CONTENT_TYPE)
                .addHeader("x-api-key", XAPIKey)
                .post(RequestBody.create(REQUEST_BODY, MediaType.get("application/json")))
                .build();

        try {
            var response = okHttpClient.newCall(request).execute();

            if (!response.isSuccessful()) {
                throw new IllegalStateException("Request is not successful");
            }

            var json = objectMapper.readTree(Objects.requireNonNull(response.body()).string());

            var vacanciesJson = json.get("data").get("vacancies");

            for (var vacJson : vacanciesJson) {
                var id = vacJson.get("id").asText();

                var vacRequest = new Request.Builder(request)
                        .url(VACANCY_URL_BASE.formatted(id))
                        .get()
                        .build();

                var vacResult = okHttpClient.newCall(vacRequest).execute();

                if (!vacResult.isSuccessful()) {
                    throw new IllegalStateException("Request is not successful");
                }

                var vacData = objectMapper
                        .readTree(Objects.requireNonNull(
                                vacResult.body())
                                .string())
                        .get("data")
                        .get("vacancy");

                var title = vacData.get("name").asText();

                StringBuilder description = new StringBuilder();

                vacData.get("info").forEach(node -> {
                    description
                            .append(node.get("value").asText())
                            .append("\n");
                });

                vacData.get("detailText").forEach(
                        node -> description
                                .append(node.asText())
                                .append("\n")
                );

                vacancies.add(
                        new VacancyDto(
                                title,
                                description.toString(),
                                VACANCY_CLIENT_URL_BASE.formatted(id),
                                LocalDateTime.now(),
                                Area.IT,
                                Company.MTC
                        )
                );
            }

        } catch (Exception e) {
            throw new ParserException(this, e);
        }

        return vacancies;
    }

    @Override
    public Area getArea() {
        return Area.IT;
    }

    @Override
    public Company getCompany() {
        return Company.MTC;
    }
}
