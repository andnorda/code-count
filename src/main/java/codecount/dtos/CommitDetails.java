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
public class CommitDetails {
    @NonNull private final String committer;
    @NonNull private final Integer insertions;
    @NonNull private final Integer deletions;
    @NonNull private final Integer timestamp;

}
