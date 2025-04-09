package com.workbot.workbot.logic.update.section.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.workbot.workbot.data.model.Area;
import com.workbot.workbot.data.model.Company;
import com.workbot.workbot.data.model.dto.VacancyDto;
import com.workbot.workbot.logic.update.section.SectionParser;
import com.workbot.workbot.logic.update.section.util.HttpConstants;
import com.workbot.workbot.logic.update.section.util.ParserException;
import okhttp3.*;
import org.jsoup.HttpStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TinkoffParser implements SectionParser {
    private static final String VACANCIES_URL = "https://www.tbank.ru/pfpjobs/papi/getVacancies";

    private static final String VACANCIES_BODY_BASE = """
                {
                    "filters": {
                        "city": [
                            "lyuboj-gorod"
                        ],
                        "cityId": [
                            "0c5b2444-70a0-4932-980c-b4dc0d3f02b5"
                        ]
                    },
                    "pagination": {
                        "it": {
                            "offset": %d,
                            "isFinished": false
                        }
                    }
                }""";

    private static final String VACANCY_URL_BASE = "https://hrsites-api-vacancies.tbank.ru" +
            "/vacancies/public/api/platform/v2/getVacancy?urlSlug=%s";

    private static final String VACANCY_CLIENT_URL_BASE = "https://www.tbank.ru/career/it/%s/%s";

    @Autowired
    private OkHttpClient okHttpClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Set<VacancyDto> parse() {
        var vacancies = new HashSet<VacancyDto>();

        int offset = 0;

        JsonNode jsonNode;
        try {
            do {
                jsonNode = parseJson(offset);

                var slugs = extractSlugs(jsonNode);

                for (var slug : slugs) {
                    vacancies.add(parseVacancy(slug));
                }

                offset = getNextOffset(jsonNode);

            } while (hasNext(jsonNode));
        } catch (Exception ex) {
            throw new ParserException(this ,ex);
        }

        return vacancies;
    }

    private JsonNode parseJson(int offset) throws IOException {
        Request request = new Request.Builder()
                .url(VACANCIES_URL)
                .addHeader("User-Agent", HttpConstants.USER_AGENT)
                .addHeader("Accept", HttpConstants.ACCEPT)
                .post(RequestBody.create(VACANCIES_BODY_BASE.formatted(offset), MediaType.get("application/json")))
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IllegalStateException("Request was not successful!");
            }

            return objectMapper.readTree(Objects.requireNonNull(response.body()).string());

        }
    }


    private boolean hasNext(JsonNode vacNode) {
        return !vacNode.get("payload")
                .get("nextPagination")
                .get("it")
                .get("isFinished")
                .asBoolean();
    }

    private int getNextOffset(JsonNode vacNode) {
        return vacNode.get("payload")
                .get("nextPagination")
                .get("it")
                .get("offset")
                .asInt();
    }

    private Set<String> extractSlugs(JsonNode vacNode) {
        Set<String> slugs = new HashSet<>();

        vacNode.get("payload").get("vacancies").forEach(
                vac -> slugs.add(vac.get("urlSlug").asText()));

        return slugs;
    }

    private VacancyDto parseVacancy(String slug) throws IOException {
        Request request = new Request.Builder()
                .url(VACANCY_URL_BASE.formatted(slug))
                .addHeader("User-Agent", HttpConstants.USER_AGENT)
                .addHeader("Accept", HttpConstants.ACCEPT)
                .get()
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IllegalStateException("Request was not successful!");
            }

            var json = objectMapper.readTree(Objects.requireNonNull(response.body()).string())
                    .get("response");

            var description = String.join("\n",
                    json.get("specialty").asText(),
                            json.get("city").asText(),
                            json.get("experiences")
                                    .findValues("name")
                                    .stream()
                                    .map(JsonNode::asText)
                                    .collect(Collectors.joining("\n")),
                            json.get("tasks").asText(),
                            json.get("responsibilities").asText(),
                            json.get("offer").asText());

            return new VacancyDto(
                    json.get("title").asText(),
                    description,
                    VACANCY_CLIENT_URL_BASE.formatted(json.get("specialtyInfo").get("urlSlug").asText(), slug),
                    LocalDateTime.now(),
                    Area.IT,
                    Company.TINKOFF
            );
        }
    }
    @Override
    public Area getArea() {
        return Area.IT;
    }

    @Override
    public Company getCompany() {
        return Company.TINKOFF;
    }
}
