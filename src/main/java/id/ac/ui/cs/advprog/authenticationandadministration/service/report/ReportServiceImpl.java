package id.ac.ui.cs.advprog.authenticationandadministration.service.report;

import id.ac.ui.cs.advprog.authenticationandadministration.dto.report.DetailReportedResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.report.RejectReportResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.report.ReportedAccountResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.report.UserReportRequest;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.report.*;
import id.ac.ui.cs.advprog.authenticationandadministration.models.Report;
import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.User;
import id.ac.ui.cs.advprog.authenticationandadministration.repository.ReportRepository;
import id.ac.ui.cs.advprog.authenticationandadministration.repository.UserRepository;
import id.ac.ui.cs.advprog.authenticationandadministration.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final UserRepository userRepository;
    private final ReportRepository reportRepository;
    private final UserService userService;

    @Override
    public ReportedAccountResponse getAllReportedAccount() {
        List<String> listUserHaveReport = userRepository.findAllHaveReportedUser();

        return ReportedAccountResponse.builder()
                .listUser(listUserHaveReport)
                .build();
    }

    @Override
    public DetailReportedResponse getReportedAccount(String username) {
        User user = getUserReport(username);
        List<Report> listReportUser = user.getReportList();

        return DetailReportedResponse.builder()
                .username(username)
                .totalReports(listReportUser.size())
                .listReports(listReportUser)
                .build();
    }

    @Async
    @Override
    public CompletableFuture<Void> approveReport(String username) {
        User user = getUserReport(username);
        userRepository.blockUserByUsername(username);

        return CompletableFuture.runAsync(() -> {
            List<Report> listReportUser = user.getReportList();
            reportRepository.deleteAll(listReportUser);
        });
    }

    @Override
    public RejectReportResponse rejectReport(String username, Integer reportId) {
        User user = getUserReport(username);
        List<Report> listReportUser = user.getReportList();
        Report report = getReportById(reportId);

        if (report.getUser() != user)
            throw new UserAndReportNotMatchedException(username, reportId);

        reportRepository.deleteById(reportId);

        return RejectReportResponse.builder()
                .haveReport((listReportUser.size() - 1) > 0)
                .build();
    }

    private User getUserReport(String username) {
        User user = userService.getUserNonAdminByUsername(username);
        List<Report> listReportUser = user.getReportList();

        if (listReportUser.isEmpty())
            throw new UserDoesNotHaveReportException(username);

        return user;
    }

    private Report getReportById(Integer reportId) {
        Optional<Report> report = reportRepository.findById(reportId);

        if (report.isPresent())
            return report.get();
        else
            throw new ReportDoesNotExistException(reportId);
    }

    @Override
    public UserReportRequest createReportUser(String username, String usernameReported , UserReportRequest request) {
        String information = request.getInformation();
        if (information == null || information.trim().isEmpty())
            throw new InformationNullException();
        User user = userService.getUserNonAdminByUsername(usernameReported);


        Report report = Report.builder()
                .information(information)
                .user(user)
                .build();

        reportRepository.save(report);

        User userReporting = userService.getUserNonAdminByUsername(username);


        List<Report> reportedUser = userReporting.getReportList();
        if (reportedUser.stream().anyMatch(o -> usernameReported.equals(o.getUser().getUsername())))
            throw new DuplicateReportException();

        userReporting.getReportList().add(report);
        return UserReportRequest.builder()
                .username(usernameReported)
                .information(information)
                .build();
    }
}
