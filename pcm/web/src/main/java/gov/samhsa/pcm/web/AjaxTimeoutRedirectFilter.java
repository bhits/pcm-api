package gov.samhsa.pcm.web;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

public class AjaxTimeoutRedirectFilter extends GenericFilterBean {
	// Custom HTTP error code to return for session timeout with Ajax-based
	// request.
	private int customSessionExpiredErrorCode;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		if (req.getSession().getAttribute("SPRING_SECURITY_CONTEXT") == null) {
			if (req.getHeader("x-requested-with") != null
					&& req.getHeader("x-requested-with").equalsIgnoreCase(
							"XMLHttpRequest")) {
				res.setStatus(this.customSessionExpiredErrorCode);
			} else {
				chain.doFilter(req, res);
			}
		} else {
			chain.doFilter(req, res);
		}
	}

	public int getCustomSessionExpiredErrorCode() {
		return customSessionExpiredErrorCode;
	}

	public void setCustomSessionExpiredErrorCode(
			int customSessionExpiredErrorCode) {
		this.customSessionExpiredErrorCode = customSessionExpiredErrorCode;
	}
}
