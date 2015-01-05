package codecount.dtos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.Builder;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class Commit {
    @NonNull private final String hash;
    @NonNull private final Integer timestamp;
}
