package com.workbot.workbot.logic.update.section.impl;

import com.workbot.workbot.data.model.Area;
import com.workbot.workbot.data.model.Company;
import com.workbot.workbot.data.model.dto.VacancyDto;
import com.workbot.workbot.logic.update.section.SectionParser;
import com.workbot.workbot.logic.update.section.util.HttpConstants;
import com.workbot.workbot.logic.update.section.util.ParserException;
import com.workbot.workbot.logic.update.section.util.SSLUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.checkerframework.checker.units.qual.A;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DIsGroupParser implements SectionParser {
    private static final String ALL_VACANCY_URL = "https://dis-group.ru/vacancy/";

    @Override
    public Set<VacancyDto> parse() {
        Set<VacancyDto> vacancyDtos = new HashSet<>();

        try {
            var result = Jsoup
                    .connect(ALL_VACANCY_URL)
                    .sslSocketFactory(SSLUtil.trustAllFactory())
                    .followRedirects(true)
                    .get();

            var links = result
                    .getElementsByClass("library-item__button")
                    .eachAttr("href");

            for (var link : links) {
                var vacResult = Jsoup
                        .connect(link)
                        .followRedirects(true)
                        .sslSocketFactory(SSLUtil.trustAllFactory())
                        .get();

                var title = vacResult
                        .getElementsByClass("breadcrumb_last")
                        .first()
                        .text();

                var features = String.join("\n", vacResult
                        .getElementsByClass("vacancy-detail")
                        .select(".vacancy-feature__description")
                        .eachText()) + "\n";

                var details = String.join("\n", vacResult
                        .select(".questions-list li")
                        .eachText());

                vacancyDtos.add(
                        new VacancyDto(
                                title,
                                features + details,
                                link,
                                LocalDateTime.now(),
                                Area.IT,
                                Company.DIS_GROUP
                        )
                );
            }

        } catch (Exception e) {
            throw new ParserException(this, e);
        }

        return vacancyDtos;
    }

    @Override
    public Area getArea() {
        return Area.IT;
    }

    @Override
    public Company getCompany() {
        return Company.DIS_GROUP;
    }
}
