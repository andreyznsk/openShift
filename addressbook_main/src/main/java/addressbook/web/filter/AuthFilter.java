package addressbook.web.filter;

import addressbook.web.AuthCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

import static org.springframework.util.StringUtils.hasText;

public class AuthFilter extends AbstractPreAuthenticatedProcessingFilter implements AuthenticationUserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(AuthFilter.class);

    private final String loginPdiHeader = "login";

    private final String loginHeader = "pdi";

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        RequestHeader requestHeader = getUserNameHeader(request);
        if (requestHeader.isEmpty()) {
            return getEmptyUserName();
        } else {
            return getHeaderUserName(requestHeader);
        }
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return getUserCredentials(request);
    }

    @Override
    public UserDetails loadUserDetails(Authentication authentication) throws UsernameNotFoundException {
        return new User((String) authentication.getPrincipal(), "", Collections.emptyList());
    }

    private RequestHeader getUserNameHeader(HttpServletRequest request) {
        String loginPdi = request.getHeader(loginPdiHeader);
        if (hasText(loginPdi)) {
            LOG.debug("Found: {} header with value: {}", loginPdiHeader, loginPdi);
        } else {
            loginPdi = getPdiFromAuthProvider(request.getHeader(loginHeader));
        }
        return new RequestHeader(loginPdi);
    }

    private String getPdiFromAuthProvider(String login) {
        return "mock PDI";
    }


    private String getHeaderUserName(RequestHeader header) {
        LOG.debug("AuthenticatedPrincipal from header {}, SberPdiOrLogin: {}", header, header.getPdi());
        return header.getPdi();
    }

    private String getEmptyUserName() {
        LOG.info("AuthenticatedPrincipal is null");
        return null;
    }

    private AuthCredentials getUserCredentials(HttpServletRequest request) {
        final String userName = request.getHeader(loginHeader);
        final String pdi = request.getHeader(loginPdiHeader);
        return new AuthCredentials(userName, pdi);
    }

    private static class RequestHeader {

        private final String pdiValue;


        public RequestHeader(String pdiValue) {
            this.pdiValue = pdiValue;
        }

        String getPdi() {
            if (hasText(pdiValue)) {
                return pdiValue.toLowerCase();
            }
            return null;
        }

        boolean isEmpty() {
            return !hasText(pdiValue);
        }

        @Override
        public String toString() {
            return "RequestHeader{" +
                    ", sberPdiValue='" + pdiValue + '\'' +
                    '}';
        }
    }

}
