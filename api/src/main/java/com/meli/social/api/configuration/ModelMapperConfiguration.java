package com.meli.social.api.configuration;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NameTokenizers;
import org.modelmapper.module.jsr310.Jsr310Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Configuration
public class ModelMapperConfiguration {
  @Bean
  public ModelMapper modelMapper() {
    ModelMapper mapper = new ModelMapper();

    mapper
      .getConfiguration()
      .setMatchingStrategy(MatchingStrategies.STRICT);

    return mapper;
  }

  @Bean
  public ModelMapper queryModelMapper() {
    ModelMapper mapper = new ModelMapper();

    mapper
      .getConfiguration()
      .setMatchingStrategy(MatchingStrategies.STRICT)
      .setSourceNameTokenizer(NameTokenizers.UNDERSCORE)
      .setDestinationNameTokenizer(NameTokenizers.CAMEL_CASE);

    mapper.registerModule(new Jsr310Module());

    mapper.addConverter(new AbstractConverter<LocalDateTime, Instant>() {
      @Override
      protected Instant convert(LocalDateTime source) {
        return source.toInstant(ZoneOffset.UTC);
      }
    });

    return mapper;
  }
}
