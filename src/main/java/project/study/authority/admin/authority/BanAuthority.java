package project.study.authority.admin.authority;

import project.study.enums.BanEnum;

public interface BanAuthority {

    void ban(Long memberId, BanEnum banEnum, String banReason);
    void banCancel(Long memberId);
}
