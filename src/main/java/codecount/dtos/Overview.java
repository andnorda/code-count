package codecount.dtos;

import codecount.domain.Language;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.Builder;

import java.util.Collection;
import java.util.Map;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class Overview {
    @NonNull private final String name;
    @NonNull private final String url;
    @NonNull private final Collection<String> branches;
    @NonNull private final Collection<String> tags;
    @NonNull private final Map<Language, Double> languages;
}
