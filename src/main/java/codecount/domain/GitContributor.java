package codecount.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.Builder;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class GitContributor {
    @NonNull private final String name;
    @NonNull private final String email;
}
