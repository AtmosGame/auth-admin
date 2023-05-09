package id.ac.ui.cs.advprog.authenticationandadministration.service.report;

import id.ac.ui.cs.advprog.authenticationandadministration.dto.report.DetailReportedResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.report.RejectReportResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.report.ReportedAccountResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.report.ReportDoesNotExistException;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.report.UserAndReportNotMatchedException;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.report.UserDoesNotHaveReportException;
import id.ac.ui.cs.advprog.authenticationandadministration.models.Report;
import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.User;
import id.ac.ui.cs.advprog.authenticationandadministration.repository.ReportRepository;
import id.ac.ui.cs.advprog.authenticationandadministration.repository.UserRepository;
import id.ac.ui.cs.advprog.authenticationandadministration.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final UserRepository userRepository;
    private final ReportRepository reportRepository;
    private final UserService userService;

    @Override
    public ReportedAccountResponse getAllReportedAccount() {
        return ReportedAccountResponse.builder()
                .listUser(userRepository.findAllHaveReportedUser())
                .build();
    }

    @Override
    public DetailReportedResponse getReportedAccount(String username) {
        User user = getUserReport(username);
        return DetailReportedResponse.builder()
                .username(username)
                .totalReports(user.getReportList().size())
                .listReports(user.getReportList())
                .build();
    }

    @Override
    public String approveReport(String username) {
        User user = getUserReport(username);
        userRepository.blockedUserByUsername(username);
        reportRepository.deleteAll(user.getReportList());
        return "Blocked User with username " + username;
    }

    @Override
    public RejectReportResponse rejectReport(String username, Integer report_id) {
        User user = getUserReport(username);

        if (getReportById(report_id).getUser() != user)
            throw new UserAndReportNotMatchedException(username, report_id);

        reportRepository.deleteById(report_id);
        return RejectReportResponse.builder()
                .haveReport((user.getReportList().size() - 1) > 0)
                .build();
    }

    private User getUserReport(String username){
        User user = userService.getUserNonAdminByUsername(username);

        if (user.getReportList().isEmpty())
            throw new UserDoesNotHaveReportException(username);

        return user;
    }

    private Report getReportById(Integer report_id){
        if (reportRepository.findById(report_id).isEmpty())
            throw new ReportDoesNotExistException(report_id);

        return reportRepository.findById(report_id).get();
    }
}
