package store.utils;

public class CountParser implements Parser<Integer>{
    public Integer parse(String rawCount){
        // 유효성 검증 로직 추가 필요
        return Integer.parseInt(rawCount);
    }
}
