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
public class Contributor {
    @NonNull private final String name;
    @NonNull private final String email;
}
