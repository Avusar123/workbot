package com.workbot.workbot.logic.update.section.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workbot.workbot.data.model.Area;
import com.workbot.workbot.data.model.Company;
import com.workbot.workbot.data.model.dto.VacancyDto;
import com.workbot.workbot.logic.update.section.SectionParser;
import com.workbot.workbot.logic.update.section.util.HttpConstants;
import com.workbot.workbot.logic.update.section.util.ParserException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
public class BeelineParser implements SectionParser {
    @Autowired
    private OkHttpClient okHttpClient;

    @Autowired
    private ObjectMapper objectMapper;

    private final static String JOB_LIST_URL = "https://job.beeline.ru/api/v1/vacancies/?role_id=role_001%2Crole_003%2Crole_002%2Crole_149%2Crole_046%2Crole_148%2Crole_055%2Crole_051%2Crole_147%2Crole_054%2Crole_044%2Crole_057%2Crole_048%2Crole_049%2Crole_150%2Crole_052%2Crole_053%2Crole_050&limit=999999";

    private final static String JOB_BASIC_API_URL = "https://job.beeline.ru/api/v1/vacancies/%s";

    private final static String JOB_BASIC_URL = "https://job.beeline.ru/vacancies/%S";

    @Override
    public Set<VacancyDto> parse() {
        var vacancies = new HashSet<VacancyDto>();

        Request request = new Request.Builder()
                .url(JOB_LIST_URL)
                .addHeader("User-Agent", HttpConstants.USER_AGENT)
                .addHeader("Accept", HttpConstants.ACCEPT)
                .addHeader("Accept-Encoding", "gzip, deflate, br")
                .addHeader("Host", "job.beeline.ru")
                .build();

            try {
                Response response = okHttpClient.newCall(request).execute();

                if (!response.isSuccessful()) {
                    throw new IllegalStateException("Request is not successful");
                }

                String responseBody = Objects.requireNonNull(response.body()).string();

                var vacanciesNode = objectMapper.readTree(responseBody).get("results");

                for (var vacNode : vacanciesNode) {
                    var vacId = Objects.requireNonNull(vacNode.get("id").asText());

                    var vacResult = objectMapper.readTree(
                            Objects.requireNonNull(
                                    okHttpClient.newCall(
                                            new Request.Builder()
                                                    .url(JOB_BASIC_API_URL.formatted(vacId))
                                                    .addHeader("User-Agent", HttpConstants.USER_AGENT)
                                                    .addHeader("Accept", HttpConstants.ACCEPT)
                                                    .addHeader("Accept-Encoding", "gzip, deflate, br")
                                                    .addHeader("Host", "job.beeline.ru")
                                                    .build())
                                            .execute()
                                    )
                                    .body()
                                    .string());

                    var title = Objects.requireNonNull(vacResult.get("name").asText());

                    var description = Objects.requireNonNull(vacResult.get("description").asText()).replaceAll("<.*?>", "");

                    var link = JOB_BASIC_URL.formatted(vacId);

                    var vacancy = new VacancyDto(
                            title,
                            description,
                            link,
                            LocalDateTime.now(),
                            Area.IT,
                            Company.BEELINE
                    );

                    vacancies.add(vacancy);
                }

                return vacancies;

            } catch (Exception e) {
                throw new ParserException(this, e);
            }
    }

    @Override
    public Area getArea() {
        return Area.IT;
    }

    @Override
    public Company getCompany() {
        return Company.BEELINE;
    }
}
