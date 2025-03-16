package com.myproject.blog.Security;

import com.myproject.blog.Service.AccountService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtProvider tokenProvider;

    @Autowired
    private AccountService accountService;

    private static String[] WHILELIST = {
            "/",
            "/home",
            "/register",
            "/db-console/**",
            "/login/**",
            "/forgot-password/**",
            "/change-password/**",
            "/about/**",
            "/resources/**",
            "/demo/**" };

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("***Loading jwt authentication filter...");

        String requestURI = request.getRequestURI();
        // ✅ Nếu request thuộc danh sách WHILELIST, bỏ qua JWT Filter
        if (isWhitelisted(requestURI)) {
            System.out.println("Skipping JWT filter for: " + requestURI);
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // Lấy jwt từ request
            String jwt = getJwtFromRequest(request);

            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                System.out.println("***Token was in context: " + jwt);
                // Lấy id user từ chuỗi jwt
                String userName = tokenProvider.getUsernameFromToken(jwt);
                // Lấy thông tin người dùng từ username
                UserDetails userDetails = accountService.loadUserByUsername(userName);
            } else {
                System.out.println("Invalid JWT token.");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token.");
                return;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("❌ JWT Processing Error: " + ex.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error processing JWT.");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        // Kiểm tra xem header Authorization có chứa thông tin jwt không
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // Hàm kiểm tra request có nằm trong danh sách whitelist không
    private boolean isWhitelisted(String requestURI) {
        for (String path : WHILELIST) {
            if (path.startsWith(requestURI)) {
                return true;
            }
        }
        return false;
    }
}

