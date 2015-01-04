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
public class FileLineCount {
    @NonNull private final String path;
    @NonNull private final Integer lineCount;
}
