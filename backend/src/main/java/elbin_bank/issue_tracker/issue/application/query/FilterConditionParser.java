package elbin_bank.issue_tracker.issue.application.query;

public class FilterConditionParser {

    public static FilterCriteria parse(String q) {
        boolean isClosed = false; // 최종 결과 매핑
        boolean open = false; // Query 에 state:open 포함여부
        boolean closed = false; // Query 에 state:close 포함여부

        if (q != null && !q.isBlank()) {
            String[] parts = q.split(" ");

            for (String p : parts) {
                if (p.startsWith("state:")) {
                    if (p.equals("state:closed")) {
                        closed = true;
                    }
                    if (p.equals("state:open")) {
                        open = true;
                    }
                }
            }
            if (open && closed) {
                throw new IllegalStateException("Filter conditions cannot be open or open closed");
            }
            isClosed = closed;
        }

        return new FilterCriteria(isClosed);
    }

}
