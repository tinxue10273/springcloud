package provider.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @authorgouhuo on 2020/06/24.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SetTagRequest extends BaseRequest {
    private Set<String> tags;
}
