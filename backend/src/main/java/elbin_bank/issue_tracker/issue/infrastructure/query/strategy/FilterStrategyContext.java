package elbin_bank.issue_tracker.issue.infrastructure.query.strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FilterStrategyContext {

    private final List<FilterStrategy> strategies;

    public FilterStrategyContext(List<FilterStrategy> strategies) {
        this.strategies = strategies;
    }

    public SqlAndParams buildSql(String q) {
        List<String> whereClauses = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();

        for (String part : q.trim().split("\\s+")) {
            for (FilterStrategy strat : strategies) {
                if (strat.supports(part)) {
                    whereClauses.add(strat.getSqlPart());
                    params.putAll(strat.getParameters(part));
                }
            }
        }

        if (whereClauses.isEmpty()) {
            return new SqlAndParams("", params);
        }

        return new SqlAndParams(" WHERE " + String.join(" AND ", whereClauses), params);
    }

    public record SqlAndParams(String sql, Map<String, Object> params) {
    }

}
