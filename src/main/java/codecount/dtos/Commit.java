package codecount.dtos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Builder;
import lombok.NonNull;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class Commit {
    @NonNull private final String hash;
    @NonNull private final Long timestamp;
}
