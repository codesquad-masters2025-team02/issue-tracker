package elbin_bank.issue_tracker.issue.domain;

import elbin_bank.issue_tracker.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("issue")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Issue extends BaseEntity {

    @Id
    private Long id;
    private Long authorId;
    private Long milestoneId;
    private String title;
    private String contents;
    private Boolean isClosed;

    public boolean isClosed() {
        return Boolean.TRUE.equals(isClosed);
    }

}
