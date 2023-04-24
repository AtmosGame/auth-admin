package id.ac.ui.cs.advprog.authenticationandadministration.service.report;

import id.ac.ui.cs.advprog.authenticationandadministration.core.report.ReportManager;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.report.DetailReportedResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.report.RejectReportResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.report.ReportedAccountResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.report.ReportDoesNotExistException;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.report.UserAndReportNotMatchedException;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.report.UserDoesNotHaveReportException;
import id.ac.ui.cs.advprog.authenticationandadministration.models.Report;
import id.ac.ui.cs.advprog.authenticationandadministration.models.User;
import id.ac.ui.cs.advprog.authenticationandadministration.repository.ReportRepository;
import id.ac.ui.cs.advprog.authenticationandadministration.repository.UserRepository;
import id.ac.ui.cs.advprog.authenticationandadministration.service.profile.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final UserRepository userRepository;
    private final ReportRepository reportRepository;
    private final ProfileService profileService;

    @Override
    public ReportedAccountResponse getAllReportedAccount() {
        return ReportedAccountResponse.builder()
                .listUser(getReportManager().getListReportedAccount())
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
    public RejectReportResponse rejectResponse(String username, Integer report_id) {
        if (getReportById(report_id).getUser() != getUserReport(username))
            throw new UserAndReportNotMatchedException(username, report_id);

        reportRepository.deleteById(report_id);
        return RejectReportResponse.builder()
                .haveReport(getReportManager().rejectReport(username))
                .build();
    }

    @Override
    public String approveReport(String username) {
        User user = getUserReport(username);
        user.setActive(false);
        userRepository.save(user);
        reportRepository.deleteAll(user.getReportList());
        getReportManager().approveReport(username);
        return "Blocked User with username " + username;
    }

    private ReportManager getReportManager(){
        return ReportManager.getInstance(userRepository.findAll());
    }

    private User getUserReport(String username){
        User user = profileService.getUserByUsername(username);
        profileService.userValidationNonAdmin(user);

        if (!(user.getReportList().size() > 0))
            throw new UserDoesNotHaveReportException(username);

        return user;
    }

    private Report getReportById(Integer report_id){
        if (reportRepository.findById(report_id).isEmpty())
            throw new ReportDoesNotExistException(report_id);

        return reportRepository.findById(report_id).get();
    }
}