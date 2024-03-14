package project.study.dto.admin;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class Criteria {

    private final int amount = 1;

    public int getStartNum(int pageNumber) {
        return (pageNumber - 1) * amount + 1;
    }

    public int getEndNum(int pageNumber) {
        return (pageNumber - 1) * amount + amount;
    }

    public int getTotalPage(int totalCnt) {
        return (int) Math.ceil( (totalCnt * 1.0) / (amount * 1.0) );
    }

    public Pageable getPageable(int pageNumber) {
        return PageRequest.of(pageNumber - 1, amount);
    }
}
