package codecount.dtos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.Builder;

import java.util.Collection;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class FileInterdependencies {
    @NonNull private final String path;
    @NonNull private final Collection<String> interdependencies;
}
