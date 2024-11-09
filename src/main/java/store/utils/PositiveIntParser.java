package store.utils;

public class PositiveIntParser implements SingleParser<Integer> {
    public Integer parse(String rawCount){
        // 유효성 검증 로직 추가 필요
        return Integer.parseInt(rawCount);
    }
}
