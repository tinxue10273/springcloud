package provider.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @authorgouhuo on 2020/04/27.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataRequest extends BaseRequest {
    private Byte type; // 0 为 UV  1为 DAU
    private Date startDate;
    private Date endDate;
}
