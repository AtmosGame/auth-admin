package id.ac.ui.cs.advprog.authenticationandadministration.service.report;

import id.ac.ui.cs.advprog.authenticationandadministration.core.report.ReportComparator;
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
import id.ac.ui.cs.advprog.authenticationandadministration.service.auth.AuthService;
import id.ac.ui.cs.advprog.authenticationandadministration.service.profile.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final UserRepository userRepository;
    private final ReportRepository reportRepository;
    private final AuthService authService;

    @Override
    public ReportedAccountResponse getAllReportedAccount() {
        Map<User, String> listReportedAccount = new TreeMap<User, String>(new ReportComparator());
        for (User user: userRepository.findAll()){
            if (!user.getRole().name().equals("ADMIN") && user.getActive() && user.getReportList().size() > 0){
                listReportedAccount.put(user, user.getUsername());
            }
        }

        return ReportedAccountResponse.builder()
                .listUser(listReportedAccount.values())
                .build();

//        return ReportedAccountResponse.builder()
//                .listUser(getReportManager().getListReportedAccount())
//                .build();
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
        user.setActive(false);
        userRepository.save(user);
        reportRepository.deleteAll(user.getReportList());
//        getReportManager().approveReport(user);
        return "Blocked User with username " + username;
    }

    @Override
    public RejectReportResponse rejectResponse(String username, Integer report_id) {
        User user = getUserReport(username);

        if (getReportById(report_id).getUser() != user)
            throw new UserAndReportNotMatchedException(username, report_id);

        reportRepository.deleteById(report_id);
//        getReportManager().rejectReport(user, getUserReport(username));
        return RejectReportResponse.builder()
                .haveReport((user.getReportList().size() - 1) > 0 ? true:false)
                .build();
    }

//    private ReportManager getReportManager(){
//        return ReportManager.getInstance(userRepository.findAll());
//    }

    private User getUserReport(String username){
        User user = authService.getUserByUsername(username);
        authService.userValidationNonAdmin(user);

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
